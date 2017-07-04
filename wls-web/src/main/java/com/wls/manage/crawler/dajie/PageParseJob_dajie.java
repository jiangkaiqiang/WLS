package com.wls.manage.crawler.dajie;

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
public class PageParseJob_dajie {

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
    	Element tags  = doc.select("div[class=p-corp-detail-tag] dl dd").first();
    	Elements advantages=tags.select("span");
    	String advantsString="";
    	for(Element advantage:advantages){
    		advantsString+=advantage.text()+"\t";
    	}
    	ni.setAdvantage(advantsString);
    	
    	Elements details  = doc.select("div[class=p-corp-detail-info] dl");
    	for(Element detail:details){
//    		System.out.println(detail);
    		Element dt  = detail.select("dt").first();
    		if(dt.text().equals("工作地点")){
    			ni.setAddress(detail.select("dd p").text());
    		}else if(dt.text().equals("招聘人数")){
    			ni.setRecruit_num(detail.select("dd p").text());
    		}else if(dt.text().equals("岗位职责")){
    			ni.setContent(detail.select("dd p").toString());
			}
    	}
    }
    



}
