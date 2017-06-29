package com.wls.manage.crawler.jianzhimao;


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
public class ListCrawlJob_jianzhimao {

    public List<Recruit> an = new ArrayList<Recruit>();
    private static Logger logger = Logger.getLogger(ListCrawler_jianzhimao.class.getName());
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
    	 Elements elements=doc.select("div[class=content_list_box] ul[class=content_list_wrap] li");
    	 for(Element element:elements){
    		 Recruit recruit = new Recruit();
    		 Element url = element.select(" a[href]").first();
    		 recruit.setUrl("http://http://shanghai.jianzhimao.com"+url.attr("href"));
    		 recruit.setTitle(url.attr("title"));
    		 Element position=element.select("div[class=left area] span").first();
    		 recruit.setAddress(position.attr("title"));
    		 an.add(recruit);
    	 }
    	 
 	 
    	
    	 
    }
}
