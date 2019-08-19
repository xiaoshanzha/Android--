package com.example.hp.register_login.bean;

/**
 * Created by HP on 2019/8/17.
 * 用户类
 */

public    class User   {
    private String userId;
    private String userPassword;
    public static boolean islogin = false;
    public static String isId = null;
    public static String isPw = null;

    public User(String userId, String userPassword) {
        this.userId = userId;
        this.userPassword = userPassword;
    }

    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
