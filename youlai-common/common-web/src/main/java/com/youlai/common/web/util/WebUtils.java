package com.youlai.common.web.util;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.nimbusds.jose.JWSObject;
import com.youlai.common.constant.AuthConstants;
import com.youlai.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class WebUtils extends org.springframework.web.util.WebUtils {


    public static HttpServletRequest getHttpServletRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    public static JSONObject getJwtPayload() {
        JSONObject jsonObject = null;
        try {
            String token = getHttpServletRequest().getHeader(AuthConstants.JWT_TOKEN_HEADER);
            token = token.replace(AuthConstants.JWT_TOKEN_PREFIX, Strings.EMPTY);
            JWSObject jwsObject = JWSObject.parse(token);
            String payload = jwsObject.getPayload().toString();
            jsonObject = JSONUtil.parseObj(payload);
        }catch (Exception e){
            return jsonObject;
        }

        return jsonObject;
    }

    public static Long getUserId() {
        Long id = getJwtPayload().getLong(AuthConstants.JWT_USER_ID_KEY);
        return id;
    }

    public static String getClientId() {
        String clientId = getJwtPayload().getStr(AuthConstants.JWT_CLIENT_ID_KEY);
        return clientId;
    }

    public static List<Long> getRoleIds() {
        List<String> list = getJwtPayload().get(AuthConstants.JWT_AUTHORITIES_KEY, List.class);
        List<Long> authorities = list.stream().map(Long::valueOf).collect(Collectors.toList());
        return authorities;
    }

    public static ServerHttpResponse getServerHttpResponse(ResultCode resultCode){

        return null;
    }

}
