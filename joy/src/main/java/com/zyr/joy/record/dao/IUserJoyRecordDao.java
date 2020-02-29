package com.zyr.joy.record.dao;

import com.zyr.common.repository.ProjectRepository;
import com.zyr.joy.record.domain.po.UserJoyRecord;
import org.springframework.stereotype.Repository;

/**
 * @Author: zhengyongrui
 * @Date: 2019-10-27 20:33
 */
@Repository
public interface IUserJoyRecordDao extends IUserJoyRecordRepository, ProjectRepository<UserJoyRecord, String> {

}
