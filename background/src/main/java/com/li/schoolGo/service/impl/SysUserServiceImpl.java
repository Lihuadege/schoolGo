package com.li.schoolGo.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.li.schoolGo.bean.SysUser;
import com.li.schoolGo.Constant.UserConst;
import com.li.schoolGo.mapper.SysUserMapper;
import com.li.schoolGo.service.SysUserService;
import com.li.schoolGo.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    @Transactional//开启事务
    public int insertNewSysUser(SysUser sysUser) {
        int res = sysUserMapper.insertSelective(sysUser);
        return res;
    }

    @Override
    @Transactional
    public int updateManagerById(SysUser sysUser) {
        int res = sysUserMapper.updateByPrimaryKeySelective(sysUser);
        return res;
    }

    @Override
    public String verify(String userId) {
        if (userId != null || userId.length() > 0) {
            Jedis jedis = redisUtil.getRedis();
            long old = System.currentTimeMillis();

            log.debug("验证用户登录信息:" + userId);
            String userKey = UserConst.USER_KEY_PREFIX + userId + UserConst.UER_KEY_SUFFIX;
            String user = jedis.get(userKey);
            try {
                //如果查询到的用户不是空，就更新Redis缓存时间
                if (user != null) {
                    jedis.expire(userKey, UserConst.USER_KEY_TIMEOUT);
                    return user;
                }
            } finally {

                long newTime = System.currentTimeMillis();

                System.out.println("total time:" + (newTime - old));
                if (jedis != null) jedis.close();
            }
        }
        return null;
    }

    @Override
    public SysUser getSysUserById(String id) {
        Example example = new Example(SysUser.class);
        example.createCriteria().andEqualTo("status",1).andEqualTo("id",id);
        SysUser user = sysUserMapper.selectOneByExample(example);
        return user;
    }

    @Override
    public Boolean delManager(ArrayList<String> checkId) {
        if(checkId.size() > 0){
            Example example = new Example(SysUser.class);
            example.createCriteria().andIn("id",checkId);
            SysUser sysUser = new SysUser();
            sysUser.setStatus(0);
            int i = sysUserMapper.updateByExampleSelective(sysUser, example);
            if(i > 0)return true;
        }
        return false;
    }

    @Override
    public Boolean undelManager(String checkId) {
        SysUser sysUser = new SysUser();
        sysUser.setId(checkId);
        sysUser.setStatus(1);
        int i = sysUserMapper.updateByPrimaryKeySelective(sysUser);
        if(i > 0)return true;
        return false;
    }

    @Override
    public Map<String, Object> getAll(Integer pageNum, Integer pageSize, String loginName, String phoneNum, String email) {

        Example example = new Example(SysUser.class);

        if(!StringUtils.isEmpty(loginName)){
            example.createCriteria().andLike("loginName", "%"+loginName+"%");
        }
        if(!StringUtils.isEmpty(phoneNum)){
            example.createCriteria().andLike("phoneNum", "%"+phoneNum+"%");
        }
        if(!StringUtils.isEmpty(email)){
            example.createCriteria().andLike("email", "%"+email+"%");
        }

        example.createCriteria().andEqualTo("status",1);

        PageHelper.startPage(pageNum,pageSize);
        List<SysUser> sysUsers = sysUserMapper.selectByExample(example);

        HashMap<String, Object> resultMap = new HashMap<>();
        PageInfo<SysUser> page = new PageInfo<>(sysUsers);

        resultMap.put("count",page.getTotal());

        if (sysUsers.size() != 0) {
            resultMap.put("data",sysUsers);
            return resultMap;
        }

        return null;
    }

    @Override
    public Map<String, Object> getAllDel(Integer pageNum, Integer pageSize, String loginName, String phoneNum, String email) {

        Example example = new Example(SysUser.class);

        if(!StringUtils.isEmpty(loginName)){
            example.createCriteria().andLike("loginName", "%"+loginName+"%");
        }
        if(!StringUtils.isEmpty(phoneNum)){
            example.createCriteria().andLike("phoneNum", "%"+phoneNum+"%");
        }
        if(!StringUtils.isEmpty(email)){
            example.createCriteria().andLike("email", "%"+email+"%");
        }

        example.createCriteria().andEqualTo("status",0);

        PageHelper.startPage(pageNum,pageSize);
        List<SysUser> sysUsers = sysUserMapper.selectByExample(example);

        HashMap<String, Object> resultMap = new HashMap<>();
        PageInfo<SysUser> page = new PageInfo<>(sysUsers);

        resultMap.put("count",page.getTotal());

        if (sysUsers.size() != 0) {
            resultMap.put("data",sysUsers);
            return resultMap;
        }

        return null;
    }

    public SysUser getSysUser(SysUser user) {
        //对前台传入的密码进行加密后，更新user中password的值
        String pwd = user.getPassword();
        String realPwd = DigestUtils.md5DigestAsHex(pwd.getBytes());
        user.setPassword(realPwd);

        //从数据库查询用户
        SysUser sysUser = sysUserMapper.selectOne(user);

        if (sysUser != null) {
            Jedis jedis = redisUtil.getRedis();

            String userKey = UserConst.USER_KEY_PREFIX + sysUser.getId() + UserConst.UER_KEY_SUFFIX;
            try {
                jedis.setex(userKey, UserConst.USER_KEY_TIMEOUT, JSON.toJSONString(sysUser));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (jedis != null) jedis.close();
            }
            return sysUser;
        }
        return null;
    }


}
