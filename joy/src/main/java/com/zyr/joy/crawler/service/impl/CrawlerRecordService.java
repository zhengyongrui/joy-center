package com.zyr.joy.crawler.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.zyr.joy.crawler.dao.ICrawlerRecordDao;
import com.zyr.joy.crawler.domain.CrawlerRecord;
import com.zyr.joy.crawler.service.ICrawlerRecordService;
import com.zyr.joy.image.domain.enumrate.JoyImageFileOriginEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: zhengyongrui
 * @Date: 2020-02-19 21:22
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CrawlerRecordService implements ICrawlerRecordService {

    private final ICrawlerRecordDao crawlerRecordDao;

    @Override
    public void saveCrawlerRecord(String url, JoyImageFileOriginEnum origin) {
        CrawlerRecord crawlerRecord = transferCrawlerRecord(url, origin);
        crawlerRecordDao.save(crawlerRecord);
    }

    private CrawlerRecord transferCrawlerRecord(String url, JoyImageFileOriginEnum origin) {
        String crawlerRecordId = getCrawlerRecordIdByUrl(url);
        return CrawlerRecord.builder().crawlerRecordId(crawlerRecordId).url(url).origin(origin).build();
    }

    @Override
    public boolean existsById(String url) {
        String crawlerRecordId = getCrawlerRecordIdByUrl(url);
        return crawlerRecordDao.existsById(crawlerRecordId);
    }

    @Override
    public void saveCrawlerRecordList(List<String> urlList, JoyImageFileOriginEnum origin) {
        List<CrawlerRecord> crawlerRecordList = urlList.stream().map(url -> transferCrawlerRecord(url, origin)).collect(Collectors.toList());
        crawlerRecordDao.saveAll(crawlerRecordList);
    }

    private String getCrawlerRecordIdByUrl(final String url) {
        return SecureUtil.md5(url);
    }
}
