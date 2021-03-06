package com.smartcold.manage.cold.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartcold.manage.cold.dao.newdb.ColdStorageAnalysisMapper;
import com.smartcold.manage.cold.entity.newdb.ColdStorageAnalysisEntity;
import com.smartcold.manage.cold.service.ColdStorageAnalysisService;

@Service
public class ColdStorageAnalysisServiceImpl implements ColdStorageAnalysisService {

	@Autowired
	private ColdStorageAnalysisMapper analysisDao;

	@Override
	public Map<String, List<ColdStorageAnalysisEntity>> findValueByDateKeys(int type, int oid, List<String> keys,
			Date startTime, Date endTime) {
		HashMap<String, List<ColdStorageAnalysisEntity>> result = new HashMap<String, List<ColdStorageAnalysisEntity>>();
		for (String key : keys) {
			result.put(key, analysisDao.findValueByDate(type, oid, key, startTime, endTime));
		}

		return result;
	}

	@Override
	public List<ColdStorageAnalysisEntity> findValueByFilter( HashMap<String, Object> filter) {
		return this.analysisDao.findValueByFilter(filter);
	}

}
