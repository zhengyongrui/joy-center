package com.zyr.joy.crawler.dailylaugh.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: zhengyongrui
 * @Date: 2020-02-15 15:58
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DailyLaughCrawlerService {

    /**
     * 是否第一次爬虫
     */
    private final boolean isFirstCrawler = true;

    /**
     * 爬虫最大页码数
     */
    private final int maxPage = 1;

    private final String url = "https://3g.163.com/touch/reconstruct/article/list/DE0E6486wangning/%s-10.html";

    private final DailyLaughPageProcessor dailylaughPageProcessor;

    private final DailyLaughPipeline dailyLaughPipeline;

    /**
     * 执行爬虫
     */
    public void run() {
        List<String> urlList = Stream.iterate(1, item -> item + 1).limit(maxPage).map(pageNum -> String.format(url, pageNum)).collect(Collectors.toList());
        Spider spider = Spider.create(dailylaughPageProcessor).addPipeline(dailyLaughPipeline).setDownloader(new HttpClientDownloader()).addUrl(urlList.toArray(new String[]{})).thread(5);
        spider.run();
    }

}
