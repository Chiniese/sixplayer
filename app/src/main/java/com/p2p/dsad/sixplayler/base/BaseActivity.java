package com.p2p.dsad.sixplayler.base;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.p2p.dsad.sixplayler.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * 所有activity的
 * Created by dsad on 2017/9/4.
 */

public class BaseActivity extends AppCompatActivity
{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        inintTTf();
        ininttoolbar();
    }

    private void ininttoolbar()
    {

    }

    /***
     * 设置全局字体
     */
    private void inintTTf()
    {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/trunk.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    //重写
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}
