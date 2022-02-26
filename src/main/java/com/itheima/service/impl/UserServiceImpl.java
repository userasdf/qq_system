package com.itheima.service.impl;

import com.itheima.entity.User;
import com.itheima.mapper.UserMapper;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    public void addUser(User user) {
        userMapper.addUser(user);
    }

    public List<User> findAll() {
        return userMapper.findAll();
    }

    public User login(User user) {
        return userMapper.login(user);
    }

    public List<User> findByName(String name) {
        return userMapper.findByName(name);
    }

    public void update(User user) {
        userMapper.update(user);
    }
}
