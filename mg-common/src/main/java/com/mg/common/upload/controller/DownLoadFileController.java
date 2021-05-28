package com.mg.common.upload.controller;

import com.mg.common.utils.excel.DownExcelUtil;
import com.mg.framework.sys.PropertyConfigurer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

/**
 * 文件下载接口
 * Created by liukefu on 2015/11/12.
 */
@Api(tags = "文件上传")
@Controller
@RequestMapping(value = "/file",
        produces = "application/json; charset=UTF-8")
public class DownLoadFileController {
    /**
     * 下载
     * @return
     */
    @ApiOperation(value = "下载文件")
    @RequestMapping(value = "/download")
    public void download(String path,HttpServletResponse response) {

        String fileName = String.valueOf(new Date().getTime())+".xls";
        DownExcelUtil.downFromTempPath(path, fileName, response);
    }

    /**
     *
     * @param path
     * @param response
     */
    @ApiOperation(value = "显示文件")
    @RequestMapping(value="showImg")
    public void showImg(String path,HttpServletResponse response)  {
        String rootPath = ""+ PropertyConfigurer.getContextProperty("temppath");
        FileInputStream inputStream = null;
        OutputStream outStream = null;
        try {
            inputStream = new FileInputStream(rootPath+"/"+path);
            int i=inputStream.available(); //得到文件大小
            byte data[]=new byte[i];
            inputStream.read(data);
            response.setContentType("image/*"); //设置返回的文件类型
            outStream=response.getOutputStream(); //得到向客户端输出二进制数据的对象
            outStream.write(data);
            outStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }finally {
            try {
                outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
