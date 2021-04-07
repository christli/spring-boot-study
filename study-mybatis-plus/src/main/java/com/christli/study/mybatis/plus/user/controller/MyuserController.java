package com.christli.study.mybatis.plus.user.controller;


import com.christli.study.mybatis.plus.user.entity.vo.Myuser;
import com.christli.study.mybatis.plus.user.service.IMyuserService;
import com.christli.study.mybatis.plus.util.JsonResult;
import com.christli.study.mybatis.plus.util.ResultCode;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author christli
 * @since 2021-04-07
 */
@RestController
@RequestMapping("/user/myuser")
public class MyuserController {

    @Autowired
    private IMyuserService myuserService;

    @ApiOperation(value = "获得用户列表", notes = "", httpMethod = "GET")
    @RequestMapping(value = "/listUser")
    public JsonResult listUser() {
        // 框架自带的方法
        List<Myuser> list = this.myuserService.list(null);
        return new JsonResult(ResultCode.SUCCESS, list);
    }

    @ApiOperation(value = "根据用户名获得用户记录", notes = "名称不能为空", httpMethod = "GET")
    @ApiImplicitParam(dataType = "string", name = "name", value = "用户名", required = true)
    @RequestMapping(value = "/getUserByName")
    public JsonResult getUserByName(String name) {
        // 自定义的方法
        Myuser myuser = this.myuserService.getUserByName(name);
        return new JsonResult(ResultCode.SUCCESS, myuser);
    }
}
