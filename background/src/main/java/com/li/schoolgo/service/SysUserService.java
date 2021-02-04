package com.li.schoolgo.service;

import com.li.schoolGo.bean.SysUser;

import java.util.Map;

public interface SysUserService {

    SysUser getSysUser(SysUser sysUser);

    String verify(Integer userId);
}
