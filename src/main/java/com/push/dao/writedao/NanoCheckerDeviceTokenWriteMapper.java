package com.push.dao.writedao;

import com.push.bean.gen.NanoCheckerDeviceToken;

public interface NanoCheckerDeviceTokenWriteMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NanoCheckerDeviceToken record);

    int insertSelective(NanoCheckerDeviceToken record);

    int updateByPrimaryKeySelective(NanoCheckerDeviceToken record);

    int updateByPrimaryKey(NanoCheckerDeviceToken record);
}