package com.p2p.dsad.sixplayler;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lidroid.xutils.exception.DbException;
import com.p2p.dsad.sixplayler.appliction.MyAppLiction;
import com.p2p.dsad.sixplayler.base.BaseActivity;
import com.p2p.dsad.sixplayler.bean.UserBean;
import com.p2p.dsad.sixplayler.fragment.AnimationFragment;
import com.p2p.dsad.sixplayler.fragment.MovieFragment;
import com.p2p.dsad.sixplayler.fragment.OtherFragment;
import com.p2p.dsad.sixplayler.fragment.TeleplayFragment;
import com.p2p.dsad.sixplayler.utils.Contacts;
import com.p2p.dsad.sixplayler.utils.UIUtils;
import com.p2p.dsad.sixplayler.utils.UniCoderUtils;
import com.p2p.dsad.sixplayler.wegit.RunTextView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends BaseActivity {

    @Bind(R.id.tv_title_title)
    TextView tvTitleTitle;
    @Bind(R.id.img_title_userlogo)
    CircleImageView imgTitleUserlogo;
    @Bind(R.id.tv_title_username)
    TextView tvTitleUsername;
    @Bind(R.id.magic_home_indecator)
    MagicIndicator magicHomeIndecator;
    @Bind(R.id.view_home_pagers)
    ViewPager viewHomePagers;
    @Bind(R.id.tool_title_bar)
    Toolbar toolTitleBar;
    @Bind(R.id.tv_home_runtext)
    RunTextView  tvHomeRuntext;
    public static RunTextView HomeRuntext;
    private List<Fragment> allfragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private MyviewpagerAdpter viewpager_adpter;
    private UserBean userbean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        inintdata();
        inintui();
    }


    private void inintdata() {
        HomeRuntext = tvHomeRuntext;
        setSupportActionBar(toolTitleBar);
        toolTitleBar.inflateMenu(R.menu.leftmenu_item);
        //关闭标题
        getSupportActionBar().setDisplayShowTitleEnabled(false);


    }

    private void inintui() {
        //获取所有的title
        titles = UIUtils.getTabTitles(R.array.TabTitles);
        //初始化fragment
        inintfragment();
        //设置viewpager
        confingviewpager();
        //设置indicator
        configIndicator();
        //设置用户的相关事宜
        configUser();
    }

    private void configUser() {
        try {
            userbean = MyAppLiction.getUserdb().findFirst(UserBean.class);
            if (userbean != null) {
                if (!TextUtils.isEmpty(userbean.getUsername()) && !TextUtils.isEmpty(userbean.getUserimg())) {
                    String img = userbean.getUserimg().substring(0, userbean.getUserimg().length() - 1);
                    String user = userbean.getUsername().substring(1, userbean.getUsername().length());
                    Glide.with(this).load(Contacts.USER_IMG + img).into(imgTitleUserlogo);
                    tvTitleUsername.setText(UniCoderUtils.decodeUnicode(user));
                }
            } else {
                tvTitleUsername.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                    }
                });
            }

        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    private void confingviewpager() {
        viewpager_adpter = new MyviewpagerAdpter(getSupportFragmentManager());
        viewHomePagers.setAdapter(viewpager_adpter);
        viewHomePagers.setOffscreenPageLimit(viewpager_adpter.getCount() - 1);
    }

    private void inintfragment() {
        allfragments.add(new MovieFragment());
        allfragments.add(new AnimationFragment());
        allfragments.add(new TeleplayFragment());
        allfragments.add(new OtherFragment());


    }

    private void configIndicator() {
        CommonNavigator nav = new CommonNavigator(this);
        //设置默认模式
        nav.setAdjustMode(true);
        //设置适配器
        nav.setAdapter(new MyIndicatorAdpter());
        //设置一个item
        magicHomeIndecator.setNavigator(nav);
        //监听viewpager绑定indicator
        viewHomePagers.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                magicHomeIndecator.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                magicHomeIndecator.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                magicHomeIndecator.onPageScrollStateChanged(state);
            }
        });
    }

    /****
     * indicator的适配器
     */
    class MyIndicatorAdpter extends CommonNavigatorAdapter {


        @Override
        public int getCount() {
            return titles.size();
        }

        @Override
        public IPagerTitleView getTitleView(Context context, final int i) {
            ClipPagerTitleView tv = new ClipPagerTitleView(getApplicationContext());
            tv.setTextColor(Color.WHITE);
            tv.setClipColor(Color.WHITE);
            tv.setText(titles.get(i));
            //设置点击事件,让viewpager跳过去
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewHomePagers.setCurrentItem(i);
                }
            });
            return tv;
        }

        @Override
        public IPagerIndicator getIndicator(Context context) {
            LinePagerIndicator indicator = new LinePagerIndicator(getApplicationContext());
            indicator.setColors(Color.WHITE);
            indicator.setLineHeight(3);
            indicator.setLineWidth(5);
            return indicator;
        }
    }

    /***
     * viewpager的适配器
     */
    class MyviewpagerAdpter extends FragmentPagerAdapter {


        public MyviewpagerAdpter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return allfragments.get(position);
        }

        @Override
        public int getCount() {
            return allfragments.size();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public static RunTextView getHomeRuntext() {
        return HomeRuntext;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //这里调下方法
        configUser();
    }
}
