package com.hj.service.art.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hj.dao.ArtMgWordsDAO;
import com.hj.entity.ArtMgWords;
import com.hj.model.ResultMap;
import com.hj.service.Redis;
import com.hj.service.art.ArtMgWordsService;
import com.hj.util.SensitiveWordUtil;
import com.hj.util.StringUtil;

@Service("artMgWordsService")
public class ArtMgWordsServiceImpl implements ArtMgWordsService{

	@Autowired
	private ArtMgWordsDAO artMgWordsDAO;
	
	public void save(ArtMgWords artMgWords) {
		artMgWordsDAO.save(artMgWords);
	}

	public ArtMgWords getById(Long id) {
		return artMgWordsDAO.getById(id);
	}

	public int update(ArtMgWords artMgWords) {
		return artMgWordsDAO.update(artMgWords);
	}

	public void delete(Long id) {
		artMgWordsDAO.delete(id);
	}

	@Override
	public ResultMap doSave(String keywordsStr) {
		String keywordsArray[] = keywordsStr.split("\\|");
		for(String keywords:keywordsArray){
			if(StringUtil.isEmpty(keywords))
				continue;
			List<ArtMgWords> list = artMgWordsDAO.getByKeywords(keywords.trim());
			if(list!=null && !list.isEmpty())
				continue;
			ArtMgWords artMgWords = new ArtMgWords();
			artMgWords.setKeywords(keywords.trim());
			artMgWords.setCode("MG_"+System.currentTimeMillis());
			artMgWords.setStatus(0);//默认为无效，需要进行审核
			artMgWordsDAO.save(artMgWords);
		}
		return ResultMap.success();
	}
	
	@Override
	public ResultMap check(String content) {
		//获取敏感词集合
		Set<String> sensitiveWordSet = getSensitiveWordSet();
		if(sensitiveWordSet==null || sensitiveWordSet.isEmpty())
			return ResultMap.failure("功能尚未开放");
        //初始化敏感词库
        SensitiveWordUtil.init(sensitiveWordSet);
        Set<String> set = SensitiveWordUtil.getSensitiveWord(content, SensitiveWordUtil.MinMatchTYpe);
        List<String> list = new ArrayList<String>();
    	list.addAll(set);
        return ResultMap.success(list);
	}

	private Set<String> getSensitiveWordSet(){
		Set<String> sensitiveWordSet = Redis.get("sensitiveWordSet");
		if(sensitiveWordSet!=null){
			return sensitiveWordSet;
		}
		sensitiveWordSet = 	new HashSet<>();
		List<String> list = artMgWordsDAO.getSensitiveWordSet();
		if(list==null || list.isEmpty())
			return sensitiveWordSet;
		sensitiveWordSet.addAll(list);
		return sensitiveWordSet;
	}
	
}
