package com.bolife.online.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/10 16:02
 * @Description:
 */
public class RedisAPI {
    public JedisPool jedisPool;// redis连接池对象

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * set key and value tp redis
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();// 获取jedis对象
            jedis.set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(jedisPool, jedis);
        }
        return false;
    }

    /**
     * 判断某个key是否存在
     * @param key
     * @return
     */
    public boolean exist(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(jedisPool, jedis);
        }
        return false;
    }

    /**
     * 通过key获取value
     * @param key
     * @return
     */
    public String get(String key) {
        String value = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(jedisPool, jedis);
        }
        return value;
    }

    /**
     * 返还到连接池
     * @param jedisPool
     * @param jedis
     */
    public static void returnResource(JedisPool jedisPool, Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }
}
