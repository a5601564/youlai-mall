package com.intasect.service.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.intasect.service.admin.pojo.entity.OauthClientDetails;
import com.intasect.service.admin.mapper.OauthClientDetailsMapper;
import com.intasect.service.admin.service.IOauthClientDetailsService;
import org.springframework.stereotype.Service;

/**
 * @author haoxr
 * @date 2020-11-06
 */
@Service
public class OauthClientDetailsServiceImpl extends ServiceImpl<OauthClientDetailsMapper, OauthClientDetails> implements IOauthClientDetailsService {
}
