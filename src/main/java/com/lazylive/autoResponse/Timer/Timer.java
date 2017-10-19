package com.lazylive.autoResponse.Timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lazylive.autoResponse.Crawler.JokeCrawler;
import com.lazylive.autoResponse.Crawler.NewsCrawler;
import com.lazylive.autoResponse.Crawler.ConnotationCrawler;
import com.lazylive.autoResponse.Crawler.Pipeline.SqlPipeline;
import com.lazylive.autoResponse.Service.CommandService;
import com.lazylive.autoResponse.Utils.TimeUtil;
import us.codecraft.webmagic.Spider;


@Component
public class Timer {
    private final SqlPipeline sqlPipeline;
    private final CommandService commandService;

    @Autowired
    public Timer(SqlPipeline sqlPipeline, CommandService commandService) {
        this.sqlPipeline = sqlPipeline;
        this.commandService = commandService;
    }

    /**
     * 每12个小时更新一次笑话
     */
    @Scheduled(cron = "0 0 0 1 * ? ")
    public void jokeTimer() {
        Spider.create(JokeCrawler.getInstance()).addUrl("http://www.jokeji.cn/list.htm").addPipeline(sqlPipeline).thread(10).run();
    }

    /**
     * 每1小时更新一次新闻
     */
    @Scheduled(cron = "0 0 0/1 * * ? ")
    public void newsTimer() {
        Spider.create(NewsCrawler.getInstance()).addUrl("http://www.chinanews.com/").addPipeline(sqlPipeline).thread(10).run();
    }

    /**
     * 每30分钟更新一次段子
     */
    @Scheduled(cron = "0 0/30 * * * ? ")
    public void connotationTimer() {
        Spider.create(ConnotationCrawler.getInstance()).addUrl("http://neihanshequ.com/").addPipeline(sqlPipeline).thread(10).run();
    }

    /**
     * 每天 0:00:00 清除不等于当天时间的爬虫数据
     */
    @Scheduled(cron = "0 0 0 * * ? ")
    public void deleteRepeat() {
        commandService.deleteCommandContentByAddDate(TimeUtil.ISO_LOCAL_DATE());
    }

}
