package com.xiushang.marketing.oceanengine.support;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import org.apache.commons.lang3.StringUtils;


public class OceanEngineResource {


    public static <T extends OceanEngineResponse> T execute(HttpMethod httpMethod, String apiUrl, String payLoad,
                                                            Class<T> clazz, String accessToken) throws OceanEngineRestException {
        String response = request(httpMethod, apiUrl, payLoad, accessToken);
        return JSON.parseObject(response, clazz);
    }


    public static String request(HttpMethod httpMethod, String apiUrl, String payLoad, String token) throws OceanEngineRestException {
        try {
            JSONObject jsonObject = JSONObject.parseObject(payLoad);

            Method method = Method.GET;
            if (httpMethod == HttpMethod.POST) {
                method = Method.POST;
            }
            HttpRequest request = HttpUtil.createRequest(method,apiUrl).contentType("application/json")
                    .header("Api-Version", "0.1")
                    .header("User-Agent", "OceanEngine Marketing-api Java SDK");

            if (StringUtils.isNotBlank(token)) {
                request.header("Access-Token", token);
            }

            String responseString =  request.form(jsonObject)//表单内容
                    .timeout(20000)//超时，毫秒
                    .execute().body();;

            return responseString;
        } catch (Exception e) {
            throw new OceanEngineRestException("api request io error, url=" + apiUrl, e);
        }
    }

}
