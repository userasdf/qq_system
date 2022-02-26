<%--
  Created by IntelliJ IDEA.
  User: wsh
  Date: 2022-01-19
  Time: 18:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册页面</title>
    <script src="/js/jquery-1.8.3.min.js"></script>
    <script>
        function check() {
            if($("#username").val()=='')
            {
                alert("请输入用户名！");
                $("#username").focus();
                return false;
            }
            if($("#password").val()=='')
            {
                $("#password").focus();
                alert("请输入用户密码！");
                return false;
            }
            if($("#age").val()=='')
            {
                $("#age").focus();
                alert("年龄不能为空！");
                return false;
            }
            if($("#school").val()=='')
                $("#school").val("学校不详");
            if($("#info").val()=='')
                $("#info").val("详细信息不详细");
            if($("#address").val()=='')
                $("#address").val("地址不详");
            return true;
        }
    </script>
</head>
<body>
<h1 style="background-color: aqua;text-align: center">注册页面</h1>
<div style="width: 300px;margin-left: 38%;margin-top: 15%;padding: 8px;border: 3px solid green">
    <form method="get" action="${pageContext.request.contextPath}/user/regist" onsubmit="return check()">
        用户名：&nbsp;&nbsp;&nbsp;<input id="username" type="text" name="username">*<br/>
        密&nbsp;&nbsp;&nbsp;码：&nbsp;&nbsp;&nbsp;<input id="password" type="text" name="password">*<br/>
        年&nbsp;&nbsp;&nbsp;龄：&nbsp;&nbsp;&nbsp;<input id="age" type="text" name="age">*<br/>
        学&nbsp;&nbsp;&nbsp;校：&nbsp;&nbsp;&nbsp;<input id="school" type="text" name="school"><br/>
        info：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="info" type="text" name="info"><br/>
        address：<input id="address" type="text" name="address"><br/>
        <p>
            <input type="reset" style="background-color: gray;width: 90px" value="重置">
            <input type="submit" style="background-color: rebeccapurple;width: 90px" value="提交">
            <a href="/index.jsp">返回登录页面</a>
        </p>
    </form>
</div>
</body>
</html>
