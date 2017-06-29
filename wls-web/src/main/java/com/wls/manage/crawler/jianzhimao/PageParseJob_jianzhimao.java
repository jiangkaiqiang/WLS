package com.wls.manage.crawler.jianzhimao;

import com.wls.manage.crawler.general.BasicCrawler;
import com.wls.manage.dto.NewInfomationDto;
import com.wls.manage.entity.Recruit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by haolidong on 2016/11/14.
 */
public class PageParseJob_jianzhimao {

    public  void parse(Recruit ni){
        String content = "";
        try {
            content= BasicCrawler.crawlPage(ni.getUrl(),"utf-8");
        }catch (Exception e){
            e.printStackTrace();
        }
//        System.out.println(content);
        Document doc = Jsoup.parse(content);
        parseNews(doc,ni);
    }

    private  void parseNews(Document doc,Recruit ni) {
    	Element contentElement  = doc.select("div [class=box] div[class=detail]").first();
    	ni.setContent(contentElement.toString());
    	
    	Element com  = doc.select("div [class=job_header] p[class=info]").first();
    	ni.setCompany(com.text());
    	
    	Element price  = doc.select("div [class=job_base] span[class=job_price]").first();
    	ni.setSalary(price.text());
    	
    	Element position  = doc.select("div [class=job_base] span[class=job_type]").first();
    	ni.setPosition(position.text());
    	
//    	recruit_num
    	Elements jobList  = doc.select("ul [class=job_list]");
    	for (int i = 0; i < jobList.size(); i++) {
			if(i==0){
				ni.setRecruit_num(jobList.get(i).text());
			}
			if(i==2){
				ni.setAddress(jobList.get(i).text());
			}
		}
    	System.out.println();
    	
    }
    
    
    public static void main(String[] args) {
    	Recruit ni = new Recruit();
    	ni.setUrl("http://shanghai.jianzhimao.com/job/QXlWU1VjcGNQNDQ9.html");
		new PageParseJob_jianzhimao().parse(ni);
	}
    



}
