package com.mg.pay.wx.util;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.mg.framework.sys.PropertyConfigurer;
import org.apache.commons.lang3.StringUtils;

public class WxPayUtils {

    public static WxPayService getWxPayService(){

        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId(StringUtils.trimToNull(PropertyConfigurer.getConfig("wxPay.appId")));
        payConfig.setMchId(StringUtils.trimToNull(PropertyConfigurer.getConfig("wxPay.mchId")));
        payConfig.setMchKey(StringUtils.trimToNull(PropertyConfigurer.getConfig("wxPay.mchKey")));
        payConfig.setSubAppId(StringUtils.trimToNull(PropertyConfigurer.getConfig("wxPay.subAppId")));
        payConfig.setSubMchId(StringUtils.trimToNull(PropertyConfigurer.getConfig("wxPay.subMchId")));
        payConfig.setKeyPath(StringUtils.trimToNull(PropertyConfigurer.getConfig("wxPay.keyPath")));

        // 可以指定是否使用沙箱环境
        payConfig.setUseSandboxEnv(Boolean.parseBoolean(PropertyConfigurer.getConfig("wxPay.useSandboxEnv")));

        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(payConfig);
        return wxPayService;

    }
}
