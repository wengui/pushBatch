package com.push.dao.writedao;

import com.push.bean.gen.NanoCheckerRef;

public interface NanoCheckerRefWriteMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NanoCheckerRef record);

    int insertSelective(NanoCheckerRef record);

    int updateByPrimaryKeySelective(NanoCheckerRef record);

    int updateByPrimaryKey(NanoCheckerRef record);
}