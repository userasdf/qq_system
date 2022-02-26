package com.itheima.service.impl;

import com.itheima.entity.Message;
import com.itheima.entity.Relation;
import com.itheima.mapper.MessageMapper;
import com.itheima.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService{

    @Autowired
    private MessageMapper messageMapper;

    public void sendMessage(Message message) {
        messageMapper.sendMessage(message);
    }

    public List<Message> getMsgList(String temp_username, String target_username) {
        Relation relation = new Relation(temp_username,target_username);
        return messageMapper.getMsgList(relation);
    }

    public void setMsgReaded(int msgId) {
        messageMapper.setMsgReaded(msgId);
    }

    public int getNewMsgNums(String temp_username, String target_username) {
        Relation relation = new Relation(temp_username,target_username);
        return messageMapper.getNewMsgNums(relation);
    }
}
