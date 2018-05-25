<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% String path=request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name = "viewport" content = "width=device-width, maximum-scale=1.0, initial-scale=1.0, user-scalable=0" / > 
<link rel="shortcut icon" href="<%=path %>/style/images/tmykw666.ico"> 
<link rel="stylesheet" href="<%=path %>/bootstrap/css/bootstrap.min.css" type="text/css" />
<script src="<%=path %>/js/jquery-2.1.1.min.js"></script>
<script src="<%=path %>/bootstrap/js/bootstrap.min.js"></script>
<title>首页</title>
<style>
*{
	font-family:"微软雅黑";
}
#avatar{
	height:50px;
	height:50px;
	margin:10px 10px;
}
.msgList{
	width:100%;
	height:30px;
	line-height:30px;
}
.msgList span{
	width:60px;
	margin:5px 10px;
}
.msgList input[type='text']{
	padding-left:8px;
	border:none;
}
#testDiv{
	height:30px;
	line-height:30px;
	margin:10px 10px;
}
#testList{
	width:100%;
	height:auto;
	padding:10px 0;
}
#testList>a{
	display:inline-block;
	width:40%;
	height:30px;
}
</style>
</head>
<body>

<h1>
${name } 欢迎来到首页
</h1>

<img id="avatar" src="${avatar }">

<div class="msgList">
	<span>UserId</span><input type="text" value="${UserId}"/>
</div>
<div class="msgList">
	<span>职位</span><input type="text" value="${position}"/>
</div>
<div class="msgList">
	<span>手机号</span><input type="text" value="${mobile}"/>
</div>
<div class="msgList">
	<span>性别</span><input type="text" value="${gender}"/>
</div>
<div class="msgList">
	<span>email</span><input type="text" value="${email}"/>
</div>

<div id="testDiv">
	<p><span>JS-SDK功能测试列表</span></p>	
</div>
<div id="testList">
	<a href="<%=path %>/qy/imgTest.do">图片上传测试</a>
	<a href="<%=path %>/qy/fxTest.do?id=1">分享测试</a>
</div>




</body>

<script>

</script>
</html>