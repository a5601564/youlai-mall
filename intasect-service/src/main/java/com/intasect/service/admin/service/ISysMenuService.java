package com.intasect.service.admin.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.intasect.service.admin.pojo.entity.SysMenu;
import com.intasect.service.admin.pojo.vo.MenuVO;
import com.intasect.service.admin.pojo.vo.RouterVO;
import com.intasect.service.admin.pojo.vo.TreeVO;

import java.util.List;
/**
 * @author haoxr
 * @date 2020-11-06
 */
public interface ISysMenuService extends IService<SysMenu> {

    List<MenuVO> listMenuVO(LambdaQueryWrapper<SysMenu> baseQuery);

    List<TreeVO> listTreeVO(LambdaQueryWrapper<SysMenu> baseQuery);

    List<RouterVO> listRouterVO();
}
