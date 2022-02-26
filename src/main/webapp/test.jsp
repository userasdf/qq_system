<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="/js/jquery-1.8.3.min.js"></script>
    <script>




        function fun(name) {
            location.href = "/down?filename="+name;
        }

        $.ajax({
            type:"post",
            url:"/ajax",
            contentType:"application/json;charset=utf-8",
            success:function (data) {
                file_names = data.split(',');
                for(i in file_names){
                    tag_input = $("<input type='button' value='"+file_names[i]+"'" +
                        " onclick=\""+"fun('"+file_names[i]+"')"+"\"/>");
                    $("#container").append(tag_input);
                }
            }
        });




    </script>
</head>
<body>
<form method="post" action="/test" enctype="multipart/form-data">
    <input type="file" name="filename">
    <button type="submit">提交</button>
</form>
<div id="container"></div>
</body>
</html>
