package com.christli.study.mybatis.plus.user.dao;

import com.christli.study.mybatis.plus.user.entity.vo.Myuser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author christli
 * @since 2021-04-07
 */
public interface MyuserMapper extends BaseMapper<Myuser> {

    // 方法名自动对应
    public Myuser getUserByName(String name);
}
