var pageCount = 8;
var zp_totalList = [];//第一次进入页面时，获取的所有招聘信息数据

//手动分页
function doPageSplit(pageSize,company){
	var matchList = [];//根据公司筛选的结果
	var showList = [];//显示的结果
	$(".dja_list").find(".li").hide();
	if(!pageSize || isNaN(pageSize) || pageSize==0){
		pageSize = 1;
	}
	if(pageSize==1 && company=='XXXX'){//第一次进入页面
		zp_totalList = $(".dja_list").find(".li");
	}
	if(zp_totalList.length==0){
		return false;
	}
	for(var i=0;i<zp_totalList.length;i++){
		if(company=="XXX")//全部，不做筛选
			break;
		if(zp_totalList[i].child(".xxx").html()==company){//筛选公司
			matchList.push(zp_totalList[i]);
		}
	}
	var totalCount = matchList.length;
	if(pageSize>(totalCount/pageCount)){//超出总数
		matchList = [];
	}else{
		var startIndex = (pageSize-1)*pageCount;
		var endIndex = startIndex+pageCount;
		showList = matchList.slice(startIndex,endIndex);
	}
	updateZpData(showList);//更新分页数据
	updatePageBtn(pageSize,totalCount/pageCount);//更新分页按钮
}
//更新分页数据
function updateZpData(showList){
	for(var i=0;i<showList.length;i++){
		showList[i].show();
	}
}
//更新分页按钮
updatePageBtn(nowPage,totalPage){
	
}