package com.christli.study.crawler;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

public class MyPageProcessor implements PageProcessor {


    private Site site = Site.me();

    @Override
    public void process(Page page) {
        List<String> ipList = page.getHtml().xpath("//ul[@class='comma-separated']/li/text()").all();
        String ip = page.getHtml().xpath("//ul[@class='comma-separated']/li/text()").get();
        System.out.println(ip);
    }

    public Site getSite() {
        return MySite.getSit(site);
    }

    public static void start() {
        // 今日头条/热点数据url
        Spider.create(new MyPageProcessor()).addUrl(
                "https://github.com.ipaddress.com")
                .thread(1).run();
    }
}