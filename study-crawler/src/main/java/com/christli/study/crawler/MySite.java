package com.christli.study.crawler;

import us.codecraft.webmagic.Site;

/**
 * 模拟请求头设置
 */
public class MySite {

    // 设置请求头
    public static Site getSit(Site site) {
//        site.addHeader("Accept", "*/*");
//        site.addHeader("Accept-Encoding", "gzip, deflate, br");
//        site.addHeader("Accept-Language", "en-US,en;q=0.9,zh-CN;q=0.8,zh;q=0.7,zh-TW;q=0.6,cy;q=0.5");
//        site.addHeader("Connection", "keep-alive");
//        site.addHeader("Sec-Fetch-Dest", "empty");
//        site.addHeader("Sec-Fetch-Mode", "cors");
//        site.addHeader("Sec-Fetch-Site", "same-origin");
        site.addHeader("Upgrade-Insecure-Requests", "1");
        site.addHeader("User-Agent",
                "Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1");

        site.setTimeOut(60000);
        site.setCharset("UTF-8");
        site.setRetryTimes(2);
        return site;
    }
}