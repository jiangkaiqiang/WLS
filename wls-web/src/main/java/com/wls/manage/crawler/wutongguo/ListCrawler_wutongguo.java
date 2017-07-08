package com.wls.manage.crawler.wutongguo;

import java.text.ParseException;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import com.wls.manage.dao.*;
import com.wls.manage.entity.Recruit;
/**
 * Created by haolidong on 2016/11/12.
 */
public class ListCrawler_wutongguo {
	@Autowired
	private RecruitMapper informationMapper;
    private static Logger logger = Logger.getLogger(ListCrawler_wutongguo.class.getName());
    public ListCrawlJob_wutongguo lj;
    public List<Recruit> parse() throws ParseException {
    	 logger.info("Start Crawl Pages on 梧桐果");   
         ListCrawlJob_wutongguo crawler =new ListCrawlJob_wutongguo();
         crawler.getUrlList("http://www.wutongguo.com/shixi/n1/");
         for(Recruit ni:crawler.an){
             new PageCrawJob_wutongguo().pageCraw(ni);
             System.out.println(ni);
         }
         return crawler.an;
	}
    
    public static void main(String[] args) throws ParseException {
		new ListCrawler_wutongguo().parse();
	}
    
    
}
