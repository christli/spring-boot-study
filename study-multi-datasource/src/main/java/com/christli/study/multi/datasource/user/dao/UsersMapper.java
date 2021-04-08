package com.christli.study.multi.datasource.user.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.christli.study.multi.datasource.user.entity.vo.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author christli
 * @since 2021-04-08
 */
//@DS("slave_1")
public interface UsersMapper extends BaseMapper<Users> {

    // 使用@DS注解，可以切换数据源。如果没有配置@DS，则使用默认数据源（使用配置文件中的primary源）
    // @DS 可以注解在方法上和类上，同时存在方法注解优先于类上注解
    // 注解在service实现或mapper接口方法上，但强烈不建议同时在service和mapper注解

//  @DS("slave_1")
//	@DS("master")
    public List<Users> listDbInfo();
}
