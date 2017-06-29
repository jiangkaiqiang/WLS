package com.wls.manage.crawler.wutongguo;

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
public class PageParseJob_wutongguo {

    public  void parse(Recruit ni){
        String content = "";
        try {
            content= BasicCrawler.crawlPage(ni.getUrl(),"gb2312");
        }catch (Exception e){
            e.printStackTrace();
        }
//        System.out.println(content);
        Document doc = Jsoup.parse(content);
        parseNews(doc,ni);
    }

    private  void parseNews(Document doc,Recruit ni) {
    	Element contentElement  = doc.select("div [class=cpBrochureMain] div[class=brochureContent]").first();
    	ni.setContent(contentElement.toString());
    }
    
    
    public static void main(String[] args) {
    	Recruit ni = new Recruit();
    	ni.setUrl("http://www.wutongguo.com/notice2472AEBC2A.html");
		new PageParseJob_wutongguo().parse(ni);
	}
    



}
