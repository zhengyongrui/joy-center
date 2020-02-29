package com.zyr.joy.image.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.zyr.joy.image.domain.vo.JoyImageFileVo;
import com.zyr.joy.image.service.IJoyImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @Author: zhengyongrui
 * @Date: 2019-10-27 19:52
 */
@RestController
@Api(value = "快乐图片")
@RequestMapping("/joy/image")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JoyImageController {

    private final IJoyImageService joyImageService;

    @ApiOperation("文件下载")
    @GetMapping("file/{fileId}")
    public ResponseEntity<InputStreamResource> file(@PathVariable("fileId") String fileId) throws IOException {
        return joyImageService.downloadFile(fileId);
    }

    @ApiOperation("查询文件列表")
    @GetMapping("/queryJoyImageList")
    @JsonView(JoyImageFileVo.JoyImageFileListView.class)
    public List<JoyImageFileVo> queryJoyImageList(@RequestParam("currentDate") long currentDate) {
        return joyImageService.queryJoyImageList(currentDate);
    }

}
