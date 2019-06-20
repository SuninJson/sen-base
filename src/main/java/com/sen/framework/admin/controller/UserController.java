package com.sen.framework.admin.controller;


import com.baomidou.mybatisplus.extension.service.IService;
import com.sen.framework.admin.entity.User;
import com.sen.framework.admin.service.IUserService;
import com.sen.framework.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Evan Huang
 * @since 2019-01-09
 */
@RestController
@RequestMapping("/admin/user")
public class UserController extends BaseController<User> {
    @Autowired
    private IUserService userService;

    @Override
    public IService getIService() {
        return userService;
    }
}
