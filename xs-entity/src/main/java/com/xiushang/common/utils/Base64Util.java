package com.xiushang.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

//import sun.misc.BASE64Decoder;


public class Base64Util {

   /* public static String ImageToBase64ByLocal(String imgFile) {
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imgFile);

            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 返回Base64编码过的字节数组字符串
        return Base64Utils.encodeToString(data);
    }*/

    /**
     * base64字符串转换成图片 (对字节数组字符串进行Base64解码并生成图片)
     *
     * @param imgStr      base64字符串
     * @param imgFilePath 指定图片存放路径  （注意：带文件名）
     * @return
     */
    public static boolean Base64ToImage(String imgStr, String imgFilePath) {

        if (StringUtils.isEmpty(imgStr)) // 图像数据为空
            return false;
        OutputStream out = null;
        try {
            //换行符问题
            String sign = imgStr.replaceAll("[\\s*\t\n\r]", "");
            // Base64解码
            byte[] b = org.apache.commons.codec.binary.Base64.decodeBase64(sign);
            //byte[] b = Base64Utils.decodeFromString(imgStr);
            //BASE64Decoder decoder = new BASE64Decoder();
            //byte[] b =  decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }

            out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();

            return true;
        } catch (Exception e) {
            System.out.println("Base64ToImage error!");
            e.printStackTrace();
            return false;
        }finally {
            if(out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


}
