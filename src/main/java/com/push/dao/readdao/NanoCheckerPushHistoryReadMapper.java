package com.push.dao.readdao;

import java.util.List;

import com.push.bean.gen.NanoCheckerPushHistory;

public interface NanoCheckerPushHistoryReadMapper {
	
    NanoCheckerPushHistory selectByPrimaryKey(Integer id);
    List<NanoCheckerPushHistory> selectAll();
}