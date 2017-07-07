package com.wls.manage.crawler.dajie;

import java.text.ParseException;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import com.wls.manage.dao.*;
import com.wls.manage.entity.Recruit;
/**
 * Created by haolidong on 2016/11/12.
 */
public class ListCrawler_dajie {
	@Autowired
	private RecruitMapper informationMapper;
    private static Logger logger = Logger.getLogger(ListCrawler_dajie.class.getName());
    public ListCrawlJob_dajie lj;
    public List<Recruit> parse() throws ParseException {
    	 logger.info("Start Crawl Pages on 大街网");   
         ListCrawlJob_dajie crawler =new ListCrawlJob_dajie();
         crawler.getUrlList("https://www.dajie.com/ajax/index/jobs?ajax=1&type=1&page=1&pageSize=10&_CSRFToken=");
         for(Recruit ni:crawler.an){
             new PageCrawJob_dajie().pageCraw(ni);
             System.out.println(ni);
         }
         
         return crawler.an;
	}
    
    public static void main(String[] args) throws ParseException {
		new ListCrawler_dajie().parse();
	}
    
    
}
