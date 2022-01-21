package com.xiushang.validation.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

public class AssertUtils {
    public static boolean notEmpty(Object value,boolean required){

        if(!required){
            return true;
        }
        if(value instanceof  String){
            if (StringUtils.isNotBlank((String)value)) {
                return true;
            }else {
                return false;
            }
        }

        if(value instanceof Map){
            if(value!=null && ((Map)value).size()>0){
                return true;
            }else {
                return false;
            }
        }
        if(value instanceof List){
            if(value!=null && ((List)value).size()>0){
                return true;
            }else {
                return false;
            }
        }


        if(value != null){
            return true;
        }

        return false;
    }
}
