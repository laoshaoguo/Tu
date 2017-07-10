package com.example.gqy.tu.HomeActivity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bm.library.Info;
import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.example.gqy.tu.R;

/**
 * Created by mszf on 2017/7/10.
 */

public class PhotoBrowse extends Activity {


    PhotoView mPhotoView;
    AlphaAnimation out = new AlphaAnimation(1, 0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_photo_browse);

        out.setDuration(300);


        mPhotoView = (PhotoView) findViewById(R.id.img);
//        mPhotoView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        String url = this.getIntent().getStringExtra("url");
        Glide.with(PhotoBrowse.this).load(url).into(mPhotoView);

        mPhotoView.enable();
        mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PhotoBrowse.this.finish();
                mPhotoView.startAnimation(out);

            }
        });
    }
}

