package com.hj.controller;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.hj.util.HttpRequestUtil;
import com.hj.util.StringUtil;
import com.hj.util.WXPayConstants;
import com.hj.util.WXPayUtil;
import com.hj.util.WeixinUtil;


/**   
* @Title: WXPayController.java
* @Package com.xk.bsj.front.web.controller
* @Description: TODO(用于测试微信支付回调)
* @author cloud
* @date 2017年8月24日 上午10:04:57
* @version V1.0
* Copyright (c) 2015, 博思堂博视界.
*/ 
@Controller
@RequestMapping(value = "/wxpay")
@SuppressWarnings("unchecked")
public class WXPayController{

	protected static final Log logger = LogFactory.getLog(WXPayController.class);
	
	public Map<String,Object> PageData(HttpServletRequest request){
	     
	      Map<String,String[]> properties = request.getParameterMap();
	      Map<String,Object> returnMap = new HashMap<String,Object>(); 
	      Iterator entries = properties.entrySet().iterator(); 
	      Map.Entry entry; 
	      String name = "";  
	      String value = "";  
	      while (entries.hasNext()) {
	        entry = (Map.Entry) entries.next(); 
	        name = (String) entry.getKey(); 
	        Object valueObj = entry.getValue(); 
	        if(null == valueObj){ 
	          value = ""; 
	        }else if(valueObj instanceof String[]){ 
	          String[] values = (String[])valueObj;
	          for(int i=0;i<values.length;i++){ 
	             value = values[i] + ",";
	          }
	          value = value.substring(0, value.length()-1); 
	        }else{
	          value = valueObj.toString(); 
	        }
	        returnMap.put(name, value); 
	      }
	      return  returnMap;
	    }
	
	
	/*=====================页面跳转=======================*/
	
	
	//扫码支付首页
	@RequestMapping("/payIndex")
	public ModelAndView toLogin() {
		return new ModelAndView("template/weixin/payIndex");
	}
	
	//支付成功页面
	@RequestMapping("/paySuccess")
	public String paySuccess(HttpServletRequest req){
		Map<String,Object> result = toCheckOrder(req);
		Integer errorCode = (Integer)result.get("errorCode");
//		if(errorCode!=null && errorCode==200){
//			String trade_state = (String)result.get("trade_state");//交易状态
//			Integer cash_fee = (Integer)result.get("cash_fee");//交易金额,精确到分
//			String time_end = (String)result.get("time_end");//完成交易时间
//			
//		}
		return "/html/weixin/paySuccess.html";
	}
	
	//查询订单页面
	@RequestMapping("/checkOrder")
	public ModelAndView checkOrder(){
		return new ModelAndView("template/weixin/checkOrder");
	}
	
	//选择公众号支付
	@RequestMapping("/choose")
	public ModelAndView choose() {
		return new ModelAndView("template/weixin/choose");
	}
	
	//公众号支付首页
	@RequestMapping("/gzpay/gzPayIndex")
	public ModelAndView gongzhongPayIndex(HttpServletRequest req){
		ModelAndView mav = new ModelAndView("template/weixin/gzPayIndex");
		
		/*
		Map<String, Object> params = getBopsParams(req);
		//正在授权绑定,使用base_info方式获取openid
    	String code = (String) params.get("code");
    	System.out.println("code="+code);
    	String openid="";
    	if(StringUtil.isNotEmpty(code)){//线上测试
    		System.out.println("(StringUtil.isNotEmpty(code))");
    		String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
        	String para = "appid="+WXPayConstants.APPID
        			+ "&secret="+WXPayConstants.APPSECRET
        			+ "&code="+code
     			   +"&grant_type=authorization_code";
        	String json = HttpRequestUtil.sendGet(url, para);
        	if(json ==null || "" == json || "".equals(json)){
        		logger.error("未能获取数据。");
        		return new ModelAndView("redirect:/wxpay/choose.htm");
        	}
     	    JSONObject jsonObject = JSONObject.parseObject(json);
     	    int errcode = (Integer)jsonObject.getIntValue("errcode");
    	    openid = (String)jsonObject.get("openid");//用户唯一
        	logger.error("+++++gongzhongPayIndex	WeixinUtil.getInfoOpenId(code)	errcode="+errcode);
        	if(errcode!=0){//发生异常,返回登录页面
        		return new ModelAndView("redirect:/wxpay/choose.htm");
        	}
    	}
		mav.addObject("openid", openid);
		*/
		
		return mav;
	}
	
	/*========================公众号支付==============================*/
	
	//==============================================================================
		//*             公众号支付 					*//
		//==============================================================================
		
		/** 
		* @Title: getprepayid
		* @Description: TODO(获取预付单信息prepay_id)
		* @param req
		* @return
		* Map<String,Object>
		* @throws 
		* @creator     :hj
		* @create date :2017年8月25日 上午9:22:53
		* @modificator :hj
		* @modify date :2017年8月25日 上午9:22:53
		*/ 
		@ResponseBody
		@RequestMapping("/getprepayid")
		public Map<String,Object> getprepayid(HttpServletRequest req){
			Map<String,Object> result = new HashMap<String,Object>();
			Map<String,Object> params = PageData(req);
			String money = (String)params.get("money");
			String openid = (String)params.get("openid");
			System.out.println("money="+money);
			if(StringUtil.isEmpty(money)){
				result.put("errorCode", 201);
				result.put("errorMsg", "金额不正确");
				return result;
			}
			Map<String,String> paramMap = new HashMap<String,String>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String total_fee =  (int)(Float.valueOf(money).floatValue()*100)+"";
			
			String nonce_str = WXPayUtil.generateNonceStr();//随机码
	        ///商品订单号,同一商户号下唯一
	        String out_trade_no =getTimeStr()+UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6);
	        result.put("out_trade_no", out_trade_no);//商户订单号，用户支付成功后查询订单信息
	        String product_id = "bst"+sdf.format(new Date());//商品编号
	        String time_start =getTimeStr();//交易起始时间(订单生成时间非必须)
	        String notify_url = "http://m.zhu007.com/wxpay/receivePayResult.htm";//回调函数
	        
	        paramMap.put("appid", WXPayConstants.APPID);
	        paramMap.put("mch_id", WXPayConstants.MCH_ID);
	        paramMap.put("device_info", "WEB"); //设备号
	        paramMap.put("nonce_str", nonce_str);
	        paramMap.put("body", "测试充值");//商品描述
	        paramMap.put("out_trade_no", out_trade_no);
	        paramMap.put("product_id", product_id);
	        paramMap.put("total_fee", total_fee);
	        paramMap.put("time_start", time_start);
	        paramMap.put("trade_type", "JSAPI");
	        paramMap.put("notify_url", notify_url); 
	        if(StringUtil.isEmpty(openid)){
	        	openid = "oyU1Cv2_D02xepf87m2tFLsrGsg0";
	        }
	        paramMap.put("openid",openid);
	        //调用统一下单API(参数无需签名)
	        Map<String,String> jsonMap = doMainOrder(paramMap);
	        
	        if(jsonMap==null){
	        	result.put("errorCode", 202);
				result.put("errorMsg", "统一下单失败");
				return result;
	        }
	        
	        System.out.println("jsonMap.toString()="+jsonMap.toString());
	        String return_code = jsonMap.get("return_code").toString();//返回状态码
	    	String return_msg = jsonMap.get("return_msg").toString();//返回信息 
	        if(StringUtil.equals("SUCCESS", return_code)){
	        	String result_code = jsonMap.get("result_code").toString();//业务结果
	        	if(StringUtil.equals("SUCCESS", result_code)){
	        		String prepay_id = jsonMap.get("prepay_id").toString();//预支付交易会话标识,有效期2小时
	            	result.put("prepay_id", prepay_id);
	            	//生成页面参数的签名
	            	paramMap.clear();
	            	String nonceStr = WXPayUtil.generateNonceStr();//随机字符串
	            	String timeStamp = WXPayUtil.getCurrentTimestamp()+"";
	            	paramMap.put("appId", WXPayConstants.APPID);
	            	paramMap.put("timeStamp",timeStamp);//时间戳
	            	paramMap.put("nonceStr", nonceStr);
	            	paramMap.put("package", "prepay_id="+prepay_id);
	            	paramMap.put("signType", "MD5");
	            	String paySign = null;
	            	try {
						paySign = WXPayUtil.generateSignature(paramMap, WXPayConstants.paternerKey);
					} catch (Exception e) {
						e.printStackTrace();
					}finally{
						if(paySign==null){
							result.put("errorCode", 202);
			        		result.put("errorMsg", "生成paySign失败");
		            	}else{
		            		result.put("errorCode", 200);
		            		result.put("appId", WXPayConstants.APPID);
		            		result.put("timeStamp",timeStamp);
			        		result.put("paySign", paySign);
			        		result.put("nonceStr", nonceStr);
		            	}
					}
//	            	System.out.println("prepay_id="+prepay_id+",appId="+WXPayConstants.APPID+",timeStamp="+timeStamp
//	            			+",paySign="+paySign+",nonceStr="+nonceStr);
	        	}else{
	        		String err_code = jsonMap.get("err_code").toString();//错误代码
	        		String err_code_des = jsonMap.get("err_code_des").toString();//错误代码描述
	        		result.put("errorCode", 202);
	        		result.put("errorMsg", "err_code="+err_code+",err_code_des="+err_code_des);
	        	}
	        }else{
	        	result.put("errorCode", 202);
	        	result.put("errorMsg", return_msg);
	        }
			return result;
			
		}
		
	/*==============================公共方法=======================*/
		
	/** 
	* @Title: doMainOrder
	* @Description: TODO(//统一下单API)
	* @param paramMap
	* @return
	* Map<String,String>
	* @throws 
	* @creator     :hj
	* @create date :2017年8月25日 上午9:30:01
	* @modificator :hj
	* @modify date :2017年8月25日 上午9:30:01
	*/ 
	public Map<String,String> doMainOrder(Map<String,String> paramMap){
		String xmlParams=null;
		try {
			//参数xml化 (方法中已把sign签名加入)
			xmlParams = WXPayUtil.generateSignedXml(paramMap,WXPayConstants.paternerKey);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(xmlParams==null){
			logger.error("doMainOrder 方法 : 生成xml失败");
			System.out.println("生成xml失败");
			return null;
		}
        //判断返回码
        String jsonStr = "";
        try {
            jsonStr = HttpRequestUtil.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder", xmlParams,false);// 调用支付接口
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(jsonStr=="" || "".equals(jsonStr) || jsonStr.length()<1){//请求失败
        	logger.error("doMainOrder 方法 : 请求失败");
			System.out.println("生成xml失败");
        	return null;
        }
        Map<String, String> jsonMap = null;//微信系统回调的参数
        try {
			jsonMap = WXPayUtil.xmlToMap(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return jsonMap;
	}
	
	/** 
	* @Title: toCheckOrder
	* @Description: TODO(查询订单)
	* @param req
	* @return
	* Map<String,Object>
	* @throws 
	* @creator     :hj
	* @create date :2017年8月24日 下午1:43:01
	* @modificator :hj
	* @modify date :2017年8月24日 下午1:43:01
	*/ 
	@ResponseBody
	@RequestMapping("/toCheckOrder")
	public Map<String,Object> toCheckOrder(HttpServletRequest req){
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> params = PageData(req);
		String transaction_id  = (String)params.get("transaction_id");//微信订单号
		String out_trade_no  = (String)params.get("out_trade_no");//商户订单号 
		//二选一方式查询订单号,微信的订单号，建议优先使用 
		Map<String,String> kvs = new HashMap<String,String>();
		kvs.put("appid", WXPayConstants.APPID);
		kvs.put("mch_id", WXPayConstants.MCH_ID);
		if(StringUtil.isEmpty(transaction_id) && StringUtil.isEmpty(out_trade_no)){
			result.put("errorCode", 201);
			result.put("errorMsg", "请填写订单号");
			return result;
		}
		if(StringUtil.isNotEmpty(transaction_id)){
			kvs.put("transaction_id", transaction_id.trim());
		}else{
			kvs.put("out_trade_no", out_trade_no.trim());
		}
		kvs.put("nonce_str", WXPayUtil.generateNonceStr());
		String xmlParams = null;
		try {
			//参数xml化 (方法中已把sign签名加入)
			xmlParams = WXPayUtil.generateSignedXml(kvs,WXPayConstants.paternerKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(xmlParams==null){
			result.put("errorCode", 201);
			result.put("errorMsg", "订单号异常，请检查！");
			return result;
		}
		//判断返回码
        String jsonStr = "";
        try {
        	//微信支付订单的查询
            jsonStr = HttpRequestUtil.sendPost("https://api.mch.weixin.qq.com/pay/orderquery", xmlParams,false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(jsonStr=="" || "".equals(jsonStr) || jsonStr.length()<1){//请求失败
        	result.put("errorCode", 202);
			result.put("errorMsg", "请求失败");
			return result;
        }
        Map<String, String> jsonMap = null;
        try {
			jsonMap = WXPayUtil.xmlToMap(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
        if(jsonMap==null){
        	result.put("errorCode", 202);
			result.put("errorMsg", "转化接收数据格式失败");
			return result;
        }
        System.out.println("jsonMap.toString()="+jsonMap.toString());
        String return_code = jsonMap.get("return_code").toString();//返回状态码
    	String return_msg = jsonMap.get("return_msg").toString();//返回信息
    	System.out.println("返回状态码 return_code="+return_code+"	返回信息 return_msg="+return_msg);
        if("SUCCESS".equals(return_code)){//成功
        	String result_code = jsonMap.get("result_code").toString();//业务结果
        	if("SUCCESS".equals(result_code)){
        		
        		//交易状态
        		String trade_state = jsonMap.get("trade_state").toString();
        		
        		if("SUCCESS".equals(trade_state)){
        			result.put("trade_state", "支付成功 ");
        			String cash_fee = jsonMap.get("cash_fee").toString();//支付现金金额(单位:分)
        			String time_end = jsonMap.get("time_end").toString();//交易结束时间
	        		if(cash_fee!=null && !"".equals(cash_fee)){
	        			result.put("cash_fee", Double.parseDouble(cash_fee)/100);
	        		}else{
	        			result.put("cash_fee",0.00);
	        		}
	        		if(time_end!=null && !"".equals(time_end)){
	        			result.put("time_end",time_end.substring(0,4)+"-"+time_end.substring(4,6)
	        					+"-"+time_end.substring(6,8)+" "+time_end.substring(8,10)
	        					+":"+time_end.substring(10,12)+":"+time_end.substring(12));
	        		}
	        		
            	//如trade_state不为 SUCCESS，则只返回out_trade_no（必传）和attach（选传）。
        		}else if("REFUND".equals(trade_state)){
        			result.put("trade_state", "转入退款");
        		}else if("NOTPAY".equals(trade_state)){
        			result.put("trade_state", "未支付");
        		}else if("CLOSED".equals(trade_state)){
        			result.put("trade_state", "已关闭");
        		}else if("REVOKED".equals(trade_state)){
        			result.put("trade_state", "已撤销");
        		}else if("USERPAYING".equals(trade_state)){
        			result.put("trade_state", "用户支付中");
        		}else if("PAYERROR".equals(trade_state)){
        			result.put("trade_state", "支付失败");
        		}
        		
        		result.put("errorCode", 200);
        		
    			result.put("out_trade_no", out_trade_no);
        	}else{//订单不存在或系统异常
        		String err_code=null;
            	if(jsonMap.get("err_code")!=null){
            		err_code = jsonMap.get("err_code").toString();//错误代码
            	}
            	if(StringUtil.isNotEmpty(err_code)){
            		String err_code_des = jsonMap.get("err_code_des").toString();//错误代码描述
            		result.put("errorCode", 202);
        			result.put("errorMsg","错误码:"+err_code+" 错误描述:"+err_code_des);
            	}
        	}
        }else{//失败
        	result.put("errorCode", 202);
			result.put("errorMsg","状态码异常,return_msg="+return_msg);
        }
		
		return result;
	}
	
	/** 
	* @Title: receivePayResult
	* @Description: TODO(获取支付结果通知)
	* @param request
	* @param response
	* void
	* @throws 
	* @creator     :hj
	* @create date :2017年9月25日 下午2:51:05
	* @modificator :hj
	* @modify date :2017年9月25日 下午2:51:05
	*/ 
	@RequestMapping("/receivePayResult")
	public void receivePayResult(HttpServletRequest request,
            HttpServletResponse response){
		System.out.println("------商户支付结果通知--------");
		logger.error("------商户支付结果通知--------");
		PrintWriter out = null;
        StringBuffer xmlStrBuf = new StringBuffer();
        try {
            BufferedReader reader = request.getReader();
            String line = null;
            while ((line = reader.readLine()) != null) {
            	xmlStrBuf.append(line);
            }   
            String xmlStr = xmlStrBuf.toString();
//            System.out.println("结果通知内容："+xmlStr);
//            logger.error("结果通知内容："+xmlStr);
            Map<String,String> respData = null;
    		try {
    			respData = WeixinUtil.xmlToMap(xmlStr);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		if(respData==null){
    			System.out.println("----------获取商户通知异常----------");
    			logger.error("----------获取商户通知异常----------");
    		}
    		System.out.println("↓↓↓↓↓↓↓↓↓↓↓↓开始打印获取到的参数值↓↓↓↓↓↓↓↓↓↓");
			logger.error("↓↓↓↓↓↓↓↓↓↓↓↓开始打印获取到的参数值↓↓↓↓↓↓↓↓↓↓");
			//获取返回的参数
			String appid = (String)respData.get("appid");
			String bank_type = (String)respData.get("bank_type");//付款银行，银行类型
			String cash_fee = (String)respData.get("cash_fee");//现金支付金额
			String device_info = (String)respData.get("device_info");//设备号
			String fee_type = (String)respData.get("fee_type");//货币种类，默认CNY，人民币
			String is_subscribe = (String)respData.get("is_subscribe");//是否关注公众账号 Y/N
			String mch_id = (String)respData.get("mch_id");//商户号
			String nonce_str = (String)respData.get("nonce_str");//随机数
			String openid = (String)respData.get("openid");//用户标识
			String out_trade_no = (String)respData.get("out_trade_no");//商户系统内部订单号
			String sign = (String)respData.get("sign");//签名
			String time_end = (String)respData.get("time_end");//支付完成时间2009年12月25日9点10分10秒表示为20091225091010
			String trade_type = (String)respData.get("trade_type");//交易类型 JSAPI、NATIVE、APP
			String transaction_id = (String)respData.get("transaction_id");//微信支付订单号
    		String return_code = (String)respData.get("return_code");//返回状态码
    		String return_msg = (String)respData.get("return_msg");//返回信息返回信息，如非空，为错误原因 签名失败 参数格式校验错误
    		
    		System.out.println("appid\t"+appid);
    		System.out.println("bank_type\t"+bank_type);
    		System.out.println("cash_fee\t"+cash_fee);
    		System.out.println("device_info\t"+device_info);
    		System.out.println("fee_type\t"+fee_type);
    		System.out.println("is_subscribe\t"+is_subscribe);
    		System.out.println("mch_id\t"+mch_id);
    		System.out.println("nonce_str\t"+nonce_str);
    		System.out.println("openid\t"+openid);
    		System.out.println("out_trade_no\t"+out_trade_no);
    		System.out.println("sign\t"+sign);
    		System.out.println("time_end\t"+time_end);
    		System.out.println("trade_type\t"+trade_type);
    		System.out.println("transaction_id\t"+transaction_id);
			logger.error("appid	"+appid);
			logger.error("bank_type	"+bank_type);
			logger.error("cash_fee	"+cash_fee);
			logger.error("device_info	"+device_info);
			logger.error("fee_type	"+fee_type);
			logger.error("is_subscribe	"+is_subscribe);
			logger.error("mch_id	"+mch_id);
			logger.error("nonce_str	"+nonce_str);
			logger.error("openid	"+openid);
			logger.error("out_trade_no	"+out_trade_no);
			logger.error("sign	"+sign);
			logger.error("time_end	"+time_end);
			logger.error("trade_type	"+trade_type);
			logger.error("transaction_id	"+transaction_id);
    		
    		Map<String,String> map = new HashMap<String,String>();
    		String xmlMap = null;
    		if("SUCCESS".equals(return_code)){
    			String result_code = (String)respData.get("result_code");//业务结果
    			if("SUCCESS".equals(result_code)){
    				map.put("return_code", "SUCCESS");
    				map.put("return_msg", "ok");
    				System.out.println(">>>>>>>>>>>>支付成功");
    				logger.error(">>>>>>>>>>>>支付成功");
    			}else{
    				map.put("return_code", "FAIL");
    				map.put("return_msg", "交易失败");
    				System.out.println("----------交易失败----------");
    				logger.error("----------交易失败----------");
    			}
    		}else{
    			System.out.println("商户支付通知，签名异常,return_msg:"+return_msg);
    			logger.error("商户支付通知，签名异常,return_msg:"+return_msg);
    			map.put("return_code", "FAIL");
    			map.put("return_msg", "签名失败");
    		}
    		
    		try {
    			xmlMap = WeixinUtil.mapToXml(map);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		if(xmlMap!=null){
    			response.getWriter().write(xmlMap);
    		}else{
    			//手动返回信息
    			response.getWriter().write( "<xml> "
    				  +"<return_code><![CDATA[SUCCESS]]></return_code>"
    				  +"<return_msg><![CDATA[OK]]></return_msg>"
    				+"</xml>");
    		}
        } catch (Exception e) {
        	logger.error("连接超时");
        } finally {
            if (out != null) {
                out.close();
            }
        }
 
	}
	
	/** 
    * @Title: getTimeStr
    * @Description: TODO(获取时间，精确到秒，2017年8月23日10:00:50 -> 20170823100050 )
    * @return
    * String
    * @throws 
    * @creator     :cloud
    * @create date :2017年8月23日 上午10:01:55
    * @modificator :cloud
    * @modify date :2017年8月23日 上午10:01:55
    */ 
    private static String getTimeStr(){
    	Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(date);
    }
	
}
