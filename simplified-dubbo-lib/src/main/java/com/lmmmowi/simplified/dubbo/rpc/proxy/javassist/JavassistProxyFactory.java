package com.lmmmowi.simplified.dubbo.rpc.proxy.javassist;

import com.lmmmowi.simplified.dubbo.common.URL;
import com.lmmmowi.simplified.dubbo.rpc.Invoker;
import com.lmmmowi.simplified.dubbo.rpc.RpcException;
import com.lmmmowi.simplified.dubbo.rpc.injvm.InvjmInvoker;
import com.lmmmowi.simplified.dubbo.rpc.proxy.AbstractProxyFactory;

/**
 * @Author: mowi
 * @Date: 2019-06-04
 * @Description:
 */
public class JavassistProxyFactory extends AbstractProxyFactory {

    @Override
    public <T> T getProxy(Invoker<T> invoker) throws RpcException {
        return null;
    }

    @Override
    public <T> Invoker<T> getInvoker(T proxy, Class<T> type, URL url) throws RpcException {
        return new InvjmInvoker<>(type);
    }
}
