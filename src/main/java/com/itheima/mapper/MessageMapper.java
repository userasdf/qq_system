package com.itheima.mapper;

import com.itheima.entity.Message;
import com.itheima.entity.Relation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface MessageMapper {

    //发消息
    @Insert("insert into message values(null,#{temp_username},#{target_username},#{msg},#{is_read},#{sendTime})")
    public void sendMessage(Message message);

    //获取消息列表
    @Select("SELECT * FROM message \n" +
            "WHERE \n" +
            "(temp_username=#{temp_username} AND target_username=#{target_username})\n" +
            "OR\n" +
            "(temp_username=#{target_username} AND target_username=#{temp_username})")
    public List<Message> getMsgList(Relation relation);

    //将消息设为已读
    @Update("UPDATE message SET is_read = 1 WHERE msgId = #{msgId}")
    public void setMsgReaded(int msgId);

    //查询好友发送的新消息个数
    @Select("SELECT COUNT(1) FROM message WHERE temp_username=#{temp_username} AND target_username=#{target_username} AND is_read=0")
    public int getNewMsgNums(Relation relation);
}
