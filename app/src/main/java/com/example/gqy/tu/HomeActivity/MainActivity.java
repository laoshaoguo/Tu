package com.example.gqy.tu.HomeActivity;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.gqy.tu.Base.BasaActivity;
import com.example.gqy.tu.Base.BaseApplication;
import com.example.gqy.tu.Bean.ImageInfo;
import com.example.gqy.tu.R;
import com.example.gqy.tu.Task.InitTask;
import com.example.gqy.tu.Utile.MoreUtile;

import java.net.URL;
import java.util.List;

public class MainActivity extends BasaActivity implements BasaActivity.Indatacall {

    private ImageView image,image2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        initdata(MainActivity.this);
    }

private void initview(){

    image=(ImageView) findViewById(R.id.image);
    image2=(ImageView) findViewById(R.id.image2);
}

Handler myhandler = new Handler(){

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (msg.arg1==1){
            Glide.with(MainActivity.this).load(BasaActivity.DataResuse.get(2).getImgUrl()).into(image);
            Glide.with(MainActivity.this).load(BasaActivity.DataResuse.get(8).getImgUrl()).into(image2);
        }
    }
};

    @Override
    public void suceess() {
        Message message = new Message();
        message.arg1=1;
        myhandler.sendMessage(message);
    }

    @Override
    public void error() {

    }
}
