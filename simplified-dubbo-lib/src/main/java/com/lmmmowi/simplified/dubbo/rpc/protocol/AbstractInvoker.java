package com.lmmmowi.simplified.dubbo.rpc.protocol;

import com.lmmmowi.simplified.dubbo.rpc.Invoker;

/**
 * @Author: mowi
 * @Date: 2019-06-04
 * @Description:
 */
public abstract class AbstractInvoker<T> implements Invoker<T> {

    private final Class<T> type;

    public AbstractInvoker(Class<T> type) {
        this.type = type;
    }

    @Override
    public Class<T> getInterface() {
        return type;
    }
}
