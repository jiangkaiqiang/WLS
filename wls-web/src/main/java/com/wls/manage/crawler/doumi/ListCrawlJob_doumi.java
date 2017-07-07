package com.wls.manage.crawler.doumi;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.wls.manage.crawler.general.BasicCrawler;
import com.wls.manage.dto.NewInfomationDto;
import com.wls.manage.entity.Recruit;

/**
 * Created by haolidong on 2016/11/12.
 */
public class ListCrawlJob_doumi {

    public List<Recruit> an = new ArrayList<Recruit>();
    private static Logger logger = Logger.getLogger(ListCrawler_doumi.class.getName());
    public  void getUrlList(String linkUrl) {
        //爬取列表页面
        logger.info("Start crawl list page:" + linkUrl);
        String content = "";
        try {
            content= BasicCrawler.crawlPage(linkUrl,"gb2312");
        }catch (Exception e){
            e.printStackTrace();
        }
        //TODO  解析文章列表页面，获取需要爬取页面URL
        Document doc = Jsoup.parse(content);
        processListType(doc,linkUrl);
    }

    private  void processListType(Document doc, String linkUrl){
    	 Elements elements=doc.select("div[class=jzList-con w]");
    	 for(Element element:elements){
    		 System.out.println(element);
//    		 Element urlString=element.select("div[class=job-pannel-one] dl dt a[href]").first();
//    		 Element companyElement = element.select("div[class=job-pannel-one] div[class=company-info-title] a[href]").first();
//    		 Element jobDes = element.select("div[class=job-pannel-one] dl dd[class=job-des] span[class=job-educational]").first();
//    		 Element address = element.select("div[class=job-pannel-two] div[class=company-info] span a[class=job-address]").first();
//    		 Element  salary= element.select("div[class=job-pannel-two] div[class=company-info] div[class=company-info-des]").first();
//    		 Element  jobTime= element.select("div[class=job-pannel-two] div[class=company-info] span[class=job-time]").first();
//    		 Element  companyImg= element.select("div[class=job-pannel-two] div[class=company-logo] a img[src]").first();
//    		 
//    		 Recruit recruit = new Recruit();
//    		 recruit.setUrl("http://www.shixi.com"+urlString.attr("href"));
//    		 recruit.setTitle(urlString.text());
//    		 recruit.setPosition(urlString.text());
//    		 recruit.setAddress(address.text());
//    		 recruit.setEducation(jobDes.text());
//    		 recruit.setCompany(companyElement.text());
//    		 recruit.setSalary(salary.text());
//    		 recruit.setCover("http://www.shixi.com"+companyImg.attr("src"));
//    		 recruit.setCategory("1");
//    		 recruit.setSource("实习网");
//    		 
//    		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//    		 Date date=null;
//			 try {
//				 date = sdf.parse(jobTime.text());
//			 } catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			 }
//    		 recruit.setPublishTime(date);
    			
//    		 an.add(recruit);
    	 }
    	 
 	 
    	
    	 
    }
}
