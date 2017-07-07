package com.wls.manage.crawler.kdjz;

import com.wls.manage.crawler.general.BasicCrawler;
import com.wls.manage.dto.NewInfomationDto;
import com.wls.manage.entity.Recruit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by haolidong on 2016/11/14.
 */
public class PageParseJob_kdjz {

    public  void parse(Recruit ni){
        String content = "";
        try {
            content= BasicCrawler.crawlPage(ni.getUrl(),"utf-8");
            System.out.println(content);
        }catch (Exception e){
            e.printStackTrace();
        }
        Document doc = Jsoup.parse(content);
        parseNews(doc,ni);
    }

    private  void parseNews(Document doc,Recruit ni) {
    	
    	Element recruitNum  = doc.select("div[class=company-info] div[class=company-name]").first();
    	System.out.println(recruitNum.toString());
//    	Element contentElement  = doc.select("div[class=work_b]").first();
//		ni.setRecruit_num(recruitNum.text());
//		ni.setContent(contentElement.toString());
    }
    



}
