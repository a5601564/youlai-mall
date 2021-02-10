package com.intasect.service.messageinfotbl.service;

import com.intasect.service.common.service.CommonServiceImpl;
import com.intasect.service.messageinfotbl.entity.MessageInfoTbl;
import com.intasect.service.messageinfotbl.entity.MessageInfoTblVo;
import com.intasect.service.messageinfotbl.mapper.MessageInfoTblMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * message_info_tbl 服务实现类
 * </p>
 *
 * @author huanzi-qch
 * @since 2021-02-10
 */
@Service
public class MessageInfoTblServiceImpl  extends CommonServiceImpl<MessageInfoTblVo,MessageInfoTbl>  implements MessageInfoTblService {

    @Autowired
    private MessageInfoTblMapper messageinfotblMapper;
}
