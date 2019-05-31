package com.lmmmowi.simplified.dubbo.spring;

import com.lmmmowi.simplified.dubbo.config.ServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @Author: mowi
 * @Date: 2019-05-30
 * @Description:
 */
public class ServiceBean<T> extends ServiceConfig<T> implements ApplicationListener<ContextRefreshedEvent> {

    private final Logger logger = LoggerFactory.getLogger(ServiceBean.class);

    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        logger.info("start export service " + getClass().getName());
        export();
    }

    @Override
    public void export() {
        super.export();
    }
}
