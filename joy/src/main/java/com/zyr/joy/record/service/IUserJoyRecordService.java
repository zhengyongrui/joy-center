package com.zyr.joy.record.service;

import com.zyr.joy.record.domain.dto.UserJoyRecordDto;

/**
 * @Author: zhengyongrui
 * @Date: 2020-02-20 22:19
 */
public interface IUserJoyRecordService {

    /**
     * 获取当前用户浏览记录
     * @return .
     */
    UserJoyRecordDto findByCurrentUser();

    /**
     * 为当前用户新增浏览记录员
     * @param userJoyRecordDto .
     */
    void newJoyRecordByCurrentUser(UserJoyRecordDto userJoyRecordDto);

    /**
     * 保存用户快乐记录
     * @param userJoyRecordDto .
     */
    void saveUserJoyRecord(UserJoyRecordDto userJoyRecordDto);

}
