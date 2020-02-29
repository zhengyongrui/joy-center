package com.zyr.joy.record.domain.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Author: zhengyongrui
 * @Date: 2020-02-16 18:19
 */
@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户快乐记录", description = "通过最近读取的时间，过滤掉已经看过的图片")
public class UserJoyRecord {

    @Id
    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("最远读过的文件日期")
    private Long longestFileDate;

    @ApiModelProperty("最新读过的文件日期")
    private Long newestFileDate;

}
