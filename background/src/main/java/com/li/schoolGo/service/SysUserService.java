package com.li.schoolGo.service;

import com.li.schoolGo.bean.SysUser;

import java.util.ArrayList;
import java.util.Map;

public interface SysUserService {

    SysUser getSysUser(SysUser sysUser);

    String verify(String userId);

    Map<String, Object> getAll(Integer pageNum, Integer pageSize, String loginName, String phoneNum, String email);

    SysUser getSysUserById(String id);

    Boolean delManager(ArrayList<String> checkId);

    int insertNewSysUser(SysUser sysUser);

    int updateManagerById(SysUser sysUser);

    Map<String, Object> getAllDel(Integer pageNum, Integer pageSize, String loginName, String phoneNum, String email);

    Boolean undelManager(String checkId);
}
