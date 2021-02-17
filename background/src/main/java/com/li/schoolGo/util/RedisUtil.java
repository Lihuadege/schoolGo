package com.li.schoolGo.util;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Slf4j
public class RedisUtil {

    private JedisPool jedisPool;

    public void initJedisPool(String host, int port, String pwd){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

        // 总数
        jedisPoolConfig.setMaxTotal(200);
        // 获取连接时等待的最大毫秒
        jedisPoolConfig.setMaxWaitMillis(10*1000);
        // 最少剩余数
        jedisPoolConfig.setMinIdle(10);
        // 如果到最大数，设置等待
        jedisPoolConfig.setBlockWhenExhausted(true);
        // 在获取连接时，检查是否有效
        jedisPoolConfig.setTestOnBorrow(true);

        jedisPool = new JedisPool(jedisPoolConfig,host,port,20*1000,pwd);

    }

    public Jedis getRedis(){
        log.debug("获取Redis连接");
        Jedis jedis = jedisPool.getResource();
        return jedis;
    }


}
