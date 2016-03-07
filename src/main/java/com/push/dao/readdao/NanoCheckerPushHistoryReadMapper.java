package com.push.dao.readdao;

import com.push.bean.gen.NanoCheckerPushHistory;

public interface NanoCheckerPushHistoryReadMapper {
	
    NanoCheckerPushHistory selectByPrimaryKey(Integer id);
}