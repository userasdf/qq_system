<%--
  Created by IntelliJ IDEA.
  User: wsh
  Date: 2022-01-19
  Time: 17:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录管理页面</title>
</head>
<body>
<h1 style="background-color: aqua;text-align: center">登录页面</h1>
<div style="width: 300px;margin-left: 38%;margin-top: 15%;padding: 8px;border: 3px solid green">
    <form method="post" action="${pageContext.request.contextPath}/user/login">
        用户名：<input type="text" name="username" value="wsh"><br/>
        密&nbsp;&nbsp;&nbsp;码：<input type="text" name="password" value="123456"><br/>
        <p>
            <input style="width: 200px;background-color: #00FF00" type="submit" value="登录">
            <a href="/regist.jsp">注册</a>
        </p>

    </form>
</div>

</body>
</html>
