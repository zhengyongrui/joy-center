package com.zyr.joy.crawler.dailylaugh.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.zyr.joy.crawler.service.ICrawlerRecordService;
import com.zyr.joy.image.domain.dto.JoyImageFileDto;
import com.zyr.joy.image.domain.enumrate.JoyImageFileOriginEnum;
import com.zyr.joy.image.service.IJoyImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: zhengyongrui
 * @Date: 2020-02-15 16:04
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DailyLaughPageProcessor implements PageProcessor {

    /**
     * 一天抓一次就够了
     */
    private Site site = Site.me().setRetryTimes(1);

    /**
     * 成功返回的关键字
     */
    private final String SUCCESS_RESPONSE_KEYWORD = "artiList({\"DE0E6486wangning\":";

    /**
     * 列表页面正则表达式
     */
    private final String helpUrl = "https://3g.163.com/touch/reconstruct/article/list/DE0E6486wangning/\\d+-10.html";

    private final IJoyImageService joyImageService;

    private final ICrawlerRecordService crawlerRecordService;

    @Override
    public void process(Page page) {
        String pageUrl = page.getUrl().get();
        if (!checkUrlIsHaveCrawler(pageUrl)) {
            if (page.getUrl().regex(helpUrl).match()) {
                String responseStr = page.getRawText();
                if (responseStr.contains(SUCCESS_RESPONSE_KEYWORD)) {
                    responseStr = responseStr.substring(responseStr.indexOf("["), responseStr.lastIndexOf("]") + 1);
                    ArrayList<JSONObject> articleUrlJsonList = JSON.parseObject(responseStr, new TypeReference<ArrayList<JSONObject>>() {});
                    if (CollectionUtils.isNotEmpty(articleUrlJsonList)) {
                        List<String> articleUrlList = articleUrlJsonList.stream().map(articleUrlJson -> articleUrlJson.getString("url")).collect(Collectors.toList());
                        page.addTargetRequests(articleUrlList);
                    }
                }
            } else {
                List<String> imageUrlList = page.getHtml().$("div.js-page.page div.photo a", "href").all();
                if (CollectionUtils.isNotEmpty(imageUrlList)) {
                    // 第一条是标题图片，不要了
                    imageUrlList.remove(0);
                    imageUrlList.forEach(log::debug);
                    saveImageList(imageUrlList);
                }
            }
        } else {
            log.debug("地址({})爬虫过不再爬虫", pageUrl);
        }
    }

    /**
     * 检查url是否已经爬虫过
     * @param url .
     * @return boolean
     */
    private boolean checkUrlIsHaveCrawler(String url) {
        return crawlerRecordService.existsById(url);
    }

    /**
     * 保存文件
     * @param imageUrlList .
     */
    private void saveImageList(List<String> imageUrlList) {
        List<JoyImageFileDto> joyImageFileDtoList = new ArrayList<>();
        for (String imageUrl : imageUrlList) {
            if (crawlerRecordService.existsById(imageUrl)) {
                log.debug("图片地址({})下载过不再下载", imageUrl);
                continue;
            }
            InputStream inputStream;
            String fileName;
            try {
                fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
                String fileType = "image/" + fileName.substring(fileName.lastIndexOf(".") + 1);
                inputStream = new URL(imageUrl).openStream();
                MultipartFile multipartFile = new MockMultipartFile(fileName, fileName, fileType, inputStream);
                JoyImageFileDto joyImageFileDto = JoyImageFileDto.builder().createDate(System.currentTimeMillis()).originUrl(imageUrl).multipartFile(multipartFile).origin(JoyImageFileOriginEnum.DAILY_LAUGH).build();
                joyImageFileDtoList.add(joyImageFileDto);
            } catch (IOException e) {
                log.error("爬虫文件下载错误", e);
                break;
            }
        }
        if (CollectionUtils.isNotEmpty(joyImageFileDtoList)) {
            crawlerRecordService.saveCrawlerRecordList(joyImageFileDtoList.stream().map(JoyImageFileDto::getOriginUrl).collect(Collectors.toList()), JoyImageFileOriginEnum.DAILY_LAUGH);
            joyImageService.saveJoyImageFileList(joyImageFileDtoList);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
