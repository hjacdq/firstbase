<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<% String path=request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>下载页面</title>
</head>
<body>
<h1>通过链接下载文件</h1>  
<a href="<%=path %>/download/cors.zip">压缩包</a>  
<a href="<%=path %>/download/1.png">图片</a>  
</body>
<script type="text/javascript" src="<%=path %>/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript">

</script>
</html>