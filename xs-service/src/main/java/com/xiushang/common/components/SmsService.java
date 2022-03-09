package com.xiushang.common.components;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.xiushang.common.user.service.SmsCodeService;
import com.xiushang.common.user.service.SystemParamService;
import com.xiushang.common.user.vo.SmsVo;
import com.xiushang.common.utils.BaseServiceImpl;
import com.xiushang.common.utils.LazyLoadUtil;
import com.xiushang.entity.QSmsCodeEntity;
import com.xiushang.entity.QSystemParamEntity;
import com.xiushang.entity.SmsCodeEntity;
import com.xiushang.entity.SystemParamEntity;
import com.xiushang.framework.entity.vo.PageTableVO;
import com.xiushang.framework.entity.vo.SearchPageVo;
import com.xiushang.framework.sys.PropertyConfigurer;
import com.xiushang.framework.utils.DeleteEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 短信服务类
 */
@Service
@Slf4j
public class SmsService extends BaseServiceImpl<SmsCodeEntity> {

	//产品名称:云通信短信API产品,开发者无需替换
	static final String product = "Dysmsapi";
	//产品域名,开发者无需替换
	static final String domain = "dysmsapi.aliyuncs.com";
	@Autowired
	private SystemParamService paramService;

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

	@Transactional
	public int sendSmsAliyunForVerificationCode(SmsVo smsVo) throws Exception {

		SendSmsResponse response = sendSmsAliyun(smsVo.getMobile(),smsVo.getTemplateParam(),smsVo.getTemplateCode());

		//保存短信验证码
		saveSmsLog(smsVo,response);

		if(response.getCode() != null && response.getCode().equals("OK")) {
			return 1;
		}

		return 0;
	}

	@Transactional
	public int sendSms(SmsVo smsVo) throws Exception {
		String shopId = smsVo.getShopId();
		if(StringUtils.isNotBlank(shopId)) {
			//判断商铺短信开关 0 关闭  1 打开
			SystemParamEntity param = paramService.getOrSaveParam("短信开关","1","商铺短信开关 0 关闭  1 打开");
			if (param != null) {
				if("0".equals(param.getParamValue())){
					log.info("shopId："+shopId+" 短信功能已关闭，未发送短信");
					return 0;
				}
			}
		}

		SendSmsResponse response = sendSmsAliyun(smsVo.getMobile(),smsVo.getTemplateParam(),smsVo.getTemplateCode());
		//保存短信日志
		saveSmsLog(smsVo,response);
		if(response.getCode() != null && response.getCode().equals("OK")) {
			return 1;
		}

		return 0;
	}

	public  boolean validateCode(String mobile,String code) {
		SmsCodeEntity smsCodeEntity = smsCodeService.findByMobileAndSmsCode(mobile,code);
		return smsCodeEntity!=null;
	}

	private void saveSmsLog(SmsVo smsVo,SendSmsResponse response ){
		log.info("=======================================");
		log.info("code:"+response.getCode());
		log.info("message:"+response.getMessage());
		log.info("bizId:"+response.getBizId());
		log.info("requestId:"+response.getRequestId());
		//保存短信记录
		SmsCodeEntity smsCodeEntity = new SmsCodeEntity();
		smsCodeEntity.setMobile(smsVo.getMobile());
		smsCodeEntity.setSmsCode(smsVo.getSmsCode());
		smsCodeEntity.setTemplateCode(smsVo.getTemplateCode());
		smsCodeEntity.setTemplateParam(smsVo.getTemplateParam());
		smsCodeEntity.setShopId(smsVo.getShopId());
		smsCodeEntity.setSystemFlag(smsVo.getSystemFlag());
		smsCodeEntity.setSendTime(new Date());

		smsCodeEntity.setRequestId(response.getRequestId());
		smsCodeEntity.setBizId(response.getBizId());
		smsCodeEntity.setCode(response.getCode());
		smsCodeEntity.setMessage(response.getMessage());
		smsCodeService.save(smsCodeEntity);
	}


	public PageTableVO findPageList(SearchPageVo searchVo) {

		QSmsCodeEntity entity = QSmsCodeEntity.smsCodeEntity;
		BooleanExpression ex = entity.deleted.eq(DeleteEnum.VALID);

		if(StringUtils.isNotBlank(searchVo.getSearchKey())){
			ex = ex.and(entity.mobile.eq(searchVo.getSearchKey()));
		}
		Page<SmsCodeEntity> page = findPageList(ex,searchVo.createPageRequest(new Sort.Order(Sort.Direction.DESC,"sendTime")));
		LazyLoadUtil.fullLoad(page);
		PageTableVO vo = new PageTableVO(page,searchVo);

		return vo;
	}
}
