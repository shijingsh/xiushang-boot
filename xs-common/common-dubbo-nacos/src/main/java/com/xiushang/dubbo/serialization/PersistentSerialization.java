package com.xiushang.dubbo.serialization;


import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.serialize.ObjectInput;
import org.apache.dubbo.common.serialize.ObjectOutput;
import org.apache.dubbo.common.serialize.Serialization;
import org.apache.dubbo.common.serialize.hessian2.Hessian2ObjectInput;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PersistentSerialization implements Serialization {
    @Override
    //注意，由于dubbo有很多序列化方式的扩展，每种方式有自己的id,自定义id时注意冲突，重复时会有ERROR提示
    public byte getContentTypeId() {
        return 22;
    }
    @Override
    public String getContentType() {
        return "persistent";
    }
    @Override
    public ObjectOutput serialize(URL url, OutputStream out) throws IOException {
        return new PersistentObjectOutput(out);
    }
    @Override
    public ObjectInput deserialize(URL url, InputStream is) throws IOException {
        return new Hessian2ObjectInput(is);
    }
}
