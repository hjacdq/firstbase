package com.hj.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import com.hj.service.JedisDataSource;

@Repository("jedisDataSource")
public class JedisDataSourceImpl implements JedisDataSource{

	private static final Logger LOG = Logger.getLogger(JedisDataSourceImpl.class);

	@Autowired
    private ShardedJedisPool shardedJedisPool;

    public ShardedJedis getRedisClient() {
        ShardedJedis shardJedis = null;
        try {
            shardJedis = shardedJedisPool.getResource();
            return shardJedis;
        } catch (Exception e) {
            LOG.error("[JedisDS] getRedisClent error:" + e.getMessage());
            if (null != shardJedis)
                shardJedis.close();
        }
        return null;
    }

    public void returnResource(ShardedJedis shardedJedis) {
        shardedJedis.close();
    }

    public void returnResource(ShardedJedis shardedJedis, boolean broken) {
        shardedJedis.close();
    }
}
