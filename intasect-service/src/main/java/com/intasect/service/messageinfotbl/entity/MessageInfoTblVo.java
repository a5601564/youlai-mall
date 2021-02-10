package com.intasect.service.messageinfotbl.entity;

import com.intasect.service.common.entity.PageCondition;

import java.util.Date;

import lombok.Data;

/**
 * <p>
 * message_info_tbl
 * </p>
 *
 * @author huanzi-qch
 * @since 2021-02-10
 */
@Data
public class MessageInfoTblVo extends PageCondition {
    private Integer id;
    private String title;
    private String content;
    private Integer contentType;
    private Integer disableFlag;
    private Date gmtCreate;
    private Date gmtModified;
    private String gmtCreateUser;
    private String gmtModifiedUser;
}
