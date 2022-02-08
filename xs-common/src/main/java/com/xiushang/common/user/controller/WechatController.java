
package com.xiushang.common.user.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.xiushang.common.annotations.XiushangApi;
import com.xiushang.common.service.OauthClientDetailsService;
import com.xiushang.common.upload.service.UploadService;
import com.xiushang.common.upload.vo.UploadBean;
import com.xiushang.common.user.service.UserService;
import com.xiushang.common.user.vo.QrCodeVo;
import com.xiushang.common.utils.HttpClientUtil;
import com.xiushang.entity.oauth.OauthClientDetailsEntity;
import com.xiushang.framework.log.CommonResult;
import com.xiushang.framework.sys.PropertyConfigurer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Api(tags = "微信接口")
@Controller
@ApiSort(value = 3)
@RequestMapping(value = "/api/wx",
        produces = "application/json; charset=UTF-8")
public class WechatController {

    private static Logger logger = LoggerFactory.getLogger(WechatController.class);
    @Autowired
    private HttpServletRequest req;
    @Autowired
    private UploadService uploadService;

    @Autowired
    private UserService userService;
    @Autowired
    private OauthClientDetailsService clientDetailsService;

    private  String getTicket(String access_token) {

        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+ access_token +"&type=jsapi";

        String json = HttpClientUtil.sendGetRequest(url);
        System.out.println("getTicket返回："+json);
        JSONObject jsonObject = JSON.parseObject(json);
        String errcode = jsonObject.getString("errcode");
        if ("0".equals(errcode)) {
            String ticket = jsonObject.getString("ticket");
            return ticket;
        }

        return null;
    }

    private String SHA1(String decript) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @ApiOperation("微信网页分享")
    @ResponseBody
    @GetMapping("/config")
    @XiushangApi
    public CommonResult config() {
        String grant_type = req.getParameter("grant_type");
        String appid = req.getParameter("appid");
        String secret = req.getParameter("secret");
        if(StringUtils.isBlank(appid)){
            appid = PropertyConfigurer.getConfig("weixin.appid");
        }
        if(StringUtils.isBlank(secret)) {
            secret = PropertyConfigurer.getConfig("weixin.secret");
        }
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type="+grant_type+"&appid="+appid+"&secret="+secret;

        String json = HttpClientUtil.sendGetRequest(url);
        //获取AccessToken
        String accessToken = "";
        JSONObject jsonObject = JSON.parseObject(json);
        String errcode = jsonObject.getString("errcode");
        if (StringUtils.isBlank(errcode)) {
            accessToken = jsonObject.getString("access_token");
        }
        //获取Ticket
        String ticket = getTicket(accessToken);

        //时间戳和随机字符串
        String nonceStr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);//随机字符串
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳

        System.out.println("accessToken:"+accessToken+"\njsapi_ticket:"+ticket+"\n时间戳："+timestamp+"\n随机字符串："+nonceStr);

        //将参数排序并拼接字符串
        String str = "jsapi_ticket="+ticket+"&noncestr="+nonceStr+"&timestamp="+timestamp+"&url="+url;

        //将字符串进行sha1加密
        String signature =SHA1(str);
        System.out.println("参数："+str+"\n签名："+signature);

        Map<String,String> map = new HashMap<>();
        map.put("appId",appid);
        map.put("timestamp",timestamp);
        map.put("accessToken",accessToken);
        map.put("ticket",ticket);
        map.put("nonceStr",nonceStr);
        map.put("signature",signature);

        return CommonResult.success(map);
    }

    @ApiOperation("微信小程序：获取当期页面的分享二维码")
    @ResponseBody
    @PostMapping("/miniQrCodeByPage")
    @XiushangApi
    public CommonResult<String> miniQrCodeByPage(@RequestBody QrCodeVo qrCodeVo) {

        String grant_type = "client_credential";

        String clientId = userService.getCurrentClientId();
        OauthClientDetailsEntity clientDetailsEntity = clientDetailsService.findByClientId(clientId);

        String appid = clientDetailsEntity.getAppId();
        String secret = clientDetailsEntity.getSecret();

        try{

            String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type="+grant_type+"&appid="+appid+"&secret="+secret;

            String json = HttpClientUtil.sendGetRequest(url);
            //获取AccessToken
            String accessToken = "";
            JSONObject jsonObject = JSON.parseObject(json);
            String errcode = jsonObject.getString("errcode");
            if (StringUtils.isBlank(errcode)) {
                accessToken = jsonObject.getString("access_token");
            }

            //保存文件到服务器
            UploadBean uploadBean = new UploadBean();
            uploadBean.setUserPath("miniQrCode");
            File file = uploadService.getTargetFile(uploadBean);

            String str = String.valueOf(Math.round(Math.random() * 1000000));
            String name = new StringBuilder("mg").append(new Date().getTime()).append(str).append(".png").toString();

            StringBuffer sb = new StringBuffer(file.getPath()).append('/').append(name);

            String page = qrCodeVo.getPage();
            String params = qrCodeVo.getParams();
            String path = sb.toString();

            downloadMiniQrCode(page,path, params,accessToken);
            logger.info("page："+page);
            logger.info("params："+params);
            logger.info("relativePath："+uploadBean.getRelativePath()+name);
            logger.info("file path : {}", path);
            return CommonResult.success(uploadBean.getRelativePath()+name);
        }catch (Exception ex){
            ex.printStackTrace();
        }


        return CommonResult.error(1000, null);
    }

    public File downloadMiniQrCode(String path, String filePath, String params, String accessToken) {
        OutputStream os = null;
        try{

            URL url = new URL("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+accessToken);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);     // 连接超时 单位毫秒
            httpURLConnection.setReadTimeout(2000);         // 读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);            // 打开写入属性
            httpURLConnection.setDoInput(true);             // 打开读取属性
            httpURLConnection.setRequestMethod("POST");     // 提交方式
            //  不得不说一下这个提交方式转换！！真的坑。。改了好长时间！！一定要记得加响应头
            httpURLConnection.setRequestProperty("Content-Type", "application/x-javascript; charset=UTF-8");
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream()); // 获取URLConnection对象对应的输出流
            // 发送请求参数
            JSONObject paramJson = new JSONObject();
            paramJson.put("scene", params);    // 你要放的内容
            paramJson.put("page", path);              // 跳转小程序地址
            paramJson.put("width", 430);        // 宽度
            paramJson.put("auto_color", true);
            paramJson.put("is_hyaline", true);


            printWriter.write(paramJson.toString());
            // flush输出流的缓冲
            printWriter.flush();
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
            //创建一个空文件
            File file = new File(filePath);
            file.setReadable(true,false);
            file.setWritable(true,false);
            file.setExecutable(true,false);

            os =  new FileOutputStream(file);
            int len;
            byte[] arr = new byte[1024];
            while ((len = bis.read(arr)) != -1) {
                os.write(arr, 0, len);
                os.flush();
            }

            return file;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (os!=null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


}
