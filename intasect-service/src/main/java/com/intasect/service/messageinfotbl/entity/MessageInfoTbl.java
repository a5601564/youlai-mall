package com.intasect.service.messageinfotbl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * message_info_tbl
 * </p>
 *
 * @author huanzi-qch
 * @since 2021-02-10
 */
@Data
public class MessageInfoTbl {

    /**
     * id
     */
        //主键生成策略自动递增：type = IdType.AUTO，指定id回显
    @TableId(value = "id", type = IdType.AUTO)
        private Integer id;

    /**
     * title
     */
    private String title;

    /**
     * content
     */
    private String content;

    /**
     * content_type
     */
    private Integer contentType;

    /**
     * disable_flag
     */
    private Integer disableFlag;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModified;

    /**
     * 创建者
     */
    private String gmtCreateUser;

    /**
     * 更新者
     */
    private String gmtModifiedUser;
}
