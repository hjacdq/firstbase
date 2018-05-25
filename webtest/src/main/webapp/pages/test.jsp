<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<% String path=request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
.main{
	width:100%;
	height:100%;
}
</style>
<title>测试页面</title>
</head>
<body>
<div class="main">
	<form action="/upload/imgUpload.do" method="post" enctype="multipart/form-data">
	    <input type="file" name="file">
	    <input type="submit" value="提交">
	</form>
</div>
</body>
<script src="<%=path %>/js/jquery-2.1.1.min.js"></script>
<script>

</script>
</html>