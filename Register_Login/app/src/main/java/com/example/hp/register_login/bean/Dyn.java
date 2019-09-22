package com.example.hp.register_login.bean;

import java.util.ArrayList;
import java.util.List;

public class Dyn {
    public String user = null;
    public String laosao = null;
    public String time = null;
    public String type = null;
    public List<String> url = new ArrayList<>();
    public int img_num = 0;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getLaosao() {
        return laosao;
    }

    public void setLaosao(String laosao) {
        this.laosao = laosao;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }

    public int getImg_num() {
        return img_num;
    }

    public void setImg_num(int img_num) {
        this.img_num = img_num;
    }
}
