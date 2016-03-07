package com.push.dao.writedao;

import com.push.bean.gen.NanoCheckerPushHistory;

public interface NanoCheckerPushHistoryWriteMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NanoCheckerPushHistory record);

    int insertSelective(NanoCheckerPushHistory record);

    int updateByPrimaryKeySelective(NanoCheckerPushHistory record);

    int updateByPrimaryKey(NanoCheckerPushHistory record);
}