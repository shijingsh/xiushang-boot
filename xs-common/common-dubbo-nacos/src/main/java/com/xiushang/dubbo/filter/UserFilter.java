package com.xiushang.dubbo.filter;


import com.xiushang.framework.utils.UserHolder;
import com.xiushang.security.SecurityUser;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import com.xiushang.framework.log.*;
import org.apache.dubbo.rpc.cluster.filter.ClusterFilter;

@Activate(group = CommonConstants.CONSUMER)
public class UserFilter implements ClusterFilter {
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        // before filter ...
        //RpcContext.getContext().setAttachment("index", "1");
        //RpcContext.getClientAttachment().setAttachment("getClientAttachment", "1");
        //RpcContext.getServerAttachment().setAttachment("getServerAttachment", "1");
        //System.out.println(invoker.getInterface().getName() + "=========filter");

        SecurityUser user = UserHolder.get();
        // dubbo 上下文中 找USER对象
        if(user==null){
            Object userObj =  RpcContext.getClientAttachment().getObjectAttachment(SecurityConstants.USER_KEY);
            if (userObj instanceof SecurityUser) {
                user = (SecurityUser) userObj;
            }
        }

        if(user!=null){
            RpcContext.getClientAttachment().setObjectAttachment(SecurityConstants.USER_KEY,user);
        }

        Result result = invoker.invoke(invocation);

        // after filter ...
        return result;
    }
}
