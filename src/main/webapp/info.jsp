<%--
  Created by IntelliJ IDEA.
  User: wsh
  Date: 2022-01-24
  Time: 21:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>个人信息</title>
    <style>

        body{
            font-size: 30px;
        }

        #left{
            width: 360px;
            height: 240px;
            float: left;
            margin-left: 175px;
        }
        #right{
            width: 400px;
            height: 240px;
            float: left;
        }
        #bottom{
            float: left;
            margin-left: 400px;
            margin-top: 320px;
        }
        .btn{
            font-size: 30px;
        }
        .p{
            margin-bottom: 45px;
        }
        .header{
            font-size: 40px;
        }
    </style>
    <script src="/js/jquery-1.8.3.min.js"></script>
    <script src="/js/clipboard.min.js"></script>
    <script>
        //清空文本框
        function emptyData(id) {
            $("#"+id).val('');
        }

        //提交检查
        function check() {
            if($("#username").val()=='')
            {
                $("#username").focus();
                alert("用户名不能为空");
                return false;
            }
            if($("#password").val()=='')
            {
                $("#password").focus();
                alert("用户密码不能为空");
                return false;
            }
            if($("#age").val()=='')
            {
                $("#age").focus();
                alert("年龄不能为空！");
                return false;
            }
            return true;
        }


    </script>
</head>
<body>
<h1 style="background-color: aqua;text-align: center">
    <span style="font-size: 30px">欢迎：${user.username}</span>
    个人信息
    <input type="button" class="header" style="background-color: red;float: right;" value="退出登录" onclick="location.href='/user/logout'">
</h1>
<h3 style="padding-left: 245px">
    <input type="button" class="header" value="云文件系统" onclick="location.href='/file/system'">
    <input type="button" class="header" style="background-color: green" value="主  页  面" onclick="location.href='/main.jsp'">
</h3>

<form method="post" action="/user/update" onsubmit="return check()">
    <div id="left">
        <p class="p">
            姓名：<input type="text" id="username" readonly="readonly" name="username" value="${user.username}">
            <button class="btn" disabled="disabled" style="background-color: yellow;border: 2px solid blue">只读</button>
        </p>
        <p class="p">
            密码：<input type="text" id="password" name="password" value="${user.password}">
            <input class="btn" type="button" onclick="emptyData('password')" value="清空">
        </p>
        <p class="p">
            年龄：<input type="text" id="age" name="age" value="${user.age}">
            <input class="btn" type="button" onclick="emptyData('age')" value="清空">
        </p>
        <p class="p">
            学校：<input type="text" id="school" name="school" value="${user.school}">
            <input class="btn" type="button" onclick="emptyData('school')" value="清空">
        </p>
        <p class="p">
            地址：<input type="text" id="address" name="address" value="${user.address}">
            <input class="btn" type="button" onclick="emptyData('address')" value="清空">
        </p>
    </div>
    <div id="right">
        <p>
            <textarea id="info" name="info" style="width: 350px;height: 420px">${user.info}</textarea>
            <input id="copy" class="btn" type="button"  data-clipboard-target="#info"  value="复制">
            <input class="btn" type="button" style="margin-left: 190px" onclick="emptyData('info')" value="清空">
        </p>
    </div>
    <div id="bottom">
        <p>
            <input class="btn" type="reset" value="重置">
            &nbsp;&nbsp;&nbsp;
            <input class="btn" type="submit" style="background-color: red" value="更新">
            &nbsp;&nbsp;&nbsp;
            <input class="btn" type="button" value="刷新" onclick="location.href='/user/findInfo'">
        </p>
    </div>

</form>
<script>
    //复制功能
    new ClipboardJS(document.getElementById("copy"));
</script>
</body>
</html>
