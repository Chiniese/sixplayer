package com.p2p.dsad.sixplayler.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.view.View;

import com.p2p.dsad.sixplayler.R;
import com.p2p.dsad.sixplayler.appliction.MyAppLiction;

import java.util.ArrayList;
import java.util.List;

/**
 * ui工具类
 * Created by dsad on 2017/9/4.
 */

public class UIUtils
{
    private UIUtils()
    {

    }

    private static Handler handler;
    public static Handler getHandler() {
        handler=new Handler();
        return handler;
    }
    private static  Context getContext()
    {
        return MyAppLiction.getmContext();
    }


    /**
     * 获取Tab标题
     * @return
     */
    public static List<String> getTabTitles(int id)
    {
        String[] rs = getContext().getResources().getStringArray(id);
        List<String> list = new ArrayList<>();
        for (String s:rs)
        {
            list.add(s);
        }
        return list;
    }


    /**
     * 获取本地版本号
     * @return 版本号
     */
    public static String getVersion()
    {
        String version ="";

        PackageManager manager = getContext().getPackageManager();
        try
        {
            PackageInfo info = manager.getPackageInfo(getContext().getPackageName(),0);
            version = info.versionName;
            return version;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 取得颜色
     * @param colorid 颜色id
     * @return
     */
    public static int getColor(int colorid)
    {
        return getContext().getResources().getColor(colorid);
    }

    /**
     * 获取view
     * @param viewid 视图id
     * @return
     */
    public static View getView(int viewid)
    {
        return View.inflate(getContext(),viewid,null);
    }

    public static void RunOnMainThread(Runnable runnable)
    {
     if (isMainThread())
     {
         runnable.run();
     }
     else
     {
         //在其他线程就提交给主线程
         getHandler().post(runnable);
     }


    }

    /***
     * 判断是否在主线程
     * @return 成功为true 失败为false
     */
    private static boolean isMainThread()
    {
        int mainid = android.os.Process.myTid();
        if (mainid==MyAppLiction.getMianid())
        {
            return true;
        }
        else
        {
            return false;
        }

    }


}
