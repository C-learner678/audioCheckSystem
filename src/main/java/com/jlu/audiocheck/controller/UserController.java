package com.jlu.audiocheck.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jlu.audiocheck.common.result.Result;
import com.jlu.audiocheck.controller.DTO.ModifyPasswordDTO;
import com.jlu.audiocheck.controller.DTO.UserDTO;
import com.jlu.audiocheck.entity.User;
import com.jlu.audiocheck.mapper.UserMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public Result login(@Valid @RequestBody UserDTO userDTO) {
        String name = userDTO.getName();
        String password = userDTO.getPassword();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        User user = userMapper.selectOne(queryWrapper);
        Assert.notNull(user, "用户名或密码错误！");
        Assert.isTrue(password.equals(user.getPassword()), "用户名或密码错误！");
        return Result.success();
    }

    @PostMapping("/register")
    @ApiOperation(value = "注册")
    public Result register(@Valid @RequestBody UserDTO userDTO){
        String name = userDTO.getName();
        String password = userDTO.getPassword();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        User user = userMapper.selectOne(queryWrapper);
        Assert.isNull(user, "用户名已被使用！");
        User user1 = new User();
        user1.setName(name);
        user1.setPassword(password);
        if(userMapper.insert(user1) > 0) {
            return Result.success();
        }else{
            return Result.fail("注册失败！");
        }
    }

    @PostMapping("/modifyPassword")
    @ApiOperation(value = "修改密码")
    public Result modifyPassword(@Valid @RequestBody ModifyPasswordDTO modifyPasswordDTO){
        String name = modifyPasswordDTO.getName();
        String oldPassword = modifyPasswordDTO.getOldPassword();
        String newPassword = modifyPasswordDTO.getNewPassword();
        Assert.isTrue(!oldPassword.equals(newPassword), "新旧密码相同！");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name).eq("password", oldPassword);
        User user = userMapper.selectOne(queryWrapper);
        Assert.notNull(user, "旧密码错误！");
        user.setPassword(newPassword);
        if(userMapper.updateById(user) > 0) {
            return Result.success();
        }else{
            return Result.fail("修改密码失败！");
        }
    }
}
