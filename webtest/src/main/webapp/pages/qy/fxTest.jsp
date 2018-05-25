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
<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<title>分享测试页面</title>
<style>
img{
	width:100px;
	height:100px;
}
</style>
</head>
<body>

<div id="picDiv">
	<img alt="" src="${picUrl }">
	<span>名字</span><input type="text" value="${name }"/>
	<span>时间</span><input type="text" value="${time }"/>
</div>


<input type="hidden" id="appId" value="${appId }"/>
<input type="hidden" id="timestamp" value="${timestamp }"/>
<input type="hidden" id="nonceStr" value="${nonceStr }"/>
<input type="hidden" id="signature" value="${signature }"/>

</body>

<script>

var appId = $("#appId").val();
var timestamp = $("#timestamp").val();
var nonceStr = $("#nonceStr").val();
var signature = $("#signature").val();

wx.config({
    beta: true,// 必须这么写，否则在微信插件有些jsapi会有问题
    debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
    appId: appId, // 必填，企业微信的cropID
    timestamp:timestamp , // 必填，生成签名的时间戳
    nonceStr: nonceStr, // 必填，生成签名的随机串
    signature: signature,// 必填，签名，见[附录1](#11974)
    jsApiList: [// 必填，需要使用的JS接口列表，所有JS接口列表见附录2
                'onMenuShareWechat',//“微信”
                'onMenuShareAppMessage'//转发”
                ] 
});


wx.ready(function(){
	
    // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，
    //config是一个客户端的异步操作
    //所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。
    //对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
    
    wx.checkJsApi({
	    jsApiList: ['onMenuShareWechat','onMenuShareAppMessage'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
	    success: function(res) {
	        // 以键值对的形式返回，可用的api值true，不可用为false
	        // 如：{"checkResult":{"chooseImage":true},"errMsg":"checkJsApi:ok"}
	    }
	});
	wx.onMenuShareWechat({
	    title: '测试分享标题', // 分享标题
	    desc: '这是测试用的分享内容', // 分享描述
	    link: 'http://16g2w65893.imwork.net/qy/fxTest.do?id=1', // 分享链接
	    imgUrl: 'http://tmykw.oss-cn-hangzhou.aliyuncs.com/data/1506508941112.jpeg', // 分享图标	
	    success: function () {
	        // 用户确认分享后执行的回调函数
	    	alert("分享给朋友成功");  
	    },
	    cancel: function () {
	        // 用户取消分享后执行的回调函数
	    	alert("分享给朋友失败");  
	    }
	});
    
	wx.onMenuShareAppMessage({
		title: '测试分享标题', // 分享标题
	    desc: '这是测试用的分享内容', // 分享描述
	    link: 'http://16g2w65893.imwork.net/qy/fxTest.do?id=1', // 分享链接
	    imgUrl: 'http://tmykw.oss-cn-hangzhou.aliyuncs.com/data/1506508941112.jpeg', // 分享图标	
	    success: function () {
	        // 用户确认分享后执行的回调函数
	    	 alert("分享给企业朋友成功");  
	    },
	    cancel: function () {
	        // 用户取消分享后执行的回调函数
	    	 alert("分享给企业朋友失败");  
	    }
	});
    
    
});

wx.error(function(res){
    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
});






</script>
</html>