package com.intasect.service.sysvalidate.mapper;

import com.intasect.service.common.mapper.CommonMapper;
import com.intasect.service.sysvalidate.entity.SysValidate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huanzi-qch
 * @since 2021-02-22
 */
@Mapper
public interface SysValidateMapper extends CommonMapper<SysValidate> {

    @Select("select * from sys_validate where gmt_create > #{requestPerDay} and email =  #{email} ")
    public List<SysValidate> getValidateLimitation(String email,String requestPerDay);
}

