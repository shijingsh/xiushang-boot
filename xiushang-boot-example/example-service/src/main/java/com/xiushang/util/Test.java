package com.xiushang.util;

import com.xiushang.common.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 测试
 * Created by liukefu on 2018/12/27.
 */
@Slf4j
public class Test {

    public static void main(String args[]){

        String key = "91:EC:C5:EC:75:88:EF:64:F4:A6:AC:D0:90:C6:F4:BA";
        log.info(key.replaceAll(":","").toLowerCase());

        Date dateStart = DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss","2021-05-08 23:59:59");
        Date dateEnd = DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss","2021-05-09 23:59:59");

        long dateStartInt1 = dateStart.getTime() - new Date().getTime();
        long dateEndInt1 = dateEnd.getTime() - new Date().getTime();

       // long dateStartInt2 = s1.getStart().getTime() - new Date().getTime();
       // long dateEndInt2 = s1.getEnd().getTime() - new Date().getTime();
        // 3 进行中 2 未开始 1结束
        int status = 0;
        if(dateEndInt1<=0){
            status = 1;
        }
        if(dateStartInt1>0){
            status = 2;
        }
        if(dateStartInt1<=0 && dateEndInt1>0){
             status = 3;
        }

        System.out.println("status:"+status);

    }
}
