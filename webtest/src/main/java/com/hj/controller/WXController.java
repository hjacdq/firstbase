package com.hj.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.hj.entity.ImageMessage;
import com.hj.entity.InputMessage;
import com.hj.entity.Member;
import com.hj.entity.MsgType;
import com.hj.entity.OutputMessage;
import com.hj.service.MemberService;
import com.hj.util.ConfigUtil;
import com.hj.util.HttpRequestUtil;
import com.hj.util.SHA1;
import com.hj.util.SerializeXmlUtil;
import com.hj.util.WeixinConfig;
import com.hj.util.WeixinJSSDK;
import com.thoughtworks.xstream.XStream;

@RequestMapping("/wx")
@Controller
public class WXController {
	

	Log logger = LogFactory.getLog(MainController.class);
	
	private String Token = "123456789abcdef";  
	
	/*
	 * 微信测试号
	 */
	private final static String APPID = "wxa568763a2294f36b";
	
	private final static String APPSECRET = "1e90d6c1f6a6e220970a39b122e03e83";
	
	/*
	 * XKT微信服务号
	 */
//	private final static String APPID = "wx642981e13a5ea371";
//	
//	private final static String APPSECRET = "5b63d78a446813af5025ea4264334b08";
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value = "/toWebApp")
	public ModelAndView toWebApp(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mav = new ModelAndView("webApp");
	   
	   String code = (String) request.getParameter("code");
	   String state = (String) request.getParameter("state");
	   mav.addObject("state", state);
	   
	   String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
	   String para = "appid="+APPID+"&secret="+APPSECRET+"&code="+code
			   +"&grant_type=authorization_code";
	   String json = HttpRequestUtil.sendGet(url, para);
	   
       if(json ==null || "" == json || "".equals(json)){
    	   mav.addObject("errcode", 403);
           mav.addObject("errmsg", "未能获取数据。");
           return mav;
       }
       JSONObject jsonObject = JSONObject.parseObject(json);
       int errcode = (Integer)jsonObject.getIntValue("errcode");
       
       if(errcode != 0){//请求不成功
    	   String errmsg = (String)jsonObject.get("errmsg");
           mav.addObject("errcode", errcode);
           mav.addObject("errmsg", errmsg);
           return mav;
       }
	   String access_token = (String)jsonObject.get("access_token");//用户网页授权凭证，有效2小时
//		   String refresh_token = (String)jsonObject.get("refresh_token");//刷新凭证
	   String openid = (String)jsonObject.get("openid");//用户唯一
	   String scope = (String)jsonObject.get("scope");//用户授权的作用域，使用逗号（,）分隔
//		   int expires_in = jsonObject.getIntValue("expires_in");//7200秒
	   if("1".equals(state)||"1"==state){////网页授权作用域为snsapi_userinfo
		   mav = toGetUserMsg(access_token,openid,mav);
	   }else if("2".equals(state)||"2"==state){//网页授权作用域为snsapi_base
		   //查询openid是否已有
		   Member member = memberService.getByOpenid(openid);
		   if(member == null){//未绑定信息
			   mav.addObject("isbinding", "no");
		   }else{//已绑定信息
			   mav.addObject("isbinding", "yes");
			   mav.addObject("phone", member.getPhone());
			   mav.addObject("nickname", member.getNickName());
	           mav.addObject("sex", member.getSex());
	           mav.addObject("headimgurl", member.getPicUrl());
		   }
	   }else{
		   mav.addObject("errcode", 405);
           mav.addObject("errmsg", "参数不全");
           return mav;
	   }
	   mav.addObject("openid", openid);
	   return mav;
   }
	
	
	//snsapi_userinfo获取用户具体信息
	   public ModelAndView toGetUserMsg(String access_token,String openid,ModelAndView mav){
		   String url = "https://api.weixin.qq.com/sns/userinfo";
		   String para = "access_token="+access_token+"&openid="+openid+"&lang=zh_CN";
		   String json = HttpRequestUtil.sendGet(url, para);
		   
		   if(json ==null || "" == json || "".equals(json)){
	    	   mav.addObject("errcode", 403);
	           mav.addObject("errmsg", "未能获取用户具体信息。");
	           return mav;
	       }
		   
		   JSONObject jsonObject = null;
		   try{
			   jsonObject = JSON.parseObject(json);
		   }catch(Exception e){
			   e.printStackTrace();
		   }
		   
		   if(jsonObject==null){
			   mav.addObject("errcode", 403);
	           mav.addObject("errmsg", "信息解析异常");
	           return mav;
		   }
		   
	       int errcode = (Integer)jsonObject.getIntValue("errcode");
	       if(errcode != 0){//请求不成功
	    	   String errmsg = (String)jsonObject.get("errmsg");
	           mav.addObject("errcode", errcode);
	           mav.addObject("errmsg", errmsg);
	           return mav;
	       }
		   mav.addObject("errcode", 0);
		   String nickname = (String)jsonObject.get("nickname");//用户昵称
		   int sex = jsonObject.getIntValue("sex");//1:男  2：女  0:未知
		   String headimgurl = (String)jsonObject.get("headimgurl");
		   
		   mav.addObject("nickname", nickname);
	       mav.addObject("sex", sex);
	       mav.addObject("headimgurl", headimgurl);
		   return mav;
	   }
	
	   
	   @ResponseBody
	   @RequestMapping(value = "/toAddUser")
	   public Map<String,Object> toAddAppUser(HttpServletRequest request, HttpServletResponse response){
		   Map<String,Object> result = new HashMap<String,Object>();
		   String phone = (String)request.getParameter("tel");
		   String openid = (String) request.getParameter("openid");
		   String nickname = (String)request.getParameter("nickname");
		   String sex = (String) request.getParameter("sex");
		   String headimgurl = (String) request.getParameter("headimgurl");
		   /*
		   if( StringUtil.isEmpty(phone) || StringUtil.isEmpty(openid) || StringUtil.isEmpty(nickname) ){
			   result.put("status", "error");
			   result.put("msg", "信息不完整，绑定失败！");
			   return result;
		   }
		   */
		   Member member = memberService.getByPhone(phone);
		   if(member==null){
			   result.put("status", "error");
			   result.put("msg", "手机的用户不存在！");
			   return result;
		   }
		   //----------------------------------------测试用
		   if(!StringUtils.isEmpty(member.getOpenId())){
			   result.put("status", "error");
			   result.put("msg", "该手机已绑定其他用户!");
			   return result;
		   }
		   
		   if(member.getSex() ==null || member.getSex() ==""|| "".equals(member.getSex())){
			   if("1".equals(sex)){
				   sex = "男";
			   }else if("2".equals(sex)){
				   sex = "女";
			   }else if("0".equals(sex)){
				   sex = "未知";
			   }
			   member.setSex(sex);
		   }
		   member.setOpenId(openid);
		   member.setNickName(nickname);
		   member.setPicUrl(headimgurl);
		   memberService.update(member);
		   
		   result.put("phone", phone);
		   result.put("status", "success");
		   result.put("msg", "绑定成功!");
		   return result;
	   }
	
	
	   /*
		 * 响应服务器的推送消息
		 */
		@ResponseBody
		@RequestMapping("/getToken")
		public void getToken(HttpServletRequest request,HttpServletResponse response){
			System.out.println("进入了getToken方法");
			boolean isGet = request.getMethod().toLowerCase().equals("get"); 
			if (isGet) {  
	            String signature = request.getParameter("signature");  
	            String timestamp = request.getParameter("timestamp");  
	            String nonce = request.getParameter("nonce");  
	            String echostr = request.getParameter("echostr");  
	            System.out.println(signature);  
	            System.out.println(timestamp);  
	            System.out.println(nonce);  
	            System.out.println(echostr);  
	            access(request, response);  
	        } else {  
	            // 进入POST聊天处理  
	            System.out.println("enter post");  
	            try {  
	                // 接收消息并返回消息  
	                acceptMessage(request, response);  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
		}
		
		//验证url真实性
		private String access(HttpServletRequest request, HttpServletResponse response) {  
			System.out.println("进入验证access");  
	        String signature = request.getParameter("signature");// 微信加密签名  
	        String timestamp = request.getParameter("timestamp");// 时间戳  
	        String nonce = request.getParameter("nonce");// 随机数  
	        String echostr = request.getParameter("echostr");// 随机字符串  
	        List<String> params = new ArrayList<String>();  
	        params.add(Token);  
	        params.add(timestamp);  
	        params.add(nonce);  
	        // 1. 将token、timestamp、nonce三个参数进行字典序排序  
	        Collections.sort(params, new Comparator<String>() {  
	            public int compare(String o1, String o2) {  
	                return o1.compareTo(o2);  
	            }  
	        });  
	        // 2. 将三个参数字符串拼接成一个字符串进行sha1加密  
	        String temp = SHA1.encode(params.get(0) + params.get(1) + params.get(2));
	        if (temp.equals(signature)) {  
	            try {  
	                response.getWriter().write(echostr);  
	                System.out.println("成功返回 echostr：" + echostr);  
	                return echostr;  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	        System.out.println("失败 认证");  
	        return null; 
		}
		
		private void acceptMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
			// 处理接收消息  
	        ServletInputStream in = request.getInputStream();  
	        // 将POST流转换为XStream对象  
	        XStream xs = SerializeXmlUtil.createXstream();  
	        xs.processAnnotations(InputMessage.class);  
	        xs.processAnnotations(OutputMessage.class);  
	        // 将指定节点下的xml节点数据映射为对象  
	        xs.alias("xml", InputMessage.class);  
	        // 将流转换为字符串  
	        StringBuilder xmlMsg = new StringBuilder();  
	        byte[] b = new byte[4096];  
	        for (int n; (n = in.read(b)) != -1;) {  
	            xmlMsg.append(new String(b, 0, n, "UTF-8"));  
	        }  
	        // 将xml内容转换为InputMessage对象  
	        InputMessage inputMsg = (InputMessage) xs.fromXML(xmlMsg.toString());  
	  
	        String servername = inputMsg.getToUserName();// 服务端  
	        String custermname = inputMsg.getFromUserName();// 客户端  
	        long createTime = inputMsg.getCreateTime();// 接收时间  
	        Long returnTime = Calendar.getInstance().getTimeInMillis() / 1000;// 返回时间  
	  
	        // 取得消息类型  
	        String msgType = inputMsg.getMsgType();  
	        // 根据消息类型获取对应的消息内容  
	        if (msgType.equals(MsgType.Text.toString())) {  
	            // 文本消息  
//	        	response.getWriter().write(""); //服务器获取信息后5秒内返回一个空字符串，防止微信服务器重复发送
//	        	sendCustomerMsg();
//	            System.out.println("开发者微信号：" + inputMsg.getToUserName());  
//	            System.out.println("发送方帐号：" + inputMsg.getFromUserName());  
//	            System.out.println("消息创建时间：" + inputMsg.getCreateTime() + new Date(createTime * 1000l));  
//	            System.out.println("消息内容：" + inputMsg.getContent());  
//	            System.out.println("消息Id：" + inputMsg.getMsgId());  
	  
	            StringBuffer str = new StringBuffer();  
	            str.append("<xml>");  
	            str.append("<ToUserName><![CDATA[" + custermname + "]]></ToUserName>");  
	            str.append("<FromUserName><![CDATA[" + servername + "]]></FromUserName>");  
	            str.append("<CreateTime>" + returnTime + "</CreateTime>");  
	            str.append("<MsgType><![CDATA[" + msgType + "]]></MsgType>");  
	            str.append("<Content><![CDATA[[奸笑]你说的是：" + inputMsg.getContent() + "吗？]]></Content>");  
	            str.append("</xml>");  
	            System.out.println(str.toString());  
	            String result = new String(str.toString().getBytes(),"UTF-8");
	            response.setCharacterEncoding("utf-8");
	            response.getWriter().write(result);  
	        }  
	        
	        
	        // 获取并返回多图片消息  
	        if (msgType.equals(MsgType.Image.toString())) {  
	            System.out.println("获取多媒体信息");  
	            System.out.println("多媒体文件id：" + inputMsg.getMediaId());  
	            System.out.println("图片链接：" + inputMsg.getPicUrl());  
	            System.out.println("消息id，64位整型：" + inputMsg.getMsgId());  
	  
	            OutputMessage outputMsg = new OutputMessage();  
	            outputMsg.setFromUserName(servername);  
	            outputMsg.setToUserName(custermname);  
	            outputMsg.setCreateTime(returnTime);  
	            outputMsg.setMsgType(msgType);  
	            ImageMessage images = new ImageMessage();  
//	            images.setMediaId(inputMsg.getMediaId());  
	            images.setMediaId("9czyn5UgGbKLsP1I_s7aGkDqsldsrc7Q7Kzlz2Js5tE");
	            outputMsg.setImage(images);  
	            System.out.println("xml转换：/n" + xs.toXML(outputMsg));  
	            response.getWriter().write(xs.toXML(outputMsg));  
	  
	        }
		}
		
		
		/** 
		* @Title: getAccessToken
		* @Description: TODO(获取基础类access_token)
		* @return
		* String
		* @throws 
		* @creator     :cloud
		* @create date :2017年1月3日 下午3:49:11
		* @modificator :cloud
		* @modify date :2017年1月3日 下午3:49:11
		*/ 
		public static String getAccessToken(){
			String url = "https://api.weixin.qq.com/cgi-bin/token";
			String param = "grant_type=client_credential&appid="+APPID+"&secret="+APPSECRET;
			String json = HttpRequestUtil.sendGet(url, param);
			JSONObject jsonObject = JSONObject.parseObject(json);
			int errcode = jsonObject.getIntValue("errcode");
		       
	       if(errcode != 0){//请求不成功
	    	   String errmsg = (String)jsonObject.get("errmsg");
	    	   return null;
	       }else{
	    	   int expires_in = jsonObject.getIntValue("expires_in");//7200秒
	    	   return (String)jsonObject.get("access_token");
	       }
		}
		
	//获取微信jssdk_ticket
	public Map<String, String> getTicket(HttpServletRequest request) {
	    String url = request.getScheme() + "://";
	    url += request.getHeader("host");
	    url += request.getRequestURI();
	    if (request.getQueryString() != null) {
	      url += "?" + request.getQueryString();
	    }
	    String appId = ConfigUtil.getProperties().getProperty("wx.appId");
	    WeixinConfig wxpayconfig = new WeixinConfig();
	    wxpayconfig.setAppId(appId);
	    wxpayconfig.setAppSecret(ConfigUtil.getProperties().getProperty("wx.appSecret"));
	    String ticket = null;
//	    String ua = request.getHeader("user-agent").toLowerCase();
//	    if (ua.indexOf("wxwork") > 0) {//企业微信入口
//	      String corpid = ConfigUtil.getProperties().getProperty("corpId");
//	      String corpsecret = ConfigUtil.getProperties().getProperty("fx_secret");
//	      ticket = qYWeixinService.getJsapiTicket(corpid, corpsecret);
//	    } else if (ua.indexOf("micromessenger") > 0) {// 微信入口
//	      ticket = weixinService.createJsapiTicket(wxpayconfig);
//	    }
	    String access_token = getAccessToken();
	    ticket = WeixinJSSDK.getJsApiTicket(access_token);
	    Map<String, String> tuple = WeixinJSSDK.getWxJsConfig(url, ticket);
	    tuple.put("appId", appId);
	    tuple.put("url", url);
	
//	    if (ua.indexOf("iphone") >= 0) {
//	    	tuple.put("phoneType", "iphone");
//	      } else {
//	    	 tuple.put("phoneType", "android");
//	      }
	    return tuple;
	}
		
		/** 
		* @Title: sendCustomerMsg
		* @Description: TODO(向用户发送消息)
		* void
		* @throws 
		* @creator     :cloud
		* @create date :2017年1月3日 下午3:48:45
		* @modificator :cloud
		* @modify date :2017年1月3日 下午3:48:45
		*/ 
		public void sendCustomerMsg(){
			String ACCESS_TOKEN = getAccessToken();
			String openid = "oEnFnws7MACuvq2TpNbhEnaw8RiA";
			String content = "感谢您的消息";
			String strJson = "{\"touser\" :\""+openid+"\",";
	        strJson += "\"msgtype\":\"text\",";
	        strJson += "\"text\":{";
	        strJson += "\"content\":\""+content+"\"";
	        strJson += "}}";
	        String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?&body=0&access_token=" + ACCESS_TOKEN;
	        String json = HttpRequestUtil.sendPost(url, strJson, null);
	        System.out.println("返回结果是："+json);
		}
		
		/** 
		* @Title: addCustomerServicer
		* @Description: TODO(添加客服)
		* void
		* @throws 
		* @creator     :cloud
		* @create date :2017年1月3日 下午3:49:02
		* @modificator :cloud
		* @modify date :2017年1月3日 下午3:49:02
		*/ 
		@RequestMapping("/toAddCS")
		public void addCustomerServicer(){
			String ACCESS_TOKEN = getAccessToken();
			String url = "https://api.weixin.qq.com/customservice/kfaccount/add?access_token="+ACCESS_TOKEN;
			String param = "{\"kf_account\" : \"test1@test\","
				     +"\"nickname\" : \"客服1\","
				     +"\"password\" : \"pswmd5\",}";
			String json = HttpRequestUtil.sendPost(url, param, null);
			JSONObject jsonObject = JSONObject.parseObject(json);
			int errcode = jsonObject.getIntValue("errcode");
			if(errcode ==0){
				System.out.println("添加客服成功!");
			}else{
				String errmsg = (String)jsonObject.get("errmsg");
				System.out.println("errcode:"+errcode+",errmsg:"+errmsg);
			}
		}
		
		@ResponseBody
		@RequestMapping("/togetAllCS")
		public Map<String,Object> getAllCustomerServicer(){
			Map<String,Object> map = new HashMap<String,Object>();
			String ACCESS_TOKEN = getAccessToken();
			String url = "https://api.weixin.qq.com/cgi-bin/customservice/getkflist?access_token="+ACCESS_TOKEN;
			String json = HttpRequestUtil.sendGet(url, "");
			JSONObject jsonObject = JSONObject.parseObject(json);
			int errcode = jsonObject.getIntValue("errcode");
			if(errcode !=0){
				String errmsg = (String)jsonObject.get("errmsg");
				System.out.println("errcode:"+errcode+",errmsg:"+errmsg);
				map.put("errcode", errcode);
				map.put("errmsg", errmsg);
				return map;
			}else{
				map.put("errcode", 0);
			}
			
			JSONArray kf_list = jsonObject.getJSONArray("kf_list");
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			for(int i=0;i<kf_list.size();i++){
				JSONObject obj= (JSONObject)kf_list.get(i);
				Map<String,Object> cs = new HashMap<String,Object>();
				String kf_account = (String)obj.get("kf_account");
				String kf_nick = (String)obj.get("kf_nick");
				String kf_id = (String)obj.get("kf_id");
				String kf_headimgurl = (String)obj.get("kf_headimgurl");
				cs.put("kf_account", kf_account);
				cs.put("kf_nick", kf_nick);
				cs.put("kf_id", kf_id);
				cs.put("kf_headimgurl", kf_headimgurl);
				list.add(cs);
			}
			map.put("cslist", list);
			return map;
		}
		
		//发送模板消息
		public static void getTemplateId(){
			String access_token = ""; 
			access_token = getAccessToken();
//			access_token = "5LCNh9lYpkjBMOosJH8HnvQG3Gl3ETLu2CmrS7uqDVIjfcO9ubLp-QPOImQrIextimw2oiPrU_dnmTFNz9-aBndOQZ4qdY0C7v1clW3Ncs1dqnMLT15aZh1PFDn8VaMIXYSaAGAABV";
			String url = "https://api.weixin.qq.com/cgi-bin/message/template/send";
			String param = "access_token="+access_token;
			
			JSONObject data = packJsonmsg("您好，欢迎您使用XX购物。","212450.62","氪金手环","祝您生活愉快!");
			
			JSONObject json = new JSONObject();
	        try {
	            json.put("touser", "oEnFnws7MACuvq2TpNbhEnaw8RiA");
//	            json.put("touser", "oEnFnwqHwXgcm1-moq7jYmANm2WU");
	            json.put("template_id", "YLsFa4hZy_xnXIlrPOo2qF9qxZFS6LfaFlnZnAPj16o");
	            json.put("url", "http://www.baidu.com");
	            json.put("topcolor", "##FF0000");
	            json.put("data", data);
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
			String result = HttpRequestUtil.sendPost(url, param, json.toJSONString());
			JSONObject jsonObject = JSONObject.parseObject(result);
			Integer errcode = jsonObject.getInteger("errcode");
			String errmsg = jsonObject.getString("errmsg");
			Long msgid = jsonObject.getLong("msgid");
			System.out.println("errcode="+errcode+",errmsg="+errmsg+",msgid="+msgid);
		}
	
		
		
		//封装微信模板:订单支付成功
		public static JSONObject packJsonmsg(String first, String orderMoneySum, String orderProductName, String remark){
	        JSONObject json = new JSONObject();
	        try {
	            JSONObject jsonFirst = new JSONObject();
	            jsonFirst.put("value", first);
	            jsonFirst.put("color", "#173177");
	            json.put("first", jsonFirst);
	            JSONObject jsonOrderMoneySum = new JSONObject();
	            jsonOrderMoneySum.put("value", orderMoneySum);
	            jsonOrderMoneySum.put("color", "#173177");
	            json.put("orderMoneySum", jsonOrderMoneySum);
	            JSONObject jsonOrderProductName = new JSONObject();
	            jsonOrderProductName.put("value", orderProductName);
	            jsonOrderProductName.put("color", "#173177");
	            json.put("orderProductName", jsonOrderProductName);
	            JSONObject jsonRemark = new JSONObject();
	            jsonRemark.put("value", remark);
	            jsonRemark.put("color", "#173177");
	            json.put("Remark", jsonRemark);
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
	        return json;
	    }
		
		public static void main(String[] args) {
			getTemplateId();
		}
	
}
