package com.intasect.service.messageinfotbl.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.intasect.service.common.mapper.CommonMapper;
import com.intasect.service.messageinfotbl.entity.MessageInfoTbl;
import com.intasect.service.messageinfotbl.entity.MessageInfoTblVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.sql.Wrapper;

/**
 * <p>
 * message_info_tbl Mapper 接口
 * </p>
 *
 * @author huanzi-qch
 * @since 2021-02-19
 */
@Mapper
public interface MessageInfoTblMapper extends CommonMapper<MessageInfoTbl> {

    @Select("select id, title, content_type, importance, disable_flag, gmt_create, gmt_modified, gmt_create_user, gmt_modified_user from message_info_tbl")
    IPage<MessageInfoTblVo> selectPage(MessageInfoTbl messageInfoTbl);
}

