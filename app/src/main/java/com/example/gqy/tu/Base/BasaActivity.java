package com.example.gqy.tu.Base;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gqy.tu.Bean.ImageInfo;
import com.example.gqy.tu.R;
import com.example.gqy.tu.Utile.MoreUtile;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mszf on 2017/7/3.
 */

public class BasaActivity extends AppCompatActivity {
    public  static List<ImageInfo> DataResuse = new ArrayList<>();
    private MoreUtile utile;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }


    public  void  initdata(final Indatacall indatacall ){
        new Thread(new Runnable() {
            @Override
            public void run() {
                utile = new MoreUtile();
                // http://www.dbmeinv.com/dbgroup/show.htm?pager_offset=2
                // http://www.dbmeinv.com/dbgroup/show.htm
                DataResuse=utile.geturl("http://www.dbmeinv.com/dbgroup/show.htm?pager_offset=1");
//                Log.e("____________",DataResuse.size()+"");
//                for (int i = 0; i < DataResuse.size(); i++) {
//                    Log.e("*********url",DataResuse.get(i).getImgUrl());
//                }
                if (DataResuse.size()>0){
                    indatacall.suceess();
                }else{
                    indatacall.error();
                }
            }
        }).start();

    }






    public interface Indatacall{
        void suceess();
        void error();
    }


    /**
     * 日志输出标志getSupportActionBar().
     **/
    private TextView title;
    private ImageView back;
    protected final String TAG = this.getClass().getSimpleName();

    protected void setTitle(String msg) {
        if (title != null) {
            title.setText(msg);
        }
    }

    /**
     * sometime you want to define back event
     */
    protected void setBackBtn() {
        if (back != null) {
            back.setVisibility(View.VISIBLE);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }else {
            Logger.t(TAG).e("back is null ,please check out");
        }

    }

    protected void setBackClickListener(View.OnClickListener l) {
        if (back != null) {
            back.setVisibility(View.VISIBLE);
            back.setOnClickListener(l);
        }else {
            Logger.t(TAG).e("back is null ,please check out");
        }

    }

    private LinearLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 经测试在代码里直接声明透明状态栏更有效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        // 这句很关键，注意是调用父类的方法
        super.setContentView(R.layout.activity_base);
//        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        if (getSupportActionBar() != null) {
            // Enable the Up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        }
        back = (ImageView) findViewById(R.id.img_back);
        title = (TextView) findViewById(R.id.title);
    }


    @Override
    public void setContentView(int layoutId) {
        setContentView(View.inflate(this, layoutId, null));
    }

    @Override
    public void setContentView(View view) {
        rootLayout = (LinearLayout) findViewById(R.id.root_layout);
        if (rootLayout == null) return;
        rootLayout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initToolbar();
    }
}
