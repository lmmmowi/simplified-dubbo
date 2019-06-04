package com.lmmmowi.simplified.dubbo.rpc;

/**
 * @Author: mowi
 * @Date: 2019-06-04
 * @Description:
 */
public interface Invoker<T> {

    Class<T> getInterface();

    Result invoke(Invocation invocation) throws RpcException;
}
