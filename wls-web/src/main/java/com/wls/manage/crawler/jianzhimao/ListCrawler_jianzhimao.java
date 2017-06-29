package com.wls.manage.crawler.jianzhimao;

import java.text.ParseException;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import com.wls.manage.dao.*;
import com.wls.manage.entity.Recruit;
/**
 * Created by haolidong on 2016/11/12.
 */
public class ListCrawler_jianzhimao {
	@Autowired
	private RecruitMapper informationMapper;
    private static Logger logger = Logger.getLogger(ListCrawler_jianzhimao.class.getName());
    public ListCrawlJob_jianzhimao lj;
    public List<Recruit> parse() throws ParseException {
    	 logger.info("Start Crawl Pages on 兼职猫");   
         ListCrawlJob_jianzhimao crawler =new ListCrawlJob_jianzhimao();
         crawler.getUrlList("http://shanghai.jianzhimao.com");
//         for(Recruit ni:crawler.an){
//             new PageCrawJob_jianzhimao().pageCraw(ni);
//         }
         return crawler.an;
	}
    
    public static void main(String[] args) throws ParseException {
		new ListCrawler_jianzhimao().parse();
	}
    
    
}
