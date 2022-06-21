package com.xiushang.common.redis.util;


import com.xiushang.common.redis.dao.AbstractBaseRedisDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisClusterConnection;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class JsonRedisClient extends AbstractBaseRedisDao {
    private JsonRedisSeriaziler jsonRedisSeriaziler = new JsonRedisSeriaziler();

    public JsonRedisSeriaziler getJsonRedisSeriaziler() {
        return this.jsonRedisSeriaziler;
    }

    public void setJsonRedisSeriaziler(JsonRedisSeriaziler jsonRedisSeriaziler) {
        this.jsonRedisSeriaziler = jsonRedisSeriaziler;
    }

    public void setValue(String key, Object value) {
        ValueOperations operations = this.redisTemplate.opsForValue();
        operations.set(key, this.jsonRedisSeriaziler.seriazileAsString(value));
    }

    public <T> T getValue(String key, Class<T> c) {
        ValueOperations operations = this.redisTemplate.opsForValue();
        String json = (String) operations.get(key);
        return this.jsonRedisSeriaziler.deserializeAsObject(json, c);
    }

    public boolean set(String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations operations = this.redisTemplate
                    .opsForValue();
            operations.set(key, this.jsonRedisSeriaziler.seriazileAsString(value));
            this.redisTemplate.expire(key, expireTime.longValue(), TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void remove(String key) {
        if ((StringUtils.isNotBlank(key)) && (this.redisTemplate.hasKey(key).booleanValue()))
            this.redisTemplate.delete(key);
    }

    /**
     * 使用scan遍历key
     * 为什么不使用keys 因为Keys会引发Redis锁，
     * 并且增加Redis的CPU占用,特别是数据庞大的情况下。
     * 这个命令千万别在生产环境乱用。
     * 支持redis单节点和集群调用
     *
     * @param matchKey
     * @return
     */
    private Set<String> scanMatch(String matchKey) {
        Set<String> keys = new HashSet();
        RedisConnectionFactory connectionFactory = redisTemplate.getConnectionFactory();
        RedisConnection redisConnection = connectionFactory.getConnection();
        Cursor<byte[]> scan = null;
        if (redisConnection instanceof JedisClusterConnection) {
            RedisClusterConnection clusterConnection = connectionFactory.getClusterConnection();
            Iterable<RedisClusterNode> redisClusterNodes = clusterConnection.clusterGetNodes();
            Iterator<RedisClusterNode> iterator = redisClusterNodes.iterator();
            while (iterator.hasNext()) {
                RedisClusterNode next = iterator.next();
                scan = clusterConnection.scan(next, ScanOptions.scanOptions().match(matchKey).count(Integer.MAX_VALUE).build());
                while (scan.hasNext()) {
                    keys.add(new String(scan.next()));
                }
                if (scan != null) {
                    scan.close();
                }
            }
            return keys;
        }
        if (redisConnection instanceof JedisConnection) {
            scan = redisConnection.scan(ScanOptions.scanOptions().match(matchKey).count(Integer.MAX_VALUE).build());
            while (scan.hasNext()) {
                //找到一次就添加一次
                keys.add(new String(scan.next()));
            }
            if (scan != null) {
                scan.close();
            }
            return keys;
        }

        return keys;

    }

    public <T> List<T> getValues(String key, Class<T> c) {
        List<T> list = new ArrayList<>();
        Set<String> keys = scanMatch(key);

        List<String> dataList = this.redisTemplate.opsForValue().multiGet(keys);
        if (list != null) {
            for (String json : dataList) {
                T o = this.jsonRedisSeriaziler.deserializeAsObject(json, c);
                list.add(o);
            }
        }

    /*
    Iterator<String> iterator = keys.iterator();
    while (iterator.hasNext()){
      String k = iterator.next();
      T o = getValue(k,c);

      list.add(o);
    }*/

        return list;
    }

}
