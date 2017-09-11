package com.p2p.dsad.sixplayler.fragment.video;

import android.os.Bundle;

import com.p2p.dsad.sixplayler.R;
import com.p2p.dsad.sixplayler.base.BaseFragment;
import com.p2p.dsad.sixplayler.bean.VideoData;
import com.p2p.dsad.sixplayler.wegit.LoadingPage;

/**
 * 视频评论
 * Created by dsad on 2017/9/7.
 */

public class VideoCommentFragments extends BaseFragment
{

    private VideoData video_data;

    @Override
    protected int getState() {
        //直接返回成功
        return LoadingPage.SUCCESS_STATE;
    }

    @Override
    protected void setContent(String content)
    {
        Bundle bundle = VideoCommentFragments.this.getArguments();
        video_data = (VideoData) bundle.getSerializable("video");
    }

    @Override
    protected String url()
    {
        return null;
    }

    @Override
    protected int getchildlayoutid()
    {
        return R.layout.fragments_videocomments;
    }
}
