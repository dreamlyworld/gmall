package com.leaves.gmall.util;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author Chenweiwei
 * @Date 2021/2/3 17:35
 * @Version 1.0
 */
public class RedisUtil {

    private JedisPool jedisPool;

    public void initPool(String host, String password, int port, int database) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(200);
        poolConfig.setMaxIdle(30);
        poolConfig.setBlockWhenExhausted(true);
        poolConfig.setMaxWaitMillis(10 * 1000);
        poolConfig.setTestOnBorrow(true);
        jedisPool = new JedisPool(poolConfig, host, port, 20 * 1000, password, database);

    }

    public  Jedis getJedis() {
        Jedis jedis = jedisPool.getResource();
        return jedis;
    }

}
