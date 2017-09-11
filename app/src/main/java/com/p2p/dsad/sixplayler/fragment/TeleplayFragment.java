package com.p2p.dsad.sixplayler.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.p2p.dsad.sixplayler.HomeActivity;
import com.p2p.dsad.sixplayler.R;
import com.p2p.dsad.sixplayler.VideoInfoActivity;
import com.p2p.dsad.sixplayler.base.BaseFragment;
import com.p2p.dsad.sixplayler.base.RecyclerAllFragmetsAdpter;
import com.p2p.dsad.sixplayler.bean.VideoData;
import com.p2p.dsad.sixplayler.utils.Contacts;
import com.p2p.dsad.sixplayler.utils.RecyclerViewItemSpace;
import com.p2p.dsad.sixplayler.utils.UniCoderUtils;
import com.p2p.dsad.sixplayler.utils.Validator;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 电视剧
 * Created by dsad on 2017/9/5.
 */

public class TeleplayFragment extends BaseFragment {
    public List<VideoData> listdatas;
    @Bind(R.id.recycler_teleplay_view)
    RecyclerView recyclerTeleplayView;
    private RecyclerAllFragmetsAdpter myadpter;

    @Override
    protected int getState() {
        return 0;
    }

    @Override
    protected void setContent(String content) {

        String truecontent = Validator.delHTMLTag(UniCoderUtils.decodeUnicode(content));
        listdatas = JSON.parseArray(truecontent, VideoData.class);
        //设置视图
        confingRecycler();
        //这里显示跑马灯
        HomeActivity.HomeRuntext.setVisibility(View.VISIBLE);
    }

    private void confingRecycler()
    {
        myadpter = new RecyclerAllFragmetsAdpter(mContext,listdatas);
        //设置点击事件
        myadpter.setOnItemClickListener(new RecyclerAllFragmetsAdpter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(VideoData data)
            {
                if (isLogin())
                {
                    onpenvideo(data);
                    return;
                }
                logindiolog();

            }
        });
        recyclerTeleplayView.setItemAnimator(new DefaultItemAnimator());
        recyclerTeleplayView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerTeleplayView.addItemDecoration(new RecyclerViewItemSpace(16));
        recyclerTeleplayView.setAdapter(myadpter);

    }

    public void onpenvideo(VideoData data)
    {
        Intent intent = new Intent(mContext, VideoInfoActivity.class);
        intent.putExtra("video",data);
        startActivity(intent);
    }
    @Override
    protected String url() {
        return Contacts.GETTELEPLAT;
    }

    @Override
    protected int getchildlayoutid() {
        return R.layout.fragment_teleplay;
    }


}
