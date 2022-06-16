package com.xiushang.common.redis.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.SerializationException;

import java.nio.charset.Charset;

public class JsonRedisSeriaziler
{
  public static final String EMPTY_JSON = "{}";
  public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

  protected ObjectMapper objectMapper = new ObjectMapper();

  public String seriazileAsString(Object object)
  {
    if (object == null)
      return "{}";
    try
    {
      return this.objectMapper.writeValueAsString(object);
    } catch (Exception ex) {
      throw new SerializationException("Could not write JSON: " +
        ex.getMessage(), ex);
    }
  }

  public <T> T deserializeAsObject(String str, Class<T> clazz)
  {
    if ((str == null) || (clazz == null))
      return null;
    try
    {
      return this.objectMapper.readValue(str, clazz);
    } catch (Exception ex) {
      throw new SerializationException("Could not write JSON: " +
        ex.getMessage(), ex);
    }
  }
}
