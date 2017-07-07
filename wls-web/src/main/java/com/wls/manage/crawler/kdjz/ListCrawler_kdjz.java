package com.wls.manage.crawler.kdjz;

import java.text.ParseException;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import com.wls.manage.dao.*;
import com.wls.manage.entity.Recruit;
/**
 * Created by haolidong on 2016/11/12.
 */
public class ListCrawler_kdjz {
	@Autowired
	private RecruitMapper informationMapper;
    private static Logger logger = Logger.getLogger(ListCrawler_kdjz.class.getName());
    public ListCrawlJob_kdjz lj;
    public List<Recruit> parse() throws ParseException {
    	 logger.info("Start Crawl Pages on 口袋兼职");   
         ListCrawlJob_kdjz crawler =new ListCrawlJob_kdjz();
         crawler.getUrlList("https://uwapi.kdjz.com/v1/jobs?page=1&sortTypeId=1&cityId=P6bK7d1gj2jAfu7oeJmaeguLWzuyow75pnsuVv6c6lCWLfZCK_IgPCxnwPU8JusYUeX8H38QOCVaaZ23zfF1GA");
         for(Recruit ni:crawler.an){
             new PageCrawJob_kdjz().pageCraw(ni);
             break;
         }
         return crawler.an;
	}
    
    public static void main(String[] args) throws ParseException {
		new ListCrawler_kdjz().parse();
	}
    
    
}
