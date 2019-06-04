package com.lmmmowi.simplified.dubbo.config;

import com.lmmmowi.simplified.dubbo.common.URL;
import com.lmmmowi.simplified.dubbo.extension.ExtensionLoader;
import com.lmmmowi.simplified.dubbo.rpc.Invoker;
import com.lmmmowi.simplified.dubbo.rpc.Protocol;
import com.lmmmowi.simplified.dubbo.rpc.ProxyFactory;

/**
 * @Author: mowi
 * @Date: 2019-06-04
 * @Description:
 */
public class ReferenceConfig<T> extends AbstractConfig {

    private static final Protocol PROTOCOL = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();

    private static final ProxyFactory PROXY_FACTORY = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getAdaptiveExtension();

    private T ref;

    private Class<?> interfaceClass;

    private String interfaceName;

    private transient volatile boolean initialized;

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public Class<?> getInterfaceClass() {
        return interfaceClass;
    }

    public synchronized T get() {
        if (ref == null) {
            init();
        }
        return ref;
    }

    private void init() {
        if (initialized) {
            return;
        }
        initialized = true;

        try {
            interfaceClass = Class.forName(interfaceName, true, Thread.currentThread().getContextClassLoader());
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }

        ref = createProxy();
    }

    private T createProxy() {
        URL url = null;

        Invoker<?> invoker = PROTOCOL.refer(interfaceClass, url);
        return (T) PROXY_FACTORY.getProxy(invoker);
    }
}
