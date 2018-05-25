package com.hj.novel.task;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 *创世
 */
public class ChuangShiConstant {
	
	private ChuangShiConstant(){ }
	
	/**
	 * 抓取首页
	 */
	public static final String url = "http://chuangshi.qq.com/";
	
	/**
	 * key:小说类型
	 * value:小说列表url
	 */
	private static Map<String,String> typeMap = null;
	
	static{
		
	}
	
	public static String getUrlByTypeName(String typeName){
		if(typeMap==null){
			typeMap = new HashMap<String,String>();
			typeMap.put("玄幻", "http://chuangshi.qq.com/bk/xh/");
			typeMap.put("体育", "http://chuangshi.qq.com/bk/ty/");
			typeMap.put("军事", "http://chuangshi.qq.com/bk/js/");
			typeMap.put("都市", "http://chuangshi.qq.com/bk/ds/");
			typeMap.put("灵异", "http://chuangshi.qq.com/bk/ly/");
			typeMap.put("仙侠", "http://chuangshi.qq.com/bk/xx/");
			typeMap.put("历史", "http://chuangshi.qq.com/bk/ls/");
			typeMap.put("游戏", "http://chuangshi.qq.com/bk/yx/");
		}
		return typeMap.get(typeName);
	}
	
}
