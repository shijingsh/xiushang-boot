package com.mg.common.upload.controller;

import com.mg.common.upload.service.UploadService;
import com.mg.common.upload.vo.UploadBase64;
import com.mg.common.upload.vo.UploadBean;

import com.mg.framework.log.CommonResult;
import com.mg.framework.utils.WebUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * 上传文件
 * @author liukefu
 */
@Api(tags = "文件上传")
@Controller
@RequestMapping(value = "/",
        produces = "application/json; charset=UTF-8")
public class UploadController{
    @Autowired
    private UploadService uploadService;
    /**
     * 上传文件
     * @param request
     * @param userPath  在temp 下自定义一个目录
     * @return
     */
    @ApiOperation(value = "上传文件")
    @ResponseBody
    @RequestMapping("/upload")
    public CommonResult<List<UploadBean>> upload(HttpServletRequest request,String userPath) {

        MultipartHttpServletRequest mulRequest = (MultipartHttpServletRequest) (request);

        List<UploadBean> list = uploadService.upload(mulRequest,userPath);

        return CommonResult.success(list);
    }

    /**
     * 上传base64格式的文件
     * @param uploadBase64
     * @return
     */
    @ApiOperation(value = "上传文件base64")
    @ResponseBody
    @RequestMapping("/uploadBase64")
    public CommonResult<List<UploadBean>> uploadBase64(@RequestBody UploadBase64 uploadBase64) {
        List<UploadBean> list = uploadService.uploadBase64(uploadBase64);

        return CommonResult.success(list);
    }

    @ResponseBody
    @RequestMapping("/uploadBig")
    @ApiOperation(value = "上传大文件")
    public CommonResult uploadBig(HttpServletRequest request,String userPath,String md5,
                            Long size,
                            Integer chunks,
                            Integer chunk) {

        MultipartHttpServletRequest mulRequest = (MultipartHttpServletRequest) (request);

        Map<String, MultipartFile> fileMap = mulRequest.getFileMap();

        Iterator<String> it = fileMap.keySet().iterator();
        UploadBean uploadBean = new UploadBean();
        if (it.hasNext()) {
            String key = it.next();
            uploadBean.setKey(key);
            uploadBean.setUserPath(userPath);
            MultipartFile multipartFile = fileMap.get(key);

            try {
                uploadService.uploadWithBlock(uploadBean, md5,
                       size,
                       chunks,
                       chunk,multipartFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return CommonResult.success(uploadBean);
    }
}
