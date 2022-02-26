package com.itheima.controller;

import com.itheima.entity.User;
import com.itheima.service.MessageService;
import com.itheima.service.RelationService;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
@RequestMapping("/relation")
public class RelationController {

    @Autowired
    private RelationService relationService;
    @Autowired
    private MessageService messageService;



    //获取验证好友列表
    @RequestMapping(value = "/ajaxGetValFriend",produces = "text/html;charset=utf8")
    @ResponseBody
    public String ajaxGetValFriend(String username) throws UnsupportedEncodingException {
        //处理中文乱码
        username = new String(username.getBytes("iso-8859-1"),"utf8");
        List<User> allValFriend = this.relationService.getAllValFriend(username);
        String valFriends = "";
        for (User user : allValFriend) {
            valFriends += user.getUsername()+","+user.getVal_msg()+";";
        }
        if(valFriends.length()>0)
            valFriends = valFriends.substring(0,valFriends.length()-1);
        return valFriends;
    }

    //获取好友列表
    @RequestMapping(value = "/ajaxGetFriend",produces = "text/html;charset=utf8")
    @ResponseBody
    public String ajaxGetFriend(String username) throws UnsupportedEncodingException {
        //处理中文乱码
        username = new String(username.getBytes("iso-8859-1"),"utf8");
        List<User> allFriend = relationService.getAllFriend(username);
        String friends = "";
        int tempNewMsgNums = 0;//当前好友发送的新消息个数
        for (User user : allFriend) {//遍历所有的好友
            tempNewMsgNums = messageService.getNewMsgNums(user.getUsername(),username);
            friends += user.getUsername()+","+tempNewMsgNums+";";
        }
        if(friends.length()>0)
            friends = friends.substring(0,friends.length()-1);
        return friends;
    }


    //添加好友
    @RequestMapping(value = "/addFriend/{temp_username}/{target_username}/{val_msg}",produces = "text/html;charset=utf8")
    @ResponseBody
    public void addFriend(@PathVariable("temp_username") String temp_username,
                            @PathVariable("target_username") String target_username,
                            @PathVariable("val_msg") String val_msg
    ) throws UnsupportedEncodingException {
        //处理中文乱码
        temp_username = new String(temp_username.getBytes("iso-8859-1"),"utf8");
        target_username = new String(target_username.getBytes("iso-8859-1"),"utf8");
        val_msg = new String(val_msg.getBytes("iso-8859-1"),"utf8");
        //添加好友
        relationService.addFriend(temp_username,target_username,val_msg);
    }


    //接受好友申请
    @RequestMapping(value = "/acceptFriend/{temp_username}/{target_username}")
    @ResponseBody
    public void acceptFriend(@PathVariable("temp_username") String temp_username,
                               @PathVariable("target_username") String target_username,
                               Model model) throws UnsupportedEncodingException {
        //处理中文乱码
        temp_username = new String(temp_username.getBytes("iso-8859-1"),"utf8");
        target_username = new String(target_username.getBytes("iso-8859-1"),"utf8");
        //接受好友申请
        relationService.acceptFriend(temp_username,target_username);
    }
}
