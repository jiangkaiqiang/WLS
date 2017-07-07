package com.wls.manage.crawler.doumi;

import java.text.ParseException;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import com.wls.manage.dao.*;
import com.wls.manage.entity.Recruit;
/**
 * Created by haolidong on 2016/11/12.
 */
public class ListCrawler_doumi {
	@Autowired
	private RecruitMapper informationMapper;
    private static Logger logger = Logger.getLogger(ListCrawler_doumi.class.getName());
    public ListCrawlJob_doumi lj;
    public List<Recruit> parse() throws ParseException {
    	 logger.info("Start Crawl Pages on 斗米网");   
         ListCrawlJob_doumi crawler =new ListCrawlJob_doumi();
         crawler.getUrlList("http://www.doumi.com/sh/o1s2/");
//         for(Recruit ni:crawler.an){
//             new PageCrawJob_doumi().pageCraw(ni);
//         }
         return crawler.an;
	}
    
    public static void main(String[] args) throws ParseException {
		new ListCrawler_doumi().parse();
	}
    
    
}
