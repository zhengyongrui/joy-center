package com.zyr.joy.image.service.impl;

import cn.hutool.core.img.Img;
import cn.hutool.crypto.SecureUtil;
import com.zyr.common.repository.ProjectFileRepositoryOperations;
import com.zyr.common.util.beans.CommonBeanUtil;
import com.zyr.common.util.img.CommonImgUtil;
import com.zyr.joy.image.dao.IJoyImageFileDao;
import com.zyr.joy.image.domain.dto.JoyImageFileDto;
import com.zyr.joy.image.domain.po.JoyImageFile;
import com.zyr.joy.image.domain.vo.JoyImageFileVo;
import com.zyr.joy.image.service.IJoyImageService;
import com.zyr.joy.record.domain.dto.UserJoyRecordDto;
import com.zyr.joy.record.service.IUserJoyRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhengyongrui
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class JoyImageService implements IJoyImageService {

    private final IJoyImageFileDao joyJoyImageFileDao;

    private final ProjectFileRepositoryOperations fileRepositoryOperations;

    private final IUserJoyRecordService userJoyRecordService;

    @Override
    public void saveJoyImageFileList(List<JoyImageFileDto> joyImageFileDtoList) {
        joyImageFileDtoList.forEach(imageFileDto -> {
            try {
                MultipartFile multipartFile = imageFileDto.getMultipartFile();
                String md5 = SecureUtil.md5(multipartFile.getInputStream());
                long fileCount = joyJoyImageFileDao.countByMd5(md5);
                // 文件库有的不再更新
                if (fileCount <= 0) {
                    InputStream inputStream = multipartFile.getInputStream();
                    // 读取图片获取宽高
                    BufferedImage srcImage = CommonImgUtil.read(multipartFile);
                    imageFileDto.setWidth(srcImage.getWidth());
                    imageFileDto.setHeight(srcImage.getHeight());
                    String contentType = multipartFile.getContentType();
                    if (!MediaType.IMAGE_GIF_VALUE.equals(contentType)) {
                        // 保存源文件
                        String originFileId = fileRepositoryOperations.saveFile(multipartFile.getInputStream(), multipartFile.getName(), contentType);
                        imageFileDto.setOriginFileId(originFileId);
                        // 压缩文件
                        Image image = CommonImgUtil.compress(multipartFile.getInputStream(), 720, null);
                        inputStream = getInputStream(image);
                        // 压缩后的文件类型改为jpg
                        contentType = MediaType.IMAGE_JPEG_VALUE;
                        // 修改文件的宽高为压缩后的宽高
                        imageFileDto.setWidth(image.getWidth(null));
                        imageFileDto.setHeight(image.getHeight(null));
                    }
                    String fileId = fileRepositoryOperations.saveFile(inputStream, multipartFile.getName(), contentType);
                    imageFileDto.setJoyImageFileId(fileId);
                    // 如果没有压缩文件，那么源文件id与现在的文件id一致
                    if (StringUtils.isBlank(imageFileDto.getOriginFileId())) {
                        imageFileDto.setOriginFileId(fileId);
                    }
                    imageFileDto.setMd5(md5);
                    imageFileDto.setCreateDate(System.currentTimeMillis());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        List<JoyImageFileDto> joyImageFileDtos = joyImageFileDtoList.stream().filter(joyImageFileDto -> StringUtils.isNotBlank(joyImageFileDto.getJoyImageFileId())).collect(Collectors.toList());
        List<JoyImageFile> joyImageFileList = CommonBeanUtil.copyBeanList(joyImageFileDtos, JoyImageFile.class);
        joyJoyImageFileDao.saveAll(joyImageFileList);
    }

    private InputStream getInputStream(Image image) throws IOException {
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(bs);
        Img.from(image).write(imageOutputStream);
        return new ByteArrayInputStream(bs.toByteArray());
    }

    @Override
    public ResponseEntity<InputStreamResource> downloadFile(String fileId) throws IOException {
        return fileRepositoryOperations.findResponseEntity(fileId);
    }

    @Override
    public List<JoyImageFileVo> queryJoyImageList(long currentDate) {
        List<JoyImageFileVo> resultList;
        UserJoyRecordDto userJoyRecordDto = getUserJoyRecordDto(currentDate);
        if (currentDate >= userJoyRecordDto.getNewestFileDate()) {
            updateNewestDate(currentDate, userJoyRecordDto);
            List<JoyImageFile> joyImageFileList = joyJoyImageFileDao.findAllByCreateDateGreaterThanOrderByCreateDate(currentDate, PageRequest.of(0, 10));
            if (CollectionUtils.isEmpty(joyImageFileList)) {
                // 已经看到最新图片了，没有新图看了，倒序看以前的图片
                joyImageFileList = joyJoyImageFileDao.findAllByCreateDateLessThanOrderByCreateDateDesc(currentDate, PageRequest.of(0, 10));
            }
            resultList = CommonBeanUtil.copyBeanList(joyImageFileList, JoyImageFileVo.class);
        } else {
            // 如果当前看的时间比最新时间旧，那可能是已经倒序看以前的图片了，所以直接倒序查看
            List<JoyImageFile> joyImageFileList = joyJoyImageFileDao.findAllByCreateDateLessThanOrderByCreateDateDesc(currentDate, PageRequest.of(0, 10));
            resultList = CommonBeanUtil.copyBeanList(joyImageFileList, JoyImageFileVo.class);
        }
        return resultList;
    }

    /**
     * 更新最新查看的时间
     *
     * @param currentDate      .
     * @param userJoyRecordDto .
     */
    private void updateNewestDate(long currentDate, UserJoyRecordDto userJoyRecordDto) {
        userJoyRecordDto.setNewestFileDate(currentDate);
        userJoyRecordService.saveUserJoyRecord(userJoyRecordDto);
    }

    /**
     * 获取当前用户的快乐记录
     *
     * @param currentDate 当前浏览到的文件时间
     * @return .
     */
    private UserJoyRecordDto getUserJoyRecordDto(long currentDate) {
        UserJoyRecordDto userJoyRecordDto = userJoyRecordService.findByCurrentUser();
        if (userJoyRecordDto == null) {
            userJoyRecordDto = UserJoyRecordDto.builder().newestFileDate(currentDate).longestFileDate(currentDate).build();
            userJoyRecordService.newJoyRecordByCurrentUser(userJoyRecordDto);
        }
        return userJoyRecordDto;
    }

}
