package com.intasect.service.admin.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.intasect.service.admin.pojo.entity.SysDept;
import com.intasect.service.admin.pojo.vo.DeptVO;
import com.intasect.service.admin.pojo.vo.TreeVO;

import java.util.List;

public interface ISysDeptService extends IService<SysDept> {

    List<DeptVO> listDeptVO(LambdaQueryWrapper<SysDept> baseQuery);

    List<TreeVO> listTreeVO(LambdaQueryWrapper<SysDept> baseQuery);
}
