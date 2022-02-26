package com.itheima.service.impl;

import com.itheima.entity.Relation;
import com.itheima.entity.User;
import com.itheima.mapper.RelationMapper;
import com.itheima.service.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelationServiceImpl implements RelationService{

    @Autowired
    private RelationMapper relationMapper;

    public void addFriend(String temp_username, String target_username,String val_msg) {
        Relation relation = new Relation(temp_username,target_username,val_msg);
        relationMapper.addFriend(relation);
    }

    public List<User> getAllFriend(String username)
    {
        return relationMapper.getAllFriend(username);
    }

    public List<User> getAllValFriend(String target_username) {
        return relationMapper.getAllValFriend(target_username);
    }

    //接受好友申请
    public void acceptFriend(String temp_username, String target_username) {
        //他人到自己
        Relation relation = new Relation(target_username,temp_username);
        relationMapper.acceptFriend(relation);//state设置为1
        //自己到他人
        relation = new Relation(temp_username,target_username);
        relationMapper.addFriend(relation);//添加一条他人到自己的记录
        relationMapper.acceptFriend(relation);//state设置为1
    }
}
