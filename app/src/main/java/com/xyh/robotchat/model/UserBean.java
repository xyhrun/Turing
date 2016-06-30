package com.xyh.robotchat.model;

/**
 * Created by 向阳湖 on 2016/6/29.
 */
public class UserBean {
    private String content;
    private int type;
    private String time;

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }

    public String getTime() {
        return time;
    }

    //初始化自己的内容
    public UserBean(String content, int type, String time) {
        this.content = content;
        this.type = type;
        this.time = time;

    }

    //初始化机器人的内容
    public UserBean(String content, int type) {
        this.content = content;
        this.type = type;
    }
}
