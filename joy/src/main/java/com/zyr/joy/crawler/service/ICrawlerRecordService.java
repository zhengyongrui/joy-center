package com.zyr.joy.crawler.service;

import com.zyr.joy.image.domain.enumrate.JoyImageFileOriginEnum;

import java.util.List;

/**
 * @Author: zhengyongrui
 * @Date: 2020-02-19 21:22
 */
public interface ICrawlerRecordService {

    /**
     * 保存爬虫记录
     * @param url .
     * @param origin 来源
     */
    void saveCrawlerRecord(String url, JoyImageFileOriginEnum origin);

    /**
     * 检查url是否下载过
     * @param url
     * @return
     */
    boolean existsById(String url);

    /**
     * 保存爬虫记录
     * @param urlList .
     * @param origin 来源
     */
    void saveCrawlerRecordList(List<String> urlList, JoyImageFileOriginEnum origin);
}
