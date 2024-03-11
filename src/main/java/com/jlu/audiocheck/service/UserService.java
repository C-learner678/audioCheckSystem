package com.jlu.audiocheck.service;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jlu.audiocheck.common.result.Result;
import com.jlu.audiocheck.controller.dto.user.LoginDTO;
import com.jlu.audiocheck.controller.dto.user.ModifyPasswordDTO;
import com.jlu.audiocheck.controller.dto.user.RegisterDTO;
import com.jlu.audiocheck.entity.User;
import com.jlu.audiocheck.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public Result login(LoginDTO loginDTO){
        String name = loginDTO.getName();
        String password = loginDTO.getPassword();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        queryWrapper.eq("password", password);
        User user = userMapper.selectOne(queryWrapper);
        Assert.notNull(user, "用户名或密码错误！");
        StpUtil.login(user.getId());
        return Result.success(StpUtil.getTokenInfo().getTokenValue());
    }

    public Result register(RegisterDTO registerDTO){
        String name = registerDTO.getName();
        String password = registerDTO.getPassword();
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

    public Result modifyPassword(ModifyPasswordDTO modifyPasswordDTO){
        Integer id = Integer.valueOf((String) StpUtil.getLoginId());
        String oldPassword = modifyPasswordDTO.getOldPassword();
        String newPassword = modifyPasswordDTO.getNewPassword();
        Assert.isTrue(!oldPassword.equals(newPassword), "新旧密码相同！");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id).eq("password", oldPassword);
        User user = userMapper.selectOne(queryWrapper);
        Assert.notNull(user, "旧密码错误！");
        user.setPassword(newPassword);
        if(userMapper.updateById(user) > 0) {
            StpUtil.logout();
            return Result.success("");
        }else{
            return Result.fail("修改密码失败！");
        }
    }

    public Result checkLogin(){
        Integer id = Integer.valueOf((String) StpUtil.getLoginId());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        User user = userMapper.selectOne(queryWrapper);
        if(user == null) {
            StpUtil.logout();
            return Result.fail("请重新登录！");
        }
        return Result.success(user.getName());
    }
}
