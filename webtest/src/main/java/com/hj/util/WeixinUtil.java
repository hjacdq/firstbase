package com.hj.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class WeixinUtil {

	private final static String Token = "123456789abcdef";  
	
	/*
	 * XKT微信服务号
	 */
	private final static String APPID = "wx642981e13a5ea371";
	
	private final static String APPSECRET = "5b63d78a446813af5025ea4264334b08";
	
	
	/** 
	* @Title: getInfoOpenId
	* @Description: TODO(获取snsapi_userinfo授权的openid)
	* @param code
	* @return
	* Map<String,Object>
	* @throws 
	* @creator     :cloud
	* @create date :2017年1月5日 上午10:01:57
	* @modificator :cloud
	* @modify date :2017年1月5日 上午10:01:57
	*/ 
	public static Map<String,Object> getInfoOpenId(String code){
		Map<String,Object> map = new HashMap<String,Object>();
    	String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
    	String para = "appid=wx642981e13a5ea371&secret=5b63d78a446813af5025ea4264334b08&code="+code
 			   +"&grant_type=authorization_code";
    	String json = HttpRequestUtil.sendGet(url, para);
    	if(json ==null || "" == json || "".equals(json)){
    		map.put("errcode", 403);
    		map.put("errmsg", "未能获取数据。");
    		return map;
    	}
 	    JSONObject jsonObject = JSONObject.parseObject(json);
 	    int errcode = (Integer)jsonObject.getIntValue("errcode");
      
 	    if(errcode != 0){//请求不成功
   	    String errmsg = (String)jsonObject.get("errmsg");
   	    	map.put("errcode", errcode);
   	    	map.put("errmsg", errmsg);
   	    	return map;
 	    }
 	    String access_token = (String)jsonObject.get("access_token");//用户网页授权凭证，有效2小时
	    String refresh_token = (String)jsonObject.get("refresh_token");//刷新凭证
	    String openid = (String)jsonObject.get("openid");//用户唯一
	    String scope = (String)jsonObject.get("scope");//用户授权的作用域，使用逗号（,）分隔
	    int expires_in = jsonObject.getIntValue("expires_in");//7200秒
	    map.put("errcode", 0);
	    map.put("openid", openid);
	    map.put("access_token", access_token);
		return map;
	} 
	
	 /** 
	* @Title: toGetUserMsg
	* @Description: TODO(snsapi_userinfo获取用户具体信息)
	* @param access_token
	* @param openid
	* @return
	* Map<String,Object>
	* @throws 
	* @creator     :hongjin
	* @create date :2017年1月5日 上午10:13:49
	* @modificator :hongjin
	* @modify date :2017年1月5日 上午10:13:49
	*/ 
	public static Map<String,Object> toGetUserMsg(String access_token,String openid){
	   Map<String,Object> map = new HashMap<String,Object>();
	   String url = "https://api.weixin.qq.com/sns/userinfo";
	   String para = "access_token="+access_token+"&openid="+openid+"&lang=zh_CN";
	   String json = HttpRequestUtil.sendGet(url, para);
	   
	   if(json ==null || "" == json || "".equals(json)){
           map.put("errcode", 403);
           map.put("errmsg", "未能获取用户具体信息。");
           return map;
       }
	   
	   JSONObject jsonObject = JSON.parseObject(json);
	   
	   if(jsonObject==null){
           map.put("errcode", 403);
           map.put("errmsg", "信息解析异常");
           return map;
	   }
	   
       int errcode = (Integer)jsonObject.getIntValue("errcode");
       if(errcode != 0){//请求不成功
    	   String errmsg = (String)jsonObject.get("errmsg");
           map.put("errcode", errcode);
           map.put("errmsg", errmsg);
           return map;
       }
       
       map.put("errcode", 0);
	   String nickname = (String)jsonObject.get("nickname");//用户昵称
	   int sex = jsonObject.getIntValue("sex");//1:男  2：女  0:未知
	   String headimgurl = (String)jsonObject.get("headimgurl");
	   
	   String unionid = (String)jsonObject.get("unionid"); //unionid
	   
	   if(sex ==1){
		   map.put("sex", "男");
	   }else if(sex==2){
		   map.put("sex", "女");
	   }else{
		   map.put("sex", "未知");
	   }
	   
	   map.put("nickname", nickname);
	   map.put("headimgurl", headimgurl);
	   map.put("unionid", unionid);
	   return map;
   }
	
	/**
     * XML格式字符串转换为Map
     *
     * @param strXML XML字符串
     * @return XML数据转换后的Map
     * @throws Exception
     */
	public static Map<String, String> xmlToMap(String strXML) throws Exception {
        try {
            Map<String, String> data = new HashMap<String, String>();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
            org.w3c.dom.Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            try {
                stream.close();
            } catch (Exception ex) {
                // do nothing
            }
            return data;
        } catch (Exception ex) {
            throw ex;
        }

    }
	
	/**
     * 将Map转换为XML格式的字符串
     *
     * @param data Map类型数据
     * @return XML格式的字符串
     * @throws Exception
     */
    public static String mapToXml(Map<String, String> data) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder= documentBuilderFactory.newDocumentBuilder();
        org.w3c.dom.Document document = documentBuilder.newDocument();
        org.w3c.dom.Element root = document.createElement("xml");
        document.appendChild(root);
        for (String key: data.keySet()) {
            String value = data.get(key);
            if (value == null) {
                value = "";
            }
            value = value.trim();
            org.w3c.dom.Element filed = document.createElement(key);
            filed.appendChild(document.createTextNode(value));
            root.appendChild(filed);
        }
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource source = new DOMSource(document);
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
        String output = writer.getBuffer().toString(); //.replaceAll("\n|\r", "");
        try {
            writer.close();
        }
        catch (Exception ex) {
        }
        return output;
    }
}
