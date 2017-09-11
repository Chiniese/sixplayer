package com.p2p.dsad.sixplayler;

import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.p2p.dsad.sixplayler.bean.VideoData;
import com.p2p.dsad.sixplayler.utils.Contacts;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TruePlayActivity extends AppCompatActivity {

    @Bind(R.id.web_play_view)
    WebView webPlayView;
    private String urlpath;
    private VideoData data;
    private WebSettings ws;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_true_play);
        ButterKnife.bind(this);
        getVideoUrl();
        loadvideo();
    }


    public void getVideoUrl() {
        Intent intent = getIntent();
        data = (VideoData) intent.getSerializableExtra("video");
        urlpath = Contacts.PLAY + data.getVideoId();
    }

    private void loadvideo() {
        ws = webPlayView.getSettings();
        webPlayView.loadUrl(urlpath);
        webPlayView.setWebViewClient(new WebViewClient() {


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //重载url,不用系统浏览器打开
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, android.net.http.SslError error)
            {
                // 重写此方法可以让webview处理https请求
                handler.proceed();
            }
        });
        webPlayView.setWebChromeClient(new WebChromeClient()
        {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress>=20)
                {
                    //加载url中的js

                }
            }

        });
        //设置websetting
        ws.setJavaScriptEnabled(true);
        //WebView默认不支持同时加载Https和Http混合模式。在API>=21的版本上面默认是关闭的，在21以下就是默认开启的，直接导致了在高版本上面http请求不能正确跳转。
        if (Build.VERSION.SDK_INT >= 21)
        {
            ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW );
        }
        //去除白边
        ws.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //mWebView.setBackgroundColor(Color.RED);
        ws.setSupportMultipleWindows(true);
        webPlayView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);//不加上，会显示白边
        ws.setPluginState(WebSettings.PluginState.ON);
        ws.setJavaScriptCanOpenWindowsAutomatically(true);
        ws.setAllowFileAccess(true);
        ws.setDefaultTextEncodingName("UTF-8");
        webPlayView.setBackgroundColor(0);
        webPlayView.setVisibility(View.VISIBLE);



    }

    @Override
    protected void onPause() {
        super.onPause();
        webPlayView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        webPlayView.onResume();
    }
}
