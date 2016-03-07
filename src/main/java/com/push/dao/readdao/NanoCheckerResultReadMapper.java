package com.push.dao.readdao;

import com.push.bean.gen.NanoCheckerResult;

public interface NanoCheckerResultReadMapper {

    NanoCheckerResult selectByPrimaryKey(Integer id);
}