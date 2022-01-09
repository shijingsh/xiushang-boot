package com.xiushang.common.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.xiushang.framework.log.CommonResult;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

public class FastJsonMappingHttpMessageConverter extends AbstractHttpMessageConverter<Object>
        implements GenericHttpMessageConverter<Object> {


    private static final Charset DEFAULT_CHARSET=Charset.forName("UTF-8");

    //fastJson特性参数
    private SerializerFeature[] serializerFeature;
    private FastJsonConfig fastJsonConfig = new FastJsonConfig();

    public FastJsonConfig getFastJsonConfig() {
        return this.fastJsonConfig;
    }

    public void setFastJsonConfig(FastJsonConfig fastJsonConfig) {
        this.fastJsonConfig = fastJsonConfig;
    }

    public FastJsonMappingHttpMessageConverter() {
        super(MediaType.ALL);
    }

    public SerializerFeature[] getSerializerFeature() {
        return serializerFeature;
    }

    public void setSerializerFeature(SerializerFeature[] serializerFeature) {
        this.serializerFeature = serializerFeature;
    }


    @Override
    public boolean canRead(Type type, Class<?> contextClass, MediaType mediaType) {
        return super.canRead(contextClass, mediaType);
    }

    @Override
    public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        InputStream in = inputMessage.getBody();
        return JSON.parseObject(in, this.fastJsonConfig.getCharset(), type, this.fastJsonConfig.getFeatures());
    }

    @Override
    public boolean canWrite(Type type, Class<?> contextClass, MediaType mediaType) {
        return super.canWrite(contextClass, mediaType);
    }

    @Override
    public void write(Object t, Type type, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        HttpHeaders headers = outputMessage.getHeaders();
        if (headers.getContentType() == null) {
            if (contentType == null || contentType.isWildcardType() || contentType.isWildcardSubtype()) {
                contentType = this.getDefaultContentType(t);
            }

            if (contentType != null) {
                headers.setContentType(contentType);
            }
        }

        if (headers.getContentLength() == -1L) {
            Long contentLength = this.getContentLength(t, headers.getContentType());
            if (contentLength != null) {
                headers.setContentLength(contentLength);
            }
        }

        this.writeInternal(t, outputMessage);
        outputMessage.getBody().flush();
    }

    @Override
    protected boolean supports(Class<?> paramClass) {
        return true;
    }

    @Override
    protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        InputStream in = inputMessage.getBody();
        return JSON.parseObject(in, this.fastJsonConfig.getCharset(), clazz, this.fastJsonConfig.getFeatures());
    }

    @Override
    protected void writeInternal(Object paramT,
                                 HttpOutputMessage paramHttpOutputMessage) throws IOException,
            HttpMessageNotWritableException {

        SerializeFilter filter = new FastJsonSimpleFilter(new String[]{"hibernateLazyInitializer"});

        if(paramT instanceof Exception){
            //报错信息，只输出message
            Exception e = (Exception) paramT;
            CommonResult<String> commonResult = CommonResult.error(1,e.getMessage());
            paramT = commonResult;
        }

        String jsonString = JSON.toJSONString(paramT,filter, SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.QuoteFieldNames,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteSlashAsSpecial);
        OutputStream out = paramHttpOutputMessage.getBody();
        out.write(jsonString.getBytes(DEFAULT_CHARSET));
        out.flush();
        out.close();

    }



}
