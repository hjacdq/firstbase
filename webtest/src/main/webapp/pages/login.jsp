<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<% String path=request.getContextPath();%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="shortcut icon" href="<%=path %>/images/tmykw666.ico"> 
<link rel="stylesheet" href="<%=path %>/css/buttons.css">
<link href="https://fonts.googleapis.com/css?family=Roboto:300,400,700" rel="stylesheet">
<link rel="stylesheet" href="<%=path %>/css/login/animate.css">
<link rel="stylesheet" href="<%=path %>/css/login/icomoon.css">
<link rel="stylesheet" href="<%=path %>/css/login/themify-icons.css">
<link rel="stylesheet" href="<%=path %>/css/login/bootstrap.css">
<link rel="stylesheet" href="<%=path %>/css/login/magnific-popup.css">
<link rel="stylesheet" href="<%=path %>/css/login/owl.carousel.min.css">
<link rel="stylesheet" href="<%=path %>/css/login/owl.theme.default.min.css">
<link rel="stylesheet" href="<%=path %>/css/login/style.css">
<title>tmywk666.cn</title>
</head>
<body>
<div class="gtco-loader"></div>
	
	<div id="page">

	
	<div class="page-inner">
	<nav class="gtco-nav" role="navigation">
		<div class="gtco-container">
			
			<div class="row">
				<div class="col-sm-4 col-xs-12">
					<div id="gtco-logo"><a href="/main/index.do">首页 <em></em></a></div>
				</div>
				<!-- <div class="col-xs-8 text-right menu-1">
					<ul>
						<li><a href="features.html">导航一</a></li>
						<li><a href="tour.html">导航二</a></li>
						<li class="has-dropdown">
							<a href="#">导航三</a>
							<ul class="dropdown">
								<li><a href="#">导航三下拉列表一</a></li>
								<li><a href="#">导航三下拉列表二</a></li>
								<li><a href="#">导航三下拉列表三</a></li>
								<li><a href="#">导航三下拉列表四</a></li>
							</ul>
						</li>
						<li><a href="pricing.html">导航四</a></li>
						<li><a href="contact.html">导航五</a></li>
						<li class="btn-cta"><a href="#"><span>开始使用</span></a></li>
					</ul>
				</div> -->
			</div>
			
		</div>
	</nav>
	
	<header id="gtco-header" class="gtco-cover" role="banner" style="background-image: url(<%=path %>/style/images/img_4.jpg)">
		<div class="overlay"></div>
		<div class="gtco-container">
			<div class="row">
				<div class="col-md-12 col-md-offset-0 text-left">
					

					<div class="row row-mt-15em">
						<div class="col-md-7 mt-text animate-box" data-animate-effect="fadeInUp">
							<span class="intro-text-small">欢迎来到天命亦可违</span>
							<h1>请登录或者注册</h1>	
						</div>
						<div class="col-md-4 col-md-push-1 animate-box" data-animate-effect="fadeInRight">
							<div class="form-wrap">
								<div class="tab">
									<ul class="tab-menu">
										<li class="active gtco-first" ><a href="#" data-tab="login">登录</a></li>
										<li class="gtco-second"><a href="#" data-tab="signup">注册</a></li>
									</ul>
									<div class="tab-content">
										<div id="signupDiv" class="tab-content-inner" data-content="signup">
											<form id="registForm">
												<div class="row form-group">
													<div class="col-md-12">
														<label for="username">用户名</label>
														<input type="text" class="form-control" name="username" maxlength="20"   placeholder="请输入用户名，限20字符"  id="username_reg">
													</div>
												</div>
												<div class="row form-group">
													<div class="col-md-12">
														<label for="password">输入密码</label>
														<input type="password" class="form-control" name="password" maxlength="18" placeholder="请输入6~18位密码" id="password_reg">
													</div>
												</div>
												<div class="row form-group">
													<div class="col-md-12">
														<label for="password2">再一次确认密码</label>
														<input type="password" class="form-control" name="password2" maxlength="18"  id="password2_reg">
													</div>
												</div>

												<div class="row form-group">
													<div class="col-md-12">
														<input type="button" class="btn btn-primary" id="regist" value="注册">
													</div>
												</div>
											</form>	
										</div>

										<div id="loginDiv" class="tab-content-inner  active" data-content="login">
											<form id="loginForm">
												<div class="row form-group">
													<div class="col-md-12">
														<label for="username">用户名</label>
														<input type="text" class="form-control" name="username" id="username_log">
													</div>
												</div>
												<div class="row form-group">
													<div class="col-md-12">
														<label for="password">密码</label>
														<input type="password" class="form-control" name="password" id="password_log">
													</div>
												</div>

												<div class="row form-group">
													<div class="col-md-12">
														<input type="button" class="btn btn-primary" id="login" value="登录">
													</div>
												</div>
											</form>	
										</div>
										<span id="errorMsg" style="color:red;">${errorMsg }</span>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</header>
	
</body>
<script src="<%=path %>/js/jquery-2.1.1.min.js"></script>
<script src="<%=path %>/js/login/modernizr-2.6.2.min.js"></script>
<script src="<%=path %>/js/login/jquery.easing.1.3.js"></script>
<script src="<%=path %>/js/login//bootstrap.min.js"></script>
<script src="<%=path %>/js/login/jquery.waypoints.min.js"></script>
<script src="<%=path %>/js/login/owl.carousel.min.js"></script>
<script src="<%=path %>/js/login/jquery.countTo.js"></script>
<script src="<%=path %>/js/login/jquery.magnific-popup.min.js"></script>
<script src="<%=path %>/js/login/magnific-popup-options.js"></script>
<script src="<%=path %>/js/login/main.js"></script>
<script type="text/javascript">

$(function(){
	$('#signupDiv').bind('keyup', function(event) {
        if (event.keyCode == "13") {
            //回车执行查询
            $('#regist').click();
        }
    });
	$('#loginDiv').bind('keyup', function(event) {
        if (event.keyCode == "13") {
            //回车执行查询
            $('#login').click();
        }
    });
})


$("#regist").click(function(){
	var username_reg = $("#username_reg").val();
	var rs = stripscript(username_reg);
	if(rs){
		$("#errorMsg").text("用户名包含特殊字符");
		return false;
	}
	var password_reg = $("#password_reg").val();
	var password2_reg = $("#password2_reg").val();
	
	$.ajax({
		url:"<%=path %>/user/regist.do",
		type:'post',
		data:{	"username":username_reg,
				"password":password_reg,
				"password":password2_reg
				},
		dataType:'json',
		success:function(data){
			var status = data.status;
			var msg = data.msg;
			if(status=='success'){
				location.href="<%=path %>/main/index.do";
			}else if(status=='error'){
				$("#errorMsg").text(msg);
			}
		},
		error:function(){
			alert("网络异常，请稍后再试!");
		}
	})
});
$("#login").click(function(){
	var username_log = $("#username_log").val();
	var rs = stripscript(username_log);
	if(rs){
		$("#errorMsg").text("用户名包含特殊字符");
		return false;
	}
	var password_log = $("#password_log").val();
	$.ajax({
		url:"<%=path %>/user/login.do",
		type:"post",
		data:{
			"username":username_log,
			"password":password_log
		},
		dataType:'json',
		success:function(data){
			var status = data.status;
			if(status=="success"){
				location.href="<%=path %>/main/index.do";
			}else if(status=="error"){
				var msg = data.msg;
				$("#errorMsg").text(msg);
			}
		},
		error:function(){
			alert("网络异常，请稍后再试！");
		}
	})
});
function stripscript(s) 
{ 
	var pattern = new RegExp("[%--`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]");
	return pattern.exec(s);
}

</script>
</html>