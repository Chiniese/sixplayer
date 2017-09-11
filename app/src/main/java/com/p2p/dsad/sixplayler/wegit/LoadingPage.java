package com.p2p.dsad.sixplayler.wegit;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.p2p.dsad.sixplayler.R;
import com.p2p.dsad.sixplayler.utils.ConnectionUtils;
import com.p2p.dsad.sixplayler.utils.UIUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * 自定义的加载布局切换类
 * 这个类是BaseFragmet要继承的
 * Created by dsad on 2017/9/5.
 */

public abstract class LoadingPage extends FrameLayout implements View.OnClickListener
{

    public final static int LOADING_STATE = 0;
    public final static int ERROR_STATE = 1;
    public final static int EMPTY_STATE = 2;
    public final static int SUCCESS_STATE = 3;
    //以上为布局状态
    //当前默认为加载状态(0)
    public int nowstate = 0;
    private View loadingview;
    private View errorview;
    private View emptyview;
    private View successview;
    public LayoutParams params;
    private ImageView loadgif;
    private ImageView errorimg;
    private ResultData result;
    private String url;
    public LoadingPage(@NonNull Context context) {
        super(context);
        inint();
    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inint();
    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inint();
    }
    private void inint()
    {
        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (loadingview==null)
        {
            loadingview = View.inflate(getContext(), R.layout.loadpage,null);
            loadgif = loadingview.findViewById(R.id.img_loading_gif);
            //加载gif(已用其他框架替换)
            //Glide.with(getContext()).load(R.drawable.loadimg2).into(loadgif);
            //添加进去
            addView(loadingview,params);

        }
        if (errorview==null)
        {
            errorview = View.inflate(getContext(),R.layout.errorpage,null);
            errorimg = errorview.findViewById(R.id.img_error_loaderror);
            errorimg.setOnClickListener(this);
            addView(errorview,params);
        }
        if (emptyview==null)
        {
            emptyview = View.inflate(getContext(),R.layout.emptypage,null);
            addView(emptyview,params);
        }
        //在主线程正常显示布局
        showSafePage();
        //联网操作在这里
        getData();
    }

    //成功的视图是不会显示在这里的
    private void showSafePage()
    {
        //保证在主线程运行把显示ui的视图方法包裹起来
        UIUtils.RunOnMainThread(new Runnable() {
            @Override
            public void run() {
                showPage();
            }
        });

    }


    public void showPage()
    {

        initsuccessview();
        loadingview.setVisibility(nowstate==LOADING_STATE?VISIBLE:GONE);
        emptyview.setVisibility(nowstate==EMPTY_STATE?VISIBLE:GONE);
        errorview.setVisibility(nowstate==ERROR_STATE?VISIBLE:GONE);
        successview.setVisibility(nowstate==SUCCESS_STATE?VISIBLE:GONE);

    }
    private void initsuccessview()
    {
        if (successview==null)
        {
            //这里由子类去加载
            successview = UIUtils.getView(getLayoutID());
            addView(successview,params);
        }
    }

    protected abstract int getStateByChild();

    protected abstract int getLayoutID();

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.img_error_loaderror:
                //重新加载数据
                nowstate = LOADING_STATE;
                inint();
                break;

        }
    }
    //联网操作
    public void getData()
    {
        url = getUrl();

        if (!TextUtils.isEmpty(url))
        {
            ConnectionUtils.getData(getUrl(), new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    //失败
                    result = result.ERROR;
                    result.setContent("");
                    loadview();
                }

                @Override
                public void onResponse(String response, int id) {
                    //成功
                    if (TextUtils.isEmpty(response))
                    {
                        result = result.EMPTY;
                        result.setContent("");
                    }
                    else
                    {
                        result = result.SUCCESS;
                        result.setContent(response);
                    }
                    loadview();
                }
            });
        }
        else
        {
            //如果在url为空的情况下
            //提供给子类直接设置页面状态(默认0)
            int childstate = getStateByChild();
            if (childstate==SUCCESS_STATE)
            {
                nowstate=SUCCESS_STATE;
                //单独加载子类成功页面
                initsuccessview();
                result = result.SUCCESS;
                result.setContent("");
                loadview();
                return;
            }
            result = result.EMPTY;
            result.setContent("");
            loadview();
        }


    }

    private void loadview()
    {
        switch (result)
        {
            case EMPTY:
                nowstate = EMPTY_STATE;
                break;
            case SUCCESS:
                nowstate = SUCCESS_STATE;
                break;
            case ERROR:
                nowstate = ERROR_STATE;
                break;
        }
        showSafePage();
        if (nowstate==SUCCESS_STATE)
        {
            //提供数据给子类
            onSuccess(result,successview);
        }

    }

    protected abstract void onSuccess(ResultData result, View successview);

    protected abstract String getUrl();

    //创建一个枚举类
    public enum ResultData
    {
        LOADING(0),ERROR(1),EMPTY(2),SUCCESS(3);
        int state;
        ResultData(int state)
        {
            this.state=state;
        }
        private String content;

        public void setContent(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }
    }
}
