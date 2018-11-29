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
.mainContent{
	width:100%;
	position: relative;
}
.index_search_div{
	width:980px;
	min-width: 980px;
	height:40px;
	line-height:40px;
	margin:20px auto;
	position: relative;
}

.overflow:HOVER {
	cursor: pointer;
	color:blue;
}
.tn-arrow{
	display: inline-block;
    width: 8px;
    height: 5px;
    margin: 0 0 0 5px;
    overflow: hidden;
    vertical-align: middle;
    font-size: 12px;
    line-height: 13px;
    -webkit-transform: none;
    -moz-transform: none;
    -o-transform: none;
    background: url(https://tmykw.oss-cn-hangzhou.aliyuncs.com/data/icon.png) 0 -977px no-repeat;
}
.navList:HOVER{
	cursor:pointer;
}
.navList{
	padding: 0px 3px;
	margin-right:30px;
}
.index_leftContent{
	display:inline-block;
	width:335px;
}
.index_rightContent{
	position:absolute;
	top:0px;
	right:0px;
	width:600px;
	margin-left:50px;
	min-height:400px;
	background-color: #fff;
}
.index_right_list{
	display:inline-block;
	width:80px;
	text-align: center;
	font-size:14px;
	margin:10px;
	font-family: sans-serif;
	color:#a30;
}
.index_right_list:HOVER{
	cursor: pointer;
	font-weight: 600;
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
	<c:import url="layout/header.jsp"></c:import>
</div>

<div class="index_search_div">
	<!-- 搜索栏 -->
	<div class="searchDiv">
		<input type="text" class="searchinput" placeholder="想了解最新的消息吗?"/>
		<img src="<%=path %>/img/search_32.png" class="searchgo"/>
		<!-- <input type="submit" class="searchgo"/> -->
		<div style="width:600px;position: absolute;right:0px;top:0px;height:40px;line-height:40px">
			<label class="navList" onclick="showRightList(1);">
				小说
				<span class="tn-arrow"></span>
			</label>
			<!-- <label class="navList">
				生活
				<span class="tn-arrow"></span>
			</label>
			<label class="navList">
				创意
				<span class="tn-arrow"></span>
			</label> -->
		</div>
	</div>
</div>

<!-- 主体部分 -->
<div class="main">
	
	<div class="mainContent">
		<div class="index_leftContent">
			<div id="carousel" class="carousel slide">
			    <ol class="carousel-indicators">
			        <li data-target="#carousel" data-ride="carousel" data-slide-to="0" class="active"></li>
			        <li data-target="#carousel" data-ride="carousel" data-slide-to="1"></li>
			        <li data-target="#carousel" data-ride="carousel" data-slide-to="2"></li>
			    </ol>   
			    <div class="carousel-inner">
			        <div class="item active">
			        	<a>
				            <img src="<%=path %>/img/index_img_1.jpg" alt="First slide" 
				            style="width:335px;height:400px"/>
			            </a>
			            <div class="carousel-caption">
			            	<h3>狗狗</h3>
			            	<p>一只二哈</p>
			            </div>
			        </div>
			        <div class="item">
			        	<a>
				            <img src="<%=path %>/img/index_img_2.jpg" alt="Second slide"
				            style="width:335px;height:400px"/>
			            </a>
			            <div class="carousel-caption">
			            	<h3>猫咪</h3>
			            	<p>一只猫咪</p>
			            </div>
			        </div>
			        <div class="item">
			        	<a>
				            <img src="<%=path %>/img/index_img_3.jpg" alt="Third slide"
				            style="width:335px;height:400px"/>
			            </a>
			            <div class="carousel-caption">
			            	<h3>围观</h3>
			            	<p>围观群众</p>
			            </div>
			        </div>
			    </div>
				<a class="left carousel-control" href="#carousel" role="button" data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="right carousel-control" href="#carousel" role="button" data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
			</div>
		</div>
		
		<div class="index_rightContent">
			<div class="index_right_list" onclick="javascript:window.open('<%=path %>/art/mgwords/toCheck.do')">
				<span>敏感词过滤</span>
			</div>
		</div>
		
	</div>
	
	<div style="width:100%;margin-top:30px;"></div>
	
	<!-- <button onclick="showMainContent();">发表内容</button>
	<div class="mainContent" id="editorDiv">
		<div style="margin:5px 0;">
			<span>标题</span>
			<input ="text" name="title" />
		</div>
		<div id="editor">
		</div>
		<div>
			<button onclick="subEditor();">提交</button>
		</div>
	</div>
	
	<div style="width:100%;margin-top:30px;"></div> -->
	
</div>

</body>
<script type="text/javascript" src="<%=path %>/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/wangEditor.min.js"></script>
<script type="text/javascript">


	var E = window.wangEditor;
	var editor = new E('#editor');
	//下面两个配置，使用其中一个即可显示“上传图片”的tab。但是两者不要同时使用！！！
	//editor.customConfig.uploadImgShowBase64 = true   // 使用 base64 保存图片
	// 上传图片到服务器
	editor.customConfig.uploadImgServer = '<%=path%>/upload/editorImgUpload.do'; 

	// 隐藏“网络图片”tab
	editor.customConfig.showLinkImg = false
	
	// 将图片大小限制为 3M(默认限制图片大小是 5M)
	editor.customConfig.uploadImgMaxSize = 3 * 1024 * 1024
	
	// 限制一次最多上传 5 张图片
	editor.customConfig.uploadImgMaxLength = 5
	
	editor.customConfig.uploadImgParams = {
	    // 如果版本 <=v3.1.0 ，属性值会自动进行 encode ，此处无需 encode
	    // 如果版本 >=v3.1.1 ，属性值不会自动 encode ，如有需要自己手动 encode
	    token: 'abcdef12345'
	}
	
	//如果还需要将参数拼接到 url 中，可再加上如下配置
	//editor.customConfig.uploadImgParamsWithUrl = true
	
	//上传图片时，可自定义filename，即在使用formdata.append(name, file)添加图片文件时，自定义第一个参数。
	editor.customConfig.uploadFileName = 'yourFileName';
	
	//上传图片时刻自定义设置 header
	/* editor.customConfig.uploadImgHeaders = {
	    'Accept': 'text/x-json'
	} */
	
	//跨域上传中如果需要传递 cookie 需设置 withCredentials
	//editor.customConfig.withCredentials = true
	
	//默认的 timeout 时间是 10 秒钟
	editor.customConfig.uploadImgTimeout = 30000
	
	editor.customConfig.uploadImgHooks = {
	    before: function (xhr, editor, files) {
	        // 图片上传之前触发
	        // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，files 是选择的图片文件
	        
	        // 如果返回的结果是 {prevent: true, msg: 'xxxx'} 则表示用户放弃上传
	        // return {
	        //     prevent: true,
	        //     msg: '放弃上传'
	        // }
	    },
	    success: function (xhr, editor, result) {
	        // 图片上传并返回结果，图片插入成功之后触发
	        // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，result 是服务器端返回的结果
	    },
	    fail: function (xhr, editor, result) {
	        // 图片上传并返回结果，但图片插入错误时触发
	        // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，result 是服务器端返回的结果
	        var msg = result.msg;
	        alert(msg);
	    },
	    error: function (xhr, editor) {
	        // 图片上传出错时触发
	        // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象
	    },
	    timeout: function (xhr, editor) {
	        // 图片上传超时时触发
	        // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象
	    },

	    // 如果服务器端返回的不是 {errno:0, data: [...]} 这种格式，可使用该配置
	    // （但是，服务器端返回的必须是一个 JSON 格式字符串！！！否则会报错）
	    customInsert: function (insertImg, result, editor) {
	        // 图片上传并返回结果，自定义插入图片的事件（而不是编辑器自动插入图片！！！）
	        // insertImg 是插入图片的函数，editor 是编辑器对象，result 是服务器端返回的结果

	        // 举例：假如上传图片成功后，服务器端返回的是 {url:'....'} 这种格式，即可这样插入图片：
	        // result 必须是一个 JSON 格式字符串！！！否则报错
	        var errorCode = result.errorCode;
	        if(errorCode==0){
	        	var urlList = result.data;
	        	for(var i=0;i<urlList.length;i++){
			        insertImg(urlList[i]);
	        	}
	        }else{
	        	var errorMsg = result.msg;
	        	alert(errorMsg);
	        }
	    }
	}
	//上传图片的错误提示默认使用alert弹出，你也可以自定义用户体验更好的提示方式
	editor.customConfig.customAlert = function (info) {
	    // info 是需要提示的内容
	    alert('自定义提示：' + info)
	}

	editor.create();


$(function(){
	$('#carousel').carousel({
		 interval: 3000
	})
	
	var html = '<div class="pt hm">'
	+'您需要登录后才可以回帖 <a href="member.php?mod=logging&amp;action=login" onclick="showWindow(\'login\', this.href)" class="xi2">登录</a> | <a href="member.php?mod=register-vipfenxiango0OlI1" class="xi2">立即注册</a>'
	+'<a href="https://bbs.vip866.com/connect.php?mod=login&amp;op=init&amp;referer=forum.php%3Fmod%3Dviewthread%26tid%3D58734%26extra%3Dpage%253D1%26page%3D1&amp;statfrom=login" target="_top" rel="nofollow"><img src="template/yeei_dream/yeei_cn//qq_login.gif" class="vm"></a>'
	+'<a href="javascript:;" onclick="showWindow(\'wechat_bind1\', \'plugin.php?id=xigua_login:login\')"><img src="source/plugin/xigua_login/static/wechat_login1.png" align="absmiddle"></a> </div>';
	editor.txt.html(html);
})
//展示发布内容
function showMainContent(){
	$("#editorDiv").show();
}
//根据分类展示列表
function showRightList(type){
	if("1"==type){
		console.log("sdas");
	}
}

//提交发布内容
function subEditor(){
	var content = editor.txt.html();
	$.ajax({
		url:"<%=path %>/art/newContent/saveContent.do",
		type:"post",
		data:{
			"title":$("#title").val(),
			"content":content
			},
		success:function(data){
			var status = data.status;
			var msg = data.msg;
			if(data.success){
				alert("保存成功");
				window.location.reload()
			}else{
				alert(data.message);
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