package com.wls.manage.crawler.zheyibu.shixi;

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
public class PageParseJob_zheyibu_shixi {

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
    	Element numElement = doc.select("div[class=details-intro] p[class=intro-divide] span").last();
    	ni.setRecruit_num(numElement.text().replaceAll(" ", ""));
    	Element introDemond=doc.select("div[class=intro-demond]").first();
    	Element introPosition=doc.select("div[class=intro-position]").first();
    	ni.setContent(introDemond.toString()+introPosition.toString());
    	
    }
    
    
    public static void main(String[] args) {
    	Recruit ni = new Recruit();
    	ni.setUrl("http://www.zheyibu.com/job/7514565.html?f=sr");
		new PageParseJob_zheyibu_shixi().parse(ni);
	}
    



}
