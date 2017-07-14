package com.wls.manage.crawler.zheyibu.quanzhi;

import java.text.ParseException;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import com.wls.manage.dao.*;
import com.wls.manage.entity.Recruit;
/**
 * Created by haolidong on 2016/11/12.
 */
public class ListCrawler_zheyibu_quanzhi {
	@Autowired
	private RecruitMapper informationMapper;
    private static Logger logger = Logger.getLogger(ListCrawler_zheyibu_quanzhi.class.getName());
    public ListCrawlJob_zheyibu_quanzhi lj;
    public List<Recruit> parse() throws ParseException {
    	 logger.info("Start Crawl Pages on 这一刻");   
         ListCrawlJob_zheyibu_quanzhi crawler =new ListCrawlJob_zheyibu_quanzhi();
         crawler.getUrlList("http://www.zheyibu.com/sou/0-0-0-0-0-0-0-1-1-1-0");
         for(Recruit ni:crawler.an){
             new PageCrawJob_zheyibu_quanzhi().pageCraw(ni);
//             System.out.println(ni);
         }
         return crawler.an;
	}
    
    public static void main(String[] args) throws ParseException {
		new ListCrawler_zheyibu_quanzhi().parse();
	}
    
    
}
