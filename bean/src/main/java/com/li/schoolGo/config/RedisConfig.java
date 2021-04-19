package com.li.schoolGo.config;

import com.li.schoolGo.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {

    private Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    //读取application.properties配置文件中关于Redis的配置
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port:0}")
    private int port;

    @Value("${spring.redis.password}")
    private String pwd;

    @Bean
    public RedisUtil getRedisUtil(){
        if(host.equals("disabled")){
            return null;
        }
        RedisUtil redisUtil = new RedisUtil();
        logger.debug("redisUtil 初始化");
        redisUtil.initJedisPool(host,port,pwd);
        return redisUtil;
    }
}
