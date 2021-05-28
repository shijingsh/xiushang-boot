package com.mg.framework.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class WebUtil {
    private static Logger logger = LoggerFactory.getLogger(WebUtil.class);

    /**
     * 从requst中获取json数据
     * 请求类型：POST
     * @param request
     * @return json格式数据
     * @throws IOException
     */
    public static String getJsonBody(HttpServletRequest request) {
        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            logger.warn("从request里读取数据流错误", ex.toString());
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    logger.warn("关闭request读取数据流错误", ex.toString());
                }
            }
        }

        return stringBuilder.toString();
    }

    public static <T> T  getJsonBody(HttpServletRequest request,Class<T> clazz) {
        String jsonString = getJsonBody(request);
        if(StringUtils.isNotBlank(jsonString)){
            return JSON.parseObject(jsonString,clazz);
        }
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 请求参数转换成对应bean
     * 请求类型：GET
     * @param request
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T  getRequstBody(HttpServletRequest request,Class<T> clazz) {
        T bean;
        try {
            bean = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        ConvertUtils.register(new Converter(){
            public Object convert(Class arg0, Object arg1){
                if(arg1 == null){
                    return null;
                }
                if(!(arg1 instanceof String)){
                    throw new ConversionException("只支持字符串转换 !");
                }
                String str = (String)arg1;
                if(StringUtils.isBlank(str)){
                    return null;
                }
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                try{
                    return sd.parse(str);
                }
                catch(ParseException e){
                    throw new RuntimeException(e);
                }
            }
        }, java.util.Date.class);
        try {
            BeanUtils.populate(bean,request.getParameterMap());
            return bean;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
