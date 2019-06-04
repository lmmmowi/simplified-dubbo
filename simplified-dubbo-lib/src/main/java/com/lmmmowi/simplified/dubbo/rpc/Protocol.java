package com.lmmmowi.simplified.dubbo.rpc;

import com.lmmmowi.simplified.dubbo.common.URL;

/**
 * @Author: mowi
 * @Date: 2019-06-04
 * @Description:
 */
public interface Protocol {

    <T> Exporter<T> export(Invoker<T> invoker) throws RpcException;

    <T> Invoker<T> refer(Class<T> type, URL url) throws RpcException;
}
