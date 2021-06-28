package com.xiushang.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class JsonUtils
{
  public static final String DATE_Pattern = "yyyy-MM-dd HH:mm:ss";
  private static SerializeConfig _scMapping = new SerializeConfig();

  static
  {
    _scMapping.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
    _scMapping.put(Timestamp.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
  }

  public static String toJsonStr(Object obj)
  {
    String jsonStr = null;

    jsonStr = JSON.toJSONString(obj, _scMapping, new SerializerFeature[0]);

    return jsonStr;
  }

  public static String toJsonStr(Object obj, String datePattern)
  {
    String jsonStr = null;

    if (StringUtils.isBlank(datePattern)) {
      datePattern = "yyyy-MM-dd HH:mm:ss";
    }
    SerializeConfig mapping = new SerializeConfig();
    mapping.put(Date.class, new SimpleDateFormatSerializer(datePattern));
    mapping.put(Timestamp.class, new SimpleDateFormatSerializer(datePattern));

    jsonStr = JSON.toJSONString(obj, mapping, new SerializerFeature[0]);

    return jsonStr;
  }

  public static JSONObject toJSONObject(String json)
    throws Exception
  {
    if (StringUtils.isBlank(json)) {
      throw new Exception("[JsonUtils] -- toJSONObject(M),Parameter(json) can not be null.");
    }

    JSONObject jo = JSONObject.parseObject(json);

    return jo;
  }

  public static JSONArray toJSONArray(String json)
    throws Exception
  {
    if (StringUtils.isBlank(json)) {
      throw new Exception("[JsonUtils] -- toJSONArray(M),Parameter(json) can not be null.");
    }

    JSONArray ja = JSONArray.parseArray(json);

    return ja;
  }

  public static <T> T jsonToBean(String json, Class<T> clazz)
    throws Exception
  {
    if (clazz == null) {
      throw new Exception("[JsonUtils] -- jsonToBean(M),Parameter(clazz) can not be null.");
    }

    Object obj = JSON.parseObject(json, clazz);

    return (T)obj;
  }

  public static <T> List<T> jsonToList(String json, Class<T> clazz)
    throws Exception
  {
    if (clazz == null) {
      throw new Exception("[JsonUtils] -- jsonToList(M),Parameter(clazz) can not be null.");
    }

    List list = JSON.parseArray(json, clazz);

    return list;
  }

  public static Map jsonToMap(String json)
    throws Exception
  {
    return (Map) JSON.parseObject(json, Map.class);
  }
}
