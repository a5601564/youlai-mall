package com.intasect.service.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.intasect.service.admin.pojo.dto.RolePermissionDTO;
import com.intasect.service.admin.pojo.entity.SysRolePermission;

import java.util.List;

public interface ISysRolePermissionService extends IService<SysRolePermission> {

    List<Long> listPermissionIds(Long moduleId,Long roleId, Integer type);
    List<Long> listPermissionIds(Long roleId, Integer type);
    boolean update(RolePermissionDTO rolePermission);


}
