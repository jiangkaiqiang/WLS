package com.wls.manage.crawler.shixi;

import java.text.ParseException;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import com.wls.manage.dao.*;
import com.wls.manage.entity.Recruit;
/**
 * Created by haolidong on 2016/11/12.
 */
public class ListCrawler_shixi {
	@Autowired
	private RecruitMapper informationMapper;
    private static Logger logger = Logger.getLogger(ListCrawler_shixi.class.getName());
    public ListCrawlJob_shixi lj;
    public List<Recruit> parse() throws ParseException {
    	 logger.info("Start Crawl Pages on 实习网");   
         ListCrawlJob_shixi crawler =new ListCrawlJob_shixi();
         crawler.getUrlList("http://www.shixi.com/search/index?page=1");
         for(Recruit ni:crawler.an){
             new PageCrawJob_shixi().pageCraw(ni);
         }
         return crawler.an;
	}
    
    public static void main(String[] args) throws ParseException {
		new ListCrawler_shixi().parse();
	}
    
    
}
