<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% String path=request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="shortcut icon" href="<%=path %>/style/images/tmykw666.ico"> 
<link rel="stylesheet" href="<%=path %>/bootstrap/css/bootstrap.min.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/common.css" type="text/css" />
<title>个人中心</title>
<style type="text/css">
body{
	background: rgba(70,149,194,0.3);
}
a:hover{ text-decoration:none} 
.header{
	width:100%;
	min-width:980px;
	background: #fff;
}
.main{
	position:relative;
	width:980px;
	min-width:980px;
	margin:0 auto;
}
.main>div{
	margin-top:20px;
}
.left-content{
	float:left;
	width:200px;
	background-color:#fff;
	height:200px;
}
.default-headPic{
	display: inline-block;
    overflow: hidden;
    vertical-align: middle;
    -webkit-transform: none;
    -moz-transform: none;
    -o-transform: none;
    background: url(https://tmykw.oss-cn-hangzhou.aliyuncs.com/data/default.jpg) -80px -60px no-repeat;
}
.headPic{
	width:180px;
	height:180px;
	margin:10px;
	background-color:#ccc;
}
.headPic:HOVER{
	cursor: pointer;
}
.headPic>img{
	width:100%;
	height:100%;
}
.right-content{
	float:left;
	margin-left:35px;
	width:745px;
	height:500px;
	background-color:#f7f7f7;
}
</style>
<style>
.setting-menu{
	width:200px;
	float:left;
}
.menu-list{
	list-style:none;
}
.memu-item{
	line-height: 34px;
    height: 35px;
    font-size: 12px;
    width: 200px;
    border-right: 1px solid #e5e5e5;
    text-align: center;
    font-family: arial,\5b8b\4f53,sans-serif;
    border-bottom: 1px solid #e5e5e5;
}
.memu-item:HOVER{
	cursor: pointer;
}
.setting-menu .menu-list .menu-item a {
    margin-left: 45px;
    display: block;
    padding-left: 11px;
    background: none;
    color: #666;
}
.setting-detail{
	width:540px;
	background-color:#fff;
	float:left;
	min-height: 500px;
}
.item-active{
	background-color: #fff;
	border-right:none;
}
.pass-portrait-filebtn{
	background: none;
    border: none;
    background-color:#fff;
    border:1px solid #ccc;
    background-repeat: no-repeat;
    height: 38px;
    line-height: 38px;
    text-align: center;
    width: 130px;
}
.pass-portrait-submit{
	background: none;
    border: none;
    background-color:#fff;
    border:1px solid #ccc;
    background-repeat: no-repeat;
    height: 38px;
    line-height: 38px;
    text-align: center;
    width: 130px;
}
.model{
	display:none;
	padding:50px;
}
/* .headPic-model{
	display:none;
} */
#imgPreviewDiv{
	width:200px;
	height:200px;
	margin-bottom: 20px;
}
#imgPreviewDiv>img{
	width:100%;
	height:100%;
}
#fileButtonDiv,#submitButtonDiv{
	display: inline-block;
}
.infoList{
	width:100%;
	height:40px;
	line-height:40px;
	font-size:14px;
}
.infoList label{
	width:100px;
}
.infoList select{
	width:80px;
	margin-right:20px;
	padding:2px;
}
.infoSpan{
	display:inline-block;
	width:150px;
}
#submitBaseInfoBtn{
	width:100px;
	height:35px;
	line-height:35px;
	margin:20px 100px;
}
.changeBtn{
	width:60px;
	height:25px;
	line-height:25px;
	position:relative;
	left:50px;
}
</style>
<style>

</style>
</head>
<body>

<!-- header -->
<div class="header">
	<c:import url="../layout/header.jsp"></c:import>
</div>

<!-- 主体部分 -->
<div class="main">
	<div class="left-content">
		<c:choose>
			<c:when test="${user.headimgurl==null || user.headimgurl==''}">  
				<div class="headPic default-headPic"></div>
			</c:when>
			<c:otherwise> 
				<div class="headPic">
					<img src="${user.headimgurl}"/>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
	
	<div class="right-content">
		<div class="setting-menu">
			<ul class="menu-list">
				<li class="memu-item item-active" data="jczl">基础资料</li>
				<li class="memu-item" data="xgtx">修改头像</li>
				<li class="memu-item" data="zhaq">账号安全</li>
				<li class="memu-item">视频</li>
				<li class="memu-item">贴吧</li>
				<li class="memu-item">学术</li>
				<li class="memu-item">登录</li>
				<li class="memu-item">设置</li>
			</ul>
		</div>
		
		<div class="setting-detail">
			
			<!-- 基础信息model -->
			<div class="baseInfo-model model">
				<h3>基础信息</h3>
				<form id="baseInfoForm">
					<div class="infoList">
						<label>性别</label>
						<c:choose>
							<c:when test="${user.sex==1}">
								<input type="radio" checked name="sex" id="sex-man" value="1"/><label style="padding-left:10px;margin-right:10px;" for="sex-man">男</label>
							</c:when>
							<c:otherwise>
								<input type="radio" name="sex" id="sex-man" value="1"/><label style="padding-left:10px;margin-right:10px;" for="sex-man">男</label>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${user.sex==2}">
								<input type="radio" checked name="sex" id="sex-woman" value="2"/><label style="padding-left:10px;" for="sex-woman">女</label>
							</c:when>
							<c:otherwise>
								<input type="radio" name="sex" id="sex-woman" value="2"/><label style="padding-left:10px;" for="sex-woman">女</label>
							</c:otherwise>
						</c:choose>
						
					</div>
					
					<div class="infoList">
						<label class="info-title">生日</label>
						<select name="year" id="year"></select>
						<select name="year" id="month"></select>
						<select name="year" id="day"></select>
					</div>
				</form>
				<input type="button" id="submitBaseInfoBtn" value="保存"/>
			</div>
		
			<!-- 头像model -->
			<div class="headPic-model model">
				<h3>头像设置</h3>
				<c:choose>
					<c:when test="${user.headimgurl==null || user.headimgurl==''}">  
						<div id="imgPreviewDiv default-headPic"> </div>
					</c:when>
					<c:otherwise> 
						<div class="headPic">
							<img id="imgShow" src="${user.headimgurl}"/>
						</div>
					</c:otherwise>
				</c:choose>
				
				<div id="fileButtonDiv">
					<form id="imgForm" method="post" enctype="multipart/form-data">
						<input type="button" id="openImgBtn" onclick="$('#fileImg').click();" class="pass-portrait-filebtn" value="选择图片"/>
						<input type="file" style="display:none" name="file" id="fileImg" />
						<input type="hidden" id="headPicUrl"/>
					</form>
				</div>
				<div id="submitButtonDiv">
					<input type="button" class="pass-portrait-submit" value="保存头像"/>
				</div>
			</div>
			
			<!-- 账号安全model -->
			<div class="account-model model">
				<h3>账号安全</h3>
				<div class="infoList">
					<label>绑定手机</label>
					<span class="infoSpan">1526****513</span>
					
					<input id="changePhoneBtn" class="changeBtn" type="button" value="修改"/>
				</div>
				<div class="infoList">
					<label>绑定邮箱</label>
					<span class="infoSpan">未绑定</span>
					<input id="changePhoneBtn" class="changeBtn" type="button" value="绑定"/>
				</div>
			</div>
		</div>
		
		
	</div>
	
</div>

</body>
<script type="text/javascript" src="<%=path %>/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="<%=path %>/bootstrap/js/bootstrap.min.js"></script>
<script src="/js/jquery.form.js" type="text/javascript"></script>  
<script>

initBaseInfoSelect();

$(function(){
	$(".baseInfo-model").show();
	$(".menu-list").on("click",".memu-item",function(){
		$(".menu-list .memu-item").each(function(){
			$(this).removeClass("item-active");
		})
		$(this).addClass("item-active");
		var data = $(this).attr("data");
		$(".model").hide();
		if(data=='jczl'){
			$(".baseInfo-model").show();
		}else if(data=='xgtx'){
			$(".headPic-model").show();
		}else if(data=='zhaq'){
			$(".account-model").show();
		}
	})
	
	$("#fileImg").change(function(){  // 当 id 为 file 的对象发生变化时
        var fileSize = this.files[0].size;
        var size = fileSize / 1024 / 1024;
        if (size > 5) {
            alert("图片不能大于2M,请将文件压缩后重新上传！");
            this.value="";
            return false;
        }else{
        	uploadPic();
        }
    })
    
    $("#year").on("change",function(){
    	var monthHtml = "<option value=''>请选择</option>";
    	var dayHtml = "<option value=''>请选择</option>";
    	for(var i=1;i<=12;i++){
    		monthHtml += '<option  value="'+i+'">'+i+'月</option>';
    	}
    	for(var i=1;i<=31;i++){
    		dayHtml += '<option value="'+i+'">'+i+'日</option>';
    	}
    	$("#month").empty();
    	$("#month").append(monthHtml);
    	$("#day").empty();
    	$("#day").append(dayHtml);
    })
    
    $("#month").on("change",function(){
		var chooseYear = $("#year").val();
    	var flag = isLeapYear(chooseYear);
		var chooseMonth = $("#month").val();
		var dayNumber = 31;
		switch(chooseMonth){
		case '1':
			dayNumber = 31;
			break;
		case '2':
			if(flag){
				dayNumber = 29;
			}else{
				dayNumber = 28;
			}
			break;
		case '3':
			dayNumber = 31;
			break;
		case '4':
			dayNumber = 30;
			break;
		case '5':
			dayNumber = 31;
			break;
		case '6':
			dayNumber = 30;
			break;
		case '7':
			dayNumber = 31;
			break;
		case '8':
			dayNumber = 31;
			break;
		case '9':
			dayNumber = 30;
			break;
		case '10':
			dayNumber = 31;
			break;
		case '11':
			dayNumber = 30;
			break;
		case '12':
			dayNumber = 31;
			break;
		default:
			break;
		}
		var dayHtml = "<option value=''>请选择</option>";
		for(var i=1;i<=dayNumber;i++){
			dayHtml += '<option value="'+i+'">'+i+'日</option>';
		}
		$("#day").empty();
		$("#day").append(dayHtml);
	})
})
function initBaseInfoSelect(){
	var userYear = '${userInfo.birthYear}';
	var userMonth = '${userInfo.birthMonth}';
	var userDay = '${userInfo.birthDay}';
	
	var yearHtml = "<option value=''>请选择</option>";
	var monthHtml = "<option value=''>请选择</option>";
	var dayHtml = "<option value=''>请选择</option>";
	for(var i=1990;i<=2018;i++){
		if(userYear==i+''){
			yearHtml += '<option selected value="'+i+'">'+i+'年</option>';
		}else{
			yearHtml += '<option value="'+i+'">'+i+'年</option>';
		}
	}
	for(var i=1;i<=12;i++){
		if(userMonth==i+''){
			monthHtml += '<option selected value="'+i+'">'+i+'月</option>';
		}else{
			monthHtml += '<option  value="'+i+'">'+i+'月</option>';
		}
	}
	for(var i=1;i<=31;i++){
		if(userDay==i+''){
			dayHtml += '<option selected value="'+i+'">'+i+'日</option>';
		}else{
			dayHtml += '<option value="'+i+'">'+i+'日</option>';
		}
	}
	$("#year").empty();
	$("#year").append(yearHtml);
	$("#month").empty();
	$("#month").append(monthHtml);
	$("#day").empty();
	$("#day").append(dayHtml);
}
function uploadPic() {  
    // 上传设置  
    var options = {  
        url: "/upload/imgUpload.do",  
        type: "post",  
        dataType: "json",  
        success: function(data, status, xhr) {  
            $("#imgShow").attr("src", data.picUrl);  
            $("#headPicUrl").val(data.picUrl);
        }  
    };  
    $("#imgForm").ajaxSubmit(options);  
}  
$("#submitButtonDiv").click(function(){
	var picUrl = $("#headPicUrl").val();
	$.ajax({
		url:"/user/changePicUrl.do",
		type:"post",
		data:{"picUrl":picUrl},
		dataType:"json",
		success:function(data){
			var errorCode = data.errorCode;
			if(errorCode==0){
				alert("保存成功");
				$(".headPic img").attr("src",picUrl);
			}else{
				var errorMsg = data.errorMsg;
				alert(errorMsg);
			}
		},
		error:function(){
			
		}
	})
})

function isLeapYear (Year) {
	if (((Year % 4)==0) && ((Year % 100)!=0) || ((Year % 400)==0)) {
		return (true);
	} else { 
		return (false); 
	}
}

$("#submitBaseInfoBtn").click(function(){
	$.ajax({
		url:"/user/saveBaseInfo.do",
		type:"post",
		data:$("#baseInfoForm").serialize(),
		dataType:"json",
		success:function(data){
			var errorCode = data.errorCode;
			if(errorCode==0){
				alert("保存成功!");
			}else{
				var errorMsg = data.errorMsg;
				alert(errorMsg);
			}
			
		},
		error:function(){
			
		}
	})
	
})
</script>
</html>