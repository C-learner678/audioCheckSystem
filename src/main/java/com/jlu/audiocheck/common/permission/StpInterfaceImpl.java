package com.jlu.audiocheck.common.permission;

import cn.dev33.satoken.stp.StpInterface;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StpInterfaceImpl implements StpInterface {
    //权限码
    @Override
    public List<String> getPermissionList(Object o, String s) {
        List<String> list = new ArrayList<String>();
        list.add("use");
        return null;
    }

    //角色标识
    @Override
    public List<String> getRoleList(Object o, String s) {
        List<String> list = new ArrayList<String>();
        list.add("admin");
        return list;
    }
}
