package com.push.dao.readdao;

import java.util.List;

import com.push.bean.gen.NanoCheckerDeviceToken;

public interface NanoCheckerDeviceTokenReadMapper {

    NanoCheckerDeviceToken selectByPrimaryKey(Integer id);
    
    NanoCheckerDeviceToken selectByDeviceToken(String devicetoken);
    
    List<String> selectAll();
}