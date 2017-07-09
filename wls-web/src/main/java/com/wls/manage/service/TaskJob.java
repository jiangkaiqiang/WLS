package com.wls.manage.service;
import org.springframework.scheduling.annotation.Scheduled;    
import org.springframework.stereotype.Component;  
import com.wls.manage.service.base.HttpService;
import com.wls.manage.service.base.impl.HttpServiceImpl;  
@Component("taskJob")  
public class TaskJob {  
	//String url = "http://www.weilanshu.com";
	String url = "http://localhost:8989";
    @Scheduled(cron = "0 30 4 * * ?")  
    public void job1() {  
        HttpService httpService = new HttpServiceImpl();
        httpService.sendGet(url+"/i/information/addInformationWithChuangyebang",20000);
    }  
    @Scheduled(cron = "0 33 4 * * ?")  
    public void job2() {  
        HttpService httpService = new HttpServiceImpl();
        httpService.sendGet(url+"/i/information/addInformationWithIresearch",20000);
    }  
    @Scheduled(cron = "0 36 4 * * ?")  
    public void job3() {  
        HttpService httpService = new HttpServiceImpl();
        httpService.sendGet(url+"/i/information/addInformationWithYiouKeJi",20000);
    }  
    @Scheduled(cron = "0 39 4 * * ?")  
    public void job4() {  
        HttpService httpService = new HttpServiceImpl();
        httpService.sendGet(url+"/i/information/addInformationWithYiouWenChuang",20000);
    }  
    @Scheduled(cron = "0 42 4 * * ?")  
    public void job5() {  
        HttpService httpService = new HttpServiceImpl();
        httpService.sendGet(url+"/i/information/addInformationWithCXiaoYuanPsy",20000);
    }  
    @Scheduled(cron = "0 45 4 * * ?")  
    public void job6() {  
        HttpService httpService = new HttpServiceImpl();
        httpService.sendGet(url+"/i/information/addInformationWithChinanews",20000);
    } 
    @Scheduled(cron = "0 48 4 * * ?")  
    public void job7() {  
        HttpService httpService = new HttpServiceImpl();
        httpService.sendGet(url+"/i/information/addInformationWithLY",20000);
    } 
    @Scheduled(cron = "17 28 0 * * ?")  
    public void job8() {  
        HttpService httpService = new HttpServiceImpl();
        httpService.sendGet(url+"/i/recruit/addRecruitWithJZM",20000);
    } 
    @Scheduled(cron = "17 34 13 * * ?")  
    public void job9() {  
        HttpService httpService = new HttpServiceImpl();
        httpService.sendGet(url+"/i/recruit/addRecruitWithWTG",20000);
    } 
    @Scheduled(cron = "0 11 0 * * ?")  
    public void job10() {  
        HttpService httpService = new HttpServiceImpl();
        httpService.sendGet(url+"/i/recruit/addRecruitWithSHIXI",20000);
    } 
    @Scheduled(cron = "0 57 0 * * ?")  
    public void job11() {  
        HttpService httpService = new HttpServiceImpl();
        httpService.sendGet(url+"/i/recruit/addRecruitWithDJ",20000);
    } 
   /* @Scheduled(cron = "0 51 4 * * ?")  
    public void job8() {  
        HttpService httpService = new HttpServiceImpl();
        httpService.sendGet(url+"/i/information/addInformationWithBJ",20000);
    } */
    /*
    @Scheduled(cron = "0 48 23 * * ?")  
    public void job7() {  
        HttpService httpService = new HttpServiceImpl();
        httpService.sendGet(url+"/i/information/addInformationWithTengxunTech",20000);
    }  */
}