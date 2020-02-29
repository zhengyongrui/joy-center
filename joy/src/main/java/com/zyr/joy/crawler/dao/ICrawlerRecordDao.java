package com.zyr.joy.crawler.dao;

import com.zyr.common.repository.ProjectRepository;
import com.zyr.joy.crawler.domain.CrawlerRecord;
import org.springframework.stereotype.Repository;

/**
 * @Author: zhengyongrui
 * @Date: 2020-02-19 21:56
 */
@Repository
public interface ICrawlerRecordDao extends ProjectRepository<CrawlerRecord, String> {
}
