package com.wls.manage.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

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
import com.taobao.api.ApiException;
import com.wls.manage.dao.EducateMapper;
import com.wls.manage.dao.FollowMapper;
import com.wls.manage.dao.HonorMapper;
import com.wls.manage.dao.JobMapper;
import com.wls.manage.dao.ResumeVisMapper;
import com.wls.manage.dao.RoleMapper;
import com.wls.manage.dao.SkillMapper;
import com.wls.manage.dao.UserMapper;
import com.wls.manage.dto.BaseDto;
import com.wls.manage.dto.ResultDto;
import com.wls.manage.dto.UploadFileEntity;
import com.wls.manage.dto.UserDto;
import com.wls.manage.entity.CookieEntity;
import com.wls.manage.entity.EducateEntity;
import com.wls.manage.entity.FollowEntity;
import com.wls.manage.entity.HonorEntity;
import com.wls.manage.entity.JobEntity;
import com.wls.manage.entity.ResumeVisEntity;
import com.wls.manage.entity.RoleEntity;
import com.wls.manage.entity.SkillEntity;
import com.wls.manage.entity.UserEntity;
import com.wls.manage.service.CookieService;
import com.wls.manage.service.FtpService;
import com.wls.manage.util.EncodeUtil;
import com.wls.manage.util.ResponseData;
import com.wls.manage.util.StringUtil;
import com.wls.manage.util.TelephoneVerifyUtil;

@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {
	private static String baseDir = "picture";
	@Autowired
	private UserMapper userDao;
	@Autowired
	private FollowMapper followMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private EducateMapper educateMapper;
	@Autowired
	private SkillMapper skillMapper;
	@Autowired
	private JobMapper jobMapper;
	@Autowired
	private HonorMapper honorMapper;
	@Autowired
	private CookieService cookieService;
	@Autowired
	private FtpService ftpService;
	@Autowired
	private ResumeVisMapper resumeVisMapper;
	
	@RequestMapping(value = "/login")
	@ResponseBody
	public Object login(HttpServletRequest request, String userName, String password) {
		if(StringUtil.isnotNull(userName)&&StringUtil.isnotNull(password)){
			UserEntity user = userDao.findUser(userName, EncodeUtil.encodeByMD5(password));
			if (user != null) {
				String cookie = cookieService.insertCookie(userName);
				user.setPassword("********");
				request.getSession().setAttribute("user", user);
	            return  ResponseData.newSuccess(String.format("token=%s", cookie));
			}
			return ResponseData.newFailure("用户名或者密码不正确~");
		}else{
			return ResponseData.newFailure("用户名和密码不能为空~");
		}
		
	}
	
	@RequestMapping(value = "/findUserByID")
	@ResponseBody
	public Object findUserByID(
			@RequestParam(value="spaceUserID", required=false) Integer spaceUserID
			) throws UnsupportedEncodingException {
	     UserEntity userEntity = userDao.findUserById(spaceUserID);
	     return userEntity;
	}
	
	@RequestMapping(value = "/askResumeFlag")
	@ResponseBody
	public Object askResumeFlag(
			@RequestParam(value="spaceUserID", required=false) Integer spaceUserID,
			@RequestParam(value="askUserID", required=false) Integer askUserID
			) throws UnsupportedEncodingException {
		if (askUserID==null) {
			return false;
		}
		if(spaceUserID.intValue()==askUserID.intValue()){
			return true;
		}
		List<ResumeVisEntity> resumeVisEntities = resumeVisMapper.findvisibleridByOwnerId(spaceUserID);
		for (ResumeVisEntity resumeVisEntity : resumeVisEntities) {
			if (askUserID.intValue()==resumeVisEntity.getVisiblerid().intValue()) {
				return true;
			}
		}
		return false;
	}
	
	@RequestMapping(value = "/askFollowFlag")
	@ResponseBody
	public Object askFollowFlag(
			@RequestParam(value="spaceUserID", required=false) Integer spaceUserID,
			@RequestParam(value="askUserID", required=false) Integer askUserID
			) throws UnsupportedEncodingException {
		if (askUserID==null) {
			return false;
		}
		List<FollowEntity> followEntities = followMapper.findFollowByUserId(askUserID);
		for (FollowEntity followEntity : followEntities) {
			if (followEntity.getFollowedid().intValue()==spaceUserID.intValue()) {
				return true;
			}
		}
		return false;
	}

	@RequestMapping(value = "/addFollow")
	@ResponseBody
	public Object addFollow(
			@RequestParam(value="spaceUserID", required=false) Integer spaceUserID,
			@RequestParam(value="askUserID", required=false) Integer askUserID
			) throws UnsupportedEncodingException {
		if (askUserID==null) {
			return ResponseData.newFailure("请先登录");
		}
		FollowEntity followEntity = new FollowEntity();
		followEntity.setFollowedid(BigInteger.valueOf(spaceUserID));
		followEntity.setFollowid(BigInteger.valueOf(askUserID));
		followMapper.insertFollow(followEntity);
		return ResponseData.newSuccess();
	}
	
	
	@RequestMapping(value = "/deleteFollow")
	@ResponseBody
	public Object deleteFollow(
			@RequestParam(value="spaceUserID", required=false) Integer spaceUserID,
			@RequestParam(value="askUserID", required=false) Integer askUserID
			) throws UnsupportedEncodingException {
		if (askUserID==null) {
			return ResponseData.newFailure("请先登录");
		}
		FollowEntity followEntity = new FollowEntity();
		followEntity.setFollowedid(BigInteger.valueOf(spaceUserID));
		followEntity.setFollowid(BigInteger.valueOf(askUserID));
		followMapper.deleteFollow(followEntity);
		return ResponseData.newSuccess();
	}
	
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseBody
	public Object logout(HttpServletRequest request) {
		request.getSession().removeAttribute("user");
		Cookie[] cookies = request.getCookies();
		if(cookies!=null&&cookies.length>0){
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("token")) {
					cookieService.deleteCookie(cookie.getValue());
				}
			}
		}
		return true;
	}

	@RequestMapping(value = "/findUser")
	@ResponseBody
	public Object findUser(HttpServletRequest request,String token) {
		UserEntity user = (UserEntity)request.getSession().getAttribute("user");
		if(user!=null){return user;}
		if(StringUtil.isNull(token)){
			Cookie[] cookies = request.getCookies();
			if(cookies!=null&&cookies.length>0){
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("token")) {token=	cookie.getValue();break;}
				}
			}
		}
		if(StringUtil.isnotNull(token)){
			CookieEntity effectiveCookie = cookieService.findEffectiveCookie(token);
			if (effectiveCookie != null) {
				user = userDao.findUserByName(effectiveCookie.getUsername());
				if(user!=null){
					user.setPassword("********");
					request.getSession().setAttribute("user", user);
					return user;
				}
			}
		}
		user = new UserEntity();
		return user;
	}
	
	@RequestMapping(value = "/findUserList", method = RequestMethod.POST)
	@ResponseBody
	public Object findUserList(
			@RequestParam(value="userid",required=false) Integer userid,
			@RequestParam(value="pageNum",required=false) Integer pageNum,
			@RequestParam(value="pageSize") Integer pageSize, 
			@RequestParam(value="audit", required=false) Integer audit,
			@RequestParam(value="provinceid", required=false) Integer provinceid,
			@RequestParam(value="cityid", required=false) Integer cityid,
			@RequestParam(value="schoolid", required=false) Integer schoolid,
			@RequestParam(value="keyword", required=false) String keyword) throws UnsupportedEncodingException {
		if( !(audit == 1 || audit == 2 || audit == 3||audit == 4 || audit == 5 || audit == 6) ){
			audit = null;
		}
		if(provinceid==null||provinceid==-1){
			provinceid = null;
		}
		if(cityid==null||cityid==-1){
			cityid = null;
		}
		if(schoolid==null||schoolid==-1){
			schoolid = null;
		}
		pageNum = pageNum == null? 1:pageNum;
		pageSize = pageSize==null? 12:pageSize;
		PageHelper.startPage(pageNum, pageSize);
		if(keyword.equals("undefined")||keyword.equals(""))
			keyword = null;
		else{
		keyword = URLDecoder.decode(keyword, "UTF-8");
		}
		Page<UserEntity> userEntities = userDao.findAllUser(audit,keyword,1,provinceid,cityid,schoolid,userid);
		Page<UserDto> userDtos = new Page<UserDto>();
		for (int i = 0; i < userEntities.size(); i++) {
			UserEntity userEntity = userEntities.get(i);
			UserDto userDto = new UserDto();
			userDto.setId(userEntity.getId());
			userDto.setAvatar(userEntity.getAvatar());
			userDto.setSignature(userEntity.getSignature());
			userDto.setNickname(userEntity.getNickname());
			userDto.setSkill1(userEntity.getSkill1());
			userDto.setSkill2(userEntity.getSkill2());
			if (userEntity.getScore()!=null) {
				if(userEntity.getScore().intValue()<100){
					userDto.setLevel(1);
				}
				else if (userEntity.getScore().intValue()>=100&&userEntity.getScore().intValue()<500) {
					userDto.setLevel(2);
				}
				else if (userEntity.getScore().intValue()>=500) {
					userDto.setLevel(3);
				}
			}
			else {
				userDto.setLevel(1);
			}
			userDtos.add(userDto);
		}
		userDtos.setPageSize(userEntities.getPageSize());
		userDtos.setPages(userEntities.getPages());
		userDtos.setTotal(userEntities.getTotal());
		return new PageInfo<UserDto>(userDtos);
		
	}
	
	
	@RequestMapping(value = "/checkVerifyCode")
	@ResponseBody
	public Object checkVerifyCode(HttpServletRequest request, String verifycode) {
		if (verifycode!=null&&request.getSession().getAttribute("identityVerifyCode")!=null) {
			if(request.getSession().getAttribute("identityVerifyCode").equals(verifycode))
				return true;
		}
		return false;
	}
	
	
	@RequestMapping(value = "/sendSignUpCode")
	@ResponseBody
	public Object sendSignUpCode(HttpServletRequest request,@RequestParam(value="telephone",required=false) String telephone) throws ApiException {
		if(telephone!=null&&!telephone.equals("")){
			TelephoneVerifyUtil teleVerify = new TelephoneVerifyUtil();
			String signUpCode = teleVerify.signUpVerify(telephone);
			request.getSession().setAttribute("signUpCode", signUpCode);
			return ResponseData.newSuccess("验证码已发送"); 
		}
		return ResponseData.newFailure("请填写手机号"); 
	}
	
	@RequestMapping(value = "/sendPwdVerifyCode")
	@ResponseBody
	public Object sendPwdVerifyCode(HttpServletRequest request,@RequestParam(value="telephonenum",required=false) String telephonenum) throws ApiException {
		if(telephonenum!=null&&!telephonenum.equals("")){
			TelephoneVerifyUtil teleVerify = new TelephoneVerifyUtil();
			String pwdverifycode = teleVerify.identityVerify(telephonenum);
			request.getSession().setAttribute("pwdverifycode", pwdverifycode);
			return ResponseData.newSuccess("验证码已发送"); 
		}
		return ResponseData.newFailure("请填写手机号"); 
	}
	
	@RequestMapping(value = "/verifySignUpCode")
	@ResponseBody
	public Object verifySignUpCode(HttpServletRequest request, String signUpCode) throws ApiException {
		String sessyzm=""+request.getSession().getAttribute("signUpCode");
		if(signUpCode==null||!(sessyzm).equalsIgnoreCase(signUpCode))
			return ResponseData.newFailure("验证码输入错误");
		else 
			return ResponseData.newSuccess("验证码验证成功");
	}
	
	@RequestMapping(value = "/pwdCodeVerify")
	@ResponseBody
	public Object pwdCodeVerify(HttpServletRequest request, String pwdverifycode) throws ApiException {
		String sessyzm=""+request.getSession().getAttribute("pwdverifycode");
		if(pwdverifycode==null||!(sessyzm).equalsIgnoreCase(pwdverifycode))
			return ResponseData.newFailure("验证码输入错误");
		else 
			return ResponseData.newSuccess("验证码验证成功");
	}
	
	/*@RequestMapping(value = "/identityVerify", method = RequestMethod.POST)
	@ResponseBody
	public Object identityVerify(HttpServletRequest request, String telephone) throws ApiException {
		if(telephone!=null&&!telephone.equals("")){
			TelephoneVerifyUtil teleVerify = new TelephoneVerifyUtil();
			String identityVerifyCode = teleVerify.identityVerify(telephone);
			request.getSession().setAttribute("identityVerifyCode", identityVerifyCode);
			return ResponseData.newSuccess("验证码已发送"); 
		}
		return ResponseData.newFailure("请填写手机号"); 
	}*/
	
	@RequestMapping(value = "/signup")
	@ResponseBody
	public Object signup(HttpServletRequest request,String username, String password,String rpassword, String email,String telephone,Integer suproleid
			,String avatar) throws ApiException {
		if (username == null || password == null ) {
			return ResponseData.newFailure("用户名和密码输入不能为空"); 
		}
		if (!password.equals(rpassword) ) {
			return ResponseData.newFailure("两次密码输入不一致"); 
		}
		UserEntity userEntity = new UserEntity();
		userEntity.setUsername(username);
		userEntity.setPassword(EncodeUtil.encodeByMD5(password));
		userEntity.setEmail(email);
		userEntity.setTelephone(telephone);
		userEntity.setSuproleid(suproleid);
		userEntity.setAvatar(avatar);
		userDao.insertUser(userEntity);
		return ResponseData.newSuccess("注册成功");
	}
	
	@RequestMapping(value = "/updateUser")
	@ResponseBody
	public Object updateUser(HttpServletRequest request, 
			@RequestParam(value="realname",required=false)String realname,
			@RequestParam(value="telephone",required=false)String telephone,
			@RequestParam(value="position",required=false)String position,
			@RequestParam(value="company",required=false)String company,
			@RequestParam(value="address",required=false)String address,
			@RequestParam(value="phone",required=false)String phone,
			@RequestParam(value="email",required=false)String email,
			@RequestParam(value="signature",required=false)String signature,
			@RequestParam(value="sex",required=false)Integer sex,
			
			@RequestParam(value="pr_id",required=false)Integer pr_id,
			@RequestParam(value="ci_id",required=false)Integer ci_id,
			@RequestParam(value="sh_id",required=false)Integer sh_id,
			@RequestParam(value="major",required=false)String major,
			@RequestParam(value="roleid",required=false)Integer roleid,
			@RequestParam(value="nickname",required=false)String nickname,
			@RequestParam(value="interest",required=false)String interest,
			@RequestParam(value="skill1",required=false)String skill1,
			@RequestParam(value="skill2",required=false)String skill2,
			@RequestParam(value="age",required=false)Integer age,
			
			
			@RequestParam(value="intention",required=false)String intention
			
			) throws ApiException {
		UserEntity user = new UserEntity();
		UserEntity old_user = (UserEntity)request.getSession().getAttribute("user");
		user.setId(old_user.getId());
		user.setAddress(address);
		user.setRealname(realname);
		user.setTelephone(telephone);
		user.setPosition(position);
		user.setCompany(company);
		user.setPhone(phone);
		user.setEmail(email);
		user.setSignature(signature);
		user.setSex(sex);
		
		user.setProvinceid(pr_id);
		user.setCityid(ci_id);
		user.setSchoolid(sh_id);
		user.setMajor(major);
		user.setRoleid(roleid);
		user.setNickname(nickname);
		user.setInterest(interest);
		user.setAge(age);
		user.setSkill1(skill1);
		user.setSkill2(skill2);
		
		user.setIntention(intention);
		if(!user.getId().equals(0)){
			if(StringUtil.isnotNull(user.getPassword()))
			{
				user.setPassword(EncodeUtil.encodeByMD5(user.getPassword()));
			}
			this.userDao.updateUser(user);
			UserEntity	ol_user = this.userDao.findUserById(user.getId().intValue());
			ol_user.setPassword("********");
			request.getSession().setAttribute("user",ol_user);
			return ResponseData.newSuccess(ol_user);
		}
		return ResponseData.newFailure();
	}
	
	
	@RequestMapping(value = "/updateResume")
	@ResponseBody
	public Object updateResume(HttpServletRequest request, 
			@RequestParam(value="realname",required=false)String realname,
			@RequestParam(value="introduction",required=false)String introduction,
			@RequestParam(value="telephone",required=false)String telephone,
			@RequestParam(value="address",required=false)String address,
			@RequestParam(value="email",required=false)String email,
			@RequestParam(value="age",required=false)int age,
			@RequestParam(value="intention",required=false)String intention
			
			) throws ApiException {
		UserEntity user = new UserEntity();
		UserEntity old_user = (UserEntity)request.getSession().getAttribute("user");
		user.setId(old_user.getId());
		user.setAddress(address);
		user.setRealname(realname);
		user.setTelephone(telephone);
		user.setEmail(email);
		user.setAge(age);
		user.setIntroduction(introduction);
		user.setIntention(intention);
		if(!user.getId().equals(0)){
			if(StringUtil.isnotNull(user.getPassword()))
			{
				user.setPassword(EncodeUtil.encodeByMD5(user.getPassword()));
			}
			this.userDao.updateUser(user);
			UserEntity	ol_user = this.userDao.findUserById(user.getId().intValue());
			ol_user.setPassword("********");
			request.getSession().setAttribute("user",ol_user);
			return ResponseData.newSuccess(ol_user);
		}
		return ResponseData.newFailure();
	}
	
	
	@RequestMapping(value = "/updateAvatar")
	@ResponseBody
	public Object updateAvatar(HttpServletRequest request, @RequestParam(required = false) MultipartFile useravatar) throws ApiException {
		UserEntity user = new UserEntity();
		UserEntity old_user = (UserEntity)request.getSession().getAttribute("user");
		user.setId(old_user.getId());
		if(useravatar!=null){
			String dir = String.format("%s/user/avatar/%s", baseDir, user.getId());
			String fileName = String.format("user%s_%s.%s", user.getId(), new Date().getTime(), "jpg");
			UploadFileEntity uploadFileEntity = new UploadFileEntity(fileName, useravatar, dir);
			ftpService.uploadFile(uploadFileEntity);
			user.setAvatar(FtpService.READ_URL+"data/"+dir + "/" + fileName);//http://42.121.130.177:8089/picture/user/1124/3456789.png
			this.userDao.updateUser(user);
			UserEntity	ol_user = this.userDao.findUserById(user.getId().intValue());
			ol_user.setPassword("********");
			request.getSession().setAttribute("user",ol_user);
			return ResponseData.newSuccess(ol_user);
		}
		return ResponseData.newFailure();
	}
	
	@RequestMapping(value = "/updatePhoto")
	@ResponseBody
	public Object updatePhoto(HttpServletRequest request, @RequestParam(required = false) MultipartFile userphoto) throws ApiException {
		UserEntity user = new UserEntity();
		UserEntity old_user = (UserEntity)request.getSession().getAttribute("user");
		user.setId(old_user.getId());
		if(userphoto!=null){
			String dir = String.format("%s/user/photo/%s", baseDir, user.getId());
			String fileName = String.format("user%s_%s.%s", user.getId(), new Date().getTime(), "jpg");
			UploadFileEntity uploadFileEntity = new UploadFileEntity(fileName, userphoto, dir);
			ftpService.uploadFile(uploadFileEntity);
			user.setPhoto(FtpService.READ_URL+"data/"+dir + "/" + fileName);//http://42.121.130.177:8089/picture/user/1124/3456789.png
			this.userDao.updateUser(user);
			UserEntity	ol_user = this.userDao.findUserById(user.getId().intValue());
			ol_user.setPassword("********");
			request.getSession().setAttribute("user",ol_user);
			return ResponseData.newSuccess(ol_user);
		}
		return ResponseData.newFailure();
	}
	
	@RequestMapping(value = "/checkOldPassword")
	@ResponseBody
	public boolean checkOldPassword(HttpServletRequest request,String pwd){
		if(StringUtil.isNull(pwd)){return false;};
		pwd=EncodeUtil.encodeByMD5(pwd);
		UserEntity ol_user = (UserEntity)request.getSession().getAttribute("user");
		UserEntity	new_user=this.userDao.findUserById(ol_user.getId().intValue());
		return pwd.equals(new_user.getPassword());
	}
	
	@RequestMapping(value = "/upPwdByTelephone")
	@ResponseBody
	public ResponseData<String> upPwdByTelephone(HttpServletRequest request,String key,String toke,UserEntity user){
		if(StringUtil.isnotNull(key)&&StringUtil.isnotNull(toke)){
			String stoke=request.getSession().getAttribute(key+"shear_yzm")+""; request.getSession().removeAttribute(key+"shear_yzm");  
			if(toke.equalsIgnoreCase(stoke)){
				if(StringUtil.isnotNull(user.getPassword())){user.setPassword(EncodeUtil.encodeByMD5(user.getPassword()));}
				userDao.updateUser(user);
				return ResponseData.newSuccess("密码修改重置成功！");
			}else{
				return ResponseData.newFailure("非法操作！");			}
		}
		return ResponseData.newFailure("非法操作！");		//返回受影响的行
	}
	
	/**
	 * 检查手机是不是已经注册
	 * @param request
	 * @param telephone
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "/userTelephoneVerify")
	@ResponseBody
	public Object userTelephoneVerify(HttpServletRequest request, String telephone) throws ApiException {
		if(userDao.findUserByTelephone(telephone)==null)
			return ResponseData.newSuccess("该手机号还未注册任何账号");
		return ResponseData.newFailure("该手机号已经注册过了");
	}
	/**
	 * 检查用户名是否占用
	 * @param request true：表示当前用户名已存在或为null->不能注册
	 * @param userName
	 * @return
	 */
	@RequestMapping(value = "/existenceUserName")
	@ResponseBody
	public Object existenceUserName(HttpServletRequest request,String userName){
		if(userDao.findUserByName(userName)==null)
			return ResponseData.newSuccess("用户名可以使用");
		return ResponseData.newFailure("用户名已经存在");
	}
	
	
	@RequestMapping(value = "/findRoles")
	@ResponseBody
	public Object findRoles(
			HttpServletRequest request
			) throws UnsupportedEncodingException {
	     List<RoleEntity> roleEntities = roleMapper.findRoles();
	     if (roleEntities!=null&&!roleEntities.isEmpty()) {
	    	 return ResponseData.newSuccess(roleEntities);
		 }
	    return ResponseData.newFailure("没有找到角色");
	}	
	
	 @RequestMapping(value = "/findRoleById", method = RequestMethod.GET)
	 @ResponseBody
	 public Object findRoleById(@RequestParam int roleID) {
	        return roleMapper.findRoleById(roleID);
     }
	
	 @RequestMapping(value = "/findFollowerByUserId", method = RequestMethod.GET)
	 @ResponseBody
	 public Object findFollowerByUserId(@RequestParam Integer userID) {
		    List<FollowEntity> followEntities = followMapper.findFollowByUserId(userID);
		    List<UserDto> userDtos = new ArrayList<UserDto>();
		    if (!followEntities.isEmpty()) {
		    	for (int i = 0; i < followEntities.size(); i++) {
			    	UserEntity userEntity = userDao.findUserById(followEntities.get(i).getFollowedid().intValue());
					UserDto userDto = new UserDto();
					if (userEntity!=null) {
						userDto.setId(userEntity.getId());
						userDto.setAvatar(userEntity.getAvatar());
						if(userEntity.getSignature()==null){
							userDto.setSignature("该用户还没有留下自己的签名");
						}
						else {
							userDto.setSignature(userEntity.getSignature());
						}
						if (userEntity.getNickname()==null) {
							userDto.setNickname("游客"+userEntity.getId());
						}
						else{
						    userDto.setNickname(userEntity.getNickname());
						}
						if (userEntity.getScore()!=null) {
							if(userEntity.getScore().intValue()<100){
								userDto.setLevel(1);
							}
							else if (userEntity.getScore().intValue()>=100&&userEntity.getScore().intValue()<500) {
								userDto.setLevel(2);
							}
							else if (userEntity.getScore().intValue()>=500) {
								userDto.setLevel(3);
							}
						}
						else {
							userDto.setLevel(1);
						}
						userDto.setSkill1(userEntity.getSkill1());
						userDto.setSkill2(userEntity.getSkill2());
						userDtos.add(userDto);
					}
				}
			}
	        return userDtos;
     }
	 
	@RequestMapping(value = "/findEducateByUserID")
	@ResponseBody
	public Object findEducateByUserID(
			@RequestParam(value="userID", required=false) Integer userID
			) throws UnsupportedEncodingException {
	     List<EducateEntity> educateEntities = educateMapper.findEducateByUserId(userID);
	     if (educateEntities!=null&&!educateEntities.isEmpty()) {
	    	 return ResponseData.newSuccess(educateEntities);
		 }
	    return ResponseData.newFailure("没有教育经历"); 
	}
	
	@RequestMapping(value = "/findSkillByUserID")
	@ResponseBody
	public Object findSkillByUserID(
			@RequestParam(value="userID", required=false) Integer userID
			) throws UnsupportedEncodingException {
	     List<SkillEntity> skillEntities = skillMapper.findSkillByUserId(userID);
	     if (skillEntities!=null&&!skillEntities.isEmpty()) {
	    	 return ResponseData.newSuccess(skillEntities);
		 }
	    return ResponseData.newFailure("没有技能");
	}	
	
	@RequestMapping(value = "/findJobByUserID")
	@ResponseBody
	public Object findJobByUserID(
			@RequestParam(value="userID", required=false) Integer userID
			) throws UnsupportedEncodingException {
	     List<JobEntity> jobEntities = jobMapper.findJobByUserId(userID);
	     if (jobEntities!=null&&!jobEntities.isEmpty()) {
	    	 return ResponseData.newSuccess(jobEntities);
		 }
	    return ResponseData.newFailure("没有工作经历");
	}	
	
	@RequestMapping(value = "/findHonorByUserID")
	@ResponseBody
	public Object findHonorByUserID(
			@RequestParam(value="userID", required=false) Integer userID
			) throws UnsupportedEncodingException {
	     List<HonorEntity> honorEntities = honorMapper.findHonorByUserId(userID);
	     if (honorEntities!=null&&!honorEntities.isEmpty()) {
	    	 return ResponseData.newSuccess(honorEntities);
		 }
	    return ResponseData.newFailure("没有获得荣誉");
	}
	
	@RequestMapping(value = "/deleteEducate")
	@ResponseBody
	public Object deleteEducate(HttpServletRequest request, 
			@RequestParam(value="educateID", required=false) Integer educateID
			) throws UnsupportedEncodingException {
		educateMapper.deleteEducate(educateID);
		UserEntity userEntity = (UserEntity)request.getSession().getAttribute("user");
		return ResponseData.newSuccess(educateMapper.findEducateByUserId(userEntity.getId().intValue()));
	}
	

	@RequestMapping(value = "/addEducate")
	@ResponseBody
	public Object addEducate(HttpServletRequest request, 
			@RequestParam(value="educatestarttime",required=false)String educatestarttime,
			@RequestParam(value="educateendtime",required=false)String educateendtime,
			@RequestParam(value="educateschool",required=false)String educateschool,
			@RequestParam(value="educatedegree",required=false)String educatedegree,
			@RequestParam(value="educatefulltime",required=false)int educatefulltime,
			@RequestParam(value="educateuserid",required=false)int educateuserid,
			@RequestParam(value="educatemajor",required=false)String educatemajor,
			@RequestParam(value="educatedescribe",required=false)String educatedescribe
			) throws ApiException {
		EducateEntity educateEntity = new EducateEntity();
		educateEntity.setDegree(educatedegree);
		educateEntity.setDescribe(educatedescribe);
		educateEntity.setEndtime(educateendtime);
		educateEntity.setFulltime(educatefulltime);
		educateEntity.setMajor(educatemajor);
		educateEntity.setSchool(educateschool);
		educateEntity.setStarttime(educatestarttime);
		educateEntity.setUserid(BigInteger.valueOf(educateuserid));
		educateMapper.insertEducate(educateEntity);
		return ResponseData.newSuccess(educateMapper.findEducateByUserId(educateuserid));
	}
	
	@RequestMapping(value = "/deleteJob")
	@ResponseBody
	public Object deleteJob(HttpServletRequest request, 
			@RequestParam(value="jobID", required=false) Integer jobID
			) throws UnsupportedEncodingException {
		jobMapper.deleteJob(jobID);
		UserEntity userEntity = (UserEntity)request.getSession().getAttribute("user");
		return ResponseData.newSuccess(jobMapper.findJobByUserId(userEntity.getId().intValue()));
	}
	
	@RequestMapping(value = "/addJob")
	@ResponseBody
	public Object addJob(HttpServletRequest request, 
			@RequestParam(value="jobstarttime",required=false)String jobstarttime,
			@RequestParam(value="jobendtime",required=false)String jobendtime,
			@RequestParam(value="jobcompany",required=false)String jobcompany,
			@RequestParam(value="jobposition",required=false)String jobposition,
			@RequestParam(value="jobcompanysize",required=false)String jobcompanysize,
			@RequestParam(value="jobindustry",required=false)String jobindustry,
			@RequestParam(value="jobcompanynature",required=false)String jobcompanynature,
			@RequestParam(value="jobuserid",required=false)int jobuserid,
			@RequestParam(value="jobdescribe",required=false)String jobdescribe
			) throws ApiException {
		JobEntity jobEntity = new JobEntity();
		jobEntity.setCompany(jobcompany);
		jobEntity.setCompanynature(jobcompanynature);
		jobEntity.setCompanysize(jobcompanysize);
		jobEntity.setDescribe(jobdescribe);
		jobEntity.setEndtime(jobendtime);
		jobEntity.setIndustry(jobindustry);
		jobEntity.setPosition(jobposition);
		jobEntity.setStarttime(jobstarttime);
		jobEntity.setUserid(BigInteger.valueOf(jobuserid));
		jobMapper.insertJob(jobEntity);
		return ResponseData.newSuccess(jobMapper.findJobByUserId(jobuserid));
	}
	
	@RequestMapping(value = "/deleteHonor")
	@ResponseBody
	public Object deleteHonor(HttpServletRequest request, 
			@RequestParam(value="honorID", required=false) Integer honorID
			) throws UnsupportedEncodingException {
		honorMapper.deleteHonor(honorID);
		UserEntity userEntity = (UserEntity)request.getSession().getAttribute("user");
		return ResponseData.newSuccess(honorMapper.findHonorByUserId(userEntity.getId().intValue()));
	}
	
	@RequestMapping(value = "/addHonor")
	@ResponseBody
	public Object addHonor(HttpServletRequest request, 
			@RequestParam(value="honorstarttime",required=false)String honorstarttime,
			@RequestParam(value="honorendtime",required=false)String honorendtime,
			@RequestParam(value="honorhonor",required=false)String honorhonor,
			@RequestParam(value="honoruserid",required=false)int honoruserid
			) throws ApiException {
		HonorEntity honorEntity = new HonorEntity();
		honorEntity.setEndtime(honorendtime);
		honorEntity.setHonor(honorhonor);
		honorEntity.setStarttime(honorstarttime);
		honorEntity.setUserid(BigInteger.valueOf(honoruserid));
		honorMapper.insertHonor(honorEntity);
		return ResponseData.newSuccess(honorMapper.findHonorByUserId(honoruserid));
	}
	
	@RequestMapping(value = "/deleteSkill")
	@ResponseBody
	public Object deleteSkill(HttpServletRequest request, 
			@RequestParam(value="skillID", required=false) Integer skillID
			) throws UnsupportedEncodingException {
		skillMapper.deleteSkill(skillID);
		UserEntity userEntity = (UserEntity)request.getSession().getAttribute("user");
		return ResponseData.newSuccess(skillMapper.findSkillByUserId(userEntity.getId().intValue()));
	}
	
	@RequestMapping(value = "/addSkill")
	@ResponseBody
	public Object addSkill(HttpServletRequest request, 
			@RequestParam(value="skillskill",required=false)String skillskill,
			@RequestParam(value="skilldegree",required=false)int skilldegree,
			@RequestParam(value="skilluserid",required=false)int skilluserid
			) throws ApiException {
		SkillEntity skillEntity = new SkillEntity();
		skillEntity.setDegree(skilldegree);
		skillEntity.setSkill(skillskill);
		skillEntity.setUserid(BigInteger.valueOf(skilluserid));
		skillMapper.insertSkill(skillEntity);
		return ResponseData.newSuccess(skillMapper.findSkillByUserId(skilluserid));
	}
	
	
	
	@RequestMapping(value = "/updateUserPwd")
	@ResponseBody
	public Object updateUserPwd(HttpServletRequest request, 
			@RequestParam(value="changepwd",required=false)String changepwd,
			@RequestParam(value="telephone",required=false)String telephone
			) throws ApiException {
		String password = EncodeUtil.encodeByMD5(changepwd);
		userDao.updateUserPwd(password, telephone);
	    return ResponseData.newSuccess("密码修改成功");
	}
	
	
 /*
  * 后端代码开始================================================================================
  */
	@RequestMapping(value = "/findUserListForBg", method = RequestMethod.POST)
	@ResponseBody
	public Object findUserListForBg(@RequestParam(value="pageNum",required=false) Integer pageNum,
			@RequestParam(value="pageSize") Integer pageSize, 
			@RequestParam(value="audit", required=false) Integer audit,
			@RequestParam(value="keyword", required=false) String keyword) throws UnsupportedEncodingException {
		if( !(audit == -1 || audit == 1 || audit == 0) ){
			audit = null;
		}
		pageNum = pageNum == null? 1:pageNum;
		pageSize = pageSize==null? 12:pageSize;
		PageHelper.startPage(pageNum, pageSize);
		if(keyword.equals("undefined"))
			keyword = null;
		else{
		keyword = URLDecoder.decode(keyword, "UTF-8");
		}
		return new PageInfo<UserEntity>(userDao.findAllUserForBg(audit,keyword));
		
	}
	
	@RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
	@ResponseBody
	public Object deleteUser(int userID) {
		 userDao.deleteUser(userID);
		 return new BaseDto(0);
	}
	
	@RequestMapping(value = "/deleteByUserIDs")
	@ResponseBody
	public Object deleteByUserIDs(Integer[] userIDs) {
		for(Integer userID:userIDs){
			userDao.deleteUser(userID);
		}
		return new BaseDto(0);
	}
	@RequestMapping(value = "/addUser", method = RequestMethod.GET)
	@ResponseBody
	public Object addUser(UserEntity user) throws UnsupportedEncodingException {
		if (user.getUsername() == null || user.getPassword() == null||user.getTelephone()==null) {
			return new ResultDto(-1, "用户名和密码,手机号不能为空");
		}
		user.setUsername(URLDecoder.decode(user.getUsername(), "UTF-8"));
		user.setPassword(EncodeUtil.encodeByMD5(user.getPassword()));
		userDao.insertUser(user);
		return new BaseDto(0);
	}
}
