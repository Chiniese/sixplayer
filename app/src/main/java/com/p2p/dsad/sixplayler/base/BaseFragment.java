package com.p2p.dsad.sixplayler.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.exception.DbException;
import com.p2p.dsad.sixplayler.HomeActivity;
import com.p2p.dsad.sixplayler.LoginActivity;
import com.p2p.dsad.sixplayler.appliction.MyAppLiction;
import com.p2p.dsad.sixplayler.bean.UserBean;
import com.p2p.dsad.sixplayler.wegit.FlowTagLayout;
import com.p2p.dsad.sixplayler.wegit.LoadingPage;

import butterknife.ButterKnife;

/**联网操作fragment的基类
 *
 * Created by dsad on 2017/9/5.
 */

public abstract class BaseFragment extends Fragment
{
     protected Context mContext;
     protected Activity mActivity;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity=  getActivity();
        mContext=getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LoadingPage page = new LoadingPage(getContext()) {
            @Override
            protected int getStateByChild() {
                return getState();
            }

            @Override
            protected int getLayoutID() {
                return getchildlayoutid();
            }

            @Override
            protected void onSuccess(LoadingPage.ResultData result, View successview)
            {
                //黄油刀在这里绑定
                ButterKnife.bind(BaseFragment.this,successview);
                setContent(result.getContent());
            }

            @Override
            protected String getUrl() {
                return url();
            }
        };

        return page;
    }

    protected abstract int getState();

    //解析内容
    protected abstract void setContent(String content);
    //url
    protected abstract String url();
    //视图id
    protected abstract int getchildlayoutid();

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
    protected void logindiolog()
    {
        final AlertDialog dialog = new AlertDialog.Builder(mContext)
                .setTitle("消息")
                .setMessage("您还未登陆")
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("登陆", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //跳往登陆的activity
                        startActivity(new Intent(mContext, LoginActivity.class));

                    }
                }).show();
    }
    public boolean isLogin()
    {
        try {
            UserBean bean = MyAppLiction.getUserdb().findFirst(UserBean.class);
            if (bean!=null)
            {
                if (TextUtils.isEmpty(bean.getUsername()))
                {
                    return false;
                }
                return true;
            }
            else
            {
                return false;
            }

        } catch (DbException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

}
