package com.p2p.dsad.sixplayler.appliction;

import android.app.Application;
import android.content.Context;
import android.media.MediaPlayer;

import com.lidroid.xutils.DbUtils;
import com.p2p.dsad.sixplayler.R;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import io.vov.vitamio.Vitamio;
import okhttp3.OkHttpClient;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * 自定义全局app域
 * Created by dsad on 2017/9/4.
 */

public class MyAppLiction extends Application
{
    private static Context mContext;
    private MediaPlayer mediaPlayer;
    private static MyAppLiction instance = null;
    private static DbUtils userdb;
    private static DbUtils.DaoConfig cofing;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mContext = getApplicationContext();
        inintOkHttp();
        inintvitamio();
        inintdb();


    }

    private void inintdb()
    {
        cofing = new DbUtils.DaoConfig(this);
        cofing.setDbName("user");
        userdb = DbUtils.create(cofing);

    }

    public static MyAppLiction getInstance()
    {
        return  instance ;
    }

    /**
     *初始化视频播放器
     */
    private void inintvitamio()
    {
        Vitamio.isInitialized(getApplicationContext());
    }

    /**
     * 初始化okhttp
     */
    private void inintOkHttp()
    {
        OkHttpClient c = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .build();
        OkHttpUtils.initClient(c);
    }


    /**
     * 获取context
     * @return 当前上下文对象
     */
    public static Context getmContext()
    {
        return mContext;
    }

    public static int getMianid()
    {
        return android.os.Process.myTid();
    }

    public static DbUtils getUserdb() {
        return userdb;
    }
}
