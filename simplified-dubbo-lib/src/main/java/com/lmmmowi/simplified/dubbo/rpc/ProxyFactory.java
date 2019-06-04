package com.lmmmowi.simplified.dubbo.rpc;

import com.lmmmowi.simplified.dubbo.common.URL;

/**
 * @Author: mowi
 * @Date: 2019-06-04
 * @Description:
 */
public interface ProxyFactory {

    <T> T getProxy(Invoker<T> invoker) throws RpcException;

    <T> Invoker<T> getInvoker(T proxy, Class<T> type, URL url) throws RpcException;

}
