package com.lmmmowi.simplified.dubbo.rpc.protocol;

import com.lmmmowi.simplified.dubbo.rpc.Exporter;
import com.lmmmowi.simplified.dubbo.rpc.Invoker;

/**
 * @Author: mowi
 * @Date: 2019-06-04
 * @Description:
 */
public abstract class AbstractExporter<T> implements Exporter<T> {

    private final Invoker<T> invoker;

    private volatile boolean unexported = false;

    public AbstractExporter(Invoker<T> invoker) {
        this.invoker = invoker;
    }

    @Override
    public Invoker<T> getInvoker() {
        return invoker;
    }

    @Override
    public void unexport() {
        if (unexported) {
            return;
        }
        unexported = true;
//        getInvoker().destroy();
    }
}
