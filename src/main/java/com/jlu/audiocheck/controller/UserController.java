package com.jlu.audiocheck.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.jlu.audiocheck.common.result.Result;
import com.jlu.audiocheck.controller.dto.user.ModifyPasswordDTO;
import com.jlu.audiocheck.controller.dto.user.LoginDTO;
import com.jlu.audiocheck.controller.dto.user.RegisterDTO;
import com.jlu.audiocheck.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public Result login(@Validated @RequestBody LoginDTO loginDTO) {
        return userService.login(loginDTO);
    }

    @PostMapping("/register")
    @ApiOperation(value = "注册")
    public Result register(@Valid @RequestBody RegisterDTO registerDTO){
        return userService.register(registerDTO);
    }

    @PostMapping("/modifyPassword")
    @ApiOperation(value = "修改密码")
    public Result modifyPassword(@Valid @RequestBody ModifyPasswordDTO modifyPasswordDTO){
        StpUtil.checkLogin();
        return userService.modifyPassword(modifyPasswordDTO);
    }

    @PostMapping("/checkLogin")
    @ApiOperation(value = "检查登录状态")
    public Result checkLogin(){
        StpUtil.checkLogin();
        return userService.checkLogin();
    }

    @PostMapping("/logout")
    @ApiOperation(value = "登出")
    public Result logout(){
        StpUtil.logout();
        return Result.success();
    }
}
