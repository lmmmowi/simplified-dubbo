package com.lmmmowi.simplified.dubbo.extension;

import com.lmmmowi.simplified.dubbo.rpc.Protocol;
import com.lmmmowi.simplified.dubbo.rpc.ProxyFactory;
import com.lmmmowi.simplified.dubbo.rpc.injvm.InjvmProtocol;
import com.lmmmowi.simplified.dubbo.rpc.proxy.javassist.JavassistProxyFactory;

/**
 * @Author: mowi
 * @Date: 2019-06-04
 * @Description:
 */
public class ExtensionLoader<T> {

    private final Class<?> type;

    private ExtensionLoader(Class<?> type) {
        this.type = type;
    }

    public static <T> ExtensionLoader<T> getExtensionLoader(Class<T> type) {
        return new ExtensionLoader<>(type);
    }

    public T getAdaptiveExtension() {
        if (type == Protocol.class) {
            return (T) new InjvmProtocol();
        }

        if (type == ProxyFactory.class) {
            return (T) new JavassistProxyFactory();
        }

        return null;
    }
}
