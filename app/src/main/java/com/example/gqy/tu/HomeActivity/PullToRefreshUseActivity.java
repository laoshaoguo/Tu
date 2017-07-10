package com.example.gqy.tu.HomeActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.gqy.tu.Adapter.PullToRefreshAdapter2;
import com.example.gqy.tu.Base.BasaActivity;
import com.example.gqy.tu.Base.BaseApplication;
import com.example.gqy.tu.Bean.Data;
import com.example.gqy.tu.R;
import com.example.gqy.tu.Utile.Utils;
import com.example.gqy.tu.View.CustomLoadMoreView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Request;


/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class PullToRefreshUseActivity extends BasaActivity implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private PullToRefreshAdapter2 pullToRefreshAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    //显示总数
    private static final int TOTAL_COUNTER = 100;

    private static final int PAGE_SIZE = 6;

    private int delayMillis = 1000;
    //加载数据总数
    private int mCurrentCounter = 0;
    //显示加载失败 点击显示更多
    private boolean isErr = true;
    private boolean mLoadMoreEndGone = false;

    private Context context;
    private String mBaseUrl = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/";

    List<Data> data, datalod;

    private int page = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pulltorefresh);
        context = PullToRefreshUseActivity.this;

        initview();

    }

    private void initview() {

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        GridLayoutManager mgr = new GridLayoutManager(this, 3);

        mRecyclerView.setLayoutManager(mgr);
        //这句就是添加我们自定义的分隔线
//        mRecyclerView.addItemDecoration(new RecyclerSpace(6, R.color.colorAccent,0));
        if (Utils.isNetworkAvailable(context)) {
            if(BaseApplication.initData!=null){
                data =BaseApplication.initData;
                initAdapter();
                addHeadView();
            }else{
                getdata();
            }

        }else {
            Toast.makeText(context,"网络被强盗拿走了请报官",Toast.LENGTH_SHORT).show();
        }

        setTitle("LOOK");
        //        setBackBtn();
//        initAdapter();
//        addHeadView();


    }


    private void addHeadView() {
        View headView = getLayoutInflater().inflate(R.layout.head_view, (ViewGroup) mRecyclerView.getParent(), false);
//        headView.findViewById(R.id.iv).setVisibility(View.GONE);
        /*((TextView) headView.findViewById(R.id.tv)).setText("change load view");*/
        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoadMoreEndGone = true;
                pullToRefreshAdapter.setLoadMoreView(new CustomLoadMoreView());
                mRecyclerView.setAdapter(pullToRefreshAdapter);
//                Toast.makeText(PullToRefreshUseActivity.this, "change complete", Toast.LENGTH_LONG).show();
            }
        });
        pullToRefreshAdapter.addHeaderView(headView);
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        mSwipeRefreshLayout.setEnabled(false);
        if (pullToRefreshAdapter.getData().size() < PAGE_SIZE) {
            pullToRefreshAdapter.loadMoreEnd(true);
        } else {
            if (mCurrentCounter >= TOTAL_COUNTER) {
//                    pullToRefreshAdapter.loadMoreEnd();//default visible
                pullToRefreshAdapter.loadMoreEnd(mLoadMoreEndGone);//true is gone,false is visible
            } else {
                if (isErr) {
                    Log.e("page", page + "");
                    getlodedata(page);

                } else {
                    isErr = true;
                    Toast.makeText(PullToRefreshUseActivity.this, R.string.network_err, Toast.LENGTH_LONG).show();
                    pullToRefreshAdapter.loadMoreFail();

                }
            }
            mSwipeRefreshLayout.setEnabled(true);
        }
    }

    @Override
    public void onRefresh() {
        pullToRefreshAdapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pullToRefreshAdapter.setNewData(data);
                isErr = false;
                mCurrentCounter = PAGE_SIZE;
                mSwipeRefreshLayout.setRefreshing(false);
                pullToRefreshAdapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }

    private void initAdapter() {
        pullToRefreshAdapter = new PullToRefreshAdapter2(data);
        pullToRefreshAdapter.setOnLoadMoreListener(this, mRecyclerView);
        pullToRefreshAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
//        pullToRefreshAdapter.setPreLoadNumber(3);
        mRecyclerView.setAdapter(pullToRefreshAdapter);
        mCurrentCounter = pullToRefreshAdapter.getData().size();


        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                String url = pullToRefreshAdapter.getData().get(position).url;
//                Toast.makeText(PullToRefreshUseActivity.this, Integer.toString(position) + "ii", Toast.LENGTH_LONG).show();
//                Log.e("url",url);
                Intent intent = new Intent();
                intent.setClass(context, PhotoBrowse.class);
                intent.putExtra("url", url);
                startActivity(intent);
                //设置切换动画，从右边进入，左边退出,带动态效果
                overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
            }
        });
    }

    //获取数据
    public void getdata() {
        String url = mBaseUrl +1 + "";
        OkHttpUtils
                .get()
                .url(url)
                .id(100)
                .build()
                .execute(new MyStringCallback());

    }


    public class MyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {
//            setTitle("loading...");
        }

        @Override
        public void onAfter(int id) {
//            setTitle("Sample-okHttp");
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
//            mTv.setText("onError:" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {
//            Log.e(TAG, "onResponse：complete" + response);
            data = Utils.stringToList(response, Data.class);
            System.out.println("data:" + data.size());
            initAdapter();
            addHeadView();
        }
    }

    public void getlodedata(int page) {
        String url = mBaseUrl + page + "";
        OkHttpUtils
                .get()
                .url(url)
                .id(100)
                .build()
                .execute(new MylodeStringCallback());

    }

    public class MylodeStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {
//            setTitle("loading...");
        }

        @Override
        public void onAfter(int id) {
//            setTitle("Sample-okHttp");
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
//            mTv.setText("onError:" + e.getMessage());

        }

        @Override
        public void onResponse(String response, int id) {
//            Log.e(TAG, "onResponse：complete" + response);


            datalod = Utils.stringToList(response, Data.class);
            pullToRefreshAdapter.addData(datalod);
            mCurrentCounter = pullToRefreshAdapter.getData().size();
            pullToRefreshAdapter.loadMoreComplete();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
            Toast.makeText(context, "欢迎下次光临", Toast.LENGTH_SHORT).show();
            if( BaseApplication.initData!=null){
                BaseApplication.initData.clear();
            }

            finish();
            overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right);
            return false;
        }
        return true;
    }

}
