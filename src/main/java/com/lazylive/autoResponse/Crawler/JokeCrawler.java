package com.lazylive.autoResponse.Crawler;

import com.lazylive.autoResponse.Entity.CommandContent;
import com.lazylive.autoResponse.Enum.CommandEnum;
import com.lazylive.autoResponse.Utils.TimeUtil;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;


public class JokeCrawler implements PageProcessor {
    private static JokeCrawler jokeCrawler = new JokeCrawler() ;
    // 设置重试时间与爬虫睡眠时间
    private Site site = Site.me().setCycleRetryTimes(3).setSleepTime(10*1000);

    @Override
    public void process(Page page) {
        String text = page.getHtml().xpath("//span[@id='text110']/p/text()").all().toString()
                .replaceAll(",\\s|\\[|]|\\[]", "")
                .replaceAll("\\d、", "\n -> ")
                .replaceAll("”","”\n\t");

        if (text == null || text.trim().isEmpty()) {
            page.setSkip(true);
        } else {
            CommandContent content = new CommandContent();
            text = text.substring(0,1).matches("\n") ? text.substring(1): text ;
            content.setContent(text+"\n");
            content.setUrl(page.getUrl().toString());
            content.setCommandId(CommandEnum.JOKE.value());
            content.setAdddate(TimeUtil .ISO_LOCAL_DATE());
            page.putField("content", content);
        }
        page.addTargetRequests(page.getHtml().links().regex("(http://www.jokeji.cn/jokehtml/\\w+/\\w+.htm)").all());
    }

    @Override
    public Site getSite() {
        return site;
    }
    private JokeCrawler(){}
    public static JokeCrawler getInstance(){
        return jokeCrawler;
    }

}
