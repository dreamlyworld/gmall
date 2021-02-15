package com.leaves.gmall;


import com.leaves.gmall.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GmallManageServiceApplicationTests {

    @Autowired
    RedisUtil redisUtil;




    @Test
    public void contextLoads2() {
        Jedis jedis = redisUtil.getJedis();
        System.out.println(jedis);
    }

}
