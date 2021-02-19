package com.intasect.service.common.service;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.json.JSONObject;
import com.intasect.service.common.entity.PageCondition;
import com.intasect.service.common.entity.PageInfo;
import com.intasect.service.common.mapper.CommonMapper;
import com.intasect.service.util.CopyUtil;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.constant.AuthConstants;
import com.youlai.common.result.Result;
import com.youlai.common.utils.UserUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用service实现类
 * @param <V> vo对象
 * @param <T> entity实体
 */
public class CommonServiceImpl<V,T> implements CommonService<V,T> {

    @Autowired
    private CommonMapper<T> commonMapper;

    private Class<V> entityVoClass;//实体类Vo

    private Class<T> entityClass;//实体类

    public CommonServiceImpl() {
        Type[] types = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
        this.entityVoClass = (Class<V>) types[0];
        this.entityClass = (Class<T>) types[1];
    }

    @Override
    public Result<PageInfo<V>> page(V entityVo) {
        //实体类缺失分页信息
        if (!(entityVo instanceof PageCondition)) {
            throw new RuntimeException("实体类" + entityVoClass.getName() + "未继承PageCondition");
        }
        PageCondition pageCondition = (PageCondition) entityVo;

        T entity = CopyUtil.copy(entityVo, entityClass);

        //查询条件
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(entity);

        //排序
        if(!StringUtils.isEmpty(pageCondition.getSord()) && "desc".equals(pageCondition.getSord().toLowerCase())){
            queryWrapper.orderByDesc(pageCondition.getSidx());
        }else{
            queryWrapper.orderByAsc(pageCondition.getSidx());
        }

        //分页
        IPage<T> page = new Page<>(pageCondition.getPage(), pageCondition.getRows());

        //查询获取数据
        page = commonMapper.selectPage(page, queryWrapper);

        //拼接数据
        PageInfo<V> pageInfo = PageInfo.of(page, entityVoClass);
        pageInfo.setSidx(pageCondition.getSidx());
        pageInfo.setSord(pageCondition.getSord());
        return Result.success(pageInfo);
    }

    @Override
    public Result<List<V>> list(V entityVo) {
        T entity = CopyUtil.copy(entityVo, entityClass);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(entity);
        List<V> vs = CopyUtil.copyList(commonMapper.selectList(queryWrapper), entityVoClass);
        return Result.success(vs);
    }

    @Override
    public Result<V> get(String id) {
        return Result.success(CopyUtil.copy(commonMapper.selectById(id),entityVoClass));
    }

    @Override
    public Result<V> save(V entityVo) {
        //传进来的对象（属性可能残缺）
        T entity = CopyUtil.copy(entityVo, entityClass);
        JSONObject user = UserUtil.getUser(RequestContextHolder.getRequestAttributes());
        //最终要保存的对象
        T entityFull = entity;

        Object id = null;

        //为空的属性值，忽略属性，BeanUtils复制的时候用到
        List<String> ignoreProperties = new ArrayList<String>();

        //获取最新数据，解决部分更新时jpa其他字段设置null问题
        try {
            //反射获取Class的属性（Field表示类中的成员变量）
            for (Field field : entity.getClass().getDeclaredFields()) {
                //获取授权
                field.setAccessible(true);
                //属性名称
                String fieldName = field.getName();
                //属性的值
                Object fieldValue = field.get(entity);

                //找出Id主键
                if (field.isAnnotationPresent(TableId.class) && !StringUtils.isEmpty(fieldValue)) {
                    id = fieldValue;
                    entityFull = commonMapper.selectById((Serializable) id);
                }

                //找出值为空的属性，值为空则为忽略属性
                if(null == fieldValue){
                    ignoreProperties.add(fieldName);
                }
            }
            /*
                org.springframework.beans BeanUtils.copyProperties(A,B); 是A中的值付给B
                org.apache.commons.beanutils; BeanUtils.copyProperties(A,B);是B中的值付给A
                把entity的值赋给entityFull，第三个参数是忽略属性，表示不进行赋值
             */
            BeanUtils.copyProperties(entity, entityFull, ignoreProperties.toArray(new String[0]));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        LocalDateTime now = LocalDateTimeUtil.now();
        //新增或更新
        if(StringUtils.isEmpty(id)){
            //1插入成功、0失败
            ReflectUtil.setFieldValue(entityFull,"gmtCreate",now);
            ReflectUtil.setFieldValue(entityFull,"gmtCreateUser",user.get("user_name"));
            int newId = commonMapper.insert(entityFull);
        }else{
            ReflectUtil.setFieldValue(entityFull,"gmtModified",now);
            ReflectUtil.setFieldValue(entityFull,"gmtModifiedUser",user.get("user_name"));
            commonMapper.updateById(entityFull);
        }

        return Result.success(CopyUtil.copy(entityFull,entityVoClass));
    }

    @Override
    public Result<String> delete(String id) {
        //1删除成功、0失败
        return Result.success(String.valueOf(commonMapper.deleteById(id)));
    }
}