package com.example.gqy.tu.HomeActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.jkyeo.splashview.SplashView;
import com.shizhefei.view.indicator.BannerComponent;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.slidebar.ScrollBar;
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
//    http://www.tngou.net/tnfs/api/list?page=1&rows=10
//    private String mBaseUrl = "http://www.tngou.net/tnfs/api/list?page=";
//        +"1"+"&rows=10";


    private String zufangba="http://bj.58.com/hezu/29718626855724x.shtml?from=1-list-12&iuType=p_0&PGTID=0d3090a7-0000-18c3-c1f8-30a7d8832204&ClickID=7";

    List<Data> data, datalod;

    private int page = 1;
    private BannerComponent bannerComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pulltorefresh);
        context = PullToRefreshUseActivity.this;
        getlodedata(page);
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
            if (BaseApplication.initData != null) {
                data = BaseApplication.initData;
                initAdapter();
                addHeadView();
            } else {
                getdata();
            }

        } else {
            Toast.makeText(context, "网络被强盗拿走了请报官", Toast.LENGTH_SHORT).show();
        }

        setTitle("LOOK");
        //        setBackBtn();
//        initAdapter();
//        addHeadView();
        // call after setContentView(R.layout.activity_sample);
        SplashView.showSplashView(this, 6, R.mipmap.welcom, new SplashView.OnSplashViewActionListener() {
            @Override
            public void onSplashImageClick(String actionUrl) {
                Log.d("SplashView", "img clicked. actionUrl: " + actionUrl);
//                Toast.makeText(context, "img clicked.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSplashViewDismiss(boolean initiativeDismiss) {
                Log.d("SplashView", "dismissed, initiativeDismiss: " + initiativeDismiss);
            }
        });
//        String url = "http://img1.126.net/channel6/2015/ad/2_1224a.jpg";
//        String url = "http://ww2.sinaimg.cn/large/72f96cbagw1f5mxjtl6htj20g00sg0vn.jpg";

//        String url = "http://tnfs.tngou.net/image/ext/161127/38b16f1831bb3ea6a44759371d070985.jpg";
        String url = "http://tnfs.tngou.net/image/ext/161111/0dafade966935c6fec5ddfef666a7003.jpg";
//        String url = "http://ws1.sinaimg.cn/large/610dc034ly1fhhz28n9vyj20u00u00w9.jpg";
        // call this method anywhere to update splash view data
        SplashView.updateSplashData(this, url, "http://jkyeo.com");

    }


    private void addHeadView() {
        View headView = getLayoutInflater().inflate(R.layout.head_view, (ViewGroup) mRecyclerView.getParent(), false);
//        headView.findViewById(R.id.iv).setVisibility(View.GONE);
        /*((TextView) headView.findViewById(R.id.tv)).setText("change load view");*/


        ViewPager viewPager = (ViewPager) headView.findViewById(R.id.banner_viewPager);
        Indicator indicator = (Indicator) headView.findViewById(R.id.banner_indicator);
        indicator.setScrollBar(new ColorBar(getApplicationContext(), Color.GRAY, 0, ScrollBar.Gravity.CENTENT_BACKGROUND));
        viewPager.setOffscreenPageLimit(2);

        bannerComponent = new BannerComponent(indicator, viewPager, false);
        bannerComponent.setAdapter(adapter);




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
        //默认就是800毫秒，设置单页滑动效果的时间
//        bannerComponent.setScrollDuration(800);
        //设置播放间隔时间，默认情况是3000毫秒
        bannerComponent.setAutoPlayTime(2500);
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

//        String url = mBaseUrl + page + "" +"&rows=10";
        String url = mBaseUrl + page + "";

        System.out.println("urlurl:" + url);
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
            Log.e(TAG, "onResponse：complete" + response);
            data = Utils.stringToList(response, Data.class);
            System.out.println("data:" + data.size());
            initAdapter();
            addHeadView();
        }
    }

    public void getlodedata(int page) {
//        String url = mBaseUrl + page + "" +"&rows=10";;
        String url = mBaseUrl + page;

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
            Log.e(TAG, "onResponse：complete" + response);


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
            if (BaseApplication.initData != null) {
                BaseApplication.initData.clear();
            }

            finish();
            overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right);
            return false;
        }
        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();
        bannerComponent.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        bannerComponent.stopAutoPlay();
    }

    private int[] images = { R.mipmap.p2, R.mipmap.p3, R.mipmap.p4,R.mipmap.banner1};
    private IndicatorViewPager.IndicatorViewPagerAdapter adapter = new IndicatorViewPager.IndicatorViewPagerAdapter() {

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = new View(container.getContext());
            }
            return convertView;
        }

        @Override
        public View getViewForPage(final int position, View convertView, final ViewGroup container) {
            if (convertView == null) {
                convertView = new ImageView(getApplicationContext());
                convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
            ImageView imageView = (ImageView) convertView;
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(images[position]);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    if (position==0){
                        intent.putExtra("url",zufangba);
                        intent.putExtra("title","租房吧");
                        intent.setClass(context,   WebActivity.class);
                        Toast.makeText(context,"你点击了"+position,Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        //设置切换动画，从右边进入，左边退出,带动态效果
                        overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
//                        Toast.makeText(context,"你点击了"+position,Toast.LENGTH_SHORT).show();
                    }else if (position==1){
                         intent.putExtra("url",zufangba);
                        intent.putExtra("title","租房吧");
                        intent.setClass(context,   WebActivity.class);
                        Toast.makeText(context,"你点击了"+position,Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        //设置切换动画，从右边进入，左边退出,带动态效果
//                        overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
                    }else {
                        intent.putExtra("url",zufangba);
                        intent.putExtra("title","租房吧");
                        intent.setClass(context,   WebActivity.class);
//                        Toast.makeText(context,"你点击了"+position,Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        //设置切换动画，从右边进入，左边退出,带动态效果
                        overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
                    }
                }
            });
            return convertView;
        }



//        @Override
//        public int getItemPosition(Object object) {
//            return RecyclingPagerAdapter.POSITION_NONE;
//        }

        @Override
        public int getCount() {
            return images.length;
        }
    };
}
