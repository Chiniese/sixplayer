package com.p2p.dsad.sixplayler;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.p2p.dsad.sixplayler.appliction.MyAppLiction;
import com.p2p.dsad.sixplayler.bean.VideoData;
import com.p2p.dsad.sixplayler.fragment.video.VideoCommentFragments;
import com.p2p.dsad.sixplayler.fragment.video.VideoInfoFragments;
import com.p2p.dsad.sixplayler.utils.UIUtils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

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

public class VideoInfoActivity extends AppCompatActivity implements View.OnClickListener {


    @Bind(R.id.img_video_imginfo)
    ImageView imgVideoImginfo;
    @Bind(R.id.coll_video_toolbatlayout)
    CollapsingToolbarLayout collVideoToolbatlayout;
    @Bind(R.id.indicator_video_info)
    MagicIndicator indicatorVideoInfo;
    @Bind(R.id.float_video_play)
    FloatingActionButton floatVideoPlay;
    @Bind(R.id.view_video_pagers)
    ViewPager viewVideoPagers;
    @Bind(R.id.float_video_play2)
    FloatingActionButton floatVideoPlay2;
    private VideoData video_data;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> indicator_titles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_info);
        ButterKnife.bind(this);
        // 设置状态栏透明
        openNavgationTras();
        inintdata();
        inintui();
    }

    /**
     * 设置状态栏跟图片一个属性
     */
    private void openNavgationTras() {
        // 4.4及以上版本开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);

        // 自定义颜色
        tintManager.setTintColor(UIUtils.getColor(R.color.toptranslate));
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    private void inintdata() {
        //拿到视频实体
        Intent intent = getIntent();
        video_data = (VideoData) intent.getSerializableExtra("video");

    }

    private void inintui() {
        //初始化fragment
        inintsfragments();
        //设置viewpager
        configviewpagers();
        //设置viewpager指示器
        configindicators();
        //设置顶部图片和点击事件
        configtopimgs();
        //设置点击事件
        floatVideoPlay.setOnClickListener(this);
        floatVideoPlay2.setOnClickListener(this);
    }

    private void configtopimgs() {
        //下载原图
        Glide.with(getApplicationContext()).load(video_data.getVideoImageURL()).into(imgVideoImginfo);
    }

    private void inintsfragments() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("video",video_data);
        VideoInfoFragments videoinfo = new VideoInfoFragments();
        VideoCommentFragments comments = new VideoCommentFragments();
        videoinfo.setArguments(bundle);
        comments.setArguments(bundle);
        fragments.add(videoinfo);
        fragments.add(comments);
    }

    private void configviewpagers() {
        viewVideoPagers.setAdapter(new MyVideoFragments(getSupportFragmentManager()));
    }

    private void configindicators() {
        //取得指示器文本
        indicator_titles = UIUtils.getTabTitles(R.array.VideoInfoTitles);
        CommonNavigator nva = new CommonNavigator(this);
        nva.setAdjustMode(true);
        nva.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return indicator_titles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int i) {
                ClipPagerTitleView tv = new ClipPagerTitleView(getApplicationContext());
                tv.setText(indicator_titles.get(i));
                tv.setTextColor(UIUtils.getColor(R.color.text_color));
                tv.setClipColor(UIUtils.getColor(R.color.appth));
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewVideoPagers.setCurrentItem(i);
                    }
                });
                return tv;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indi = new LinePagerIndicator(getApplicationContext());
                indi.setColors(Color.WHITE);
                return indi;
            }
        });
        //设置好指示器
        indicatorVideoInfo.setNavigator(nva);
        //与viewpager适配
        viewVideoPagers.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                indicatorVideoInfo.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                indicatorVideoInfo.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                indicatorVideoInfo.onPageScrollStateChanged(state);
            }
        });

    }
    @Override
    public void onClick(View view)
    {
        Intent intent = new Intent(VideoInfoActivity.this,TruePlayActivity.class);
        intent.putExtra("video",video_data);
        startActivity(intent);
    }

    class MyVideoFragments extends FragmentPagerAdapter {


        public MyVideoFragments(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

}
