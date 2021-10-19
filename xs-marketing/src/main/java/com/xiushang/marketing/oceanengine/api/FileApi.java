package com.xiushang.marketing.oceanengine.api;

import com.xiushang.marketing.oceanengine.api.bean.file.*;
import com.xiushang.marketing.oceanengine.support.HttpMethod;
import com.xiushang.marketing.oceanengine.support.OceanEngineResource;
import com.xiushang.marketing.oceanengine.support.UrlConst;


public class FileApi extends OceanEngineResource {

    public static ImageUploadResponse uploadCreativeImage(String token, ImageUploadRequest request) throws Exception {
        return null;
    }

    public static ImageUploadResponse uploadAdvertiserImage(String token, ImageUploadRequest request) throws Exception {
        return null;
    }


    public static VideoUploadResponse uploadVideo(String token, VideoUploadRequest request) throws Exception {
        return null;
    }


    public static ImageQueryResponse queryImage(String token, ImageQueryRequest request) {
        String url = UrlConst.DMP_FILE_IMG_GET_URL + "?" + request.toQueryString();
        return execute(HttpMethod.GET, url, "", ImageQueryResponse.class, token);
    }

    public static VideoQueryResponse queryVideo(String token, VideoQueryRequest request) {
        String url = UrlConst.DMP_FILE_VIDEO_GET_URL + "?" + request.toQueryString();
        return execute(HttpMethod.GET, url, "", VideoQueryResponse.class, token);
    }

    public static VideoCoverImgResponse queryVideoCoverImg(String token, VideoCoverImgRequest request) {
        String url = UrlConst.DMP_FILE_VIDEO_COVER_IMG_URL + "?" + request.toQueryString();
        return execute(HttpMethod.GET, url, "", VideoCoverImgResponse.class, token);
    }

    public static ImageLibResponse queryImgLib(String token, ImageLibRequest request) {
        String url = UrlConst.DMP_FILE_IMG_LIB_GET_URL + "?" + request.toQueryString();
        return execute(HttpMethod.GET, url, "", ImageLibResponse.class, token);
    }

    public static VideoLibResponse queryVideoLib(String token, VideoLibRequest request) {
        String url = UrlConst.DMP_FILE_VIDEO_LIB_GET_URL + "?" + request.toQueryString();
        return execute(HttpMethod.GET, url, "", VideoLibResponse.class, token);
    }
}
