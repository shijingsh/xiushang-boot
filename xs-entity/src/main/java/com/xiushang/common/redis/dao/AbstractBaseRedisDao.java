package com.xiushang.common.redis.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

public abstract class AbstractBaseRedisDao<K, V>
{

  @Autowired
  protected RedisTemplate<K, V> redisTemplate;

  public void setRedisTemplate(RedisTemplate<K, V> redisTemplate)
  {
    this.redisTemplate = redisTemplate;
  }

  protected RedisSerializer<String> getRedisSerializer()
  {
    return this.redisTemplate.getStringSerializer();
  }
}
