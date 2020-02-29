package com.zyr.joy.image.domain.dto;

import com.zyr.joy.image.domain.enumrate.JoyImageFileOriginEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhengyongrui
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JoyImageFileDto {

    @Id
    private String joyImageFileId;

    @ApiModelProperty("文件来源")
    private JoyImageFileOriginEnum origin;

    @ApiModelProperty("文件描述")
    private String title;

    @ApiModelProperty("md5")
    private String md5;

    @ApiModelProperty("创建用户")
    private String createUserId;

    @ApiModelProperty("创建时间")
    private Long createDate;

    @ApiModelProperty("文件")
    private MultipartFile multipartFile;

    @ApiModelProperty("源文件地址")
    private String originUrl;

    @ApiModelProperty("源文件id")
    private String originFileId;

    @ApiModelProperty("宽度")
    private Integer width;

    @ApiModelProperty("高度")
    private Integer height;


}
