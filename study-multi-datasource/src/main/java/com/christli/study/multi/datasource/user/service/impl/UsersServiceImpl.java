package com.christli.study.multi.datasource.user.service.impl;

import com.christli.study.multi.datasource.user.entity.vo.Users;
import com.christli.study.multi.datasource.user.dao.UsersMapper;
import com.christli.study.multi.datasource.user.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author christli
 * @since 2021-04-08
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

    @Override
    public List<Users> listDbInfo() {
        return this.baseMapper.listDbInfo();
    }
}
