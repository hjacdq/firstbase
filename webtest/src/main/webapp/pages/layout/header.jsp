<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% String path=request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link rel="stylesheet" href="<%=path %>/css/common.css" type="text/css" />
<style>
* { padding: 0; margin: 0; }
#top { margin: 0 auto;width:980px;min-width:980px; height: 40px;  border-radius: 10px;}
#top ul { position: relative; margin: 0 auto; width: 960px; }
#top li { float: left; margin-right: 0px; width: 100px; height: 40px; }
li { list-style: none; }
#top>ul>li>a { font-style: normal; position: relative; color:#000; text-align: center; line-height: 40px; text-decoration: none; display: block; z-index: 200 }
.user_logdiv{
	display:none;
	width:100px;
	height:auto;
	z-index:99;
	position:relative;
	top:30px;
	right:-800px; 
}
.user_logdiv_list{width:100%;height:20px;margin:15px 0;}
.user_logdiv_list>span{display:block;text-align:center;}
.user_logdiv_list>span:HOVER
{cursor:pointer;}
.user_login_show_div{
	float:right;
	height:40px;
	line-height:40px;
	margin-right:100px;
}
.box
{
	margin:0 auto;
    width:0px;
    height:0px;
    border-top:5px solid rgba(0,0,0,0);
    border-right:5px solid  rgba(0,0,0,0);
    border-bottom:5px solid #efefef;
    border-left:5px solid  rgba(0,0,0,0);
    position: relative;
    top:5px;
}
.user_login_show_div>span:HOVER {
	cursor: pointer;
}
</style>
<script type="text/javascript" src="<%=path %>/js/jquery-2.1.1.min.js"></script>
</head>
<script>
</script>
<body>
<div id="top">
    <c:choose>
    	<c:when test="${empty username}">
    		<div class="user_login_show_div">
    			<span onclick="toLogin();">登录 / 注册</span>
			</div>
    	</c:when>
    	<c:otherwise>
    		<div class="user_login_show_div" id="logOne">
  				<a id="userInfo" href="javascript:void(0)" style="color:#1E90FF;'">${username }</a>
  			</div>
    	</c:otherwise>
    </c:choose>
    
    
  	<div class="user_logdiv">
	  	<div>
			<div class="box"></div>
		</div>
		<div style="background-color: #efefef;padding-bottom: 5px;" id="userMenuDiV">
			<div style="margin-top:5px;width:100%;height:5px;"></div>
			<div class="user_logdiv_list" >
				<span onclick="toMine();">个人中心</span>
			</div>
			<!-- <div style="width:100%;height:1px;border-top:1px solid #B0E0E6;"></div> -->
			<div class="user_logdiv_list">
				<!-- <a href="javascript:void(0)" onclick="logout();">退出登录</a> -->
				<span  onclick="logout();">退出登录</span>
			</div>
		</div>
	</div>
</div>
</body>
<script type="text/javascript">
$(function(){
	$("#top").on("mouseenter","#logOne",function(){
		$(".user_logdiv").fadeIn();
	})
	$("#userMenuDiV").hover(
			function(){
				$(".user_logdiv").fadeIn();
			},
			function(){
			   setTimeout(function () {
				   $(".user_logdiv").fadeOut();
			    }, 800);
			}
	);
})
function toIndex(){
	location.href="<%=path %>/main/index.do";
}
function toTest(){
	location.href="<%=path %>/main/toTest.do";
}
function toChat(){
	location.href="<%=path %>/main/toChat.do";
}
function toMine(){
	location.href="<%=path %>/user/mine.do";
}
function toLogin(){
	location.href="<%=path %>/main/login.do";
}
function logout(){
	location.href="<%=path %>/main/logout.do";
}
</script>
</html>