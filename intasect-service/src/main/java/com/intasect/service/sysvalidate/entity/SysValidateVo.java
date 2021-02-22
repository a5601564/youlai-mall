package com.intasect.service.sysvalidate.entity;

import com.intasect.service.common.entity.PageCondition;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author huanzi-qch
 * @since 2021-02-22
 */
@Data
public class SysValidateVo extends PageCondition {
    private Integer id;
    private Integer userId;
    private String email;
    private String resetToken;
    private String type;
    private Date gmtCreate;
    private Date gmtModified;
}
