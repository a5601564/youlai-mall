package com.intasect.service.sysvalidate.service;

import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONObject;
import com.intasect.service.common.service.CommonServiceImpl;
import com.intasect.service.sysvalidate.entity.SysValidate;
import com.intasect.service.sysvalidate.entity.SysValidateVo;
import com.intasect.service.sysvalidate.mapper.SysValidateMapper;
import com.youlai.common.utils.UserUtil;
import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huanzi-qch
 * @since 2021-02-22
 */
@Service
public class SysValidateServiceImpl  extends CommonServiceImpl<SysValidateVo,SysValidate>  implements SysValidateService {

    @Autowired
    private SysValidateMapper sysvalidateMapper;

    /**
     * 验证是否发送重置邮件：每个email的重置密码每日请求上限为requestPerDay次，与上一次的请求时间间隔为interval分钟。
     * @param email
     * @return
     */
    @Override
    public boolean sendValidateLimitation(String email){
        String requestPerDay = "";

        List<SysValidate> validates = sysvalidateMapper.getValidateLimitation(email, requestPerDay);

        if(validates.size() > 4)
            return false;

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        JSONObject user = UserUtil.getUser(requestAttributes);
        Integer userId = (Integer) user.get(UserUtil.UserId);

        // 若允许重置密码，则在pm_validate表中插入一行数据，带有token
        String uid = UUID.fastUUID().toString();

        SysValidate sysValidate = new SysValidate();
        sysValidate.setEmail(email);
        sysValidate.setUserId(userId);
        sysValidate.setResetToken(uid);

        int insert = sysvalidateMapper.insert(sysValidate);
        if(insert == 0)
            return false;

        Request request = UserUtil.getRequest(requestAttributes);
        // 设置邮件内容
//        String appUrl = request.getScheme() + "://" + request.getServerName();
//        SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
//        passwordResetEmail.setFrom(from);
//        passwordResetEmail.setTo(email);
//        passwordResetEmail.setSubject("【电商价格监控】忘记密码");
//        passwordResetEmail.setText("您正在申请重置密码，请点击此链接重置密码: \n" + appUrl + "/validate/reset?token=" + validate.getResetToken());
//        validateService.sendPasswordResetEmail(passwordResetEmail);

        return true;
    }
}
