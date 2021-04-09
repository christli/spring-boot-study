package com.christli.study.transaction.user.service;

import com.christli.study.transaction.user.entity.vo.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author christli
 * @since 2021-04-09
 */
public interface IUserService extends IService<User> {

    boolean test1(User user) throws Exception ;

    boolean test2(User user);

    boolean test3(User user);

    boolean test4(User user);

}
