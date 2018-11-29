<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% String path=request.getContextPath();%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="shortcut icon" href="<%=path %>/style/images/tmykw666.ico"> 
<link rel="stylesheet" href="<%=path %>/bootstrap/css/bootstrap.min.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/common.css" type="text/css" />
<%-- <link rel="stylesheet" href="<%=path %>/css/index/index.css?v=1.01" type="text/css" /> --%>
<title>首页</title>
<style type="text/css">
body{
	background: rgba(70,149,194,0.1);
}
.main{
	position:relative;
	width:980px;
	min-width:980px;
	margin:0 auto;
}
.fixedDiv{
	position: fixed;
    top: 0px;
    left: 0px;
}
.leftlay{
	width:100px;
	height:230px;
	border:1px solid #ccc;
	float:left; 
}
.rightlay{
	width:850px;
	min-width:750px;
	height:auto;
	border:1px solid #ccc;
	
	float:right;	
	position: relative;
	padding-top: 500px;
}
.leftlay li{
	height:40px;
	line-height:40px;
	position: relative;
	text-align:center;
	list-style: none;
	margin:5px 0;
}
.leftlay li:hover{
	background:#48D1CC;
	color:#fff;
	cursor: pointer;
}
.mainContent{
	width:100%;
}
.check_leftContent{
	float:left;
	width:650px;
	height:400px;
}
.check_rightContent{
	margin-left:10px;
	float:left;
	width:300px;
	height:400px;
	background-color: #eee;
}
.check_span{
	display:inline-block;
	width:100%;
	height:34px;
	line-height:34px;
	text-align: center;
}
.check_showList{
	
}
</style>
<style>
::-webkit-input-placeholder { /* WebKit browsers */  
    color:    #A9A9A9;  
}  
:-moz-placeholder { /* Mozilla Firefox 4 to 18 */  
   color:    #A9A9A9;  
   opacity:  1;  
}  
::-moz-placeholder { /* Mozilla Firefox 19+ */  
   color:    #A9A9A9;  
   opacity:  1;  
}  
:-ms-input-placeholder { /* Internet Explorer 10+ */  
   color:    #A9A9A9;  
}  
</style>
</head>
<body>

<!-- header -->
<div class="header">
	<c:import url="../layout/header.jsp"></c:import>
</div>

<div class="index_search_div">
</div>

<!-- 主体部分 -->
<div class="main">
	
	<div class="mainContent">
		<div  class="check_leftContent">
			<textarea style="width:100%;height:100%;padding:5px;resize:none" id="content" placeholder="请输入内容。。。。"></textarea>
		</div>
		
		<div class="check_rightContent">
			<div>
				<span class="check_span">检查结果</span>
			</div>
			<div id="checkResult"></div>
			<div style="height:10px;border-top:1px solid #ccc;width:100%;"></div>
			<!-- <div class="check_showList">
				<span style="display:inline-block;text-align:center;width:100px;margin:0 5px;"></span>
				-》
				<input style="display:inline-block;text-align:center;width:100px;" type="text" />
			</div> -->
		</div>
		
	</div>
	
	<div style="width:100%;margin-top:10px;"></div>
	
	<div>
		<button onclick="submitCheck();">检查敏感词</button>
	</div>
	
	<div style="width:100%;margin-top:30px;"></div>
	
</div>

</body>
<script type="text/javascript" src="<%=path %>/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/bootstrap.min.js"></script>
<script type="text/javascript">
$(function(){
	$('#carousel').carousel({
		 interval: 3000
	})
	$(".rightContentDiv img").mouseenter(function(){
		$(this).removeClass("img_common");
		$(this).addClass("img_hover");
	})
	$(".rightContentDiv img").mouseout(function(){
		$(this).removeClass("img_hover");
		$(this).addClass("img_common");
	})
})
function submitCheck(){
	$.ajax({
		url:"<%=path %>/art/mgwords/check.do",
		type:"post",
		data:{"content":$("#content").val()},
		success:function(data){
			if(data.success){
				var list = data.data;
				if(list.length>0){
					$("#checkResult").html("语句中包含敏感词的个数为：<span style='color:red;'>" + list.length + "</span>。包含：[<span style='color:red;'>" + list+"</span>]");
				}else{
					$("#checkResult").html("内容无敏感词");
				}
			}else if(status=='error'){
				alert(msg);
			}
		},
		error:function(){
			alert('网络异常');
		}
	})
}
//$(".user_logdiv").slideToggle(300);
</script>
</html>