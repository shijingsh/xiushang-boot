package com.mg.common.components;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.mg.common.entity.SmsCodeEntity;
import com.mg.common.user.service.SmsCodeService;
import com.mg.framework.sys.PropertyConfigurer;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

/**
 * 短信服务类
 */
@Service
public class SmsService {
	protected final Logger logger = Logger.getLogger("SmsMsgLog");

	//产品名称:云通信短信API产品,开发者无需替换
	static final String product = "Dysmsapi";
	//产品域名,开发者无需替换
	static final String domain = "dysmsapi.aliyuncs.com";

	@Autowired
	private SmsCodeService smsCodeService;

	public static SendSmsResponse sendSmsAliyun(String mobile, String templateParam,String templateCode) throws ClientException {

		//可自助调整超时时间
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");

		//模板代码
		String accessKeyId = PropertyConfigurer.getConfig("sms.accessKeyId");
		String accessKeySecret = PropertyConfigurer.getConfig("sms.accessKeySecret");
		String signName = PropertyConfigurer.getConfig("sms.signName");
		//初始化acsClient,暂不支持region化
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		IAcsClient acsClient = new DefaultAcsClient(profile);

		//组装请求对象-具体描述见控制台-文档部分内容
		SendSmsRequest request = new SendSmsRequest();
		//必填:待发送手机号
		request.setPhoneNumbers(mobile);
		//必填:短信签名-可在短信控制台中找到
		if(StringUtils.isNotBlank(signName)){
			request.setSignName(signName);
		}else{
			request.setSignName("秀上");
		}
		//必填:短信模板-可在短信控制台中找到
		request.setTemplateCode(templateCode);
		//可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		request.setTemplateParam(templateParam);

		//选填-上行短信扩展码(无特殊需求用户请忽略此字段)
		//request.setSmsUpExtendCode("90997");

		//可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		request.setOutId("yourOutId");

		//hint 此处可能会抛出异常，注意catch
		SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

		return sendSmsResponse;
	}

	public int sendSmsAliyunForVerificationCode(String mobile, String randomStr) throws Exception {
		//模板代码
		String templateParam = "{\"code\":\""+randomStr+"\"}";
		String templateCode = PropertyConfigurer.getConfig("sms.templateCode");
		SendSmsResponse response = sendSmsAliyun(mobile,templateParam,templateCode);
		if(response.getCode() != null && response.getCode().equals("OK")) {
			return 1;
		}

		return 0;
	}
	public int sendSms(String mobile, String templateParam,String templateCode) throws Exception {
		SendSmsResponse response = sendSmsAliyun(mobile,templateParam,templateCode);
		logger.info("=======================================");
		logger.info("code:"+response.getCode());
		logger.info("message:"+response.getMessage());
		logger.info("bizId:"+response.getBizId());
		logger.info("requestId:"+response.getRequestId());
		if(response.getCode() != null && response.getCode().equals("OK")) {
			return 1;
		}

		return 0;
	}

	public  boolean validateCode(String mobile,String code) {
		SmsCodeEntity smsCodeEntity = smsCodeService.findByMobileAndSmsCode(mobile,code);
		return smsCodeEntity!=null;
	}
}
