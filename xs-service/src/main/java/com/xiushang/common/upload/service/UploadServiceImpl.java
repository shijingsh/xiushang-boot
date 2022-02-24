package com.xiushang.common.upload.service;

import com.xiushang.common.upload.utils.ChunkedUploadUtils;
import com.xiushang.common.upload.vo.UploadBase64;
import com.xiushang.common.upload.vo.UploadBean;
import com.xiushang.common.utils.Base64Util;
import com.xiushang.common.utils.FtpUtils;
import com.xiushang.common.utils.JsonUtils;
import com.xiushang.framework.sys.PropertyConfigurer;
import com.xiushang.framework.utils.UserHolder;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * 图片上传公用类
 * Created by liukefu on 2016/12/17.
 */
@Service
public class UploadServiceImpl implements UploadService {
    private static Logger logger = LoggerFactory.getLogger(UploadServiceImpl.class);
    private static final char separator = '/';

    public List<UploadBean> upload(MultipartHttpServletRequest mulRequest, String userPath) {
        logger.info("上传文件开始...");
        List<UploadBean> list = new ArrayList<>();
        Map<String, MultipartFile> fileMap = mulRequest.getFileMap();
        Iterator<String> it = fileMap.keySet().iterator();
        logger.info("文件个数："+fileMap.size());
        logger.info("保存路径："+userPath);
        try{
            while (it.hasNext()) {
                UploadBean uploadBean = new UploadBean();
                uploadBean.setUserPath(userPath);

                //保存文件到服务器
                File file = getTargetFile(uploadBean);
                String key = it.next();
                uploadBean.setKey(key);
                MultipartFile multipartFile = fileMap.get(key);
                if (!multipartFile.isEmpty()) {
                    String originalFilename = multipartFile.getOriginalFilename();// IMG_161423328450980_compressed.jpg
                    if(StringUtils.isNotBlank(originalFilename)){
                        originalFilename = originalFilename.replaceAll("_compressed","");
                    }
                    uploadBean.setSourceFileName(originalFilename);
                    ftpUpload(uploadBean,file,multipartFile);
                }

                //返回文件路径
                uploadBean.setRelativePath(uploadBean.getRelativePath() + uploadBean.getFileName());

                logger.info("fileName："+uploadBean.getFileName());
                logger.info("relativePath："+uploadBean.getRelativePath());
                logger.info("path："+uploadBean.getPath());

                list.add(uploadBean);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        logger.info("upload result:");
        logger.info(JsonUtils.toJsonStr(list));
        return list;
    }

    public Map<String,UploadBean> uploadForMap(MultipartHttpServletRequest mulRequest, String userPath) {

        Map<String,UploadBean> map = new HashMap<>();
        Map<String, MultipartFile> fileMap = mulRequest.getFileMap();
        Iterator<String> it = fileMap.keySet().iterator();
        while (it.hasNext()) {
            UploadBean uploadBean = new UploadBean();
            uploadBean.setUserPath(userPath);

            //保存文件到服务器
            File file = getTargetFile(uploadBean);
            String key = it.next();
            uploadBean.setKey(key);
            MultipartFile multipartFile = fileMap.get(key);
            if (!multipartFile.isEmpty()) {
                ftpUpload(uploadBean,file,multipartFile);
            }

            //返回文件路径
            uploadBean.setRelativePath(uploadBean.getRelativePath() + uploadBean.getFileName());

            map.put(key,uploadBean);
        }

        return map;
    }

    private String getNewFileName(File file, MultipartFile item) {
        StringBuffer sb = new StringBuffer();
        String str = String.valueOf(Math.round(Math.random() * 1000000));
        sb.append("mg").append(new Date().getTime()).append(str);
        sb.append(item.getOriginalFilename().substring(item.getOriginalFilename().lastIndexOf(".")));
        return sb.toString();
    }

    public File getTargetFile(UploadBean uploadBean) {
        String path = getTargetFilePath(uploadBean);
        File file = new File(path);
        file.mkdirs();

        return file;
    }

    public String getTargetFilePath(UploadBean uploadBean) {
        String instanceId = UserHolder.getTenantId();
        String rootPath = "null";
        if (StringUtils.isNotBlank(instanceId)) {
            rootPath = rootPath + separator +  instanceId;
        }
        String savePath = separator + rootPath + separator;
        if (StringUtils.isNotBlank(uploadBean.getUserPath())) {
            savePath = savePath + uploadBean.getUserPath() + separator  + getDatePath();
        }else{
            savePath = savePath + getDatePath();
        }
        uploadBean.setRelativePath(savePath);
        savePath = PropertyConfigurer.getContextProperty("file.temppath") + savePath;


        return savePath;
    }

    private String getDatePath(){
        Date date = new Date();
        String todayY = DateFormatUtils.format(date, "yyyy");
        String todayM = DateFormatUtils.format(date, "MM");
        String today = DateFormatUtils.format(date, "dd");

        return todayY + separator + todayM + separator + today + separator;
    }

    public boolean removeFile(String path) {

        if(StringUtils.isNotBlank(path) || path.startsWith("http")){
            return false;
        }
        String home = (String) PropertyConfigurer.getContextProperty("file.temppath");
        File file = new File(home + path);
        file.deleteOnExit();
        return true;
    }

    public List<UploadBean> uploadBase64(UploadBase64 uploadBase64) {
        List<UploadBean> list = new ArrayList<>();
        UploadBean uploadBean = new UploadBean();
        uploadBean.setUserPath(uploadBase64.getUserPath());

        //保存文件到服务器
        File file = getTargetFile(uploadBean);
        String key = uploadBase64.getKey();
        uploadBean.setKey(key);

        String str = String.valueOf(Math.round(Math.random() * 1000000));
        String name = new StringBuilder("mg").append(new Date().getTime()).append(str).append(".jpg").toString();

        StringBuffer sb = new StringBuffer(file.getPath()).append(separator).append(name);
        logger.info("上传文件:"+sb.toString());
        try {
            File f = new File(sb.toString());
            logger.info("设置上传文件权限");
            f.setReadable(true,false);
            f.setWritable(true,false);
            f.setExecutable(true,false);

            boolean b = Base64Util.Base64ToImage(uploadBase64.getImgStr(),f.getAbsolutePath());
            logger.info("base64转文件格式成功标志："+b);
            logger.info("absolutePath："+f.getAbsolutePath());

            String enableFtp = PropertyConfigurer.getConfig("ftp.enable");
            if("1".equals(enableFtp)){
                logger.info("启用了FTP上传");
                FtpUtils ftp =new FtpUtils();
                ftp.uploadFile(uploadBean.getRelativePath(), name, new FileInputStream(f));
            }

            uploadBean.setFileName(f.getName());
            uploadBean.setPath(f.getPath());
            logger.info("relativePath："+uploadBean.getRelativePath());
            logger.info("file path : {}", file.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //返回文件路径
        uploadBean.setRelativePath(uploadBean.getRelativePath() + uploadBean.getFileName());

        list.add(uploadBean);

        logger.info("upload result:");
        logger.info(JsonUtils.toJsonStr(list));
        return list;
    }

    public void ftpUpload(UploadBean uploadBean,File file,MultipartFile multipartFile){
        try {
            String enableFtp = PropertyConfigurer.getConfig("ftp.enable");
            try {
                if("1".equals(enableFtp) || "true".equalsIgnoreCase(enableFtp)){
                    String name = getNewFileName(file, multipartFile);
                    uploadBean.setFileName(name);
                    logger.info("启用了FTP上传");
                    FtpUtils ftp =new FtpUtils();
                    ftp.uploadFile(uploadBean.getRelativePath(), name, multipartFile.getInputStream());
                }else{
                    String name = getNewFileName(file, multipartFile);
                    uploadBean.setFileName(name);
                    StringBuffer sb = new StringBuffer(file.getPath()).append(separator).append(name);
                    logger.info("未启用了FTP上传");
                    File f = new File(sb.toString());
                    logger.info("设置上传文件权限");
                    f.setReadable(true,false);
                    f.setWritable(true,false);
                    f.setExecutable(true,false);
                    multipartFile.transferTo(f);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            //uploadBean.setPath(f.getPath());
            logger.info("file path : {}", file.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UploadBean uploadWithBlock(UploadBean uploadBean,
                                String md5,
                                Long size,
                                Integer chunks,
                                Integer chunk,
                                MultipartFile file) throws IOException {
        String fileName = ChunkedUploadUtils.getFileName(file,md5, chunks);

        String path = getTargetFilePath(uploadBean);
        ChunkedUploadUtils.writeWithBlock(path + fileName, size, file.getInputStream(), file.getSize(), chunks, chunk);
        ChunkedUploadUtils.addChunk(md5,chunk);
        if (ChunkedUploadUtils.isUploaded(md5)) {
            ChunkedUploadUtils.removeKey(md5);
        }

        return uploadBean;
    }
}
