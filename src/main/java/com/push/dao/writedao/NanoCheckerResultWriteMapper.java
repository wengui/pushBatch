package com.push.dao.writedao;

import com.push.bean.gen.NanoCheckerResult;

public interface NanoCheckerResultWriteMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NanoCheckerResult record);

    int insertSelective(NanoCheckerResult record);

    int updateByPrimaryKeySelective(NanoCheckerResult record);

    int updateByPrimaryKey(NanoCheckerResult record);
}