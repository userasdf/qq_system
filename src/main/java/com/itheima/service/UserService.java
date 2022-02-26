package com.itheima.service;

import com.itheima.entity.User;

import java.util.List;

public interface UserService {

    public void addUser(User user);

    public List<User> findAll();

    public User login(User user);

    public List<User> findByName(String name);

    public void update(User user);
}
