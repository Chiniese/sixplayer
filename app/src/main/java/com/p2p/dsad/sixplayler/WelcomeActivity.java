package com.p2p.dsad.sixplayler;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.p2p.dsad.sixplayler.base.BaseActivity;
import com.p2p.dsad.sixplayler.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;


public class WelcomeActivity extends BaseActivity {

    @Bind(R.id.img_wel_bg)
    ImageView imgWelBg;
    @Bind(R.id.tv_wel_appname)
    TextView tvWelAppname;
    @Bind(R.id.tv_wel_version)
    TextView tvWelVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        inintdata();
        inintui();
    }

    private void inintui()
    {
        AlphaAnimation anima = new AlphaAnimation(0,1);
        anima.setDuration(1000);
        anima.start();
        imgWelBg.setAnimation(anima);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                startActivity(new Intent(WelcomeActivity.this,HomeActivity.class));
                finish();
            }
        },2000);

    }

    private void inintdata()
    {
        tvWelVersion.setText("当前版本:"+UIUtils.getVersion());
    }




}
