package com.hj.novel.task;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.NamedNodeMap;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

/**
 * @author Administrator
 *抓取网页数据
 */
public class NovelCatchTask {

	public static void main(String[] args) {
		NovelCatchTask task = new NovelCatchTask();
		task.csTask();
	}
	
	/**
	 * 抓取创世
	 */
	public void csTask(){
		WebClient webClient = new WebClient(BrowserVersion.CHROME);  
		//2.设置连接的相关选项  
		webClient.getOptions().setCssEnabled(false);  
//		webClient.getOptions().setJavaScriptEnabled(true);  //需要解析js  
//		webClient.getOptions().setThrowExceptionOnScriptError(false);  //解析js出错时不抛异常  
		webClient.getOptions().setJavaScriptEnabled(false);  //不解析js  
		webClient.getOptions().setTimeout(10000);  //超时时间  ms  
		//3.抓取页面  
		String url = ChuangShiConstant.getUrlByTypeName("玄幻");
//		int nowPage = 1;
//		int totalPage = 20;
//		while(true){
//			
//			
//			nowPage++;
//			if(nowPage>totalPage){
//				break;
//			}
//		}
		//http://chuangshi.qq.com/bk/xh/p/8.html
		long time1 = System.currentTimeMillis();
		HtmlPage page = null;
		try {
			page = webClient.getPage(url);
		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(page==null){
			return;
		}
		webClient.waitForBackgroundJavaScript(2000); 
		DomNodeList<DomElement> list =   page.getElementsByTagName("table");
        HtmlTable table = (HtmlTable)list.get(0);
        int rowCount = table.getRows().size();
        int cellCount = 6;//只有6列
        //每页列表的小说集合
        List<BookInfo> bookList = new ArrayList<BookInfo>();
        for(int i=1;i<rowCount;i++){//从第二行开始遍历每一行
        	HtmlTableRow row = table.getRows().get(i);
        	List<HtmlTableCell> cellList = row.getCells();
        	BookInfo book = new BookInfo();
        	for(int j=0;j<cellCount;j++){
        		HtmlTableCell cellOne = cellList.get(j);
        		if(j==2){//小说名
        			DomNodeList<HtmlElement> aList =cellOne.getElementsByTagName("a");
        			if(aList.size()>1){
        				HtmlAnchor bookNameA = (HtmlAnchor)aList.get(0);//书名a标签
        				HtmlAnchor newestA = (HtmlAnchor)aList.get(1);//最新章节a标签
        				String lastChapterUrl = newestA.getAttribute("href");
//        				book.setFirstChapterUrl(lastChapterUrl.substring(0, lastChapterUrl.lastIndexOf("-")+1)+"1.html");
        				book.setLastChapterUrl(lastChapterUrl);
//        				String totalCountStr = lastChapterUrl.substring(lastChapterUrl.lastIndexOf("-")+1, lastChapterUrl.lastIndexOf("."));
//        				book.setTotalChapterCount(Integer.valueOf(totalCountStr));
        				String href = bookNameA.getAttribute("href");
        				book.setName(bookNameA.asText());
        				book.setIndexUrl(href);
        			}
        		}else if(j==3){//字数
        			book.setWordsNumber(cellOne.asText());
        		}else if(j==4){//作者
        			book.setAuthor(cellOne.asText());
        		}else if(j==5){//更新时间
        			
        		}
        	}
        	bookList.add(book);
        }
        /*
         * 对比缓存中是否已经是最新章节(设置开始抓取的章节Url)
         */
        
        //获取每页小说的基础信息
        bookList = excuteSinglePageBooks(bookList,webClient);
       
        for(BookInfo book:bookList){
        	
        }
        webClient.close();
	}
	
	/**
	 * @param bookList
	 * @param webClient
	 * @return
	 * 获取每页小说的基础信息
	 */
	private static List<BookInfo> excuteSinglePageBooks(List<BookInfo> bookList,WebClient webClient){
		//循环本页小说列表
        for(BookInfo book : bookList){
        	String indexUrl = book.getIndexUrl();
        	HtmlPage page = null;
        	try {
				page =  webClient.getPage(indexUrl);
			} catch (FailingHttpStatusCodeException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        	if(page==null){
        		continue;
        	}
        	DomNodeList<DomElement> list  = page.getElementsByTagName("a");
            HtmlAnchor a = null;
            for(DomElement ele : list){
            	if("bookcover".equals(ele.getAttribute("class"))){
            		a = (HtmlAnchor)ele;
            	}
            }
            //小说封面图
            DomElement img = a.getFirstElementChild();
            book.setPicUrl(img.getAttribute("src"));
//            System.out.println(img.getAttribute("src"));
            
            //立即阅读按钮-可跳转到章节列表页面
            HtmlAnchor readNowA = (HtmlAnchor)page.getElementById("readNow");
//            System.out.println(readNowA.getAttribute("href"));
        	book.setChapterListUrl(readNowA.getAttribute("href"));
        	HtmlPage chaperListPage = null;
        	try {
				chaperListPage = readNowA.click();
			} catch (IOException e) {
				e.printStackTrace();
			}
        	if(chaperListPage==null){
        		continue;
        	}
        	DomElement ul = chaperListPage.getFirstByXPath("//ul[@class='block_ul']");
            List<?> greenAList =ul.getByXPath("//a[@class='green']");
            HtmlAnchor firstChapterA = ul.getFirstByXPath("//a[@class='green']");
            HtmlAnchor lastChapterA = (HtmlAnchor)greenAList.get(greenAList.size()-1);
            book.setFirstChapterUrl(firstChapterA.getAttribute("href"));
            book.setStartCatchUrl(firstChapterA.getAttribute("href"));
            book.setLastChapterUrl(lastChapterA.getAttribute("href"));
            book.setTotalChapterCount(greenAList.size());
        }
        return bookList;
	}
	
}
