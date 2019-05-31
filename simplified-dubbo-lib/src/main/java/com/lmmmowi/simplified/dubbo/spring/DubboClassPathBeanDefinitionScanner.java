package com.lmmmowi.simplified.dubbo.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

/**
 * @Author: mowi
 * @Date: 2019-05-30
 * @Description:
 */
public class DubboClassPathBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {

    public DubboClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    public boolean checkCandidate(String beanName, BeanDefinition beanDefinition) throws IllegalStateException {
        return super.checkCandidate(beanName, beanDefinition);
    }
}
