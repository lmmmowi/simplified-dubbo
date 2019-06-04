package com.lmmmowi.simplified.dubbo.rpc.injvm;

import com.lmmmowi.simplified.dubbo.common.URL;
import com.lmmmowi.simplified.dubbo.rpc.Exporter;
import com.lmmmowi.simplified.dubbo.rpc.Invoker;
import com.lmmmowi.simplified.dubbo.rpc.RpcException;
import com.lmmmowi.simplified.dubbo.rpc.protocol.AbstractProtocol;

/**
 * @Author: mowi
 * @Date: 2019-06-04
 * @Description:
 */
public class InjvmProtocol extends AbstractProtocol {

    @Override
    public <T> Exporter<T> export(Invoker<T> invoker) throws RpcException {
        return new InjvmExporter<>(invoker);
    }

    @Override
    public <T> Invoker<T> refer(Class<T> type, URL url) throws RpcException {
        return null;
    }
}
