package com.zyr.joy.image.domain.vo;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * @author zhengyongrui
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("欢乐图片vo")
public class JoyImageFileVo {

    /**
     * 快乐图片列表View
     */
    public interface JoyImageFileListView {}

    @Id
    @JsonView(JoyImageFileListView.class)
    private String joyImageFileId;

    @ApiModelProperty("文件地址")
    private String url;

    @ApiModelProperty("md5")
    private String md5;

    @JsonView(JoyImageFileListView.class)
    private Long createDate;

}
