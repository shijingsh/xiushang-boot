package com.xiushang.common.redis.util;


import com.xiushang.common.redis.dao.AbstractBaseRedisDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

public class JsonRedisClient extends AbstractBaseRedisDao
{
  private JsonRedisSeriaziler jsonRedisSeriaziler;

  public JsonRedisSeriaziler getJsonRedisSeriaziler()
  {
    return this.jsonRedisSeriaziler;
  }

  public void setJsonRedisSeriaziler(JsonRedisSeriaziler jsonRedisSeriaziler) {
    this.jsonRedisSeriaziler = jsonRedisSeriaziler;
  }

  public void setValue(String key, Object value)
  {
    ValueOperations operations = this.redisTemplate.opsForValue();
    operations.set(key, this.jsonRedisSeriaziler.seriazileAsString(value));
  }

  public <T> T getValue(String key, Class<T> c)
  {
    ValueOperations operations = this.redisTemplate.opsForValue();
    String json = (String)operations.get(key);
    return this.jsonRedisSeriaziler.deserializeAsObject(json, c);
  }

  public boolean set(String key, Object value, Long expireTime)
  {
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

  public void remove(String key)
  {
    if ((StringUtils.isNotBlank(key)) && (this.redisTemplate.hasKey(key).booleanValue()))
      this.redisTemplate.delete(key);
  }
}
