package com.christli.study.transaction.user.dao;

import com.christli.study.transaction.user.entity.vo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author christli
 * @since 2021-04-09
 */
public interface UserMapper extends BaseMapper<User> {

    @Override
    @Insert("insert into user(id,username,email,password_hash,avatar) values (#{id},#{username},#{email},#{passwordHash},#{avatar})")
    int insert(User user) ;

}
