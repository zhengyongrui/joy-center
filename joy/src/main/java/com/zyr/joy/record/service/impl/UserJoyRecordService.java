package com.zyr.joy.record.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.zyr.joy.record.dao.IUserJoyRecordDao;
import com.zyr.joy.record.domain.dto.UserJoyRecordDto;
import com.zyr.joy.record.domain.po.UserJoyRecord;
import com.zyr.joy.record.service.IUserJoyRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author: zhengyongrui
 * @Date: 2020-02-16 18:21
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserJoyRecordService implements IUserJoyRecordService {

    private final IUserJoyRecordDao userJoyRecordDao;

    @Override
    public UserJoyRecordDto findByCurrentUser() {
        // todo 获取当前用户
        final String userId = "1";
        Optional<UserJoyRecord> userJoyRecordOptional = userJoyRecordDao.findById(userId);
        return userJoyRecordOptional.map(userJoyRecord -> BeanUtil.toBean(userJoyRecord, UserJoyRecordDto.class)).orElse(null);
    }

    @Override
    public void newJoyRecordByCurrentUser(UserJoyRecordDto userJoyRecordDto) {
        // todo 获取当前用户
        final String userId = "1";
        userJoyRecordDto.setUserId(userId);
        UserJoyRecord userJoyRecord = BeanUtil.toBean(userJoyRecordDto, UserJoyRecord.class);
        userJoyRecordDao.save(userJoyRecord);
    }

    @Override
    public void saveUserJoyRecord(UserJoyRecordDto userJoyRecordDto) {
        UserJoyRecord userJoyRecord = BeanUtil.toBean(userJoyRecordDto, UserJoyRecord.class);
        userJoyRecordDao.save(userJoyRecord);
    }
}
