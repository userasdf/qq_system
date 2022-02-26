package com.itheima.entity;

public class Message {
    private String temp_username;
    private String target_username;
    private String msg;
    private int is_read;
    private int msgId;
    private String sendTime;

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    public int getIs_read() {
        return is_read;
    }

    public void setIs_read(int is_read) {
        this.is_read = is_read;
    }

    public String getTemp_username() {
        return temp_username;
    }

    public void setTemp_username(String temp_username) {
        this.temp_username = temp_username;
    }

    public String getTarget_username() {
        return target_username;
    }

    public void setTarget_username(String target_username) {
        this.target_username = target_username;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
