package com.p2p.dsad.sixplayler.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.p2p.dsad.sixplayler.R;
import com.p2p.dsad.sixplayler.bean.VideoData;

import java.util.List;

/**
 * 所有的fragment适配器
 * Created by dsad on 2017/9/6.
 */

public class RecyclerAllFragmetsAdpter extends RecyclerView.Adapter<RecyclerAllFragmetsAdpter.AllFragmentsViewHolder>
{

    private final Context mContext;
    private List<VideoData> listdata;
    private OnRecyclerItemClickListener listener;
    public RecyclerAllFragmetsAdpter(Context mContext, List<VideoData> listdata)
    {
        this.listdata = listdata;
        this.mContext = mContext;
    }
    @Override
    public AllFragmentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item,parent,false);
        return new AllFragmentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AllFragmentsViewHolder holder, int position) {
        VideoData data = listdata.get(position);
        Glide.with(mContext).load(data.getVideoImageURL()).placeholder(R.mipmap.imageload).into(holder.img_video);
        holder.tv_videoname.setText(data.getVideoName());
        holder.tv_videoinfo.setText(data.getVideoCont());
    }

    public void setOnItemClickListener(OnRecyclerItemClickListener listener)
    {
        this.listener=listener;
    }

    @Override
    public int getItemCount() {
        return listdata==null?0:listdata.size();
    }

    class AllFragmentsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private ImageView img_video;
        private TextView tv_videoname;
        private TextView tv_videoinfo;
        public AllFragmentsViewHolder(View itemView) {
            super(itemView);
            img_video = itemView.findViewById(R.id.img_re_videoimg);
            tv_videoinfo = itemView.findViewById(R.id.tv_re_videoinfo);
            tv_videoname = itemView.findViewById(R.id.tv_re_videoname);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener!=null)
            {
                listener.onItemClick(listdata.get(getLayoutPosition()));
            }
        }
    }

    public interface OnRecyclerItemClickListener
    {
        void onItemClick(VideoData data);
    }

}
