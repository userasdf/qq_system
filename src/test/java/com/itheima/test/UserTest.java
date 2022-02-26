package com.itheima.test;

import com.itheima.entity.User;
import com.itheima.service.RelationService;
import com.itheima.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class UserTest {

    @Autowired
    private UserService userService;

    @Autowired
    private RelationService relationService;



    @Test
    public void testBool()
    {
        File file = new File("D:\\我这一生\\研究生阶段！！！！！！！\\学习方面\\maven_project\\apache-tomcat-7.0.56\\webapps\\ROOT\\upload/wsh//");
        if(!file.exists())
        {
            System.out.println("dsaf");
            file.mkdirs();
        }

    }

    @Test
    public void testDate()
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(format.format(new Date()));
    }

    @Test
    public void testFile()
    {
        File file = new File("D:\\我这一生\\研究生阶段！！！！！！！\\学习方面\\maven_project\\qq_system\\src\\main\\webapp\\test");
        File[] listFiles = file.listFiles();
        for (File listFile : listFiles) {
            if(listFile.isFile())
                System.out.println(listFile.getName());
        }
    }

    @Test
    public void testRelation()
    {
        List<User> allValFriend = this.relationService.getAllValFriend("小明");


        String valFriends = "";
        for (User user : allValFriend) {
            valFriends += user.getUsername()+","+user.getVal_msg()+";";
        }
        if(valFriends.length()>0)
            valFriends = valFriends.substring(0,valFriends.length()-1);
        System.out.println(valFriends);
    }

    @Test
    public void testFindAll()
    {
        List<User> all = userService.findAll();
        for (User user : all) {
            System.out.println(user);
        }
    }

    @Test
    public void test()
    {
        User user = new User();
        user.setUsername("小明");
        user.setPassword("123456");
        user.setSchool("山东工商学院");
        user.setAge(23);
        userService.addUser(user);
    }

}
