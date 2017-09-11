package com.p2p.dsad.sixplayler.utils;

/**
 *所有链接的工具类
 * Created by dsad on 2017/9/4.
 */

public class Contacts
{
    //测试
    public final static String url = "http://112.253.22.157/17/z/z/y/u/zzyuasjwufnqerzvyxgkuigrkcatxr/hc.yinyuetai.com/D046015255134077DDB3ACA0D7E68D45.flv";
    //服务器
    public final static String BASE_URL="http://www.sixnosix.cn/ashx/";
    //登录
    public final static String LOGIN = BASE_URL+"Login.ashx";
    //修改用户名
    public final static String RESTUSERNAME= BASE_URL+"PersonalMessage.ashx";
    //修改密码
    public final static String RESETPASSWORD = BASE_URL+"ForgetPassWord.ashx";
    //首页
    public final static String HOMEPAGE = BASE_URL+"HomePage.ashx";
    //动漫id
    public final static String ANIMATION_ID = "53d91043-4c9d-4ecb-b10e-876001c8d0e4";
    //电影id
    public final static String MOVIE_ID = "f070d276-f807-408e-8a00-df7cc248ac92";
    //电视剧id
    public final static String TELEPLAY_ID = "0d4e1ba8-6b8d-438e-a7f0-50b68e8fd406";
    //其他id
    public final static String OTHER_ID = "490a0935-0cc6-4943-9ed4-2a87a943c733";
    //登录动作
    public final static String ACTION_LOGIN ="APP";
    //修改用户名action
    public final static String ACTION_RESTUSERNAME ="APPUserName";
    //修改用户密码action
    public final static String ACTION_RESTPASSWORD ="APPwrod";
    //访问首页的Action
    public final static String  ACTION_HOMEPAGE="APPHomePage";
    //直接访问首页拿电影数据
    public final static String GETMOVIEDATA = HOMEPAGE+"?action="+ACTION_HOMEPAGE+"&ID="+MOVIE_ID;
    //直接访问首页拿动画数据
    public final static String GETANIMATION = HOMEPAGE+"?action="+ACTION_HOMEPAGE+"&ID="+ANIMATION_ID;
    //直接访问首页拿电视剧数据
    public final static String GETTELEPLAT = HOMEPAGE+"?action="+ACTION_HOMEPAGE+"&ID="+TELEPLAY_ID;
    //直接访问首页拿其他数据
    public final static String GETOTHER = HOMEPAGE+"?action="+ACTION_HOMEPAGE+"&ID="+OTHER_ID;
    //播放视频的地址
    public final static String PLAY=BASE_URL+"HTMLVideoPlayer.ashx"+"?action=HTML&ID=";
    //登陆的地址
    public final static String LOGIN_USER = LOGIN+"?action="+ACTION_LOGIN+"&";
    //用户头像
    public final static String USER_IMG="http://www.sixnosix.cn/HeadPortraitImages/";
}
