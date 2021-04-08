package com.christli.study.multi.datasource.user.controller;


import com.christli.study.multi.datasource.user.entity.vo.Users;
import com.christli.study.multi.datasource.user.service.IUsersService;
import com.christli.study.multi.datasource.util.JsonResult;
import com.christli.study.multi.datasource.util.ResultCode;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author christli
 * @since 2021-04-08
 */
@RestController
@RequestMapping("/user/users")
public class UsersController {

    @Autowired
    private IUsersService usersService;

    @ApiOperation(value = "获得用户列表信息", httpMethod = "POST")
    @PostMapping("/listDbInfo")
    public JsonResult listDbInfo() {
        List<Users> dataList = this.usersService.listDbInfo();
        if (dataList == null || dataList.size() == 0) {
            return new JsonResult(ResultCode.SUCCESS_FAIL);
        }
        return new JsonResult(ResultCode.SUCCESS, dataList);
    }
}
