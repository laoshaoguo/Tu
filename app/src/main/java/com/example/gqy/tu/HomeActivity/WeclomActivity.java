package com.example.gqy.tu.HomeActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.gqy.tu.Base.BaseApplication;
import com.example.gqy.tu.Bean.Data;
import com.example.gqy.tu.R;
import com.example.gqy.tu.Utile.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Request;


/**
 * Created by mszf on 2017/7/7.
 */

public class WeclomActivity extends Activity {

    private String mBaseUrl = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.acticity_weclomactivity);
        if (Utils.isNetworkAvailable(WeclomActivity.this)) {
            getdata();
        }else {
            Toast.makeText(WeclomActivity.this,"网络被强盗拿走了请报官",Toast.LENGTH_SHORT).show();
            Integer time = 3000;    //设置等待时间，单位为毫秒
            Handler handler = new Handler();
            //当计时结束时，跳转至主界面
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(WeclomActivity.this, PullToRefreshUseActivity.class));
                    //设置切换动画，从右边进入，左边退出,带动态效果
                    overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
                    WeclomActivity.this.finish();
//                  返回动画
//                overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right);

                }
            }, time);
        }

    }
    //获取数据
    public void getdata() {
        String url = mBaseUrl + 1+ "";
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
            startActivity(new Intent(WeclomActivity.this, PullToRefreshUseActivity.class));
            overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
            WeclomActivity.this.finish();
        }

        @Override
        public void onResponse(String response, int id) {
            if( BaseApplication.initData!=null){
                BaseApplication.initData.clear();
            }
            BaseApplication.initData = Utils.stringToList(response, Data.class);
            startActivity(new Intent(WeclomActivity.this, PullToRefreshUseActivity.class));
            overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
            WeclomActivity.this.finish();

        }
    }
}

