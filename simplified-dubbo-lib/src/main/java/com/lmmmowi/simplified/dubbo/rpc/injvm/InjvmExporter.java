package com.lmmmowi.simplified.dubbo.rpc.injvm;

import com.lmmmowi.simplified.dubbo.rpc.Invoker;
import com.lmmmowi.simplified.dubbo.rpc.protocol.AbstractExporter;

/**
 * @Author: mowi
 * @Date: 2019-06-04
 * @Description:
 */
public class InjvmExporter<T> extends AbstractExporter<T> {

    public InjvmExporter(Invoker<T> invoker) {
        super(invoker);
    }
}
