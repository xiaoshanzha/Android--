package com.example.hp.universityset_notice;
/**
 * Created by HP on 2018/5/28.
 */

public    class users   {
    private String things;
    private int imageId;
    private String name;
    public users(int imageId,String name,String things){
        this.things = things;
        this.imageId = imageId;
        this.name = name;
    }

    public String getThings(){
        return things;
    }
    public int getImageId(){
        return imageId;
    }
    public String getName(){
        return name;
    }
}
