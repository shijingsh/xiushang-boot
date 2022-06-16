package com.xiushang.common.upload.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.xiushang.common.annotations.XiushangApi;
import com.xiushang.common.upload.service.UploadService;
import com.xiushang.common.upload.vo.UploadBase64;
import com.xiushang.common.upload.vo.UploadBean;

import com.xiushang.framework.log.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * 上传文件
 */
@Api(tags = "文件上传")
@Controller
@ApiSort(value = 4)
@RequestMapping(value = "/api",
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
    @XiushangApi
    @ResponseBody
    @PostMapping("/upload")
    public CommonResult<List<UploadBean>> upload(HttpServletRequest request, @ApiParam(value = "上传根路径 <br />（指定上传文件存放的文件夹）") String userPath) {

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
    @XiushangApi
    @ResponseBody
    @PostMapping("/uploadBase64")
    public CommonResult<List<UploadBean>> uploadBase64(@Valid @RequestBody UploadBase64 uploadBase64) {
        List<UploadBean> list = uploadService.uploadBase64(uploadBase64);

        return CommonResult.success(list);
    }

    @ApiOperation(value = "上传大文件")
    @XiushangApi
    @ResponseBody
    @PostMapping("/uploadBig")
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
