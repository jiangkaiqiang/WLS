package com.wls.manage.crawler.shixi;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.wls.manage.dto.NewInfomationDto;
import com.wls.manage.entity.Recruit;

/**
 * Created by haolidong on 2016/11/14.
 */
public class PageCrawJob_shixi {
    public  void  pageCraw(Recruit ni){
        String content="";
        try{
            Thread.sleep(300);
            PageParseJob_shixi crawler =new PageParseJob_shixi();
            crawler.parse(ni);
        } catch (Exception e){
            e.printStackTrace();
        }
  
    }

    public static void main(String args[]){
//        System.out.println(new PageCrawJob_shixi().pageCraw("http://www.cyzone.cn/a/20161230/307006.html"));
    }
}
