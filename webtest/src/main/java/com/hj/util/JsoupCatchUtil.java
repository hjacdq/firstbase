package com.hj.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupCatchUtil {

	public static void main(String[] args) {
		String url = "http://www.17k.com/chapter/2459058/28420669.html";
		String html = pickData(url);
		System.out.println(html);
//		Element bookContent = analyzeHTMLByString(html);
//		System.out.println(bookContent);
	}
	/*
     * 爬取网页信息
     */
    private static String pickData(String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(url);
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                // 打印响应状态
                if (entity != null) {
                    return EntityUtils.toString(entity);
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    /*
     * 使用jsoup解析网页信息
     */
    private static Element analyzeHTMLByString(String html){
//        String solarDate,lunarDate,chineseAra,should,avoid=" ";
        Document document = Jsoup.parse(html);
        Elements bookContentList = document.getElementsByClass("p");
        Element bookContent = bookContentList.get(0);
        return bookContent;
    }
  
}
