package com.push.dao.writedao;

import org.apache.ibatis.annotations.Param;

import com.push.bean.gen.NanoCheckerRef;

public interface NanoCheckerRefWriteMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NanoCheckerRef record);

    int insertSelective(NanoCheckerRef record);

    int updateByPrimaryKeySelective(NanoCheckerRef record);

    int updateByPrimaryKey(NanoCheckerRef record);
    
    /**
     * 更新最大值
     * @param record
     * @return
     */
    int updateByMaxValue(@Param("itemname")String itemname ,@Param("max")Float max ,@Param("setflag")String setflag, @Param("age")Integer age);
    
    /**
     * 更新最小值
     * @param record
     * @return
     */
    int updateByMinValue(@Param("itemname")String itemname ,@Param("min")Float min ,@Param("setflag")String setflag, @Param("age")Integer age);
    
    /**
     * 同时更新最大值最小值
     * @param record
     * @return
     */
    int updateByMaxAndMinValue(@Param("itemname")String itemname ,@Param("max")Float max ,@Param("min")Float min ,@Param("setflag")String setflag, @Param("age")Integer age);

}