package com.mg.common.upload.service;

import com.mg.common.upload.vo.UploadBase64;
import com.mg.common.upload.vo.UploadBean;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 图片上传公用类
 */
public interface UploadService {

    List<UploadBean> upload(MultipartHttpServletRequest mulRequest, String userPath);

    Map<String,UploadBean> uploadForMap(MultipartHttpServletRequest mulRequest, String userPath);

    boolean removeFile(String path);

    List<UploadBean> uploadBase64(UploadBase64 uploadBase64);

    UploadBean uploadWithBlock(UploadBean uploadBean,
                                String md5,
                                Long size,
                                Integer chunks,
                                Integer chunk,
                                MultipartFile file) throws IOException;

    File getTargetFile(UploadBean uploadBean);
}
