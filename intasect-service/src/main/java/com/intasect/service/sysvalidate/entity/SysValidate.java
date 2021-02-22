package com.intasect.service.sysvalidate.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author huanzi-qch
 * @since 2021-02-22
 */
@Data
public class SysValidate {

        //主键生成策略自动递增：type = IdType.AUTO，指定id回显
    @TableId(value = "id", type = IdType.AUTO)
        private Integer id;

    private Integer userId;

    private String email;

    private String resetToken;

    private String type;

    private Date gmtCreate;

    private Date gmtModified;
}
