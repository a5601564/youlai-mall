package com.intasect.service.messageinfotbl.controller;

import com.intasect.service.common.controller.CommonController;
import com.intasect.service.messageinfotbl.entity.MessageInfoTbl;
import com.intasect.service.messageinfotbl.entity.MessageInfoTblVo;
import com.intasect.service.messageinfotbl.service.MessageInfoTblService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * message_info_tbl 前端控制器
 * </p>
 *
 * @author huanzi-qch
 * @since 2021-02-19
 */
@RestController
@RequestMapping("/messageInfoTbl/")
public class MessageInfoTblController extends CommonController<MessageInfoTblVo,MessageInfoTbl> {

    @Autowired
    private MessageInfoTblService messageInfoTblService;

}