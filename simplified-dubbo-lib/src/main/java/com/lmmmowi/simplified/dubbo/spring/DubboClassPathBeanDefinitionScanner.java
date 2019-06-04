package com.lmmmowi.simplified.dubbo.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

/**
 * @Author: mowi
 * @Date: 2019-05-30
 * @Description: 直接继承Spring的ClassPathBeanDefinitionScanner，目的是为了暴露checkCandidate方法，因为该方法是protect，无法被外部类调用
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
