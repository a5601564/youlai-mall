package com.intasect.service.sysvalidate.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONObject;
import com.intasect.service.common.controller.CommonController;
import com.intasect.service.common.entity.PageInfo;
import com.intasect.service.sysvalidate.entity.SysValidate;
import com.intasect.service.sysvalidate.entity.SysValidateVo;
import com.intasect.service.sysvalidate.mapper.SysValidateMapper;
import com.intasect.service.sysvalidate.service.SysValidateService;
import com.intasect.service.util.CopyUtil;
import com.youlai.admin.pojo.entity.SysUser;
import com.youlai.admin.service.ISysUserService;
import com.youlai.common.result.Result;
import com.youlai.common.result.ResultCode;
import com.youlai.common.utils.UserUtil;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huanzi-qch
 * @since 2021-02-22
 */
@RestController
@RequestMapping("/sysValidate/")
public class SysValidateController extends CommonController<SysValidateVo,SysValidate> {

    @Autowired
    private SysValidateService sysValidateService;

    @Autowired
    private SysValidateMapper sysValidateMapper;

    @Autowired
    private ISysUserService sysUserService;

    /**
     * 发送忘记密码邮件请求，每日申请次数不超过5次，每次申请间隔不低于1分钟
     * @param email
     * @param request
     * @return
     */
    @RequestMapping(value = "/sendValidationEmail", method = {RequestMethod.POST})
    public Result<String> sendValidationEmail(String email,HttpServletRequest request) {
        SysUser resultUser = sysUserService.getUserByEmail(email);
        if (resultUser == null) {
            return Result.failed(ResultCode.DATABASE_DATA_NOT_EXIST.getCode());
        } else {
            boolean flag = sysValidateService.sendValidateLimitation(email);
            if (!flag) {
                return Result.failed(ResultCode.DATABASE_DATA_NOT_EXIST.getCode());
            }
            return Result.success(ResultCode.DATABASE_DATA_NOT_EXIST.getCode());
        }
    }
}