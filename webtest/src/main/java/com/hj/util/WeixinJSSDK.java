package com.hj.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 微信 js sdk
 * 
 * @author apple
 *
 */
public abstract class WeixinJSSDK {

	private static final Log logger = LogFactory.getLog(WeixinJSSDK.class);

	/**
	 * 获取 js api
	 * 
	 * @param access_token
	 * @return
	 */
	public static String getJsApiTicket(String access_token) {
		String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
		String param = access_token="access_token="+ access_token + "&type=jsapi";
		String json = HttpRequestUtil.sendGet(url, param);
		JSONObject jsonObject = JSONObject.parseObject(json);
		int errcode = jsonObject.getIntValue("errcode");
	       
       if(errcode != 0){//请求不成功
    	   String errmsg = (String)jsonObject.get("errmsg");
    	   return null;
       }else{
    	   int expires_in = jsonObject.getIntValue("expires_in");//7200秒
    	   return (String)jsonObject.get("ticket");
       }
	}

	/**
	 * 三元组，0:noncestr，1:timestamp，2:signature
	 * 
	 * @param url
	 * @param ticket
	 * @return
	 */
	public static Map<String, String> getWxJsConfig(String url, String ticket) {

		 String noncestr = create_nonce_str(); 
		 String timestamp = create_timestamp();  
 
	        //注意这里参数名必须全部小写，且必须有序  
		 String signature  = "jsapi_ticket=" + ticket +  
	                  "&noncestr=" + noncestr +  
	                  "&timestamp=" + timestamp +  
	                  "&url=" + url;  
 	        try  
	        {  
	            MessageDigest crypt = MessageDigest.getInstance("SHA-1");  
	            crypt.reset();  
	            crypt.update(signature.getBytes("UTF-8"));  
	            signature = byteToHex(crypt.digest());  
	        }  
	        catch (NoSuchAlgorithmException e)  
	        {  
	            e.printStackTrace();  
	        }  
	        catch (UnsupportedEncodingException e)  
	        {  
	            e.printStackTrace();  
	        }  
	        
	        
	    	Map<String, String> params = new HashMap<String, String>();
			params.put("noncestr", noncestr);
			params.put("jsapi_ticket", ticket);
			params.put("timestamp", timestamp);
			params.put("url", url);
			params.put("signature", signature);
	  
		return params;
	}
	
	
	 private static String byteToHex(final byte[] hash) {  
	        Formatter formatter = new Formatter();  
	        for (byte b : hash)  
	        {  
	            formatter.format("%02x", b);  
	        }  
	        String result = formatter.toString();  
	        formatter.close();  
	        return result;  
	    }  
	  
	    private static String create_nonce_str() {  
	        return UUID.randomUUID().toString();  
	    }  
	  
	    private static String create_timestamp() {  
	        return Long.toString(System.currentTimeMillis() / 1000);  
	    }  
}
