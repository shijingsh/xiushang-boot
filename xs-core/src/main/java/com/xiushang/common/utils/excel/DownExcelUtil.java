package com.xiushang.common.utils.excel;

import com.xiushang.framework.sys.PropertyConfigurer;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class DownExcelUtil {

    /**
     * 下载文件
     * @param path 系统完整路径
     * @param fileName  文件名称
     * @param response
     */
    public static void down(String path, String fileName, HttpServletResponse response){
        File file = new File(path);
        InputStream fis = null;
        OutputStream os = null;
        try {
            fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            response.reset();
            // 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
            response.addHeader("Content-Disposition", "attachment;filename=" + toUtf8String(fileName));
            response.addHeader("Content-Length", "" + file.length());
            os = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            // 输出文件
            os.write(buffer);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(os!=null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void downFromTempPath(String filePath, String fileName, HttpServletResponse response){
        String path = PropertyConfigurer.getContextProperty("file.temppath")+filePath;
        File file = new File(path);
        InputStream fis = null;
        OutputStream os = null;
        try {
            fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            response.reset();
            // 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
            response.addHeader("Content-Disposition", "attachment;filename=" + toUtf8String(fileName));
            response.addHeader("Content-Length", "" + file.length());
            os = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            // 输出文件
            os.write(buffer);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(os!=null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public static String toUtf8String(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = Character.toString(c).getBytes("utf-8");
                } catch (Exception ex) {
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0) k += 256;
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }
}
