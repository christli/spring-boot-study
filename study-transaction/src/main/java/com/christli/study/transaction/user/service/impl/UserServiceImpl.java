package com.christli.study.transaction.user.service.impl;

import com.christli.study.transaction.user.entity.vo.User;
import com.christli.study.transaction.user.dao.UserMapper;
import com.christli.study.transaction.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.SQLException;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author christli
 * @since 2021-04-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;
    @Autowired
    private TransactionDefinition transactionDefinition;

    @Override
    @Transactional
    public boolean test1(User user) throws Exception {
        /*
         * 简单的事物回滚 由Spring框架进行回滚
         */
        long id = user.getId();
        System.out.println("查询的数据1:" + this.baseMapper.selectById(id));
        // 新增两次，会出现主键ID冲突，看是否可以回滚该条数据
        this.baseMapper.insert(user);
        System.out.println("查询的数据2:" + this.baseMapper.selectById(id));
        this.baseMapper.insert(user);
        return false;
    }

    @Override
    @Transactional
    public boolean test2(User user) {

        /*
         * 简单的事物回滚 自己捕获该异常进行手动回滚
         */
        long id = user.getId();
        try {
            System.out.println("查询的数据1:" + this.baseMapper.selectById(id));
            // 新增两次，会出现主键ID冲突，看是否可以回滚该条数据
            this.baseMapper.insert(user);
            System.out.println("查询的数据2:" + this.baseMapper.selectById(id));
            this.baseMapper.insert(user);
        } catch (Exception e) {
            System.out.println("发生异常,进行手动回滚！");
            // 手动回滚事物
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            // 注意手动回滚事物要在异常抛出之前！
            e.printStackTrace();
        }

        return false;
    }

    @Override
    @Transactional
    public boolean test3(User user) {

        /*
         * 子方法出现异常进行回滚
         */
        try {
            System.out.println("查询的数据1:" + this.baseMapper.selectById(user.getId()));
            deal1(user);
            deal2(user);
            deal3(user);
        } catch (Exception e) {
            System.out.println("发生异常,进行手动回滚！");
            // 手动回滚事物
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
        }
        return false;

    }

    public void deal1(User user) throws SQLException {
        this.baseMapper.insert(user);
        System.out.println("查询的数据2:" + this.baseMapper.selectById(user.getId()));
    }

    public void deal2(User user)  throws SQLException{
        if(user.getId()<20){
            //SQL异常
            this.baseMapper.insert(user);
        }else{
            user.setId(21);
            this.baseMapper.updateById(user);
            System.out.println("查询的数据3:" + this.baseMapper.selectById(user.getId()));
        }
    }


    @Transactional(rollbackFor = SQLException.class)
    public void deal3(User user)  {
        if(user.getId()>20){
            //SQL异常
            this.baseMapper.insert(user);
        }

    }



    @Override
    public boolean test4(User user) {
        /*
         * 手动进行事物控制
         */
        TransactionStatus transactionStatus=null;
        boolean isCommit = false;
        try {
            transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
            System.out.println("查询的数据1:" + this.baseMapper.selectById(user.getId()));
            // 进行新增/修改
            this.baseMapper.insert(user);
            System.out.println("查询的数据2:" + this.baseMapper.selectById(user.getId()));
            if(user.getId()<20) {
                user.setId(user.getId()+2);
                this.baseMapper.updateById(user);
                System.out.println("查询的数据3:" + this.baseMapper.selectById(user.getId()));
            }else {
                throw new Exception("模拟一个异常!");
            }
            //手动提交
            dataSourceTransactionManager.commit(transactionStatus);
            isCommit= true;
            System.out.println("手动提交事物成功!");
            throw new Exception("模拟第二个异常!");

        } catch (Exception e) {
            //如果未提交就进行回滚
            if(!isCommit){
                System.out.println("发生异常,进行手动回滚！");
                //手动回滚事物
                dataSourceTransactionManager.rollback(transactionStatus);
            }

            e.printStackTrace();
        }
        return false;
    }

}
