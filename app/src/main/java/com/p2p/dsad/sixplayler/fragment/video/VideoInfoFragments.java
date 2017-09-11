package com.p2p.dsad.sixplayler.fragment.video;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.p2p.dsad.sixplayler.R;
import com.p2p.dsad.sixplayler.base.BaseFragment;
import com.p2p.dsad.sixplayler.bean.VideoData;
import com.p2p.dsad.sixplayler.wegit.LoadingPage;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 视频简介的fragments
 * Created by dsad on 2017/9/7.
 */

public class VideoInfoFragments extends BaseFragment {

    private VideoData video_data;
    @Bind(R.id.tv_video_info)
    TextView tvVideoInfo;
    @Bind(R.id.tv_video_name)
    TextView tvVideoName;
    @Override
    protected int getState() {
        return LoadingPage.SUCCESS_STATE;
    }

    @Override
    protected void setContent(String content) {
        Bundle bundle = VideoInfoFragments.this.getArguments();
        video_data = (VideoData) bundle.getSerializable("video");
        //这里当做inintdata方法
        tvVideoInfo.setText(video_data.getVideoCont());
        tvVideoName.setText("   "+video_data.getVideoName());
    }

    //作废
    @Override
    protected String url() {
        return null;
    }

    @Override
    protected int getchildlayoutid() {
        return R.layout.fragment_videoinfo;
    }


}
