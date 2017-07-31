package com.example.gqy.tu.HomeActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gqy.tu.Base.BasaActivity;
import com.example.gqy.tu.R;


/**
 * Created by ren on 2016/12/7.
 */
public class WebActivity extends Activity implements View.OnClickListener {
    private WebView webView;
    private String url;
    private String title;
    private TextView text_titele;
    private ImageView image_back;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        url=getIntent().getStringExtra("url");
        title=getIntent().getStringExtra("title");
        init();
    }

    private void init() {
        progressBar=(ProgressBar)findViewById(R.id.progressBar);

        image_back = (ImageView) findViewById(R.id.image_back);
        image_back.setOnClickListener(this);
        text_titele = (TextView) findViewById(R.id.text_titele);

        text_titele.setText(title);
        webView = (WebView) findViewById(R.id.webView);
        //启用支持javascript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
//        // 设置可以访问文件
//        settings.setAllowFileAccess(true);
//        // 设置支持缩放
//        settings.setBuiltInZoomControls(true);
//        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        // webSettings.setDatabaseEnabled(true);
//
//        // 使用localStorage则必须打开
//        settings.setDomStorageEnabled(true);
//
//        settings.setGeolocationEnabled(true);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);}
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器

                if (parseScheme(url)) {
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } else {
                    view.loadUrl(url);
                }
                return true;

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

        });

        //WebView加载web资源
//        webView.loadUrl("https://qr.alipay.com/bax00407zaemfqatubzp007b");s
//        webView.loadUrl("http://gateway.ipaysoon.com/unionpay/index.jsp");
//        webView.loadUrl("http://gateway.ipaysoon.com/MSZFPlatform/servicehelper/unionpay?token=27095876-8637-4de8-9506-5539e64f044e");
//        webView.loadUrl("http://blog.csdn.net/ahwr24/article/details/7048724a");
//        webView.loadUrl("https://creditcard.cmbc.com.cn/wsonline/home/homeHZ.jhtml?recommendInfo=JtyjcNYu6ar519JmlRAFP1IWHHE+sfXvHTol02yIYWAjaETFoxpeCwAsgcFok0OK3ZRXs3LtNqHdaEE36ePJ/W8palDy8hHKcPyOYh7yU8zInnzrtFv90gqtdZ53XW3jnM9/EHzIten2y2rJwtN6FBEP6y45B9Qnn4GPwwXvIRG7TYvkotnCBZSPT0/PlNxsSDb2QtgHyHQGGA0bvwpOVLxoH5F2kxGVH0BOFq6RDygWKaA2C7IeTsTGlYAebsxyfbT4xLRLgOJHxnKi0DUwzvuYLaIjrTj82MmUqIXv5/g/MAx4NCdkVq1pSCO3YRvXqvP2H5X4DMHf+zMAll4GGA==&time=1500630804322&time=1500630964465");
//        webView.loadUrl("https://creditcard.cmbc.com.cn/wsv2/?etr=JtyjcNYu6ar519JmlRAFP1IWHHE%20sfXvHTol02yIYWAjaETFoxpeCwAsgcFok0OK3ZRXs3LtNqHdaEE36ePJ/W8palDy8hHKcPyOYh7yU8zInnzrtFv90gqtdZ53XW3jnM9/EHzIten2y2rJwtN6FBEP6y45B9Qnn4GPwwXvIRG7TYvkotnCBZSPT0/PlNxsSDb2QtgHyHQGGA0bvwpOVLxoH5F2kxGVH0BOFq6RDygWKaA2C7IeTsTGlYAebsxyfbT4xLRLgOJHxnKi0DUwzvuYLaIjrTj82MmUqIXv5/g/MAx4NCdkVq1pSCO3YRvXqvP2H5X4DMHf%20zMAll4GGA==&time=1500859654306");
//        webView.loadUrl("https://creditcard.cmbc.com.cn/wsv2/?etr=JtyjcNYu6ar519JmlRAFP1IWHHE%20sfXvHTol02yIYWAjaETFoxpeCwAsgcFok0OK3ZRXs3LtNqHdaEE36ePJ/W8palDy8hHKcPyOYh7yU8zInnzrtFv90gqtdZ53XW3jnM9/EHzIten2y2rJwtN6FBEP6y45B9Qnn4GPwwXvIRG7TYvkotnCBZSPT0/PlNxsSDb2QtgHyHQGGA0bvwpOVLxoH5F2kxGVH0BOFq6RDygWKaA2C7IeTsTGlYAebsxyfbT4xLRLgOJHxnKi0DUwzvuYLaIjrTj82MmUqIXv5/g/MAx4NCdkVq1pSCO3YRvXqvP2H5X4DMHf%20zMAll4GGA==&time=1500631276536");
//        webView.loadUrl("https://creditcard.cmbc.com.cn/wsv2");
        webView.loadUrl(url);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                if (newProgress == 100) {
                    // 网页加载完成
                    Log.e("---------", "加载完成");
                    progressBar.setVisibility(View.GONE);
                } else {
                    // 加载中
                    Log.e("---------", "正在加载..." + newProgress);
                    progressBar.setVisibility(View.VISIBLE);
                }

            }
        });
        //添加下面2行代码来忽略SSL验证
        WebViewClient  mWebviewclient = new WebViewClient(){
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
                handler.proceed();
            }
        };
        webView.setWebViewClient(mWebviewclient);
//        打包混淆需要
      /*  所以还要必要在混淆文件proguard.cfg中，加入以下：
        -keep public class android.net.http.SslError

                -dontwarn android.webkit.WebView
                -dontwarn android.net.http.SslError
                -dontwarn Android.webkit.WebViewClient*/



    }

    private boolean parseScheme(String url) {
//        if(url.contains("https://mapi.alipay.com")) {
//        if(url.contains("qr.alipay.com")) {
        if (url.contains("gateway.ipaysoon.com")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                if (webView.canGoBack()){
                    webView.goBack();// 返回前一个页面
                }else{
                    finish();
                    overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right);
                }

                break;
            default:
                break;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (webView.canGoBack()){
            webView.goBack();// 返回前一个页面
        }else{
            finish();
            overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right);
        }
        return true;
    }
}
