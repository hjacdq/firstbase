package com.hj.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hj.util.OSSClientUtil;

@Service("imgUploadService")
public class ImgUploadServiceImpl {
	
	@Autowired
	private OSSClientUtil ossClient;
	
	public String updateHead(MultipartFile file){
	    if (file == null || file.getSize() <= 0) {
	    	return "";
	    }
	    String name = ossClient.uploadImg2Oss(file);
	    String imgUrl = ossClient.getImgUrl(name);
	    return imgUrl;
	}
}
