package com.push.dao.readdao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.push.bean.gen.NanoCheckerRef;
import com.push.bean.gen.NanoCheckerReport;

public interface NanoCheckerRefReadMapper {

    NanoCheckerRef selectByPrimaryKey(Integer id);
    
    List<NanoCheckerReport> selectByPatientId(@Param("pname")String pname ,@Param("ptime")Date ptime);
    
    /**
     * 关联设定页面数据取得
     * @return
     */
    List<NanoCheckerReport> selectByCheckerRef();
}