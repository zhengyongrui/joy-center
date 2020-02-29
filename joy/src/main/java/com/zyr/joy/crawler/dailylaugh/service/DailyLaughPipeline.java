package com.zyr.joy.crawler.dailylaugh.service;

import com.zyr.joy.crawler.AbstractCommonPipeline;
import com.zyr.joy.crawler.service.ICrawlerRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Collections;
import java.util.List;

/**
 * @Author: zhengyongrui
 * @Date: 2020-02-15 15:53
 */
@Slf4j
@Service
public class DailyLaughPipeline extends AbstractCommonPipeline implements Pipeline {

    public DailyLaughPipeline(ICrawlerRecordService crawlerRecordService) {
        super(crawlerRecordService);
    }

    @Override
    protected List<String> getNeedRecordUrlRegexList() {
        return Collections.singletonList("http://3g.163.com/auto/\\d{2}/\\d{4}/\\d{2}/\\w+.html");
    }
}
