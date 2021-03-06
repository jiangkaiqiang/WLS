package com.smartcold.zigbee.manage.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smartcold.zigbee.manage.dto.RdcScoreDTO;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.smartcold.zigbee.manage.dto.RdcAddDTO;
import com.smartcold.zigbee.manage.dto.RdcDTO;
import com.smartcold.zigbee.manage.dto.RdcEntityDTO;
import com.smartcold.zigbee.manage.dto.RdcShareDTO;
import com.smartcold.zigbee.manage.entity.RdcEntity;

/**
 * Author: qiunian.sun Date: qiunian.sun(2016-04-29 00:13)
 */
public interface RdcService {

	public final Gson gson = new Gson();

	List<RdcEntity> findRdcList();

	List<RdcDTO> findAllRdcDtos();

	List<RdcAddDTO> findRDCDTOByRDCId(@RequestParam int rdcID);

	List<RdcEntityDTO> findRdcDTOList();
	
	boolean checkName(String name);

	List<RdcScoreDTO> findHotRdcDTOList(@RequestParam int npoint);

	List<RdcScoreDTO> findScoreRdcDTOList(@RequestParam int npoint);

	void sumRdcsScore();

	 /**
	  * 获得关联库全部共享信息
	  * @param filter
	  * @return
	  */
	 public PageInfo<RdcEntityDTO> getRDCList(int pageNum,int pageSize,HashMap<String, Object> filter);
}
