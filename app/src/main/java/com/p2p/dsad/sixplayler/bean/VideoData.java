package com.p2p.dsad.sixplayler.bean;

import java.io.Serializable;

/**
 * 视频的总类
 * Created by dsad on 2017/9/5.
 */

public class VideoData implements Serializable
{
        private String $type;
    //简介
        private String VideoCont;
    //视频id:播放的关键
        private String VideoId;
    //视频图片的url
        private String VideoImageURL;
    //视频名字
        private String VideoName;

    public String get$type() {
        return $type;
    }

    public String getVideoCont() {
        return VideoCont;
    }

    public String getVideoId() {
        return VideoId;
    }

    public String getVideoImageURL() {
        return VideoImageURL;
    }

    public String getVideoName() {
        return VideoName;
    }

    public void set$type(String $type) {
        this.$type = $type;
    }

    public void setVideoCont(String videoCount) {
        VideoCont = videoCount;
    }

    public void setVideoId(String videoId) {
        VideoId = videoId;
    }

    public void setVideoImageURL(String videoImageURL) {
        VideoImageURL = videoImageURL;
    }

    public void setVideoName(String videoName) {
        VideoName = videoName;
    }
}
