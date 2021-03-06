package com.smartcold.zigbee.manage.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smartcold.zigbee.manage.dao.FileDataMapper;
import com.smartcold.zigbee.manage.dao.RdcShareMapper;
import com.smartcold.zigbee.manage.dto.RdcShareDTO;
import com.smartcold.zigbee.manage.entity.FileDataEntity;
import com.smartcold.zigbee.manage.service.FtpService;
import com.smartcold.zigbee.manage.service.RdcShareService;
import com.smartcold.zigbee.manage.util.SetUtil;

/*
 * Copyright (C) DCIS 版权所有
 * 功能描述:睿库共享
 * Create on MaQiang 2016-6-27 09:28:36
 */
@Service("rdcShareService")
public class RdcShareServiceImpl implements RdcShareService {

	@Autowired
	private RdcShareMapper rdcShareMapper;
	
	
    @Autowired
	private FileDataMapper fileDataDao;
    /**
	 * 获得共享详情
	 * @param id
	 * @return
	 */
	public RdcShareDTO getSEByID(String id){
		 RdcShareDTO vo = this.rdcShareMapper.getSEByID(id);
		 if(vo!=null){
			 List<FileDataEntity> files = this.fileDataDao.findByBelongIdAndCategory(vo.getId(), FileDataMapper.CATEGORY_SHARE_PIC);
			 if(SetUtil.isnotNullList(files)){
					List<String> filelist =new ArrayList<String>();
					for (FileDataEntity file : files) {
						filelist.add(FtpService.READ_URL+file.getLocation());
					}
					vo.setFiles(filelist);
					vo.setLogo(files.get(files.size()-1).getLocation());
				} 
		 }
		 return vo;
	}
    /**
     * 
     */
	@Override
	public int addShareMsg(RdcShareDTO rdcShareDTO) {
		return rdcShareMapper.addshareInfo(rdcShareDTO);
	}
    /**
     * 获得睿库信息
     * @param filter
     * @return
     */
    public PageInfo<RdcShareDTO> getRdcList(int pageNum,int pageSize,Map<String, Object> parameters){
  	    PageHelper.startPage(pageNum, pageSize);
  		Page<RdcShareDTO> serdcList = this.rdcShareMapper.getRdcList(parameters);
  		for (RdcShareDTO rdcShareDTO : serdcList) {
  			 List<FileDataEntity> files = fileDataDao.findByBelongIdAndCategory(rdcShareDTO.getRdcID(), FileDataMapper.CATEGORY_STORAGE_PIC);
  			if(SetUtil.isnotNullList(files)){
  				List<String> filelist =new ArrayList<String>();
  				for (FileDataEntity file : files) {
  					filelist.add(FtpService.READ_URL+file.getLocation());
				}
  				rdcShareDTO.setFiles(filelist);
  				rdcShareDTO.setLogo(files.get(files.size()-1).getLocation());
  			}
		}
  		return new PageInfo<RdcShareDTO>(serdcList);
    }
    
    
	/**
	 * 获得货品共享信息
	 * @param pageNum
	 * @param pageSize
	 * @param filter
	 * @return
	 */
	@Override
	public PageInfo<RdcShareDTO> getSEGDList(int pageNum, int pageSize,Map<String, Object> parameters) {
		PageHelper.startPage(pageNum, pageSize);
		Page<RdcShareDTO> serdcList = this.rdcShareMapper.getSEGDList(parameters);
		return new PageInfo<RdcShareDTO>(serdcList);
	}

	/**
	 * 获得配送共享信息
	 * @param pageNum
	 * @param pageSize
	 * @param filter
	 * @return
	 */
	@Override
	public PageInfo<RdcShareDTO> getSEPSList(int pageNum, int pageSize,Map<String, Object> parameters) {
		PageHelper.startPage(pageNum, pageSize);
		Page<RdcShareDTO> serdcList = this.rdcShareMapper.getSEPSList(parameters);
		return new PageInfo<RdcShareDTO>(serdcList);
	}

	
	/**
	 * 获得仓库共享信息
	 * @param pageNum
	 * @param pageSize
	 * @param filter
	 * @return
	 */
	@Override
	public PageInfo<RdcShareDTO> getSERDCList(int pageNum, int pageSize,HashMap<String, Object> parameters) {
		PageHelper.startPage(pageNum, pageSize);
		Page<RdcShareDTO> serdcList = this.rdcShareMapper.getSERDCList(parameters);
		return new PageInfo<RdcShareDTO>(serdcList);
	}

	/**
	   * 获得关联库全部信息
	   * @param filter
	   * @return
	   */
     @Override
	 public PageInfo<RdcShareDTO> getSEListByRdcID(int pageNum,int pageSize,Map<String, Object> parameters){
  	     PageHelper.startPage(pageNum, pageSize);
		 Page<RdcShareDTO> serdcList = this.rdcShareMapper.getSEListByRdcID(parameters);
		 return new PageInfo<RdcShareDTO>(serdcList);
	 }
    
}
