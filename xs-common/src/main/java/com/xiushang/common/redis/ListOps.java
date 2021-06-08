package com.xiushang.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("listOps")
public class ListOps
{

  @Autowired
  private StringRedisTemplate stringRedisTemplate;

  public Long push(String key, String value)
  {
    return this.stringRedisTemplate.opsForList().leftPush(key, value);
  }

  public Long pushObject(String key, Object objectValue)
  {
    ListOperations list = this.stringRedisTemplate.opsForList();
    return list.leftPush(key, objectValue);
  }

  public String pop(String key)
  {
    return (String)this.stringRedisTemplate.opsForList().leftPop(key);
  }

  public Object popObject(String key)
  {
    ListOperations list = this.stringRedisTemplate.opsForList();
    Object object = list.leftPop(key);
    return object;
  }

  public Long in(String key, String value)
  {
    return this.stringRedisTemplate.opsForList().leftPush(key, value);
  }

  public String out(String key)
  {
    return (String)this.stringRedisTemplate.opsForList().rightPop(key);
  }

  public Object outObject(String key)
  {
    return this.stringRedisTemplate.opsForList().rightPop(key);
  }

  public Long length(String key)
  {
    return this.stringRedisTemplate.opsForList().size(key);
  }

  public List<String> range(String key, int start, int end)
  {
    return this.stringRedisTemplate.opsForList().range(key, start, end);
  }

  public List<Object> rangeObject(String key, int start, int end)
  {
    ListOperations listOps = this.stringRedisTemplate.opsForList();
    return listOps.range(key, start, end);
  }

  public void remove(String key, long i, String value)
  {
    this.stringRedisTemplate.opsForList().remove(key, i, value);
  }

  public String index(String key, long index)
  {
    return (String)this.stringRedisTemplate.opsForList().index(key, index);
  }

  public Object indexObject(String key, long index) {
    ListOperations listOps = this.stringRedisTemplate.opsForList();
    return listOps.index(key, index);
  }

  public void set(String key, long index, String value)
  {
    this.stringRedisTemplate.opsForList().set(key, index, value);
  }

  public void trim(String key, long start, int end)
  {
    this.stringRedisTemplate.opsForList().trim(key, start, end);
  }
}
