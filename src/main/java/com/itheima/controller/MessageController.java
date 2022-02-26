package com.itheima.controller;

import com.itheima.entity.Message;
import com.itheima.entity.User;
import com.itheima.service.MessageService;
import com.itheima.service.RelationService;
import com.itheima.service.UserService;
import com.itheima.utils.MyTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    //发送消息
    @RequestMapping("/sendMessage")
    @ResponseBody
    public void sendMessage(Message message) throws UnsupportedEncodingException {
        //处理中文乱码
        message.setTemp_username(new String(message.getTemp_username().getBytes("iso-8859-1"),"utf8"));
        message.setTarget_username(new String(message.getTarget_username().getBytes("iso-8859-1"),"utf8"));
        message.setMsg(new String(message.getMsg().getBytes("iso-8859-1"),"utf8"));
        message.setIs_read(0);//消息未读
        message.setSendTime(MyTool.getTime());
        //发送消息
        messageService.sendMessage(message);
    }


    //ajax获取消息列表
    @RequestMapping(value = "/ajaxMsgList/{temp_username}/{target_username}",produces = "text/html;charset=utf8")
    @ResponseBody
    public String ajaxMsgList(@PathVariable("temp_username") String temp_username,
                              @PathVariable("target_username") String target_username,
                              HttpServletRequest request)
            throws UnsupportedEncodingException {

        //获取用户名
        String username = ((User)request.getSession().getAttribute("user")).getUsername();

        //处理中文乱码
        temp_username = new String(temp_username.getBytes("iso-8859-1"),"utf8");
        target_username = new String(target_username.getBytes("iso-8859-1"),"utf8");
        //获取消息
        List<Message> msgList = messageService.getMsgList(temp_username, target_username);
        //返回的消息字符串
        String msgs = "";
        int isHaveNew = 0;//是否有新消息

        //遍历消息列表
        for (Message message : msgList) {
            if(message.getIs_read()==0&&username.equals(message.getTarget_username()))//如果有新消息
            {
                isHaveNew = 1;//告诉ajax有新消息
                messageService.setMsgReaded(message.getMsgId());//将该消息设置为已读
            }
            msgs += message.getTemp_username()+"/"+
                    message.getTarget_username()+"/"+
                    message.getMsg()+"/"+
                    message.getSendTime()+"|";
        }
        if(msgs.length()>0)
            msgs = msgs.substring(0,msgs.length()-1);
        msgs += isHaveNew;//一个带有是否有新消息的消息列表字符串
        return msgs;
    }

}
