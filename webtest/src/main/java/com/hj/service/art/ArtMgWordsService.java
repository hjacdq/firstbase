package com.hj.service.art;

import com.hj.model.ResultMap;

public interface ArtMgWordsService {
	
	/**
	 * @param keywordsStr
	 * @return
	 * 保存
	 */
	public ResultMap doSave(String keywordsStr);
	
	/**
	 * @param content
	 * @return
	 * 检查敏感词
	 */
	public ResultMap check(String content);
	
}
