package com.zyr.joy.crawler;

import com.zyr.joy.crawler.service.ICrawlerRecordService;
import com.zyr.joy.image.domain.enumrate.JoyImageFileOriginEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

/**
 * @Author: zhengyongrui
 * @Date: 2020-02-19 20:27
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public abstract class AbstractCommonPipeline implements Pipeline {

    private final ICrawlerRecordService crawlerRecordService;

    /**
     * 需要记录的url列表正则表达式
     */
    private List<String> needRecordUrlRegexList;

    @Override
    public void process(ResultItems resultItems, Task task) {
        needRecordUrlRegexList = getNeedRecordUrlRegexList();
        for (String needRecordUrlRegex : needRecordUrlRegexList) {
            if (resultItems.getRequest().getUrl().matches(needRecordUrlRegex)) {
                String url = resultItems.getRequest().getUrl();
                log.info("{}爬虫完成,加入列表下次不爬了", resultItems.getRequest().getUrl());
                crawlerRecordService.saveCrawlerRecord(url, JoyImageFileOriginEnum.DAILY_LAUGH);
                break;
            }
        }
    }

    /**
     * 需要记录的url列表正则表达式列表
     */
    protected abstract List<String> getNeedRecordUrlRegexList();
}
