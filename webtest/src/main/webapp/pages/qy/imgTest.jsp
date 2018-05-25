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
<title>图片上传测试页面</title>
<style>
.picImg{
	width:100px;
	height:100px;
}

#img{
	width:60px;
	height:60px;
}
</style>
</head>
<body>

<div id="picDiv">
	
</div>
<!-- <img  src="http://tmykw.oss-cn-hangzhou.aliyuncs.com/data/1506505704515.jpg"> -->
<img id="img" src="<%=path %>/style/images/camara.png">

<button onclick="getLacation();">获取当前地理位置</button>

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
var imgArr = [];
var latitude; // 纬度，浮点数，范围为90 ~ -90
var longitude;//经度

wx.config({
    beta: true,// 必须这么写，否则在微信插件有些jsapi会有问题
    debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
    appId: appId, // 必填，企业微信的cropID
    timestamp:timestamp , // 必填，生成签名的时间戳
    nonceStr: nonceStr, // 必填，生成签名的随机串
    signature: signature,// 必填，签名，见[附录1](#11974)
    jsApiList: [// 必填，需要使用的JS接口列表，所有JS接口列表见附录2
                "onMenuShareWechat",
                "chooseImage",
                "uploadImage",
                "previewImage",//预览图片
                "getLocation",//获取地理位置接口
                "openLocation"//使用企业微信内置地图查看位置接口
                ] 
});

wx.ready(function(){
    // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，
    //config是一个客户端的异步操作
    //所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。
    //对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
	wx.onMenuShareWechat({
	    title: '测试分享标题', // 分享标题
	    desc: '这是测试用的分享内容', // 分享描述
	    link: 'http://16g2w65893.imwork.net/qy/sdkTest.do', // 分享链接
	    imgUrl: 'http://tmykw.oss-cn-hangzhou.aliyuncs.com/data/1506505704515.jpg', // 分享图标
	    success: function () {
	        // 用户确认分享后执行的回调函数
	    },
	    cancel: function () {
	        // 用户取消分享后执行的回调函数
	    }
	});
    
    //获取当前地理位置
	wx.getLocation({
	    type: 'gcj02', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
	    success: function (res) {
	        latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
	        longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
	        var speed = res.speed; // 速度，以米/每秒计
	        var accuracy = res.accuracy; // 位置精度
	    }
	});
});

wx.error(function(res){
    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
});

$(function(){
	//预览上传的图片
	$("#picDiv").on("click",".picImg",function(){
		$("#picDiv .picImg").each(function(){
			var src =  $(this).attr("src");
			imgArr.push(src);
		});
		wx.previewImage({
		    current: imgArr[0], // 当前显示图片的http链接
		    urls: imgArr // 需要预览的图片http链接列表
		});
	})
	
})

//点击选择图片
$("#img").click(function(){
	if(imgArr.length>=3){
		alert("最多上传三张图片");
		return false;
	}
	wx.chooseImage({
	    count: 1, // 默认9
	    sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
	    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
	    success: function (res) {
	        //var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
	        var localId = res.localIds[0];
	        uptoWX(localId);
	    }
	});
})

//图片上传到微信(图片太小，只能通过上传到微信后，再下载到本地)
function uptoWX(localId){
	wx.uploadImage({
	    localId: localId, // 需要上传的图片的本地ID，由chooseImage接口获得
	    isShowProgressTips: 1, // 默认为1，显示进度提示
	    success: function (res) {
	        var serverId = res.serverId; // 返回图片的服务器端ID
	        downWX(serverId.toString());
	    },
	    fail:function(){
	    	alert("拍照失败，请重试!");
	    	return false;
	    }
	});
}
//后台从微信服务器获取临时图片素材，再上传到阿里云，获取阿里云的图片链接
function downWX(serverId){
	jQuery.ajax({
		url:"/qy/getPhoto.do",
		type:"post",
		data:{"serverId":serverId},
		dataType:"json",
		success:function(data){
			if(data.status=='success'){
				var imgUrl = data.imgUrl;//阿里云图片地址
				var html = "<img  src='"+imgUrl+"' class='picImg'/>";
				$("#picDiv").append(html);
			}
		},
		error:function(){
			alert("网络异常!");
		}
	})
}

//获取当前地理位置
function getLacation(){
	wx.openLocation({
	    latitude: latitude, // 纬度，浮点数，范围为90 ~ -90
	    longitude: longitude, // 经度，浮点数，范围为180 ~ -180。
	    name: '当前位置', // 位置名
	    address: '', // 地址详情说明
	    scale: 16, // 地图缩放级别,整形值,范围从1~28。默认为16
	});
}


</script>
</html>