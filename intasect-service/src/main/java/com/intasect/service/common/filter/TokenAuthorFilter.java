//package com.intasect.service.common.filter;
//
//import cn.hutool.core.util.StrUtil;
//import cn.hutool.json.JSONObject;
//import cn.hutool.json.JSONUtil;
//import com.intasect.service.util.WebUtils;
//import com.nimbusds.jose.JWSObject;
//import com.youlai.common.constant.AuthConstants;
//import com.youlai.common.result.ResultCode;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.logging.log4j.util.Strings;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.http.HttpMethod;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///***************
// * token验证拦截
// * @author bamboo zjcjava@163.com
// * @time 2017-08-01
// */
//@Component
//@Slf4j
//public class TokenAuthorFilter implements Filter {
//
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    @Override
//    public void destroy() {
//
//    }
//
//    @SneakyThrows
//    @Override
//    public void doFilter(ServletRequest req, ServletResponse rep,
//                         FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) req;
//        HttpServletResponse response = (HttpServletResponse) rep;
//
//        // 无token放行
//
//        String token = request.getHeader(AuthConstants.JWT_TOKEN_HEADER);
//        if (StrUtil.isBlank(token)) {
//            return ;
//        }
//
//        // 解析JWT获取jti，以jti为key判断redis的黑名单列表是否存在，存在拦截响应token失效
//        token = token.replace(AuthConstants.JWT_TOKEN_PREFIX, Strings.EMPTY);
//        JWSObject jwsObject = JWSObject.parse(token);
//        String payload = jwsObject.getPayload().toString();
//        JSONObject jsonObject = JSONUtil.parseObj(payload);
//        String jti = jsonObject.getStr(AuthConstants.JWT_JTI_KEY);
//        Boolean isBlack = redisTemplate.hasKey(AuthConstants.TOKEN_BLACKLIST_PREFIX + jti);
//        if (isBlack) {
//            return ;
//        }
//        chain.doFilter(request,response);
//    }
//
//    @Override
//    public void init(FilterConfig arg0) throws ServletException {
//
//    }
//
//}