package com.example.gqy.tu.HomeActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.gqy.tu.Base.BasaActivity;
import com.example.gqy.tu.Bean.Data;
import com.example.gqy.tu.R;
import com.example.gqy.tu.Utile.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

public class MainActivity extends BasaActivity implements BasaActivity.Indatacall {

    private ImageView image, image2;
    private String mBaseUrl = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        initdata(MainActivity.this);
    }

    private void initview() {

        image = (ImageView) findViewById(R.id.image);
        image2 = (ImageView) findViewById(R.id.image2);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, PullToRefreshUseActivity.class);
                startActivity(intent);
            }
        });
        getdata("2");
    }

    Handler myhandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 1) {
                Glide.with(MainActivity.this).load(BasaActivity.DataResuse.get(2).getImgUrl()).into(image);
                Glide.with(MainActivity.this).load(BasaActivity.DataResuse.get(8).getImgUrl()).into(image2);

            }
        }
    };

    @Override
    public void suceess() {
        Message message = new Message();
        message.arg1 = 1;
        myhandler.sendMessage(message);
    }

    @Override
    public void error() {

    }
//获取数据
    public void getdata(String page) {
        String url = mBaseUrl + page;
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

           List<Data> data= Utils.stringToList(response,Data.class);
            System.out.println("**********:"+data.get(0).url);

        }
    }




}
