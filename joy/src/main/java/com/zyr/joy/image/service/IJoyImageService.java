package com.zyr.joy.image.service;


import com.zyr.joy.image.domain.dto.JoyImageFileDto;
import com.zyr.joy.image.domain.vo.JoyImageFileVo;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

/**
 * @author zhengyongrui
 */
public interface IJoyImageService {

    /**
     * 保存文件列表
     * @param joyImageFileDtoList 列表
     */
    void saveJoyImageFileList(List<JoyImageFileDto> joyImageFileDtoList);

    /**
     *
     * 下载文件
     * @param fileId 文件id，也是JoyImageFileId
     * @return .
     * @throws IOException .
     */
    ResponseEntity<InputStreamResource> downloadFile(String fileId) throws IOException;

    /**
     * 查询文件列表
     * @param currentDate 当前浏览到的文件日志
     * @return 列表
     */
    List<JoyImageFileVo> queryJoyImageList(long currentDate);

}
