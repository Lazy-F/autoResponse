package com.lazylive.autoResponse.Crawler;

import com.lazylive.autoResponse.Entity.CommandContent;
import com.lazylive.autoResponse.Enum.CommandEnum;
import com.lazylive.autoResponse.Utils.TimeUtil;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;


public class ConnotationCrawler implements PageProcessor {
    private static ConnotationCrawler connotationCrawler;
    // 设置重试时间与爬虫睡眠时间
    private Site site = Site.me().setSleepTime(0);

    @Override
    public void process(Page page) {
        String text = page.getHtml().xpath("/html/body/div[3]/div[1]/div/ul/li[1]/div/div[2]/a/div/h1/p/text()").toString()
                .replaceAll(",\\s|\\[|]|\\[]", "")
                .replaceAll("”","”\n   ");
        if (text == null || text.trim().isEmpty() ) {
            page.setSkip(true);
        } else {
            CommandContent content = new CommandContent();
            content.setContent(" -> "+text+"\n");
            content.setUrl(page.getUrl().toString());
            content.setCommandId(CommandEnum.CONNOTATION.value());
            content.setAdddate(TimeUtil.ISO_LOCAL_DATE());
            page.putField("content", content);
        }

        page.addTargetRequests(page.getHtml().links().regex("(http://neihanshequ.com/p\\d{11}/)").all());
    }

    @Override
    public Site getSite() { return site; }
    private ConnotationCrawler(){}
    public static ConnotationCrawler getInstance(){
        return connotationCrawler;
    }

}
