package com.zyr.joy.crawler.service.impl;

import com.zyr.joy.crawler.dailylaugh.service.DailyLaughCrawlerService;
import com.zyr.joy.crawler.service.ICrawlerService;
import com.zyr.joy.image.domain.enumrate.JoyImageFileOriginEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: zhengyongrui
 * @Date: 2020-02-14 23:33
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CrawlerService implements ICrawlerService {

    private final DailyLaughCrawlerService dailylaughCrawlerService;

    @Override
    public boolean triggerCrawler(JoyImageFileOriginEnum origin) {
        dailylaughCrawlerService.run();
        return false;
    }
}
