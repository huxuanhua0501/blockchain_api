<%@ page language="java" pageEncoding="UTF-8"%>
2 <!DOCTYPE HTML>
3 <html>
4   <head>
    5     <title>文件上传</title>
    6   </head>
7
8   <body>
9     <form action="${pageContext.request.contextPath}/servlet/UploadHandleServlet" enctype="multipart/form-data" method="post">
    10         上传用户：<input type="text" name="username"><br/>
    11         上传文件1：<input type="file" name="file1"><br/>
    12         上传文件2：<input type="file" name="file2"><br/>
    13         <input type="submit" value="提交">
    14     </form>
15   </body>
16 </html>