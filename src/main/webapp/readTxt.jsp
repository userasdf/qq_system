<%--
  Created by IntelliJ IDEA.
  User: wsh
  Date: 2022-01-28
  Time: 11:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>编辑文本页面</title>
    <style>
        body{
            font-size: 30px;
        }
        .header{
            font-size: 40px;
        }
        #txt{
            width: 800px;
            height: 270px;
            margin-left: 115px;
        }
    </style>
    <script src="/js/jquery-1.8.3.min.js"></script>
    <script>

        //清空文本
        function clearContent() {
            $("#txt").val('');
        }

        function saveTxt() {
            //获取当前目录
            var temp_dir = $("#temp_dir").text().substring(5);
            //获取文件名
            var file_name = $("#file_name").text().substring(4);
            //获取文本内容
            var content = $("#txt").val();
            $.ajax({
                type:"post",
                url:"/file/saveTxt?content="+content+"&tempDir="+temp_dir+"&fileName="+file_name,
                contentType:"application/json;charset=utf8"
            });
            alert('保存成功！')
        }
    </script>
</head>
<body>
<h1 style="background-color: aqua;text-align: center">
    <span style="font-size: 30px">欢迎：${user.username}</span>
    编辑文本
    <input type="button" class="header" style="background-color: red;float: right;" value="退出登录" onclick="location.href='/user/logout'">
</h1>
<h3 style="padding-left: 245px">
    <input type="button" class="header" value="云文件系统" onclick="location.href='/file/system?tempDir=${tempDir}'">
    <input type="button" class="header" style="background-color: green" value="主  页  面" onclick="location.href='/main.jsp'">
    <input class="header" type="button" style="background-color: green" value="个人信息" onclick="location.href='/info.jsp'"></h3>
<div style="padding-left: 115px;font-size: 35px" id="temp_dir">当前目录：${tempDir}</div>
<div style="padding-left: 115px;font-size: 35px" id="file_name">文件名：${fileName}</div>
<p style="margin: 0px 0px 0px 115px">
    <input type="button" value="清空" onclick="clearContent()">
    <input type="button" value="复制" data-clipboard-target="#txt" id="btn">
    <input type="button" value="保存" onclick="saveTxt()">
</p>
<textarea id="txt">${content}</textarea>
<script src="/js/clipboard.min.js"></script>
<script>
    var btn = document.getElementById("btn");
    new ClipboardJS(btn);
</script>
</body>
</html>
