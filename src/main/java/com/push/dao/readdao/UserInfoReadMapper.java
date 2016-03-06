package com.push.dao.readdao;

import com.push.bean.gen.UserInfo;

public interface UserInfoReadMapper {

    UserInfo selectByPrimaryKey(Integer id);
}