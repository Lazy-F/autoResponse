package com.lazylive.autoResponse.Crawler;

import com.lazylive.autoResponse.Entity.CommandContent;
import com.lazylive.autoResponse.Enum.CommandEnum;
import com.lazylive.autoResponse.Utils.TimeUtil;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;


public class NewsCrawler implements PageProcessor {
    private static NewsCrawler newsCrawler;
    private Site site = Site.me().setSleepTime(0).setCycleRetryTimes(3);

    @Override
    public void process(Page page) {
        String title = page.getHtml().xpath("//div[@class='content']/h1/text()").toString(); // 新闻标题
        String listContent = page.getHtml().xpath("//*[@id='cont_1_1_2']/div[6]/p[1]/text()").toString(); // 新闻正文

        if (title == null || title.trim().isEmpty()) {
            // 跳过
            page.setSkip(true);
        } else { // 新闻标题
            CommandContent content = new CommandContent();
            listContent = listContent.trim().isEmpty() || listContent == null ? "" : "\n" + listContent;
            content.setContent("中新网:" + title + listContent);
            content.setUrl(page.getUrl().toString());
            content.setCommandId(CommandEnum.NEWS.value());
            content.setAdddate(TimeUtil.ISO_LOCAL_DATE());
            page.putField("content", content);
        }
        // 添加链接 (中新网 国内|国际版块)
        page.addTargetRequests(page.getHtml().links().regex("(http://www.chinanews.com/(gn|gj)/20\\d{2}/"
                + TimeUtil.ISO_LOCAL_DATE().substring(5, 10) + "/\\d{7}\\.shtml)").all());
        // 添加链接
        page.addTargetRequests(
                page.getHtml().links().regex("(http://www\\.chinanews\\.com/(world|china)\\.shtml)").all());
    }

    @Override
    public Site getSite() {
        return site;
    }

    private NewsCrawler (){}
    public static NewsCrawler getInstance(){
        return newsCrawler;
    }
}
