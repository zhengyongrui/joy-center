package com.zyr.joy.image.dao;

import com.zyr.common.repository.ProjectRepository;
import com.zyr.joy.image.domain.po.JoyImageFile;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: zhengyongrui
 * @Date: 2019-10-27 20:33
 */
@Repository
public interface IJoyImageFileDao extends IJoyImageFileRepository, ProjectRepository<JoyImageFile, String> {

    /**
     * 根据md查询文件数量
     * @param md5 .
     * @return long
     */
    long countByMd5(String md5);

    /**
     * 查找大于当前并升序排序的文件列表
     * @param createDate 当前时间
     * @param pageable .
     * @return .
     */
    List<JoyImageFile> findAllByCreateDateGreaterThanOrderByCreateDate(long createDate, PageRequest pageable);

    /**
     * 查找小于当前并降序排序的文件列表
     * @param createDate 当前时间
     * @param pageable .
     * @return .
     */
    List<JoyImageFile> findAllByCreateDateLessThanOrderByCreateDateDesc(long createDate, PageRequest pageable);
}
