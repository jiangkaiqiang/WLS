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
import com.wls.manage.crawler.dajie.ListCrawler_dajie;
import com.wls.manage.crawler.jianzhimao.ListCrawler_jianzhimao;
import com.wls.manage.crawler.shixi.ListCrawler_shixi;
import com.wls.manage.crawler.shixiseng.ListCrawler_shixiseng;
import com.wls.manage.crawler.wutongguo.ListCrawler_wutongguo;
import com.wls.manage.dao.RecruitMapper;
import com.wls.manage.dto.BaseDto;
import com.wls.manage.dto.CommentDto;
import com.wls.manage.dto.ResponseDto;
import com.wls.manage.dto.UploadFileEntity;
import com.wls.manage.entity.CommentEntity;
import com.wls.manage.entity.PublishEntity;
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
	 * 实习僧
	 * @return
	 * @throws Exception
	 */
	/*@RequestMapping(value = "/addRecruitWithSXS")
	@ResponseBody
	public Object addRecruitWithSXS() throws Exception {
		ListCrawler_shixiseng listCrawler = new ListCrawler_shixiseng();
		List<Recruit> recruits = listCrawler.parse();
		for (int i = 0; i < recruits.size(); i++) {
			recruitDao.insert(recruits.get(i));
		}
		return ResponseData.newSuccess("添加成功");
	}*/
	
	/**
	 * 梧桐果
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addRecruitWithWTG")
	@ResponseBody
	public Object addRecruitWithWTG() throws Exception {
		ListCrawler_wutongguo listCrawler = new ListCrawler_wutongguo();
		List<Recruit> recruits = listCrawler.parse();
		for (int i = 0; i < recruits.size(); i++) {
			recruitDao.insert(recruits.get(i));
		}
		return ResponseData.newSuccess("添加成功");
	}
	
	/**
	 * 兼职猫
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addRecruitWithJZM")
	@ResponseBody
	public Object addRecruitWithJZM() throws Exception {
		ListCrawler_jianzhimao listCrawler = new ListCrawler_jianzhimao();
		List<Recruit> recruits = listCrawler.parse();
		for (int i = 0; i < recruits.size(); i++) {
			recruitDao.insert(recruits.get(i));
		}
		return ResponseData.newSuccess("添加成功");
	}
	
	/**
	 * 大街网
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addRecruitWithDJ")
	@ResponseBody
	public Object addRecruitWithDJ() throws Exception {
		ListCrawler_dajie listCrawler = new ListCrawler_dajie();
		List<Recruit> recruits = listCrawler.parse();
		for (int i = 0; i < recruits.size(); i++) {
			recruitDao.insert(recruits.get(i));
		}
		return ResponseData.newSuccess("添加成功");
	}
	
	/**
	 * 实习
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addRecruitWithSHIXI")
	@ResponseBody
	public Object addRecruitWithSHIXI() throws Exception {
		ListCrawler_shixi listCrawler = new ListCrawler_shixi();
		List<Recruit> recruits = listCrawler.parse();
		for (int i = 0; i < recruits.size(); i++) {
			recruitDao.insert(recruits.get(i));
		}
		return ResponseData.newSuccess("添加成功");
	}
	
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
	
	
	/**
	 * 增加发布
	 * @param message
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addRecruit")
	@ResponseBody
	public Object addRecruit(@RequestParam(required = false) String title,//标题
			@RequestParam(required = false) String salary,
			@RequestParam(required = false) String position,
			@RequestParam(required = false) String education,
			@RequestParam(required = false) String pubcategory,
			@RequestParam(required = false) String publisher,
			@RequestParam(required = false) String companyName,
			@RequestParam(required = false) String companyAddress,
			@RequestParam(required = false) Integer schoolid,
			@RequestParam(required = false) String content, 
			@RequestParam(required = false) MultipartFile picFile0,
			@RequestParam(required = false) MultipartFile picFile1,
			@RequestParam(required = false) MultipartFile picFile2,
			@RequestParam(required = false) MultipartFile picFile3,
			@RequestParam(required = false) MultipartFile picFile4,
			@RequestParam(required = false) MultipartFile picFile5
			){
		if(schoolid==null){
			schoolid = -1;
		}
		MultipartFile[] picFiles = {picFile5, picFile4, picFile3, picFile2, picFile1,picFile0};
		/*MultipartFile[] appendixs = {appendix2, appendix1, appendix0};
		MultipartFile[] videoFiles = {videoFile0, videoFile1};*/
		
		//PageParseUtil pageParseUtil = new PageParseUtil();
		//List<String> publishCovers = pageParseUtil.parse(content);
		Recruit recruit = new Recruit();
		recruit.setCompany(publisher);
		recruit.setTitle(title);
		recruit.setSalary(salary);
		recruit.setPosition(position);
		recruit.setEducation(education);
		recruit.setCategory(pubcategory);
		recruit.setContent(content);
		recruit.setCompany(companyName);
		recruit.setAddress(companyAddress);
		recruit.setSource("蔚蓝树");
		recruit.setPublishTime(new Date());
		String picFile = "";
		/*String appendixString = "";
		String videoFile = "";*/
		for (MultipartFile file : picFiles) {
			if (file == null) {
				continue;
			}
			String dir = String.format("%s/recruit/picFile", baseDir);
			String fileName = String.format("pic_%s.%s", new Date().getTime(), "jpg");
			UploadFileEntity uploadFileEntity = new UploadFileEntity(fileName, file, dir);
			ftpService.uploadFile(uploadFileEntity);
			picFile = picFile + FtpService.READ_URL+"data/"+dir + "/" + fileName;
		}
		/*for (MultipartFile file : videoFiles) {
			if (file == null) {
				continue;
			}
			String prefix=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
			String dir = String.format("%s/publish/videoFile", baseDir);
			String fileName = String.format("video_%s.%s", new Date().getTime(), prefix);
			UploadFileEntity uploadFileEntity = new UploadFileEntity(fileName, file, dir);
			ftpService.uploadFile(uploadFileEntity);
			videoFile = videoFile + FtpService.READ_URL+"data/"+dir + "/" + fileName+";";
		}
		for (MultipartFile file : appendixs) {
			if (file == null) {
				continue;
			}
			String prefix=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
			String dir = String.format("%s/publish/appendixs", baseDir);
			String fileName = String.format("apd_%s.%s", new Date().getTime(), prefix);
			UploadFileEntity uploadFileEntity = new UploadFileEntity(fileName, file, dir);
			ftpService.uploadFile(uploadFileEntity);
			appendixString = appendixString +file.getOriginalFilename()+"<"+FtpService.READ_URL+"data/"+dir + "/" + fileName+">";
		}*/
		if (!picFile.equals("")) {
			recruit.setCover(picFile);
		}
		/*if (!appendixString.equals("")) {
			publishEntity.setAppendixs(appendixString);	
		}
		if (!videoFile.equals("")) {
			publishEntity.setPubvideo(videoFile);
		}*/
	
		/*if (publishCovers!=null&&!publishCovers.isEmpty()) {
			publishEntity.setPubcover(publishCovers.get(0));
		}*/
		recruitDao.insert(recruit);
		return ResponseData.newSuccess();
	}
}
