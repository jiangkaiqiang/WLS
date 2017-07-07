package com.wls.manage.crawler.kdjz;


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
public class ListCrawlJob_kdjz {

    public List<Recruit> an = new ArrayList<Recruit>();
    private static Logger logger = Logger.getLogger(ListCrawler_kdjz.class.getName());
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
//        System.out.println(content);
        processListType(content);
    }

    private  void processListType(String contentStr){
    	
    	JSONObject jsonObject = JSONObject.fromObject(contentStr);
		JSONObject data = (JSONObject) jsonObject.get("data");
		JSONArray lists = data.getJSONArray("list");
		for (int i = 0; i < lists.size(); i++) {
			JSONObject info = (JSONObject) lists.get(i);
			Recruit recruit = new Recruit();
			recruit.setTitle(info.get("title").toString());
			recruit.setPosition(info.get("title").toString());
			recruit.setCover("https:"+info.get("jobTypeIconUrl").toString());
			recruit.setSalary(info.getString("salary").toString()+"元每"+info.get("salaryUnitName").toString());
			recruit.setUrl("https://www.kdjz.com/job/"+info.get("id").toString());
			recruit.setPublishTime(new Date());
			recruit.setCategory("3");
			recruit.setSource("口袋兼职");
			an.add(recruit);
		}	 
    }
}
