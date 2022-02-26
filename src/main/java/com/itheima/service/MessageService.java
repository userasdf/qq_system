package com.itheima.service;

import com.itheima.entity.Message;

import java.util.List;

public interface MessageService {

    //发消息
    public void sendMessage(Message message);

    //获取消息列表
    public List<Message> getMsgList(String temp_username,String target_username);

    //将消息标记为已读
    public void setMsgReaded(int msgId);

    //获取当前好友发送的新消息个数
    public int getNewMsgNums(String temp_username, String target_username);
}
