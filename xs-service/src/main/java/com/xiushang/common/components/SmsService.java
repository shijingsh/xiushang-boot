package com.xiushang.common.components;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.xiushang.common.sms.SmsAliyunService;
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

	@Autowired
	private SystemParamService paramService;

	@Autowired
	private SmsCodeService smsCodeService;

	@Autowired
	private SmsAliyunService smsAliyunService;

	@Transactional
	public int sendSmsAliyunForVerificationCode(SmsVo smsVo) throws Exception {

		SendSmsResponse response = smsAliyunService.sendSms(smsVo.getMobile(),smsVo.getTemplateParam(),smsVo.getTemplateCode());

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

		SendSmsResponse response = smsAliyunService.sendSms(smsVo.getMobile(),smsVo.getTemplateParam(),smsVo.getTemplateCode());
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
