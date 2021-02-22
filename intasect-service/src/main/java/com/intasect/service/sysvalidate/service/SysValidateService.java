package com.intasect.service.sysvalidate.service;

import com.intasect.service.common.service.CommonService;
import com.intasect.service.sysvalidate.entity.SysValidate;
import com.intasect.service.sysvalidate.entity.SysValidateVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huanzi-qch
 * @since 2021-02-22
 */
public interface SysValidateService  extends CommonService<SysValidateVo,SysValidate> {

    boolean sendValidateLimitation(String email);
}