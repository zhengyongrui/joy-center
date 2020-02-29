package com.zyr.joy.record.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * @Author: zhengyongrui
 * @Date: 2020-02-16 18:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserJoyRecordDto {

    @Id
    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("最远读过的文件日期")
    private Long longestFileDate;

    @ApiModelProperty("最新读过的文件日期")
    private Long newestFileDate;


}
