package com.christli.study.aop.log.service;

import com.christli.study.aop.log.domain.SysOperationLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AsyncLogService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 保存系统日志记录
     */
    @Async
    public void saveSysLog(SysOperationLog log) {

        String sql = "INSERT INTO sys_operation_log(title,business_type,method,STATUS,error_msg,oper_time) VALUES(?,?,?,?,?,?)";
        jdbcTemplate.update(sql, new Object[]{log.getTitle(), log.getBusinessType(), log.getMethod(), log.getStatus(), log.getErrorMsg(), new Date()});
    }
}
