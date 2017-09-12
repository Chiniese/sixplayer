# sixplayer
# 前排！
# 视频播放带简单的登录校验
# 网页地址:www.sixnosix.cn
----
### ps:已经过网站所属者允许特此开发学习(公司里的那个项目抱歉不能放上来)。所以这里就放上自己的（已经过允许）
> 1.自定义了loadpage来实现三种情况的布局(加载成功,失败,空数据),也就是继承了一个layout,将其设定为抽象类必须的参数都是要在实现的类(fragment)里去提供,比如联网操作所需的url,以及成功后传递给抽象方法,由实现类去获取。
<pre><code>

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
</pre></code>
>2.也利用了CoordinatorLayout,谷歌粑粑推出的新控件!来隐藏toobar和视频图文介绍以及floatactionbar的沉浮
![Image text](https://github.com/Aoyihala/img/raw/master/3.gif)
---
![Image text](https://github.com/Aoyihala/img/raw/master/4.gif)
---
![Image text](https://github.com/Aoyihala/img/raw/master/5.gif)
---
### ps:仅供学习交流,视频没有提供真实地址,只有用webview播放了,就不放图了--(自定义播放器白写了.....)
