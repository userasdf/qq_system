<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>云文件系统</title>
    <style>
        body{
            font-size: 30px;
        }

        #container{
            border: 2px solid green;
            overflow-y: scroll;
            height: 600px;
            width: 800px;
            margin-left: 100px;
        }
        .header{
            font-size: 40px;
        }
    </style>

    <%
        /*访问控制*/
        if(request.getSession().getAttribute("user")==null)
            response.sendRedirect("/index.jsp");
    %>

    <script src="/js/jquery-1.8.3.min.js"></script>
    <script>



        <%--实时显示文件目录--%>
        setInterval(function (args) {
            //获取当前目录
            var temp_dir = $("#temp_dir").text().substring(5);
            show_content(temp_dir);
        },500);

        /*返回上一级目录*/
        function backPreLevel() {
            //获取当前目录
            var temp_dir = $("#temp_dir").text().substring(5);
            if(temp_dir=='/')
            {
                alert("已经是根目录");
                return
            }
            temp_dir = temp_dir.substring(0,temp_dir.lastIndexOf("/"));
            //上一级目录
            new_dir = temp_dir.substring(0,temp_dir.lastIndexOf("/"))+"/";

            $("#temp_dir").text("当前目录："+new_dir);
            $("#myForm").attr("action","/file/upload?tempDir="+new_dir);
        }

        <%--进入文件夹或下载文件--%>
        function fun(name,is_file) {
            //获取当前目录
            var temp_dir = $("#temp_dir").text().substring(5);

            //判断是下载文件还是进入文件夹
            if(is_file=='true'){
                location.href = "/file/down?filename="+name+"&tempDir="+temp_dir;
            }else{
                var new_dir = temp_dir+name+"/";
                $("#temp_dir").text("当前目录："+new_dir);
                $("#myForm").attr("action","/file/upload?tempDir="+new_dir);
            }

        }
        <%--重命名--%>
        function rename(old_name) {
            //获取当前目录
            var temp_dir = $("#temp_dir").text().substring(5);
            //新文件名
            var new_name = prompt("请输入文件名：",old_name);
            if(new_name==null||new_name=='')//用户取消，停止执行接下来的代码
                return;
            $.ajax({
                type:"post",
                url:"/file/rename?tempDir="+temp_dir+"&oldName="+old_name+"&newName="+new_name
            });
        }

        <%--读取文件--%>
        function readTxt(file_name) {
            //获取当前目录
            var temp_dir = $("#temp_dir").text().substring(5);
            location.href="/file/readTxt?tempDir="+temp_dir+"&fileName="+file_name;
        }

        <%--显示当前文件夹里面的内容--%>
        function show_content(temp_dir) {
            $.ajax({
                type:"post",
                url:"/file/loadContent?tempDir="+temp_dir,
                contentType:"application/json;charset=utf-8",
                success:function (data) {
                    //清空容器
                    $("#container").empty();
                    file_names = data.split(';');//获取结果列表

                    //如果是空记录
                    if(file_names[0]=="空记录")
                    {
                        $("#container").append($("<span style='color: red'>没有记录</span>"))
                        return;
                    }
                    //如果不是空记录
                    for(i in file_names){//遍历内容
                        file_name = file_names[i].split(',')[0];//文件或文件夹名字
                        is_file = file_names[i].split(',')[1];//是否是文件
                        //父标签
                        tag_p = $("<p style='margin:5px 5px 10px 5px;background-color: yellow'></p>");
                        //文件或文件夹名字
                        tag_name = $("<span>"+file_name+"</span>");
                        //按钮1：下载或进入按钮
                        tag_input_show = $("<input type='button' value='进入' style='float: right;font-size: 25px;'" +
                            " onclick=\""+"fun('"+file_name+"','"+is_file+"')"+"\"/>");
                        if(is_file=='true'){//如果是文件
                            tag_input_show.attr("value","下载");
                            tag_p.css("background-color","#00FF00")
                        }
                        //按钮2：删除按钮
                        tag_input_delete = $("<input type='button' value='删除' style='margin-right: 5px;float: right;font-size: 25px;'" +
                            " onclick=\""+"deleteItem('"+file_name+"')"+"\"/>");
                        //按钮3：重命名按钮
                        tag_input_rename = $("<input type='button' value='重命名' style='margin-right: 5px;float: right;font-size: 25px;' " +
                            " onclick=\""+"rename('"+file_name+"')"+"\"/>");
                        tag_input_read = $("<input type='button' value='读取' style='margin-right: 5px;float: right;font-size: 25px;' " +
                            " onclick=\""+"readTxt('"+file_name+"')"+"\"/>");
                        //添加内容
                        tag_p.append(tag_name).append(tag_input_show)
                            .append(tag_input_delete).append(tag_input_rename);
                        //判断是否是TXT、doc、docx文件
                        if(is_file=='true'&& (
                            (file_name.length>4&&file_name.substring(file_name.length-3,file_name.length)=='txt')
                            ||(file_name.length>4&&file_name.substring(file_name.length-3,file_name.length)=='doc')
                            ||(file_name.length>5&&file_name.substring(file_name.length-4,file_name.length)=='docx')))
                        {
                            tag_p.append(tag_input_read);
                        }
                        $("#container").append(tag_p);
                    }
                }
            });
        }

        //创建文件夹
        function createFolder() {
            //获取当前目录
            var temp_dir = $("#temp_dir").text().substring(5);
            var new_folder = prompt("请输入文件夹名字：");
            if(new_folder==null||new_folder=='')//用户取消，停止执行接下来的代码
                return;

            $.ajax({
                type:"post",
                url:"/file/createFolder?tempDir="+temp_dir+"&newFolder="+new_folder,
                contentType:"application/json;charset=utf-8"
            });
        }


        //新建文档
        function createTxt() {
            //获取当前目录
            var temp_dir = $("#temp_dir").text().substring(5);
            var file_name = prompt("请输入文件名字：");
            if(file_name==null||file_name=='')//用户取消，停止执行接下来的代码
                return;

            $.ajax({
                type:"post",
                url:"/file/createTxt?tempDir="+temp_dir+"&fileName="+file_name,
                contentType:"application/json;charset=utf-8"
            });
        }

        //删除文件或文件夹
        function deleteItem(name) {
            //提示用户是否需要删除
            var choose = confirm("确定要删除项目："+name+"?");
            if(choose==false)//用户取消删除
                return;

            //获取当前目录
            var temp_dir = $("#temp_dir").text().substring(5);
            //需要删除的路径
            var path = temp_dir+"/"+name;
            $.ajax({
                type:"post",
                url:"/file/delete?path="+path,
                contentType:"application/json;charset=utf8"
            });
        }

        //检查用户上传的文件
        function check() {
            choose_file = $("#file").attr("value");
            if(choose_file=='')
            {
                alert("请选择上传的文件");
                return false;
            }
            return true;
        }

    </script>
</head>
<%-----------------------------------body---------------------------------------%>
<body>
<h1 style="background-color: aqua;text-align: center">
    <span style="font-size: 30px">欢迎：${user.username}</span>
    云文件系统
    <input class="header" type="button" value="退出登录" style="background-color: red;float: right;" onclick="location.href='/user/logout'">
</h1>
<h3 style="padding-left: 245px">
    <input class="header" type="button" value="主   页   面" onclick="location.href='/main.jsp'">
    <input class="header" type="button" style="background-color: green" value="个人信息" onclick="location.href='/info.jsp'">
</h3>
<form style="margin-left: 100px;padding-left:15px;padding-bottom:20px;width:784px;border: #00FF00 2px solid" id="myForm" method="post" action="/file/upload?tempDir=${tempDir}"
      enctype="multipart/form-data" onsubmit="return check()">
    <p>设别名：<input style="height: 50px;width: 637px" name="fileName"  type="text" placeholder="请输入文件名（可选）"></p>
    <input id="file" style="font-size: 35px" type="file" name="uploadFile">
    <button type="submit" style="font-size: 35px">提交</button>
</form>
<div style="padding-left: 115px;font-size: 35px" id="temp_dir">当前目录：${tempDir}</div>
<div style="padding-left: 115px">
    <input type="button" style="font-size: 35px" value="新建文件夹" onclick="createFolder()">
    <input type="button" style="font-size: 35px;background-color: green" value="新建文档" onclick="createTxt()">
    <input type="button" style="margin-left: 185px;background-color: purple;font-size: 35px" value="返回上一级" onclick="backPreLevel()">
</div>
<%--当前目录下面的内容--%>
<div id="container"></div>
</body>
</html>
