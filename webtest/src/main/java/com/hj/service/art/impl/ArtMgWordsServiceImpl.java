package com.hj.service.art.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hj.dao.ArtMgWordsDAO;
import com.hj.entity.ArtMgWords;
import com.hj.model.ResultMap;
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
//		Set<String> sensitiveWordSet = redisClientTemplate.get(key)
			Set<String> sensitiveWordSet = 	new HashSet<>();
        sensitiveWordSet.add("太多");
        sensitiveWordSet.add("爱恋");
        sensitiveWordSet.add("静静");
        sensitiveWordSet.add("哈哈");
        sensitiveWordSet.add("啦啦");
        sensitiveWordSet.add("感动");
        sensitiveWordSet.add("发呆");
        //初始化敏感词库
        SensitiveWordUtil.init(sensitiveWordSet);
		//初始化敏感词库
        SensitiveWordUtil.init(sensitiveWordSet);
		return null;
	}

}
