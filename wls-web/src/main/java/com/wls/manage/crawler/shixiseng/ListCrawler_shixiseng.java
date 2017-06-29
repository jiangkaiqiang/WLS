package com.wls.manage.crawler.shixiseng;

import java.text.ParseException;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import com.wls.manage.dao.*;
import com.wls.manage.entity.Recruit;
/**
 * Created by haolidong on 2016/11/12.
 */
public class ListCrawler_shixiseng {
	@Autowired
	private RecruitMapper informationMapper;
    private static Logger logger = Logger.getLogger(ListCrawler_shixiseng.class.getName());
    public ListCrawlJob_shixiseng lj;
    public List<Recruit> parse() throws ParseException {
    	 logger.info("Start Crawl Pages on 实习僧");   
         ListCrawlJob_shixiseng crawler =new ListCrawlJob_shixiseng();
         crawler.getUrlList("http://www.shixiseng.com/interns?p=1&t=zj");
         for(Recruit ni:crawler.an){
             new PageCrawJob_shixiseng().pageCraw(ni);
         }
         return crawler.an;
	}
    
    
}
