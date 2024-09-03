package com.example.controller;

import com.example.common.Result;
import com.example.entity.Account;
import com.example.service.AdminService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;


@RestController
public class WebController {

    @Resource
    private AdminService adminService;
    /**
     * 默认请求接口
     */
    @GetMapping("/")
    public Result hello() {
        return Result.success();
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result login(@RequestBody Account account) {
        if ("ADMIN".equals(account.getRole())) {
            return Result.success(adminService.login(account));
        }
        return Result.success();
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    public Result register() {
        return Result.success();
    }

    /**
     * 修改密码
     */
    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody Account account) {
        if ("ADMIN".equals(account.getRole())) {
            adminService.updatePassword(account);
        }
        return Result.success();
    }

}
