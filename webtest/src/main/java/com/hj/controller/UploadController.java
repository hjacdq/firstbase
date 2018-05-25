package com.hj.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.hj.service.impl.ImgUploadServiceImpl;
import com.hj.util.ExcelUtils;
import com.hj.util.StringUtil;

@RequestMapping("/upload")
@Controller
public class UploadController {
	
	@Autowired
	private ImgUploadServiceImpl imgUploadService;
	
	private static List<String> imgSufixList = new ArrayList<String>();
	
	static{
		imgSufixList.add("png");
		imgSufixList.add("jpg");
		imgSufixList.add("jpeg");
		imgSufixList.add("bmp");
		imgSufixList.add("gif");
	}
	
	@RequestMapping("/uploadToServer")
	public void uploadFile(HttpServletRequest req,HttpServletResponse res) throws IllegalStateException, IOException{
		System.out.println("开始");  
		  
		//创建一个通用的多部分解析器  
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(req.getSession().getServletContext());  
        //判断 request 是否有文件上传,即多部分请求  
        if(multipartResolver.isMultipart(req)){  
            //转换成多部分request    
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)req;  
            //取得request中的所有文件名  
            Iterator<String> iter = multiRequest.getFileNames();  
            while(iter.hasNext()){  
                //记录上传过程起始时的时间，用来计算上传时间  
                int pre = (int) System.currentTimeMillis();  
                //取得上传文件  
                MultipartFile file = multiRequest.getFile(iter.next());  
                if(file != null){  
                    //取得当前上传文件的文件名称  
                    String myFileName = file.getOriginalFilename();  
                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在  
                    if(myFileName.trim() !=""){  
                        System.out.println(myFileName);  
                        //重命名上传后的文件名  
                        String fileName = "demoUpload" + file.getOriginalFilename();  
                        //定义上传路径  
                        String path = "D:/" + fileName;  
                        File localFile = new File(path);  
                        file.transferTo(localFile);  
                    }  
                }  
                //记录上传该文件后的时间  
                int finaltime = (int) System.currentTimeMillis();  
                System.out.println(finaltime - pre);  
            }  
        }   
        
	}
	
	@RequestMapping("/excel")
    public void upload(HttpServletRequest request, HttpServletResponse response) throws IOException, BiffException, WriteException {
        MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = mRequest.getFile("file");
        Workbook workbook = Workbook.getWorkbook(file.getInputStream());
        //遍历Sheet页
//        Arrays.stream(workbook.getSheets()).forEach(sheet -> {
//                    int size = sheet.getRows();
//                    for(int i=0; i<size; i++){
//                        //遍历每一行，读取每列信息
//                        Arrays.stream(sheet.getRow(i)).forEach(cell -> System.out.println(cell.getContents().equals("")?'空':cell.getContents()));
//                    }
//                });
        Sheet[] sheets= workbook.getSheets();
        for(Sheet s:sheets){
        	int size = s.getRows();
        	for(int i=0;i<size;i++){
        		
        	}
        }

        response.setHeader("Content-Disposition", "attachment; filename=return.xls");
        WritableWorkbook writableWorkbook = ExcelUtils.createTemplate(response.getOutputStream());
        writableWorkbook.write();
        writableWorkbook.close();
    }
	
	/**
	 * @param request
	 * @param file
	 * @return
	 * 图片上传到阿里云
	 */
	@ResponseBody
	@RequestMapping("/imgUpload")
	public Map<String,Object> uploadImg(HttpServletRequest request,MultipartFile file){
		String picUrl = imgUploadService.updateHead(file);
		Map<String, Object> value = new HashMap<String, Object>();
		value.put("success", true);
		value.put("errorCode", 0);
	  	value.put("errorMsg", "");
	  	value.put("picUrl", picUrl);
	  	System.out.println(picUrl);
	  	return value;
	}
	
	/**
	 * @param req
	 * @param res
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 * 富文本编辑器上传图片到阿里云
	 */
	@ResponseBody
	@RequestMapping("/editorImgUpload")
	public Map<String,Object> editorImgUpload(HttpServletRequest req,HttpServletResponse res) throws IllegalStateException, IOException{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<String> picUrlList = new ArrayList<String>();
		//创建一个通用的多部分解析器  
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(req.getSession().getServletContext());  
        //判断 request 是否有文件上传,即多部分请求  
        if(multipartResolver.isMultipart(req)){  
            //转换成多部分request    
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)req;  
            //取得request中的所有文件名  
            Iterator<String> iter = multiRequest.getFileNames();  
            while(iter.hasNext()){ 
                //记录上传过程起始时的时间，用来计算上传时间  
                int pre = (int) System.currentTimeMillis();  
                //取得上传文件  
                MultipartFile file = multiRequest.getFile(iter.next());  
                if(file != null){  
                    //取得当前上传文件的文件名称  
                    String myFileName = file.getOriginalFilename();  
                    if(StringUtil.isBlank(myFileName)){
                    	continue;
                    }else if(myFileName.indexOf(".")<=0){
                    	continue;
                    }
                    String fileSufix = myFileName.substring(myFileName.indexOf(".")+1);
                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在  
                    if(myFileName.trim() !="" && imgSufixList.contains(fileSufix)){  
                        //重命名上传后的文件名  
                    	String picUrl = imgUploadService.updateHead(file);
                    	if(StringUtil.isNotBlank(picUrl)){
                    		picUrlList.add(picUrl);
                    	}
                    }  
                }
            }
            if(picUrlList.size()>0){
            	resultMap.put("errorCode", 0);
            	resultMap.put("data", picUrlList);
            }else{
            	resultMap.put("errorCode", 1);
            	resultMap.put("errorMsg", "上传失败");
            }
            return resultMap;
            
        }else{
        	resultMap.put("errorCode", 1);
        	resultMap.put("errorMsg", "参数异常");
        	return resultMap;
        }
        
	}
}
