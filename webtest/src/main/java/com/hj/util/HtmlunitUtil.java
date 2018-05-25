package com.hj.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlHeading1;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLUListElement;

public class HtmlunitUtil {

	public static void main(String[] args) throws Exception {  
		HtmlunitUtil crawl = new HtmlunitUtil();  
		String url = "http://chuangshi.qq.com/bk/xh/";//玄幻小说列表  
//		String url = "http://chuangshi.qq.com/bk/xh/AGoENV1nVjYAM1RgATUBYA-r-1.html";//第一章  
//		String url = "http://chuangshi.qq.com/bk/xh/14435500.html";//小说首页
//		String url = "http://chuangshi.qq.com/bk/xh/AGkEPV1mVjQAPVRmATEBZA-l.html";//章节列表
        System.out.println("----------------------抓取页面时解析js-------------------");  
        crawl.crawlPageWithAnalyseJs(url);  
    }  
      
    
      
    /** 
     * 功能描述：抓取页面时并解析页面的js 
     * @param url 
     * @throws Exception  
     */  
    public void crawlPageWithAnalyseJs(String url) throws Exception{  
        //1.创建连接client   
        WebClient webClient = new WebClient(BrowserVersion.CHROME);  
        //2.设置连接的相关选项  
        webClient.getOptions().setCssEnabled(false);  
        webClient.getOptions().setJavaScriptEnabled(true);  //需要解析js  
        webClient.getOptions().setThrowExceptionOnScriptError(false);  //解析js出错时不抛异常
        webClient.getOptions().setTimeout(10000);  //超时时间  ms  
        //3.抓取页面  
        HtmlPage page = webClient.getPage(url); 
        //4.将页面转成指定格式  
        webClient.waitForBackgroundJavaScript(2000);   //等侍js脚本执行完成  
//        System.out.println(page.asXml());
        
        
        DomNodeList<DomElement> list =   page.getElementsByTagName("table");
        HtmlTable table = (HtmlTable)list.get(0);
        int rowCount = table.getRows().size();
        int cellCount = 6;//只有6列
        //每页列表的小说集合
        for(int i=1;i<rowCount;i++){//从第二行开始遍历每一行
        	HtmlTableRow row = table.getRows().get(i);
        	List<HtmlTableCell> cellList = row.getCells();
        	for(int j=0;j<cellCount;j++){
        		HtmlTableCell cellOne = cellList.get(j);
        		if(j==2){//小说名
        			DomNodeList<HtmlElement> aList =cellOne.getElementsByTagName("a");
        			if(aList.size()>1){
        				HtmlAnchor bookNameA = (HtmlAnchor)aList.get(0);//书名a标签
        				HtmlAnchor newestA = (HtmlAnchor)aList.get(1);//最新章节a标签
        				String lastChapterUrl = newestA.getAttribute("href");
        				System.out.println(newestA.asText());
//        				book.setFirstChapterUrl(lastChapterUrl.substring(0, lastChapterUrl.lastIndexOf("-")+1)+"1.html");
//        				String totalCountStr = lastChapterUrl.substring(lastChapterUrl.lastIndexOf("-")+1, lastChapterUrl.lastIndexOf("."));
//        				book.setTotalChapterCount(Integer.valueOf(totalCountStr));
        				String href = bookNameA.getAttribute("href");
        			}
        		}else if(j==3){//字数
        		}else if(j==4){//作者
        		}else if(j==5){//更新时间
        		}
        	}
        }
        
        
        
        //5.关闭模拟的窗口  
        webClient.close();  
    }  
}
