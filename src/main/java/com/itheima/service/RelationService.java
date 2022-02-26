package com.itheima.service;

import com.itheima.entity.User;

import java.util.List;

public interface RelationService {

    //添加好友
    public void addFriend(String temp_username,String target_username,String val_msg);

    //获取当前用户的所有好友
    public List<User> getAllFriend(String username);

    //获取所有验证好友
    public List<User> getAllValFriend(String target_username);

    //接受好友申请
    public void acceptFriend(String temp_username,String target_username);
}
