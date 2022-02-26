package com.itheima.mapper;

import com.itheima.entity.Relation;
import com.itheima.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface RelationMapper {

    //添加好友
    @Insert("insert into relation values(#{temp_username},#{target_username},0,#{val_msg})")
    public void addFriend(Relation relation);

    //获取当前用户的所有好友
    @Select("SELECT * FROM USER,relation " +
            "WHERE user.`username`=relation.`target_username`" +
            " and temp_username=#{temp_username} " +
            "and state=1")
    public List<User> getAllFriend(String username);

    //获取所有验证好友
    @Select("SELECT * FROM relation,USER" +
            " WHERE user.`username`=relation.`temp_username` " +
            "AND target_username=#{target_username}" +
            " AND state=0")
    public List<User> getAllValFriend(String target_username);

    //接受好友申请
    @Update("UPDATE relation SET state=1 " +
            "WHERE temp_username=#{temp_username} AND target_username=#{target_username}")
    public void acceptFriend(Relation relation);
}
