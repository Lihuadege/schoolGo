package com.li.schoolgo.service.impl;

import com.alibaba.fastjson.JSON;
import com.li.schoolGo.bean.SysUser;
import com.li.schoolgo.Constant.UserConst;
import com.li.schoolgo.mapper.SysUserMapper;
import com.li.schoolgo.service.SysUserService;
import com.li.schoolgo.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import redis.clients.jedis.Jedis;

import java.util.Map;

@Service
public class SysUserServiceImpl implements SysUserService {

//    @Autowired
//    private RedisUtil redisUtil;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public String verify(Integer userId) {
        return null;
    }

    public SysUser getSysUser(SysUser user){
        //对前台传入的密码进行加密后，更新user中password的值
        String pwd = user.getPassword();
        String realPwd = DigestUtils.md5DigestAsHex(pwd.getBytes());
        user.setPassword(realPwd);

        //从数据库查询用户
        SysUser sysUser = sysUserMapper.selectOne(user);

        if(sysUser != null){
//            Jedis jedis = redisUtil.getJedis();
//            String userKey = UserConst.USER_KEY_PREFIX + sysUser.getId() + UserConst.UER_KEY_SUFFIX;
//            try{
//                jedis.setex(userKey,UserConst.USER_KEY_TIMEOUT, JSON.toJSONString(sysUser));
//            }catch (Exception e){
//                e.printStackTrace();
//            }finally {
//                if(jedis != null)jedis.close();
//            }
            return sysUser;
        }
        return null;
    }


}
