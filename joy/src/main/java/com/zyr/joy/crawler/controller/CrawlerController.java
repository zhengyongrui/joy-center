package com.zyr.joy.crawler.controller;

import com.zyr.joy.crawler.service.ICrawlerService;
import com.zyr.joy.image.domain.enumrate.JoyImageFileOriginEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhengyongrui
 * @Date: 2020-02-14 23:04
 */
@RestController
@Api(tags = "爬虫")
@RequestMapping("/joy/crawler")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CrawlerController {

    private final ICrawlerService crawlerService;

    @ApiOperation("触发爬虫")
    @GetMapping("/triggerCrawler")
    public boolean triggerCrawler(@RequestParam("origin") JoyImageFileOriginEnum origin) {
        return crawlerService.triggerCrawler(origin);
    }

}
