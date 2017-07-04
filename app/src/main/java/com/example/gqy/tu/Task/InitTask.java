package com.example.gqy.tu.Task;


import android.os.AsyncTask;

import com.example.gqy.tu.Base.BasaActivity;
import com.example.gqy.tu.Base.BaseApplication;
import com.example.gqy.tu.Bean.ImageInfo;
import com.example.gqy.tu.Utile.MoreUtile;

import java.net.URL;
import java.util.List;

/**
 * Created by mszf on 2017/7/3.
 */
//String URL,Intere,List<ImageInfo> data
public class InitTask extends AsyncTask<URL, Integer, List<ImageInfo>> {
    private MoreUtile utile;

    @Override
    protected List<ImageInfo> doInBackground(URL... urls) {
        List<ImageInfo> data = utile.geturl(urls.toString());
        return data;
    }

    @Override
    protected void onPostExecute(List<ImageInfo> imageInfos) {
        super.onPostExecute(imageInfos);
        if (null != imageInfos) {
            BasaActivity.DataResuse = imageInfos;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        utile = new MoreUtile();
    }
};
