package com.christli.study.mybatis.plus.user.service.impl;

import com.christli.study.mybatis.plus.user.entity.vo.Myuser;
import com.christli.study.mybatis.plus.user.dao.MyuserMapper;
import com.christli.study.mybatis.plus.user.service.IMyuserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author christli
 * @since 2021-04-07
 */
@Service
public class MyuserServiceImpl extends ServiceImpl<MyuserMapper, Myuser> implements IMyuserService {

    @Override
    public Myuser getUserByName(String name) {
        return this.baseMapper.getUserByName(name);
    }
}
