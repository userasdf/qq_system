package com.itheima.controller;


import com.itheima.entity.User;
import com.itheima.service.RelationService;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RelationService relationService;

    //登录
    @RequestMapping("/login")
    public String login(User user, HttpServletRequest request){
        User res = userService.login(user);
        if(res==null)
            return "redirect:/index.jsp";
        request.getSession().setAttribute("user",res);
        return "main";
    }


    //重新获取用户个人信息
    @RequestMapping("/findInfo")
    public String findInfo(HttpServletRequest request)
    {
        String username = ((User) request.getSession().getAttribute("user")).getUsername();
        User user = userService.findByName(username).get(0);
        request.getSession().setAttribute("user",user);
        return "info";
    }

    //修改个人信息
    @RequestMapping("/update")
    public String update(User user,HttpServletRequest request)
    {
        userService.update(user);//修改个人信息
        request.getSession().setAttribute("user",user);//user属性更新
        return "info";
    }

    //获取用户列表
    @RequestMapping(value = "/ajaxGetUserList",produces = "text/html;charset=utf8")
    @ResponseBody
    public String ajaxGetUserList(String username,HttpServletRequest request) throws IOException{
        username = new String(username.getBytes("iso-8859-1"),"utf8");
        List<User> userList = this.userService.findByName(username);
        //将要返回的用户列表字符串
        String users= "";
        //当前登录的用户名
        String temp_username = ((User)request.getSession().getAttribute("user")).getUsername();
        for (User user : userList) {
            if(user.getUsername().equals(temp_username))
                continue;//不显示当前登录的用户姓名
            users += user.getUsername()+",";
        }
        if(users.length()>0)
            users = users.substring(0,users.length()-1);//去掉最后一个','号
        return users;
    }


    //注册
    @RequestMapping("/regist")
    public String regist(User user)
    {
        System.out.println(user);
        userService.addUser(user);
        return "index";
    }

    //登出
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request)
    {
        request.getSession().removeAttribute("user");
        return "index";
    }

}
