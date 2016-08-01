package com.smartcold.manage.cold.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartcold.manage.cold.dao.CompressorGroupSetMapper;
import com.smartcold.manage.cold.entity.StorageKeyValue;
import com.smartcold.manage.cold.enums.StorageType;
import com.smartcold.manage.cold.service.CompressorGroupService;
import com.smartcold.manage.cold.service.StorageService;

/**
 * Author: qiunian.sun Date: qiunian.sun(2016-05-02 15:05)
 */
@Controller
@ResponseBody
@RequestMapping(value = "/compressorGroup")
public class CompressorGroupController {


	@Autowired
	private CompressorGroupSetMapper compressorGroupSetDao;

	@Autowired
	private CompressorGroupService compressorGroupService;
	
	@Autowired
	private StorageService storageService;

	@RequestMapping(value = "/findByUserId", method = RequestMethod.GET)
	public Object findByUserId(@RequestParam int userId) {
		return compressorGroupService.findByUserId(userId);
	}


	@RequestMapping(value = "/findByRdcId", method = RequestMethod.GET)
	public Object findByGroupId(@RequestParam int rdcId) {
		return compressorGroupSetDao.findByRdcId(rdcId);
	}
	
	@RequestMapping("/findPressByNums")
	public Object findPressByNums(int compressorID,String key,
			@RequestParam(value="nums",defaultValue="2")Integer nums){
		List<StorageKeyValue> lowPress = storageService.findByNums(StorageType.COMPRESSOR, 
				compressorID, "COMPRESSOR_PRESS_L", nums);
		List<StorageKeyValue> highPress = storageService.findByNums(StorageType.COMPRESSOR, 
				compressorID, "COMPRESSOR_PRESS_H", nums);
		Map result = new HashMap<String, List<StorageKeyValue>>(2);
		result.put("lowPress", lowPress);
		result.put("highPress", highPress);
		return result;
	}
	
	@RequestMapping("/findCompressorByNums")
	public Object findCompressorByNums(int compressorID,
			@RequestParam(value="nums",defaultValue="1")Integer nums){
		Map<String, List<StorageKeyValue>> result = new HashMap<String, List<StorageKeyValue>>();
		StorageType stype = StorageType.COMPRESSOR;
		result.put("compressor1",storageService.findByNums(stype, compressorID, "COMPRESSOR_1", nums));
		result.put("compressor2",storageService.findByNums(stype, compressorID, "COMPRESSOR_2", nums));
		result.put("compressor3",storageService.findByNums(stype, compressorID, "COMPRESSOR_3", nums));
		result.put("compressor4",storageService.findByNums(stype, compressorID, "COMPRESSOR_4", nums));
		result.put("compressor5",storageService.findByNums(stype, compressorID, "COMPRESSOR_5", nums));
		result.put("compressor6",storageService.findByNums(stype, compressorID, "COMPRESSOR_6", nums));
		return result;
	}
}
