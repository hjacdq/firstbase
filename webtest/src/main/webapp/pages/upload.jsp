<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<% String path=request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>文件上传演示</title>
</head>
<body>
	<form action="<%=path %>/upload/file.do" method="post" enctype="multipart/form-data">  
	<input type="file" name="file" /> <input type="submit" value="Submit" /></form>  
</body>
</html>