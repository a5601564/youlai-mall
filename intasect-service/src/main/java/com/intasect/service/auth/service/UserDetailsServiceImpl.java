package com.intasect.service.auth.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.intasect.service.admin.pojo.dto.UserDTO;
import com.intasect.service.admin.pojo.entity.SysUser;
import com.intasect.service.admin.pojo.entity.SysUserRole;
import com.intasect.service.admin.service.ISysRoleService;
import com.intasect.service.admin.service.ISysUserRoleService;
import com.intasect.service.admin.service.ISysUserService;
import com.intasect.service.auth.domain.User;
import com.youlai.common.constant.AuthConstants;
import com.youlai.common.result.ResultCode;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 自定义用户认证和授权
 */
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private HttpServletRequest request;
    private ISysUserService iSysUserService;
    private ISysUserRoleService iSysUserRoleService;
    private ISysRoleService iSysRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String clientId = request.getParameter(AuthConstants.JWT_CLIENT_ID_KEY);
        User user = null;
        switch (clientId) {
            case AuthConstants.ADMIN_CLIENT_ID: // 后台用户
                UserDTO userRes = this.getUserByUsername(username);
                if (userRes == null) {
                    throw new UsernameNotFoundException(ResultCode.USER_NOT_EXIST.getMsg());
                }
                UserDTO userDTO = userRes;
                userDTO.setClientId(clientId);
                user = new User(userDTO);
                break;
        }
        if (!user.isEnabled()) {
            throw new DisabledException("该账户已被禁用!");
        } else if (!user.isAccountNonLocked()) {
            throw new LockedException("该账号已被锁定!");
        } else if (!user.isAccountNonExpired()) {
            throw new AccountExpiredException("该账号已过期!");
        }
        return user;
    }

    public UserDTO getUserByUsername(
            @PathVariable String username
    ) {
        SysUser user = iSysUserService.getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, username));

        if (user == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        BeanUtil.copyProperties(user, userDTO);
        List<Long> roleIds = iSysUserRoleService.list(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getUserId, user.getId())
        ).stream().map(item -> item.getRoleId()).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(roleIds)) {
            List<Long> roles = iSysRoleService.listByIds(roleIds).stream()
                    .map(role -> role.getId()).collect(Collectors.toList());
            userDTO.setRoles(roles);
        }
        return userDTO;
    }


}
