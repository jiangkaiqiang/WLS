package com.wls.manage.crawler.zheyibu.quanzhi;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.xpath.operations.Div;
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
public class ListCrawlJob_zheyibu_quanzhi {

    public List<Recruit> an = new ArrayList<Recruit>();
    private static Logger logger = Logger.getLogger(ListCrawler_zheyibu_quanzhi.class.getName());
    public  void getUrlList(String linkUrl) {
        //爬取列表页面
        logger.info("Start crawl list page:" + linkUrl);
        String content = "";
        try {
            content= BasicCrawler.crawlPage(linkUrl,"utf-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        //TODO  解析文章列表页面，获取需要爬取页面URL
        Document doc = Jsoup.parse(content);
        processListType(doc,linkUrl);
    }

    private  void processListType(Document doc, String linkUrl){
    	 Elements elements=doc.select("ul[class=left-result] li");
    	 for(Element element:elements){
    		 Recruit recruit = new Recruit();
    		 Element titleElement = element.select("div[class=xq-divide] a[href]").first();
    		 Element img=element.select("div[class=result-content] img[src]").first();
    		 Element companyElement=element.select("div[class=job-detail] a[class=com-name]").first();
    		 Element addressElement=element.select("span[class=white-space]").first();
    		 Element salaryElement=element.select("div[class=result-salary]").first();
    		 recruit.setUrl(titleElement.attr("href"));
    		 recruit.setCover(img.attr("src"));
    		 recruit.setPublishTime(new Date());
    		 recruit.setTitle(titleElement.text());
    		 recruit.setSource("这1步");
    		 recruit.setCategory("2");
    		 recruit.setPosition(titleElement.text());
    		 recruit.setCompany(companyElement.text());
    		 recruit.setAddress(addressElement.text());
    		 recruit.setSalary(salaryElement.text());
    		 an.add(recruit);
    	 }
    	 
 	 
    	
    	 
    }
}
