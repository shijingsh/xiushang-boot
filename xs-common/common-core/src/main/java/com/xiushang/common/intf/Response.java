package com.xiushang.common.intf;

public interface Response<T> extends java.io.Serializable {

   int code();

   String message();

   T data() ;

}
