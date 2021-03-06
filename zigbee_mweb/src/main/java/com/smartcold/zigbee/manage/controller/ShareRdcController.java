package com.smartcold.zigbee.manage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.smartcold.zigbee.manage.dao.FileDataMapper;
import com.smartcold.zigbee.manage.dto.RdcShareDTO;
import com.smartcold.zigbee.manage.entity.UserEntity;
import com.smartcold.zigbee.manage.service.CommonService;
import com.smartcold.zigbee.manage.service.DocLibraryService;
import com.smartcold.zigbee.manage.service.RdcShareService;
import com.smartcold.zigbee.manage.util.ResponseData;
import com.smartcold.zigbee.manage.util.SessionUtil;
import com.smartcold.zigbee.manage.util.StringUtil;

@Controller
@RequestMapping(value = "/ShareRdcController")
public class ShareRdcController  {
	
	private int pageNum;//当前页数
	private int pageSize;//每页数量
	
	@Resource(name="commonService")
	private CommonService commonService;
	
	@Resource(name="rdcShareService")
	private RdcShareService rdcShareService;
	
	@Resource(name="docLibraryService")
	private DocLibraryService docLibraryService;
	

	/**
	 * @author MaQiang
	 * @date 2016年6月28日16:00:58
	 */
	private void getPageInfo(HttpServletRequest request) {
		this.pageNum  = Integer.parseInt(request.getParameter("pageNum") == null ? "1" : request.getParameter("pageNum"));
		this.pageSize = Integer.parseInt(request.getParameter("pageSize") == null ? "10" : request.getParameter("pageSize")); // 每页数据量
	}
	
	//-----------------------------------------------------------------------------辅助查询条件------------------------------------------------------------
	/**
	 * 查询下拉框数据  Description: ui_getSeleectData
	 * Description: ui_getSeleectData
	 * @author MaQiang
	 * @date 2016年4月25日下午3:32:25
	 * @param tb：对应数据库表明
	 * @param cl：对应数据库列名
	 * @param vl：保存数据库值
	 * @param txt：下拉框显示值
	 * @param cs  对应实体类 ：默认- com.farmen.core.entities.CommMataData
	 * @return
	 */
	@RequestMapping(value = "/ui_getSeleectData")
	@ResponseBody
	public ResponseData<Map<String, Object>> ui_getSeleectData(String data,String value,String text,String cs,String parentCode,String filter) {
				if(data==null||"".equals(data)){
					return ResponseData.newFailure("请设置完整信息");
				}else{
					 try {
						 List<Map<String, Object>> objdata = this.commonService.getBaseData(data+filter, value, text);
						 return ResponseData.newSuccess(objdata);
					} catch (Exception e) {
							e.printStackTrace();
							return ResponseData.newFailure("网络异常！稍后重试！");
					}
				}
	}
	
	/**
	 * 获得睿库共享过滤信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getSEFilterData")
	@ResponseBody
	public ResponseData<HashMap<String, Object>> getSEFilterData(HttpServletRequest request) {
		HashMap<String, Object> data = new HashMap<String, Object>();
		ResponseData<HashMap<String, Object>> result = ResponseData.getInstance();
		data.put("mt", this.commonService.getBaseData("storagemanagetype", "id", "type"));// 经营类型
		data.put("st", this.commonService.getBaseData("storagetempertype", "id", "type"));// 温度类型
		result.setEntity(data);
		return result;
	}
	/**
	 * 获得睿库商品过滤信息
	 * 
	 * @return
	 */

	@RequestMapping(value = "/getGDFilterData")
	@ResponseBody
	public ResponseData<HashMap<String, Object>> getGDFilterData(HttpServletRequest request) {
		ResponseData<HashMap<String, Object>> result = ResponseData.getInstance();
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("gt", this.commonService.getCommData("good_type"));// 经营类型
		result.setEntity(data);
		return result;
	}
	
	/**
	 * 获得配送过滤信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getPSFilterData")
	@ResponseBody
	public ResponseData<HashMap<String, Object>> getPSFilterData(HttpServletRequest request) {
		ResponseData<HashMap<String, Object>> result = ResponseData.getInstance();
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("fm", this.commonService.getCommData("ps_fm_type"));//业务类型
		data.put("cl", this.commonService.getCommData("ps_cl_type"));//温度类型
		data.put("sk", this.commonService.getBaseData("storagetruck", "id", "type"));// 车型//
		result.setEntity(data);
		return result;
	}
	
	/**
	 * 提供筛选条件
	 * @param sqm
	 * @return
	 */
	private static String getSqmFilter(String sqm)
	{  
		StringBuffer sqlfilter=new StringBuffer("(");
		if(StringUtil.isnotNull(sqm)){
			String filter[]=sqm.split(",");
			for (String betdata : filter) {
			  String betsmdata[]=	betdata.split("~");
			    if(betsmdata.length==2){
			    	sqlfilter.append(" s.sqm BETWEEN "+betsmdata[0]+" AND "+betsmdata[1] +" or");
			    }else{
			    	sqlfilter.append(" s.sqm  "+betsmdata[0]+" or");
			    }
			}
			return sqlfilter.substring(0, sqlfilter.length()-2)+")";
	   }
		return "";
	}
	//-------------------------------------------------2->数据展示 1：货品 2：配送  3：仓库具有公共属性,可重用方法，但为了后期维护和程序健壮性----采用3个公共方法,方便分库分表---------------------------------------------------
	/**
	 * 获得发布信息详细信息
	 * @param request
	
	 * @return
	 */
	@RequestMapping(value = "/getSEByID")
	@ResponseBody
	public ResponseData<RdcShareDTO> getSEByID(HttpServletRequest request,String id) {
		if(StringUtil.isnotNull(id)){
			RdcShareDTO data = this.rdcShareService.getSEByID(id);
			return ResponseData.newSuccess(data);
		}
	    return ResponseData.newFailure("无效请求！");
	}
	/**
	 * 获得货品信息
	 * @param request
	 * @param type  出售求购
	 * @param datatype 数据类型  1：货品 2：配送  3：仓库
	 * @param goodtype 过滤条件
	 * @param keyword  关键字
	 * @param provinceid 地域
	 * @param orderBy 排序
	 * @return
	 */
	@RequestMapping(value = "/getSEListByRdcID")
	@ResponseBody
	public ResponseData<RdcShareDTO> getSEListByRdcID(HttpServletRequest request,String rdcID,String type,String datatype, String keyword,String orderBy) {
		this.getPageInfo(request);
		HashMap<String, Object> filter=new HashMap<String, Object>();
		filter.put("sstauts", 1);//必须
		filter.put("type", type);
		filter.put("rdcID", rdcID);
		filter.put("keyword", keyword);
		filter.put("datatype", datatype);
		filter.put("orderBy", orderBy);
		PageInfo<RdcShareDTO> data = this.rdcShareService.getSEListByRdcID(this.pageNum, this.pageSize, filter);
		return ResponseData.newSuccess(data);
	}
	
	
    /**
     * 获得货品信息
     * @param request
     * @param type  出售求购
     * @param datatype 数据类型  1：货品 2：配送  3：仓库
     * @param goodtype 过滤条件
     * @param keyword  关键字
     * @param provinceid 地域
     * @param orderBy 排序
     * @return
     */
	@RequestMapping(value = "/getSEGDList")
	@ResponseBody
	public ResponseData<RdcShareDTO> getSEGDList(HttpServletRequest request,String type,String datatype,String goodtype, String keyword,String provinceid,String orderBy) {
		this.getPageInfo(request);
		HashMap<String, Object> filter=new HashMap<String, Object>();
		filter.put("type", type);
		filter.put("sstauts", 1);//必须
		filter.put("datatype", datatype);//必须
		filter.put("goodtype", goodtype);
		filter.put("keyword", keyword);
		filter.put("provinceid", provinceid);
		filter.put("orderBy", orderBy);
		PageInfo<RdcShareDTO> data = this.rdcShareService.getSEGDList(this.pageNum, this.pageSize, filter);
		return ResponseData.newSuccess(data);
	}
	/**
	 * 获得配送信息
	 * @param request
	 * @param type 出租、求车？1:2
	 * @param datatype 数据类型  1：货品 2：配送  3：仓库->3
	 * @param keyword  关键字
	 * @param stprovinceID->stcityID:出发地
	 * @param toprovinceID->tocityID:目的地
	 * @param validStartTime->validEndTime:发货时间 
	 * @param carType 车型
	 * @param businessType 业务类型
	 * @param storagetempertype 温度类型
	 * @param orderBy 排序
	 * @return
	 */
	@RequestMapping(value = "/getSEPSList")
	@ResponseBody
	public ResponseData<RdcShareDTO> getSEPSList(HttpServletRequest request,String type,String datatype, String keyword,String stprovinceID,String stcityID,String toprovinceID,String tocityID,String validStartTime ,String validEndTime,String storagetempertype,String businessType,String carType,String orderBy) {
		this.getPageInfo(request);
		HashMap<String, Object> filter=new HashMap<String, Object>();
		filter.put("type", type);//OK
		filter.put("sstauts", 1);//必须
		filter.put("datatype", datatype);//必须 OK
		filter.put("keyword", keyword);//OK
		filter.put("stprovinceID", stprovinceID);//
		filter.put("stcityID", stcityID);//
		filter.put("toprovinceID", toprovinceID);//
		filter.put("tocityID", tocityID);//
		filter.put("validStartTime", validStartTime);//
		filter.put("validEndTime", validEndTime);//
		filter.put("carType", carType);//车型->ok
		filter.put("businessType", businessType);//业务类型->OK
		filter.put("storagetempertype", storagetempertype);//storagetempertype->OK
		filter.put("orderBy", orderBy);//
		System.err.println(filter);
		PageInfo<RdcShareDTO> data = this.rdcShareService.getSEPSList(this.pageNum, this.pageSize, filter);
		return ResponseData.newSuccess(data);
	}
	
	/**
	 * 获得睿库共享列表
	 * @param request
	 * @param type->  null：不限  1：出租/出售 2：求租/求购
	 * @param orderBy 排序
	 * @param datatype 数据类型  1：货品 2：配送  3：仓库
	 * @param sqm 面积-> rcd r
	 * @param provinceid 区域 -> rcd r
	 * @param keyword 支持关键字搜索-> rcd r
	 * @param managetype 经营类型  -> rdcext t
	 * @param storagetempertype 温度类型 -> rdcext t
	 * @return
	 */
	@RequestMapping(value = "/getSERDCList")
	@ResponseBody
	public ResponseData<RdcShareDTO> getSERDCList(HttpServletRequest request,String datatype,String goodtype, String keyword,String type,String provinceid, String managetype,String storagetempertype,String sqm,String orderBy) {
		this.getPageInfo(request);
		HashMap<String, Object> filter=new HashMap<String, Object>();
		filter.put("type", type);
		filter.put("sstauts", 1);//必须：是否有效  --级别1->有效时间：级别2  
		filter.put("datatype",datatype);//必须
		filter.put("goodtype", goodtype);
		filter.put("sqm", getSqmFilter(sqm));//  "<1000,1000~3000,3000~6000,6000~12000,12000~20000"
		filter.put("keyword", keyword);
		filter.put("provinceid", provinceid);
		filter.put("managetype", managetype);
		filter.put("storagetempertype", storagetempertype);
		filter.put("orderBy", orderBy);
		PageInfo<RdcShareDTO> data = this.rdcShareService.getSERDCList(this.pageNum, this.pageSize, filter);
		return ResponseData.newSuccess(data);
	}
	
	//------------------------------------------------------------------------------------3:抢单操作-------------------------------------------------------
	/**
	 * 
	 * @return
	 */
	/*@RequestMapping(value="sharvistPhone")
	@ResponseBody
	public ResponseData<String> sharvistPhone(HttpServletRequest request,String dataid,String telephone,String yzm){
		try {
			if(StringUtil.isnotNull(telephone)){
				TelephoneVerifyUtil teleVerify = new TelephoneVerifyUtil();
				String signUpCode = teleVerify.identityVerify(telephone);
				request.getSession().setAttribute("shear_order_id", dataid);
				request.getSession().setAttribute("shear_order_yzm", signUpCode);
				request.getSession().setAttribute("shear_order_telephone", telephone);
				return ResponseData.newSuccess("验证码已发送到您的手机！请注意查收！");
			}
			return  ResponseData.newFailure("请输入有效手机号码！！");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseData.newFailure("未知异常！");
	}*/
	
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value="sharvistCode")
	@ResponseBody
	public boolean sharvistCode(HttpServletRequest request,String dataid,String telephone,String yzm){
	  if(StringUtil.isnotNull(yzm)){
		  String sysyzm=	(String) request.getSession().getAttribute("shear_order_yzm");
		  return yzm.equalsIgnoreCase(sysyzm); 
	  }
	  return false;
	}
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value="shareApplyObj")
	@ResponseBody
	public ResponseData<Object> shareApplyObj(HttpServletRequest request,String dataid,String phone){
		
		return null;
	}
	//------------------------------------------------------------------------------------4:免费发布消息-------------------------------------------------------
	/**
	 * 免费发布消息
	 * @param request
	 * @param datatype
	 * @param dataid
	 * @return
	 */
	@RequestMapping(value="getRdcByUid")
	@ResponseBody
	public ResponseData<RdcShareDTO> getRdcByUid(HttpServletRequest request){
		this.getPageInfo(request);//
		UserEntity user =(UserEntity) SessionUtil.getSessionAttbuter(request, "user");//警告 ->调用该方法必须登录
		if(user!=null&&user.getId()!=0 ){
			HashMap<String, Object> parameters=new HashMap<String, Object>();
			parameters.put("userid",user.getId());// 
			PageInfo<RdcShareDTO> rdcList = this.rdcShareService.getRdcList(this.pageNum, this.pageSize, parameters);
			return ResponseData.newSuccess(rdcList);
		}
	    return ResponseData.newFailure();
	}
	
	
	/**
	 * 免费发布消息
	 * @param request
	 * @param datatype
	 * @param dataid
	 * @RequestParam("files") CommonsMultipartFile[] files
	 * @return
	 */
	@RequestMapping(value="shareFreeRelease")
	@ResponseBody
	public ResponseData<RdcShareDTO> shareFreeRelease(HttpServletRequest request,String  data){
		try {
			UserEntity user =(UserEntity) SessionUtil.getSessionAttbuter(request, "user");
			if(StringUtil.isnotNull(data)&&user!=null&&user.getId()!=0){//
				RdcShareDTO	rdcShareDTO= JSON.parseObject(data, RdcShareDTO.class);//页面数据/ /1.获得表单数据
				rdcShareDTO.setReleaseID(user.getId());//设置发布消id//user.getId()
				rdcShareDTO.setStauts(1);
	            this.rdcShareService.addShareMsg(rdcShareDTO);//免费发布消息
	            this.docLibraryService.handleFile(rdcShareDTO.getId(), FileDataMapper.CATEGORY_SHARE_PIC, user, request);
	            return ResponseData.newSuccess("发布成功！");
			}else{
				return ResponseData.newFailure("当前用户没有执行登录操作！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseData.newFailure("发布失败!请稍后重试！");
	}
	
}
