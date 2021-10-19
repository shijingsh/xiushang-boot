package com.xiushang.marketing.oceanengine.api;

import com.alibaba.fastjson.JSON;
import com.xiushang.marketing.oceanengine.api.bean.itunes.ITunesAppInfo;
import com.xiushang.marketing.oceanengine.support.HttpMethod;
import com.xiushang.marketing.oceanengine.support.OceanEngineResource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
public class ItunesApi extends OceanEngineResource {
    static String ITUNES_LOOKUP_URL = "https://itunes.apple.com/cn/lookup?id=%s&entity=album";

    public static ITunesAppInfo lookup(String appUrl) {
        String[] ids = appUrl.split("id");
        String[] split = ids[1].split("\\?");
        String url = String.format(ITUNES_LOOKUP_URL, split[0]);

        String appInfoString = request(HttpMethod.GET, url, null, null);
        return JSON.parseObject(appInfoString, ITunesAppInfo.class);
    }

}
