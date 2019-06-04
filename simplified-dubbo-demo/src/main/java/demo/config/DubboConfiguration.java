package demo.config;

import com.lmmmowi.simplified.dubbo.spring.beans.factory.annotation.ReferenceAnnotationBeanPostProcessor;
import com.lmmmowi.simplified.dubbo.spring.beans.factory.annotation.ServiceAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;

/**
 * @Author: mowi
 * @Date: 2019-06-04
 * @Description:
 */
public class DubboConfiguration {

    @Bean
    public ServiceAnnotationBeanPostProcessor serviceAnnotationBeanPostProcessor() {
        ServiceAnnotationBeanPostProcessor serviceAnnotationBeanPostProcessor = new ServiceAnnotationBeanPostProcessor();
        serviceAnnotationBeanPostProcessor.setToScanPackages(new HashSet<String>() {
            {
                add("demo.provider");
            }
        });
        return serviceAnnotationBeanPostProcessor;
    }

    @Bean
    public ReferenceAnnotationBeanPostProcessor referenceAnnotationBeanPostProcessor() {
        ReferenceAnnotationBeanPostProcessor referenceAnnotationBeanPostProcessor = new ReferenceAnnotationBeanPostProcessor();
        return referenceAnnotationBeanPostProcessor;
    }

}
