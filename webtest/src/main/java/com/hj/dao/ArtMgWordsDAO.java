package com.hj.dao;


import java.util.List;

import com.hj.entity.ArtMgWords;

public interface ArtMgWordsDAO {
	
	public void save(ArtMgWords artMgWords);

	public ArtMgWords getById(Long id);
	
	public List<ArtMgWords> getByKeywords(String keywords);
	
	public int update(ArtMgWords artMgWords);
	
	public void delete(Long id);
	
	/**
	 * @return
	 * 获取敏感子的集合
	 */
	public List<String> getSensitiveWordSet();
	
}
