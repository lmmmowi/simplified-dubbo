package com.lmmmowi.simplified.dubbo.rpc;

/**
 * @Author: mowi
 * @Date: 2019-06-04
 * @Description:
 */
public interface Exporter<T> {

    Invoker<T> getInvoker();

    void unexport();
}
