package com.example.gqy.tu.Adapter;

import android.graphics.Bitmap;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.gqy.tu.Base.BasaActivity;
import com.example.gqy.tu.Base.BaseApplication;
import com.example.gqy.tu.Bean.Data;
import com.example.gqy.tu.Bean.Status;
import com.example.gqy.tu.HomeActivity.MainActivity;
import com.example.gqy.tu.R;
import com.example.gqy.tu.Utile.SpannableStringUtils;
import com.example.gqy.tu.Utile.ToastUtils;
import com.example.gqy.tu.Utile.Utils;
import com.example.gqy.tu.data.DataServer;

import java.util.List;

/**
 * 文 件 名: PullToRefreshAdapter
 * 创 建 人: Allen
 * 创建日期: 16/12/24 19:55
 * 邮   箱: AllenCoder@126.com
 * 修改时间：
 * 修改备注：
 */
public class PullToRefreshAdapter2 extends BaseQuickAdapter<Data, BaseViewHolder> {
    public PullToRefreshAdapter2(List<Data> datarecuse) {
        super( R.layout.ulltorefreshadapter2, datarecuse);
    }

    @Override
    protected void convert(BaseViewHolder viewholder, Data item) {

//        Glide.with(mContext).load("http://tnfs.tngou.net/image"+item.img).into((ImageView) viewholder.getView(R.id.imagview));
//        Glide.with(mContext).load("http://tnfs.tngou.net/img"+item.img).into((ImageView) viewholder.getView(R.id.imagview));
        Glide.with(mContext).load(item.url).into((ImageView) viewholder.getView(R.id.imagview));

//        Glide.with(mContext).load(str+str2).into((ImageView) viewholder.getView(R.id.imagview));
    }

//    ClickableSpan clickableSpan = new ClickableSpan() {
//        @Override
//        public void onClick(View widget) {
//            ToastUtils.showShortToast("事件触发了 landscapes and nedes");
//        }
//
//        @Override
//        public void updateDrawState(TextPaint ds) {
//            ds.setColor(Utils.getContext().getResources().getColor(R.color.clickspan_color));
//            ds.setUnderlineText(true);
//        }
//    };


}
