package com.xiushang.dubbo.filter;


import org.apache.dubbo.rpc.*;

public class UserFilter implements Filter {
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        // before filter ...
        //RpcContext.getContext().setAttachment("index", "1");
        RpcContext.getClientAttachment().setAttachment("getClientAttachment", "1");
        RpcContext.getServerAttachment().setAttachment("getServerAttachment", "1");
        System.out.println(invoker.getInterface().getName() + "=========filter");
        Result result = invoker.invoke(invocation);

        // after filter ...
        return result;
    }
}
