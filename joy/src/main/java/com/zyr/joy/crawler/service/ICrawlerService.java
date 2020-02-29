package com.zyr.joy.crawler.service;

import com.zyr.joy.image.domain.enumrate.JoyImageFileOriginEnum;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 爬虫服务
 * @Author: zhengyongrui
 * @Date: 2020-02-14 23:32
 */
public interface ICrawlerService {

    boolean triggerCrawler(@RequestParam JoyImageFileOriginEnum origin);

}
