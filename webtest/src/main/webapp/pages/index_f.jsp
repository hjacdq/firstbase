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
<link rel="stylesheet" href="<%=path %>/css/common.css" type="text/css" />
<%-- <link rel="stylesheet" href="<%=path %>/css/index/index.css?v=1.01" type="text/css" /> --%>
<script type="text/javascript" src="<%=path %>/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="<%=path %>/bootstrap/js/bootstrap.min.js"></script>
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
	position: relative;
}
.index_search_div{
	width:980px;
	min-width: 980px;
	height:40px;
	line-height:40px;
	margin:20px auto;
}


.rightContentDiv{
	height:400px;
	width:270px;
	display: inline-block;
	background-color: #fff;
	/* margin:10px 5px; */
	position: absolute;
	top:0px;
}
.img_common{
	height:400px;
	width:270px;
}
.img_hover{
	position: relative;
	width:300px;
	height:450px;
	left:-15px;
}
.hotContntDiv{
	width:100%;
	min-height:200px;
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
		<input type="text" class="searchinput"/>
		<img src="${root}/img/search_32.png" class="searchgo"/>
		<!-- <input type="submit" class="searchgo"/> -->
	</div>
</div>

<!-- 主体部分 -->
<div class="main">
	
	<div class="mainContent">
	</div>
	
	<div class="mainContent">
		<div style="width:300px;">
			<div id="banner" class="carousel slide">
			    <!-- 轮播（Carousel）指标 -->
			    <ol class="carousel-indicators">
			        <li data-target="#banner" data-ride="carousel" data-slide-to="0" class="active"></li>
			        <li data-target="#banner" data-ride="carousel" data-slide-to="1"></li>
			        <li data-target="#banner" data-ride="carousel" data-slide-to="2"></li>
			    </ol>   
			    <!-- 轮播（Carousel）项目 -->
			    <div class="carousel-inner">
			        <div class="item active">
			            <img src="${root }/img/index_img_1.jpg" alt="First slide" 
			            style="width:300px;height:400px"/>
			        </div>
			        <div class="item">
			            <img src="${root }/img/index_img_2.jpg" alt="Second slide"
			            style="width:300px;height:400px"/>
			        </div>
			        <div class="item">
			            <img src="${root }/img/index_img_3.jpg" alt="Third slide"
			            style="width:300px;height:400px"/>
			        </div>
			    </div>
			    <!-- 轮播（Carousel）导航 -->
			       <a class="left carousel-control" href="#banner" role="button" data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="right carousel-control" href="#banner" role="button" data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
			</div>
		</div>
		<div class="leftlay">
			<ul>
				<li>XX</li>
				<li>XX</li>
				<li>XX</li>
				<li>XX</li>
				<li>XX</li>
			</ul>
		</div>
		
		<div class="rightlay">
			<div class="rightContentDiv">
				<img src="${root }/img/index_img_1.jpg" class="img_common" />
			</div>
			<div class="rightContentDiv" style="left:290px;">
				<img src="${root }/img/index_img_2.jpg" class="img_common" />
			</div>
			<div class="rightContentDiv" style="left:580px;">
				<img src="${root }/img/index_img_3.jpg" class="img_common" />
			</div>
			
			<div class="hotContntDiv">
				<h3>热门话题</h3>
				<div>
					<span>
						叙利亚自2011年爆发内战以来，俄罗斯与伊朗一直是巴沙尔·阿萨德最主要的国际支持者，并且，俄罗斯与伊朗誓言，将联手阻挡试图推翻叙利亚总统巴沙尔·阿萨德的外部势力。
						俄罗斯在政治和军事阿萨德强有力的支持，在政治上，俄罗斯在联合国上都是同美国唱对台戏力挺阿萨德，多次否决不利叙的决议。在军事上，俄罗斯给予向叙政府军提供空中打击和后勤支援。
					</span>
					<a>更多>></a>
				</div>
				<div>
					<span>
						叙利亚自2011年爆发内战以来，俄罗斯与伊朗一直是巴沙尔·阿萨德最主要的国际支持者，并且，俄罗斯与伊朗誓言，将联手阻挡试图推翻叙利亚总统巴沙尔·阿萨德的外部势力。
						俄罗斯在政治和军事阿萨德强有力的支持，在政治上，俄罗斯在联合国上都是同美国唱对台戏力挺阿萨德，多次否决不利叙的决议。在军事上，俄罗斯给予向叙政府军提供空中打击和后勤支援。
					</span>
					<a>更多>></a>
				</div>
				<div>
					<span>
						叙利亚自2011年爆发内战以来，俄罗斯与伊朗一直是巴沙尔·阿萨德最主要的国际支持者，并且，俄罗斯与伊朗誓言，将联手阻挡试图推翻叙利亚总统巴沙尔·阿萨德的外部势力。
						俄罗斯在政治和军事阿萨德强有力的支持，在政治上，俄罗斯在联合国上都是同美国唱对台戏力挺阿萨德，多次否决不利叙的决议。在军事上，俄罗斯给予向叙政府军提供空中打击和后勤支援。
					</span>
					<a>更多>></a>
				</div>
			</div>
		</div>
	</div>
</div>
			<!-- <span class="add-on"><i class="icon-remove"></i></span>
			<span class="add-on"><i class="icon-th"></i></span> -->

</body>

<script type="text/javascript">
$(function(){
	$(".rightContentDiv img").mouseenter(function(){
		$(this).removeClass("img_common");
		$(this).addClass("img_hover");
	})
	$(".rightContentDiv img").mouseout(function(){
		$(this).removeClass("img_hover");
		$(this).addClass("img_common");
	})
})
//$(".user_logdiv").slideToggle(300);

</script>
</html>