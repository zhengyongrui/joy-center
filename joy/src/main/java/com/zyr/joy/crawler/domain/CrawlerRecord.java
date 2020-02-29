package com.zyr.joy.crawler.domain;

import com.zyr.joy.image.domain.enumrate.JoyImageFileOriginEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * @Author: zhengyongrui
 * @Date: 2020-02-19 21:53
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrawlerRecord {

    @Id
    @ApiModelProperty("url的md5值")
    private String crawlerRecordId;

    private String url;

    private JoyImageFileOriginEnum origin;

}
