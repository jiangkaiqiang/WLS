package com.wls.manage.crawler.zheyibu.shixi;

import java.text.ParseException;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import com.wls.manage.dao.*;
import com.wls.manage.entity.Recruit;
/**
 * Created by haolidong on 2016/11/12.
 */
public class ListCrawler_zheyibu_shixi {
	@Autowired
	private RecruitMapper informationMapper;
    private static Logger logger = Logger.getLogger(ListCrawler_zheyibu_shixi.class.getName());
    public ListCrawlJob_zheyibu_shixi lj;
    public List<Recruit> parse() throws ParseException {
    	 logger.info("Start Crawl Pages on 这1步");   
         ListCrawlJob_zheyibu_shixi crawler =new ListCrawlJob_zheyibu_shixi();
         crawler.getUrlList("http://www.zheyibu.com/sou/0-0-0-0-0-0-0-2-1-1-0");
         for(Recruit ni:crawler.an){
             new PageCrawJob_zheyibu_shixi().pageCraw(ni);
//             System.out.println(ni);
         }
         return crawler.an;
	}
    
    public static void main(String[] args) throws ParseException {
		new ListCrawler_zheyibu_shixi().parse();
	}
    
    
}
