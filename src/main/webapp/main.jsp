<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>主页面</title>

    <%
        /*访问控制*/
        if(request.getSession().getAttribute("user")==null)
            response.sendRedirect("/index.jsp");
    %>

    <script src="/js/jquery-1.8.3.min.js"></script>

    <script>




        //查询用户
        function selectUser() {
            var username = $("#username").val()
            //使用ajax方式动态获取用户列表
            $.ajax({
                type:"POST",
                url:"/user/ajaxGetUserList?username="+username,
                contentType:"application/json;charset=utf-8",
                success:function (data) {
                    $("#userList").empty()//清空用户列表
                    var name_list = data.split(',');
                    if(name_list=='')//如果是空字符串，不执行接下来的代码
                    {
                        $("#userList").append($("<span style='color: red'>没有记录</span>"));
                        return;
                    }

                    for(i in name_list)
                    {
                        tag_space1 = $("<span>&nbsp;&nbsp;&nbsp;</span>");//空格
                        temp_username = '${user.username}';
                        target_username = name_list[i];
                        tag_p = $("<p><span>"+name_list[i]+"</span></p>");
                        tag_input = $("<input type='button' value='添加好友'" +
                            " onclick=\""+"addFriend('"+temp_username+"','"+target_username+"')"+"\"/>");
                        tag_p.append(tag_space1).append(tag_input);
                        $("#userList").append(tag_p)//填入用户列表
                    }
                },
                error:function () {
                    alert("error")
                }
            });
        }



        //实时加载好友验证
        setInterval(function () {
            $.ajax({
                type:"POST",
                url:"/relation/ajaxGetValFriend?username=${user.username}",
                contentType:"application/json;charset=utf-8",
                success:function (data) {
                    $("#valFriend").empty();//清空验证用户列表
                    temp_username = '${user.username}';//当前登录用户名
                    var val_friend_list = data.split(';');//验证用户列表
                    if(val_friend_list=='')//如果是空字符串，不执行接下来的代码
                    {
                        $("#valFriend").append($("<span style='color: red'>没有记录</span>"));
                        return;
                    }
                    for(i in val_friend_list)//遍历验证用户列表
                    {
                        friend_name = val_friend_list[i].split(',')[0];//验证用户名
                        val_msg = val_friend_list[i].split(',')[1];//验证消息

                        tag_space1 = $("<span>&nbsp;&nbsp;&nbsp;</span>");//空格
                        tag_space2 = $("<span>&nbsp;&nbsp;&nbsp;</span>");//空格
                        tag_p = $("<p></p>");//父标签
                        tag_name = $("<span>"+friend_name+"</span>");//显示用户名
                        tag_msg = $("<span>"+val_msg+"</span>");//显示用户消息
                        tag_input = $("<input type='button' value='管理'" +
                            " onclick=\""+"val_friend('"+temp_username+"','"+friend_name+"')"+"\"/>");
                        tag_p.append(tag_name).append(tag_space1).append(tag_msg).append(tag_space2).append(tag_input);
                        $("#valFriend").append(tag_p);
                    }
                }
            });
        },500);

        //实时展示好友列表
        setInterval(function () {
            $.ajax({
                type:"POST",
                url:"/relation/ajaxGetFriend?username=${user.username}",
                contentType:"application/json;charset=utf-8",
                success:function (data) {
                    $("#friendList").empty();//清空好友列表
                    temp_username = '${user.username}';//当前登录用户名
                    var friend_list = data.split(';');//好友列表
                    if(friend_list=='')//如果是空字符串，不执行接下来的代码
                    {
                        $("#friendList").append($("<span style='color: red'>没有记录</span>"));
                        return;
                    }
                    for(i in friend_list)//遍历好友列表
                    {
                        friend_name = friend_list[i].split(',')[0];//好友姓名
                        new_msg_nums = friend_list[i].split(',')[1];//新消息个数

                        tag_p = $("<p></p>");//父标签
                        tag_space = $("<span>&nbsp;&nbsp;&nbsp;</span>");//空格
                        tag_name = $("<span>"+friend_name+"</span>");//显示用户名
                        tag_newMsgNums = $("<span style='color: red'>"+new_msg_nums+"</span>");//显示新消息个数
                        tag_input = $("<input type='button' value='发消息'" +//发消息按钮
                            " onclick=\""+"chooseFriend('"+temp_username+"','"+friend_name+"')"+"\"/>");

                        //分有新消息和没有新消息两种情况
                        if(parseInt(new_msg_nums)>0)
                            tag_p.append(tag_name).append(tag_newMsgNums).append(tag_space).append(tag_input);
                        else
                            tag_p.append(tag_name).append(tag_space).append(tag_input);
                        $("#friendList").append(tag_p);
                    }
                }
            });
        },500);

        //选择好友
        function chooseFriend(temp, target) {

            $("#dialog").html("与<span style='color: red'>"+target+"</span>的对话:");
            $("#dialog").attr("name",target);
        }




        <%--添加好友--%>
        function addFriend(temp_username,target_username) {
            msg = prompt("请输入验证消息","我是");
            //使用ajax方式添加好友
            $.ajax({
                type:"POST",
                url:"/relation/addFriend/"+temp_username+"/"+target_username+"/"+msg,
                contentType:"application/json;charset=utf-8",
            });
        }



        <%--验证好友--%>
        function val_friend(temp_username,target_username) {
            var res = confirm("是否同意好友申请？")
            if(res){
                //使用ajax方式验证好友
                $.ajax({
                    type:"POST",
                    url:"/relation/acceptFriend/"+temp_username+"/"+target_username,
                    contentType:"application/json;charset=utf-8",
                });
            }
            else{
                alert("拒绝了。。。")
                //拒绝添加好友
            }
        }

        //连接：发送消息、实时接收消息两个方法
        var is_scrolled = 0;

        //实时加载消息列表
        setInterval(function () {
            target_username = $("#dialog").attr("name");
            temp_username = '${user.username}';
            if(target_username==null||target_username=='')
                return;
            $.ajax({
                type:"POST",
                url:"/message/ajaxMsgList/"+temp_username+"/"+target_username,
                contentType:"application/json;charset=utf-8",
                success:function (data) {
                    $("#msg_list").empty();//清空消息列表

                    //判断是否有新消息
                    var is_have_new = data[data.length-1];
                    //去掉最后一个字符
                    data = data.substring(0,data.length-1);
                    var msg_list = data.split('|');//消息列表
                    if(msg_list=='')//如果没有消息，不执行接下来的代码
                    {
                        $("#msg_list").append($("<span style='color: red'>没有记录</span>"))
                        return;
                    }

                    for(i in msg_list)//遍历消息列表
                    {
                        left_name = msg_list[i].split('/')[0];
                        right_name = msg_list[i].split('/')[1];
                        msg = msg_list[i].split('/')[2];
                        sendTime = msg_list[i].split('/')[3];
                        if(msg=="")
                            msg = 'null';
                        //显示消息
                        tag_msg = $("<div style=\"border: 2px solid blue;width: 150px;" +
                            "margin-bottom: 5px;margin-left: 3px\">"+msg+"</div>");
                        //显示时间
                        tag_time = $("<span style='margin-left: 60px'>"+sendTime+"</span>");
                        //父标签
                        tag_p = $("<p'></p>");
                        tag_p.append(tag_time).append(tag_msg);
                        if(left_name==temp_username)//我发出去的消息，文本右对齐
                        {
                            tag_msg.css("border","2px solid red")
                                .css("margin-left","123px");
                        }
                        //添加到消息列表
                        $("#msg_list").append(tag_p);
                    }
                    if(is_have_new=='1')//如果有新消息
                    {
                        $("#msg_list").scrollTop(50000)//将消息框滚动条显示到最低端
                    }
                    if(is_scrolled>0)//用户刚发完消息，滚动消息框
                    {
                        is_scrolled -= 0.25;//目的是让其判断两次，如果只判断一次，可能还没加载完消息，就执行了滚动任务
                        $("#msg_list").scrollTop(50000)//将消息框滚动条显示到最低端
                    }
                }
            });
        },50);

        <%--发送消息--%>
        function sendMsg() {
            var temp_username = '${user.username}';
            var target_username = $("#dialog").attr("name");
            if(target_username==undefined)
            {
                alert("请先选择好友，再发送消息！");
                return;
            }
            var msg = $("#msg").val();//获取发送的消息
            $("#msg").val('');//将消息框清空
            //以ajax方式发送消息
            $.ajax({
                type:"POST",
                url:'/message/sendMessage?temp_username='
                +temp_username+'&target_username='+target_username+'&msg='+msg,
                contentType:"application/json;charset=utf-8"
            });

            //发送完消息，告诉“实时显示消息方法”要滚动消息了
            is_scrolled = 1

        }


    </script>
    <style>
        body{
            font-size: 30px;
        }
        .list{
            background-color: yellow;
            text-align: center;
        }
        .mycss{
            border: 2px solid blue;
            float: left;
            padding: 10px;
        }
        .first{
            width: 230px;
            margin-right: 10px;
        }
        .second{
            width: 230px;
            margin-right: 10px;
        }
        .third{
            width: 300px;
            margin-right: 10px;
        }
        .last{
            width: 280px;
        }
        .header{
            font-size: 40px;
        }
    </style>


</head>
<body>
<h1 style="background-color: aqua;text-align: center">
    <span style="font-size: 30px">欢迎：${user.username}</span>
    主页面
    <input class="header" type="button" style="background-color: red;float: right;" value="退出登录" onclick="location.href='/user/logout'">
</h1>
<h3 style="padding-left: 245px">
    <input class="header" type="button" value="云文件系统" onclick="location.href='/file/system'">
    <input class="header" type="button" style="background-color: green" value="个人信息" onclick="location.href='/info.jsp'">
    <input class="header" type="button" style="background-color: green" value="游戏" onclick="location.href='/game/MRGame.html'">
</h3>

<%--用户列表--%>
<div class="mycss first">
    <p class="list">搜索用户</p>
    <input type="text" id="username">
    <input type="button" onclick="selectUser()" value="查询"/>
    <%--展示用户列表--%>
    <div id="userList" style="height: 300px;overflow-y: scroll;border: 1px solid purple"></div>
</div>



<%--好友列表--%>
<div class="mycss second">
    <p class="list">好友列表</p>
    <%--展示好友列表--%>
    <div id="friendList" style="height: 300px;overflow-y: scroll;border: 1px solid purple"></div>
</div>

<%--消息框--%>
<div class="mycss third">
    <p class="list">消息列表</p>
    <p id="dialog"></p>
    <%--消息列表--%>
    <div id="msg_list" style="height: 275px;overflow-y: scroll;border: 1px solid purple"></div>
    <%--输入文本框--%>
    <textarea style="width: 300px;margin-top: 3px" id="msg"></textarea>
    <input type="button" onclick="sendMsg()" value="发送">
    </form>
</div>


<%--用户验证--%>
<div class="mycss last">
    <p class="list">好友验证</p>
    <%--展示验证用户--%>
    <div id="valFriend" style="height: 300px;overflow-y: scroll;border: 1px solid purple"></div>
</div>

</body>
</html>
