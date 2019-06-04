package com.lmmmowi.simplified.dubbo.spring;

import com.lmmmowi.simplified.dubbo.config.ReferenceConfig;
import org.springframework.beans.factory.FactoryBean;

/**
 * @Author: mowi
 * @Date: 2019-06-04
 * @Description:
 */
public class ReferenceBean<T> extends ReferenceConfig<T> implements FactoryBean<T> {

    @Override
    public T getObject() throws Exception {
        return get();
    }

    @Override
    public Class<?> getObjectType() {
        return getInterfaceClass();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
