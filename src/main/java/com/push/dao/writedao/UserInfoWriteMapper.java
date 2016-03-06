package com.push.dao.writedao;

import com.push.bean.gen.UserInfo;

public interface UserInfoWriteMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);
}