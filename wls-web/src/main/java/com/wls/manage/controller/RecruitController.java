package com.wls.manage.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wls.manage.dao.RecruitMapper;
import com.wls.manage.dto.BaseDto;
import com.wls.manage.dto.CommentDto;
import com.wls.manage.dto.ResponseDto;
import com.wls.manage.dto.UploadFileEntity;
import com.wls.manage.entity.CommentEntity;
import com.wls.manage.entity.Recruit;
import com.wls.manage.entity.ResponseEntity;
import com.wls.manage.service.FtpService;
import com.wls.manage.util.ResponseData;
/**
 * 招聘controller
 * @author jkq
 *
 */
@Controller
@RequestMapping(value = "/recruit")
public class RecruitController extends BaseController {
	private static String baseDir = "picture";
	@Autowired
	private FtpService ftpService;

	@Autowired
	private RecruitMapper recruitDao;
	
	/**
	 * 提供查询服务
	 * @param pageNum
	 * @param pageSize
	 * @param posterID
	 * @param keyword
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/findAllRecruit")
	@ResponseBody
	public Object findAllRecruit(@RequestParam(value="pageNum",required=false) Integer pageNum,
			@RequestParam(value="pageSize") Integer pageSize, 
			@RequestParam(value="audit", required=false) Integer audit,
			@RequestParam(value="keyword", required=false) String keyword) throws UnsupportedEncodingException {
		if( !(audit == 1 || audit == 2 || audit == 3||audit == 4 || audit == 5) ){
			audit = null;
		}
		pageNum = pageNum == null? 1:pageNum;
		pageSize = pageSize==null? 12:pageSize;
		PageHelper.startPage(pageNum, pageSize);
		if(keyword.equals("undefined")||keyword.equals(""))
			keyword = null;
		else{
		keyword = URLDecoder.decode(keyword, "UTF-8");
		}
		Page<Recruit> recruits = recruitDao.findAllRecruit(audit, keyword);
		return new PageInfo<Recruit>(recruits);
	}
	
	
	/**
	 * 根据资讯id查找资讯
	 * @param inforID
	 * @return
	 */
	@RequestMapping(value = "/findRecruitByID")
	@ResponseBody
	public Object findRecruitByID(@RequestParam Integer recruitID) {
		Recruit recruit = recruitDao.selectByPrimaryKey(recruitID);
		return recruit;
	}
	

	/**
	 * 将数据插入数据库，需要传入NewInfomationDto和自己设置的分类：1：科技类，2：文娱类，3：创业类，4：时事类，5：校园类
	 * @param newInfomationDtos
	 * @param category
	 */
	/*public void insertInformationByType(List<NewInfomationDto> newInfomationDtos,String category) {
		for (NewInfomationDto newInfomationDto : newInfomationDtos) {
			 InformationEntity informationEntity = new InformationEntity();
			    informationEntity.setContent(newInfomationDto.getContent());
			    informationEntity.setCoverpiclist(newInfomationDto.getPic());
			    informationEntity.setInfocategory(category);
			    informationEntity.setSource(newInfomationDto.getSource());
			    informationEntity.setTitle(newInfomationDto.getTitle());
			    informationEntity.setTime(newInfomationDto.getTime());
			    //数据库去重
			    if (informationDao.findInformationByTitle(informationEntity.getTitle())==null) {
			    	 informationDao.insertInformation(informationEntity);
				}
		}
	}*/
	
	/**
	 * 实习僧
	 * @return
	 * @throws Exception
	 */
	/*@RequestMapping(value = "/addRecruitWithSXS")
	@ResponseBody
	public Object addInformationWithBJ() throws Exception {
		ListCrawler_shengxiseng listCrawler = new ListCrawler_shengxiseng();
		List<NewInfomationDto> newInfomationDtos = listCrawler.parse();
		insertInformationByType(newInfomationDtos,"1");
		return ResponseData.newSuccess("添加成功");
	}*/
	
	/**
	 * 后端管理系统================================================================
	 */
	@RequestMapping(value = "/deleteRecruit", method = RequestMethod.GET)
	@ResponseBody
	public Object deleteRecruit(Integer recruitID) {
		 recruitDao.deleteByPrimaryKey(recruitID);
		 return new BaseDto(0);
	}
	
	@RequestMapping(value = "/deleteByRecruitIDs")
	@ResponseBody
	public Object deleteByRecruitIDs(Integer[] recruitIDs) {
		for(Integer recruitID:recruitIDs){
			recruitDao.deleteByPrimaryKey(recruitID);
		}
		return new BaseDto(0);
	}
}
