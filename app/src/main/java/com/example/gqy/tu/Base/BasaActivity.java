package com.example.gqy.tu.Base;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.gqy.tu.Bean.ImageInfo;
import com.example.gqy.tu.Utile.MoreUtile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mszf on 2017/7/3.
 */

public class BasaActivity extends Activity{
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
                DataResuse=utile.geturl("http://www.dbmeinv.com/dbgroup/show.htm?pager_offset=2");
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
}
