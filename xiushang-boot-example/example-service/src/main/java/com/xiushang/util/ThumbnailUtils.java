package com.xiushang.util;

import com.xiushang.framework.sys.PropertyConfigurer;
import org.apache.commons.lang3.time.DateFormatUtils;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 * 生产缩略图
 * Created by liukefu on 2018/8/9.
 */
public class ThumbnailUtils {
    private static final char separator = '/';
    /**
     * 转换url:data数据为正常图片
     * @param dataUrl Base64编码的图片
     * @return 返回文件名
     */
    public String getDataUrlPic(String dataUrl){
        StringBuffer sb = new StringBuffer();
        sb.append("mg").append(new Date().getTime()).append(Math.round(Math.random() * 1000000));

        String imgName = sb.toString() + ".png";
        String rootPath = PropertyConfigurer.getContextProperty("temppath")+"";
        String today = DateFormatUtils.format(new Date(), "yyyyMMdd");
        String imgPath = separator + today + separator;

        if(generateImage(dataUrl,imgName,rootPath+imgPath)){
            return imgPath + imgName;
        }
        return "";
    }

    /**
     * 把转换后的图片存放到指定目录
     * @param imgStr dataUrl
     * @param imgName 图片名称
     * @param imgPath 存放路径
     * @return
     */
    public boolean generateImage(String imgStr,String imgName,String imgPath){
        //把“data:image/jpeg;base64”去掉,
        imgStr = imgStr.substring(imgStr.indexOf(",") + 1);
        if (imgStr == null) {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            File headPath = new File(imgPath);
            if (!headPath.exists()) {
                headPath.mkdirs();
            }
            String imgFilePath = imgPath + "/" + imgName;
            File f = new File(imgFilePath);
            if(!f.exists()){
                f.createNewFile();
            }
            f.setReadable(true,false);
            f.setWritable(true,false);

            OutputStream out = new FileOutputStream(f);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
