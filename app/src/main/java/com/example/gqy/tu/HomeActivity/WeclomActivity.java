package com.example.gqy.tu.HomeActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.example.gqy.tu.R;


/**
 * Created by mszf on 2017/7/7.
 */

public class WeclomActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.acticity_weclomactivity);
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

