package com.xiushang.dubbo.serialization;


import com.alibaba.com.caucho.hessian.io.Hessian2Output;
import org.apache.dubbo.common.serialize.ObjectOutput;

import java.io.IOException;
import java.io.OutputStream;

public class PersistentObjectOutput implements ObjectOutput{
    private final Hessian2Output mH2o;
    //注意：设置Output的Factory为上面自己定义的序列化工厂类
    public PersistentObjectOutput(OutputStream os) {
        this.mH2o = new Hessian2Output(os);
        this.mH2o.setSerializerFactory(PersistentSerializerFactory.TESTSERIALIZER_FACTORY);
    }

    @Override
    public void writeBool(boolean v) throws IOException {
        this.mH2o.writeBoolean(v);
    }
    @Override
    public void writeByte(byte v) throws IOException {
        this.mH2o.writeInt(v);
    }
    @Override
    public void writeShort(short v) throws IOException {
        this.mH2o.writeInt(v);
    }
    @Override
    public void writeInt(int v) throws IOException {
        this.mH2o.writeInt(v);
    }
    @Override
    public void writeLong(long v) throws IOException {
        this.mH2o.writeLong(v);
    }
    @Override
    public void writeFloat(float v) throws IOException {
        this.mH2o.writeDouble((double)v);
    }
    @Override
    public void writeDouble(double v) throws IOException {
        this.mH2o.writeDouble(v);
    }
    @Override
    public void writeBytes(byte[] b) throws IOException {
        this.mH2o.writeBytes(b);
    }
    @Override
    public void writeBytes(byte[] b, int off, int len) throws IOException {
        this.mH2o.writeBytes(b, off, len);
    }
    @Override
    public void writeUTF(String v) throws IOException {
        this.mH2o.writeString(v);
    }
    @Override
    public void writeObject(Object obj) throws IOException {
        this.mH2o.writeObject(obj);
    }
    @Override
    public void flushBuffer() throws IOException {
        this.mH2o.flushBuffer();
    }
}
