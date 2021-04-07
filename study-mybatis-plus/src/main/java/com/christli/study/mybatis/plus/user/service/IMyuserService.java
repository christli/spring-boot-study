package com.christli.study.mybatis.plus.user.service;

import com.christli.study.mybatis.plus.user.entity.vo.Myuser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author christli
 * @since 2021-04-07
 */
public interface IMyuserService extends IService<Myuser> {
    public Myuser getUserByName(String name);
}
