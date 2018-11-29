package com.hj.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hj.base.WebBaseController;
import com.hj.service.Redis;
import com.hj.util.HttpRequestUtil;
import com.hj.util.SHA1;
import com.hj.util.StringUtil;

@RequestMapping("/qy")
@Controller
public class QYController extends WebBaseController{

	/*
	 * 博思堂企业微信
	 */
//	private static final String corpId = "wwce722fedda97c34a";
//	
//	private static final String secret = "IlbjNDw5JowMaNjanS4GQYNwTTCLKab1YfFqXalP3V0";
//	
//	private static final String access_token="uMhg_jyoLVwIzfHfymVIaLqQ1fXOXDY37WPq6Qd8XbDLuSwRPp--D1wIjkNMPioU4Dr95pjw2raNX5vKlGCjtGmVtvqTwuK2_Y5AktVwh2TJDEYsAat94HMfqdgjEfpFRB57x0l5D92e_Z66ZdoSlSMNgOre7aGmc_aRJuLcLvb9j8SGWnfhVlbKXnUNxRI4WVPvkhW0L3nGhdhD_xcxcg";
	
	@Autowired
	private com.hj.util.OSSClientUtil OSSClientUtil;
	
	private static final String corpId = "wwc197d450ebffe260";
	
	//通讯录同步的秘钥
	private static final String srcret = "lzaH9LunDX8cg8s9forSIbM-Sq1XSd959VHSjACmFTc";
	
	//应用"娱乐"的秘钥
	private static final String yule_secret = "F67H1xW2fAd0ouisEgxQ57jhl0eceU6qhifo7mHgAMY";
	
	private static String access_token = null;
	
	
	//获取access_token
	private String getAccess_Token(String corpid,String corpsecret){
		String result = null;
		String lastTimeStr = Redis.get(corpid+"_"+corpsecret+"_time");
		Long lastTime = 0l;
		boolean flag = false;//是否需要重新拉取
		if(lastTimeStr!=null){//缓存中有access_token
			if(StringUtil.isNumeric(lastTimeStr)){
				lastTime = Long.parseLong(lastTimeStr);
			}
			Long nowTime = new Date().getTime()/1000;
			if(nowTime-lastTime<7200){
				result = Redis.get(corpid+"_"+corpsecret+"_str");
			}else{//缓存数据超时，需要重新拉取
				flag = true;
			}
		}else{//缓存中没有access_token
			flag = true;
		}
		
		if(flag){
			String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
			String param = "corpid="+corpid+"&corpsecret="+corpsecret;
			if(StringUtil.isEmpty(corpid) || StringUtil.isEmpty(corpsecret)){
				return null;
			}
			String json = HttpRequestUtil.sendGet(url, param);
			JSONObject jsonObject = JSONObject.parseObject(json);
			Integer errcode = (Integer)jsonObject.getIntValue("errcode");
			if(errcode!=null && errcode==0){
				result = (String)jsonObject.get("access_token");
				Redis.set(corpid+"_"+corpsecret+"_time",(new Date().getTime()/1000)+"");
				Redis.set(corpid+"_"+corpsecret+"_str",result);
			}else{
				System.out.println("获取access_token失败");
			}
		}
		return result;
	}
	
	//获取部门成员
	private static void getSimplelist(String department_id,String fetch_child){
		String url = "https://qyapi.weixin.qq.com/cgi-bin/user/simplelist";
		String param = "access_token="+access_token+"&department_id="+department_id;
		if(StringUtil.isNotBlank(fetch_child)){
			param+="&fetch_child="+fetch_child;
		}
		String json = HttpRequestUtil.sendGet(url, param);
		JSONObject jsonObject = JSONObject.parseObject(json);
		Integer errcode = (Integer)jsonObject.getIntValue("errcode");
		String errorMsg = (String)jsonObject.get("errmsg");
		if(errcode!=null && errcode==0){
			JSONArray arr = jsonObject.getJSONArray("userlist");
			for (Iterator iterator = arr.iterator(); iterator.hasNext();) { 
		          JSONObject job = (JSONObject) iterator.next();
		          String userid = job.getString("userid");
		          String name = job.getString("name");
		          JSONArray department = job.getJSONArray("department");
		          System.out.println("userid="+userid+",name="+name+",department="+department); 
			}
		}
		
	}
	
	//获取部门列表
	private static void getDepartMentList(String id){
		String url = "https://qyapi.weixin.qq.com/cgi-bin/department/list";
		String param = "access_token="+access_token+"&id="+id;
		String json = HttpRequestUtil.sendGet(url, param);
		JSONObject jsonObject = JSONObject.parseObject(json);
	    Integer errcode = (Integer)jsonObject.getIntValue("errcode");
		String errorMsg = (String)jsonObject.get("errmsg");
		if(errcode!=null && errcode==0){
			JSONArray arr = jsonObject.getJSONArray("department");
			for (Iterator iterator = arr.iterator(); iterator.hasNext();) { 
		          JSONObject job = (JSONObject) iterator.next();
		          Integer departmentId = job.getInteger("id");
		          String name = job.getString("name");
		          Integer parentid = job.getInteger("parentid");
		          Integer order = job.getInteger("order");
		          System.out.println("departmentId="+departmentId+",name="+name
		        		  +",parentid="+parentid+",order="+order); 
			}
		}
		
	}
	
	//userid转openid
	private static void convert_to_openid(String userid,Integer agentid){
		String url ="https://qyapi.weixin.qq.com/cgi-bin/user/convert_to_openid";
		String param = "access_token="+access_token;
		JSONObject outputJson = new JSONObject();
		outputJson.put("userid", userid);
		if(agentid!=null){
			outputJson.put("agentid", agentid);
		}
		String result = HttpRequestUtil.sendPost(url, param, outputJson.toJSONString());
		JSONObject jsonObject = JSONObject.parseObject(result);
		Integer errcode = jsonObject.getInteger("errcode");
		String errmsg = jsonObject.getString("errmsg");
		String openid = jsonObject.getString("openid");
		String appid = jsonObject.getString("appid");
		System.out.println("errcode="+errcode+",errmsg="+errmsg+",openid="+openid+",appid="+appid);
	}
	
	//获取应用列表
	private static void getAgentList(){
		String url = "https://qyapi.weixin.qq.com/cgi-bin/agent/list";
		String param = "access_token="+access_token;
		String json = HttpRequestUtil.sendGet(url, param);
		JSONObject jsonObject = JSONObject.parseObject(json);
		Integer errcode = (Integer)jsonObject.getIntValue("errcode");
		String errorMsg = (String)jsonObject.get("errmsg");
		if(errcode!=null && errcode==0){
			JSONArray arr = jsonObject.getJSONArray("agentlist");
			for (Iterator iterator = arr.iterator(); iterator.hasNext();) { 
		          JSONObject job = (JSONObject) iterator.next();
		          Integer agentid = job.getInteger("agentid");
		          String square_logo_url = job.getString("square_logo_url");
		          String name = job.getString("name");
		          System.out.println("square_logo_url="+square_logo_url+",name="+name+",agentid="+agentid); 
			}
		}
	}
	
	//获取一个成员的信息
	private static void getUserOne(String userid){
		String url = "https://qyapi.weixin.qq.com/cgi-bin/user/get";
		String param = "access_token="+access_token+"&userid="+userid;
		String json = HttpRequestUtil.sendGet(url, param);
		JSONObject jsonObject = JSONObject.parseObject(json);
		Integer errcode = (Integer)jsonObject.getIntValue("errcode");
		String errorMsg = (String)jsonObject.get("errmsg");
		
		String name = (String)jsonObject.get("name");
		String position = (String)jsonObject.get("position");
		String mobile = (String)jsonObject.get("mobile");
		String email = (String)jsonObject.get("email");
		String avatar = (String)jsonObject.get("avatar");
		String english_name = (String)jsonObject.get("english_name");
		System.out.println("name="+name+",position="+position+",mobile="+mobile
				+"email="+email+",avatar="+avatar+",english_name="+english_name);
		
	}
	
	//获取标签列表
	private static void getTagList(){
		String url = "https://qyapi.weixin.qq.com/cgi-bin/tag/list";
		String param ="access_token="+access_token;
		String json = HttpRequestUtil.sendGet(url, param);
		JSONObject jsonObject = JSONObject.parseObject(json);
		Integer errcode = (Integer)jsonObject.getIntValue("errcode");
		String errorMsg = (String)jsonObject.get("errmsg");
		if(errcode!=null && errcode==0){
			JSONArray arr = jsonObject.getJSONArray("taglist");
			for (Iterator iterator = arr.iterator(); iterator.hasNext();) { 
		          JSONObject job = (JSONObject) iterator.next();
		          Integer tagid = job.getInteger("tagid");
		          String tagname = job.getString("tagname");
		          System.out.println("tagid="+tagid+",tagname="+tagname); 
			}
		}
	}
	
	//发送信息
	private static void sendMessage(String touser,String toparty,String msgtype,String text,Integer safe){
		String url = "https://qyapi.weixin.qq.com/cgi-bin/message/send";
		String param ="access_token="+access_token;
		JSONObject outputJson = new JSONObject();
		outputJson.put("touser", touser);
		outputJson.put("toparty",toparty);
		outputJson.put("totag", "");
		outputJson.put("msgtype", msgtype);
		outputJson.put("agentid", null);
		outputJson.put("text",text);
		outputJson.put("safe",safe);//表示是否是保密消息，0表示否，1表示是，默认0
		String result = HttpRequestUtil.sendPost(url,param, outputJson.toJSONString());
		JSONObject jsonObject = JSONObject.parseObject(result);
		Integer errcode = jsonObject.getInteger("errcode");
		String errmsg = jsonObject.getString("errmsg");
		String invaliduser = jsonObject.getString("invaliduser");
		String invalidparty = jsonObject.getString("invalidparty");
		String invalidtag = jsonObject.getString("invalidtag");
		System.out.println("errcode="+errcode+",errmsg="+errmsg+",invaliduser="+invaliduser
				+",invalidparty="+invalidparty+",invalidtag="+invalidtag);
	}
	
	//获取打卡数据
	private static void getcheckindata(List<String> useridlist){
		String url = "https://qyapi.weixin.qq.com/cgi-bin/checkin/getcheckindata";
		String param ="access_token="+access_token;
		JSONObject outputJson = new JSONObject();
		outputJson.put("opencheckindatatype", 3);//打卡类型。1：上下班打卡；2：外出打卡；3：全部打卡
		outputJson.put("starttime",1506787200);
		outputJson.put("endtime", 1507996800);
		outputJson.put("useridlist", useridlist);
		String result = HttpRequestUtil.sendPost(url,param, outputJson.toJSONString());
		JSONObject jsonObject = JSONObject.parseObject(result);
		Integer errcode = jsonObject.getInteger("errcode");
		String errmsg = jsonObject.getString("errmsg");
//		{"errcode":301002,
//		"errmsg":"not allow operate another agent with this accesstoken., hint: [1508399152_d8dba001eb10f95eee098c18864e861f]",
//		"checkindata":[]}
		if(errcode!=null && errcode==0){
			JSONArray arr = jsonObject.getJSONArray("result");
			for (Iterator iterator = arr.iterator(); iterator.hasNext();) { 
		          JSONObject job = (JSONObject) iterator.next();
		          String userid = job.getString("userid");
		          String tagname = job.getString("groupname");
		          String checkin_type = job.getString("checkin_type");
		          String exception_type = job.getString("exception_type");
		          Long checkin_time = job.getLong("checkin_time");
		          String location_title = job.getString("location_title");
		          String location_detail = job.getString("location_detail");
		          System.out.println("userid="+userid+",checkin_type="+checkin_type
		        		  +",checkin_time="+checkin_time+",location_detail="+location_detail); 
			}
		}
	}
	
	//获取审批数据
	private static void getapprovaldata(){
		String url = "https://qyapi.weixin.qq.com/cgi-bin/corp/getapprovaldata";
		String param ="access_token="+access_token;
		JSONObject outputJson = new JSONObject();
		outputJson.put("starttime",1506787200);
		outputJson.put("endtime", 1507996800);
//		outputJson.put("next_spnum", "");
		String result = HttpRequestUtil.sendPost(url,param, outputJson.toJSONString());
		//{"errcode":301002,"errmsg":"not allow operate another agent with this accesstoken., hint: [1508401020_23b456afcde1b3486111da01fdda081c]","data":[]}
		JSONObject jsonObject = JSONObject.parseObject(result);
		Integer errcode = jsonObject.getInteger("errcode");
		String errmsg = jsonObject.getString("errmsg");
		if(errcode!=null && errcode==0){
			Integer count = jsonObject.getInteger("count");
			JSONArray arr = jsonObject.getJSONArray("data");
			for (Iterator iterator = arr.iterator(); iterator.hasNext();) { 
		          JSONObject job = (JSONObject) iterator.next();
		          String spname = job.getString("spname");
		          String apply_name = job.getString("apply_name");
		          String apply_org = job.getString("apply_org");
		          System.out.println("spname="+spname+",apply_name="+apply_name
		        		  +",apply_org="+apply_org); 
			}
		}
		
	}
	
	//获取jsapi_ticket
	private String getTicket(String corpId,String secret){
		access_token = getAccess_Token(corpId, secret);
		String url = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket";
		String param ="access_token="+access_token;
		String json = HttpRequestUtil.sendGet(url, param);
		JSONObject jsonObject = JSONObject.parseObject(json);
		Integer errcode = (Integer)jsonObject.getIntValue("errcode");
		String errmsg = (String)jsonObject.get("errmsg");
		String ticket = null;
		if(errcode!=null && errcode==0 && "ok".equals(errmsg)){
			ticket = (String)jsonObject.get("ticket");
		}
		return ticket;
	}
	
	//获取签名
	private static String getSignature(Map<String,String> data){
//		Set<String> keySet = data.keySet();
//        String[] keyArray = keySet.toArray(new String[keySet.size()]);
//        Arrays.sort(keyArray);
//        StringBuilder sb = new StringBuilder();
//        for (String k : keyArray) {
//            if (data.get(k).trim().length() > 0){
//            	// 参数值为空，则不参与签名
//            	sb.append(k).append("=").append(data.get(k).trim()).append("&");
//            }
//        }
		String noncestr = data.get("noncestr");
		String jsapi_ticket = data.get("jsapi_ticket");
		String timestamp = data.get("timestamp");
		String url = data.get("url");
		StringBuilder sb =  new StringBuilder();
		sb.	append("jsapi_ticket="+jsapi_ticket).append("&");
		sb.	append("noncestr="+noncestr).append("&");
		sb.	append("timestamp="+timestamp).append("&");
		sb.	append("url="+url);
        return SHA1.encode(sb.toString());
	}
	
	//32位随机字符串
	public static String getNonceStr() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
    }
	
	
	/**
	 * 跳转主页
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView toIndex(HttpServletRequest req,HttpServletResponse res){
		String redirect_uri = null;
		try {
			redirect_uri = URLEncoder.encode("http://16g2w65893.imwork.net/qy/OAuth.do", "utf-8");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		
		if(redirect_uri==null){
			System.out.println("redirect_uri==null");
			return new ModelAndView("qy/index");
		}else{
			//snsapi_base：静默授权，可获取成员的基础信息；
			//snsapi_userinfo：静默授权，可获取成员的详细信息，但不包含手机、邮箱；
			//snsapi_privateinfo：手动授权，可获取成员的详细信息，包含手机、邮箱。
			String scope="snsapi_privateinfo";
			String agentid = "1000002";
			String url = "https://open.weixin.qq.com/connect/oauth2/authorize?"
					+ "appid="+corpId
					+ "&redirect_uri="+redirect_uri
					+ "&response_type=code"
					+ "&scope="+scope
					+ "&agentid="+agentid
					+ "&state=abc#wechat_redirect";
			return new ModelAndView("redirect:"+url);
		}
	}
	
	//授权以后的信息
	@RequestMapping("/OAuth")
	public ModelAndView OAuth(HttpServletRequest req,HttpServletResponse res){
		ModelAndView mav = new ModelAndView("qy/index");
		Map<String,Object> params = PageData(req);
		
		String code = (String)params.get("code");
		String state = (String)params.get("state");
		
		access_token = getAccess_Token(corpId, yule_secret);
		
		System.out.println("code="+code+",state="+state);
		String url = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo";
		String param = "access_token="+access_token+"&code="+code;
		
		String json = HttpRequestUtil.sendGet(url, param);
		JSONObject jsonObject = JSONObject.parseObject(json);
		Integer errcode = (Integer)jsonObject.getIntValue("errcode");
		String errorMsg = (String)jsonObject.get("errmsg");
		//企业成员
		String UserId = (String)jsonObject.get("UserId");
		String DeviceId = (String)jsonObject.get("DeviceId");
		String user_ticket = (String)jsonObject.get("user_ticket");
		//非企业成员
		String OpenId = (String)jsonObject.get("OpenId");
		System.out.println("errcode="+errcode+",errorMsg="+errorMsg+",UserId="+UserId+",DeviceId="+DeviceId+",OpenId="+OpenId);
		mav.addObject("UserId",UserId);
		if(StringUtil.isNotEmpty(user_ticket)){
			url = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserdetail";
			param = "access_token="+access_token;
			JSONObject outputJson = new JSONObject();
			outputJson.put("user_ticket", user_ticket);
			String result = HttpRequestUtil.sendPost(url,param, outputJson.toJSONString());
			JSONObject jsonObj = JSONObject.parseObject(result);
			String name = jsonObj.getString("name");
			String position = jsonObj.getString("position");
			String mobile = jsonObj.getString("mobile");
			String gender = jsonObj.getString("gender");
			String email = jsonObj.getString("email");
			String avatar = jsonObj.getString("avatar");
			mav.addObject("name", name);
			mav.addObject("position", position);
			mav.addObject("mobile", mobile);
			mav.addObject("gender", gender);
			mav.addObject("email", email);
			mav.addObject("avatar", avatar);
		}
		
		return mav;
		
	}
	
	//图片上传测试页面
	@RequestMapping("/imgTest")
	public ModelAndView sdkTest(HttpServletRequest req,HttpServletResponse res){
		ModelAndView mav = new ModelAndView("qy/imgTest");
		Map<String,String> data = new HashMap<String,String>();
		
		String noncestr = getNonceStr();
		String jsapi_ticket = getTicket(corpId,yule_secret);
		String timestamp = new Date().getTime()/1000+"";
		String url = "http://16g2w65893.imwork.net/qy/imgTest.do";
		data.put("noncestr", noncestr);
		data.put("jsapi_ticket", jsapi_ticket);
		data.put("timestamp", timestamp);
		data.put("url", url);
		String signature = getSignature(data);
		
		mav.addObject("appId", corpId);
		mav.addObject("timestamp", timestamp);
		mav.addObject("nonceStr", noncestr);
		mav.addObject("signature", signature);
		
		return mav;
	}
	
	@ResponseBody
    @RequestMapping(value = "/getPhoto")
    public Map<String,Object> getPhoto(HttpServletRequest request){
    	Map<String,Object> params = PageData(request);
    	String media_id = (String)params.get("serverId");
    	Map<String,Object> result = new HashMap<String,Object>();
    	if (StringUtil.isBlank(media_id)) {
    		result.put("status", "error");
    		result.put("msg", "media_id为空");
            return result;
        }
    	access_token = getAccess_Token(corpId, yule_secret);
    	String url = "https://qyapi.weixin.qq.com/cgi-bin/media/get?access_token=" + access_token + "&media_id=" + media_id;
    	InputStream is = null;
        try {
        	//获取微信服务器返回的输入流
			URL u = new URL(url);
			HttpURLConnection conn = (HttpURLConnection)u.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			
			is = conn.getInputStream();
			//返回的输入流转化为byte[]
			ByteArrayOutputStream out=new ByteArrayOutputStream();
	        byte[] buffer=new byte[1024*4];
	        int n=0;
	        while ( (n=is.read(buffer)) !=-1) {
	            out.write(buffer,0,n);
	        }
	        //阿里云上传
	        SimpleDateFormat dataFormat = new SimpleDateFormat(
					"yyyyMMddHHmmssSSSS");
			Date currentTime = new Date();
			String postName = ".jpg";
			String subcontextPath = dataFormat.format(currentTime) + postName;
			String contextPath = subcontextPath;
			
			byte[] bytes = out.toByteArray();
			
			boolean flag = OSSClientUtil.writeFile(bytes, contextPath);
			String imgUrl=null;
			if(flag){
				imgUrl= "http://tmykw.oss-cn-hangzhou.aliyuncs.com/data/"+contextPath;
			}
			result.put("status", "success");
			result.put("imgUrl", imgUrl);
			System.out.println(imgUrl);
			is.close();
			
			return result;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        result.put("status", "error");
		result.put("msg", "系统异常");
        return result;
    	
    }
	
	//注:当前版本不支持onMenuShareWechat,onMenuShareAppMessage接口
	//分享测试
	@RequestMapping("/fxTest")
	public ModelAndView fxTest(HttpServletRequest req,HttpServletResponse res){
		ModelAndView mav = new ModelAndView("qy/fxTest");
		
		Map<String,Object> params = PageData(req);
		String id = (String)params.get("id");
		if(StringUtil.isNotEmpty(id)){
			mav.addObject("picUrl", "http://tmykw.oss-cn-hangzhou.aliyuncs.com/data/1506505704515.jpg");
			mav.addObject("name","树袋熊");
			mav.addObject("time","2017-10-23");
		}
		
		Map<String,String> data = new HashMap<String,String>();
		
		String noncestr = getNonceStr();
		String jsapi_ticket = getTicket(corpId,yule_secret);
		String timestamp = new Date().getTime()/1000+"";
		String url = "http://16g2w65893.imwork.net/qy/fxTest.do";
		data.put("noncestr", noncestr);
		data.put("jsapi_ticket", jsapi_ticket);
		data.put("timestamp", timestamp);
		data.put("url", url);
		String signature = getSignature(data);
		
		mav.addObject("appId", corpId);
		mav.addObject("timestamp", timestamp);
		mav.addObject("nonceStr", noncestr);
		mav.addObject("signature", signature);
		
		return mav;
	}
	
	public static void main(String[] args) {
		//获取所有部门列表
//		getDepartMentList("");
		//获取信息技术部的人员列表
//		getSimplelist("15","");
		//userid转openid
//		convert_to_openid("SZ1608009",null);
		//获取应用列表
//		getAgentList();
		//获取单个成员的信息
//		getUserOne("SZ1608010");
		//获取标签列表
//		getTagList();
		
		//发送文本消息(目前权限禁用)
//		JSONObject json = new JSONObject();
//		json.put("content", "<a href=\"http://www.baidu.com\">测试消息，请忽略</a>");
//		sendMessage("SZ1608010","15","text",json.toJSONString(),0);
		
//		//获取打卡数据
//		List<String> list = new ArrayList<String>();
//		list.add("SZ1608010");
//		list.add("SZ1608009");
//		getcheckindata(list);
		
		//获取审批数据
//		getapprovaldata();
	}
	
}
