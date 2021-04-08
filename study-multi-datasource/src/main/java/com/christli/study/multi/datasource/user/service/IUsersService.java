package com.christli.study.multi.datasource.user.service;

import com.christli.study.multi.datasource.user.entity.vo.Users;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author christli
 * @since 2021-04-08
 */
public interface IUsersService extends IService<Users> {

    public List<Users> listDbInfo();

}
