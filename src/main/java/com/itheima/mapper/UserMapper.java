package com.itheima.mapper;

import com.itheima.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper {

    //添加用户
    @Insert("insert into user values(#{username},#{password},#{school},#{age},#{info},#{address})")
    public void addUser(User user);

    //获取所有用户
    @Select("select * from user")
    public List<User> findAll();

    //用户登录（根据用户名和密码来查找用户）
    @Select("select * from user where username=#{username} and password=#{password}")
    public User login(User user);

    //根据用户名称查询用户
    @Select("select * from user where username like CONCAT('%',#{name},'%')")
    public List<User> findByName(String name);

    //修改个人信息
    @Update("UPDATE USER SET PASSWORD=#{password},school=#{school},age=#{age},info=#{info},address=#{address} WHERE username=#{username}")
    public void update(User user);
}
