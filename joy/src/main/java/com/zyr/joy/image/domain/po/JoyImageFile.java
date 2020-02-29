package com.zyr.joy.image.domain.po;

import com.zyr.joy.image.domain.enumrate.JoyImageFileOriginEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author zhengyongrui
 */
@Document
@Data
@ApiModel("欢乐图片po")
public class JoyImageFile {

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

    @ApiModelProperty("源文件地址")
    private String originUrl;

    @ApiModelProperty("源文件id")
    private String originFileId;

    @ApiModelProperty("宽度")
    private Integer width;

    @ApiModelProperty("高度")
    private Integer height;

}
