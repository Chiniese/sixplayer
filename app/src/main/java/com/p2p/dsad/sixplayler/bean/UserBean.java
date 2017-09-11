package com.p2p.dsad.sixplayler.bean;

/**
 *
 * Created by dsad on 2017/9/11.
 */

public class UserBean
{
    private int id;
    private String username;
    private String useraccont;
    private String userimg;

    public void setUseraccont(String useraccont) {
        this.useraccont = useraccont;
    }

    public void setUserimg(String userimg) {
        this.userimg = userimg;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUseraccont() {
        return useraccont;
    }

    public String getUserimg() {
        return userimg;
    }

    public String getUsername() {
        return username;
    }
}
