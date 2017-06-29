package com.wls.manage.crawler.shixiseng;


import java.util.ArrayList;
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
public class ListCrawlJob_shixiseng {

    public List<Recruit> an = new ArrayList<Recruit>();
    private static Logger logger = Logger.getLogger(ListCrawler_shixiseng.class.getName());
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
    	 Elements elements=doc.select("div[class=posi-list] div[class=list]");
    	 for(Element element:elements){
    		 Element urlString=element.select("div[class=po-name] div[class=names cutom_font] a[href]").first();
    		 Recruit recruit = new Recruit();
    		 recruit.setUrl("http://www.shixiseng.com"+urlString.attr("href"));
    		 recruit.setTitle(urlString.text().replaceAll("[ \t\n]+", ""));
    		 
    		 Element addr=element.select("div[class=addr]").first();
    		 recruit.setAddress(addr.text());
    		 
    		 Element comLogo=element.select("div[class=com-logo] a img").first();
    		 recruit.setCover(comLogo.attr("src"));
    		 
    		 
    		 Element cutomFont=element.select("div[class=part] a").first();
    		 String cusString=cutomFont.text();
    		 
    		 Element cutomFontZ=element.select("div[class=part]").first();
    		 recruit.setPosition(cutomFontZ.text().substring(cusString.length()).replaceAll("[ -]", ""));
    		 recruit.setSource("实习僧");
    		 recruit.setCategory("实习");
    		 an.add(recruit);
    	 }
    	 
 	 
    	
    	 
    }
}
