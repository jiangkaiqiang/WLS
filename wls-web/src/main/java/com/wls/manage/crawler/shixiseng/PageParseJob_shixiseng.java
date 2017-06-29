package com.wls.manage.crawler.shixiseng;

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
public class PageParseJob_shixiseng {

    public  void parse(Recruit ni){
        String content = "";
        try {
            content= BasicCrawler.crawlPage(ni.getUrl(),"utf-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        Document doc = Jsoup.parse(content);
        parseNews(doc,ni);
    }

    private  void parseNews(Document doc,Recruit ni) {
    	Element contentElement  = doc.select("div[class=dec_content]").first();
		ni.setContent(contentElement.toString());
    }
    



}
