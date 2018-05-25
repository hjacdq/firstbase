<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% String path=request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="shortcut icon" href="<%=path %>/style/images/tmykw666.ico"> 
<link rel="stylesheet" href="<%=path %>/bootstrap/css/bootstrap.min.css" type="text/css" />
<script type="text/javascript" src="<%=path %>/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="<%=path %>/bootstrap/js/bootstrap.min.js"></script>
<title>下载首页</title>
<link rel="stylesheet" href="<%=path %>/style/css/common.css" type="text/css" />
</head>
<body>

<!-- header -->
<div class="header">
	<c:import url="../layout/header.jsp"></c:import>
</div>

<!-- 搜索栏 -->
<div class="searchDiv">
	<div class="sec_box">
  <ul>
    <li><a href="javascript:void(0)" onclick="toTest();">下载测试</a></li>
    <li><a href="javascript:void(0)">XX</a></li>
    <li><a href="javascript:void(0)">XX</a></li>
    <li><a href="javascript:void(0)">XX</a></li>
    <li><a href="javascript:void(0)">XX</a></li>
    <li><a href="javascript:void(0)">XX</a></li>
    <li><a href="javascript:void(0)">XX</a></li>
    <li><a href="javascript:void(0)">XX</a></li>
    <li><a href="javascript:void(0)">XX</a></li>
  </ul>
  
</div>
</div>

<!-- 主体部分 -->
<div class="main">
	
	
</div>

</body>

<script type="text/javascript">

function toTest(){
	
}

</script>
</html>