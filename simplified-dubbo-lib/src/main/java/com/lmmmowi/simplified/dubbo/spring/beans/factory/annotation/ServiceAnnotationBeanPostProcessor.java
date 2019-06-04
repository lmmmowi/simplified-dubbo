package com.lmmmowi.simplified.dubbo.spring.beans.factory.annotation;

import com.lmmmowi.simplified.dubbo.config.annotation.Service;
import com.lmmmowi.simplified.dubbo.spring.DubboClassPathBeanDefinitionScanner;
import com.lmmmowi.simplified.dubbo.spring.ServiceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.support.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.*;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.springframework.context.annotation.AnnotationConfigUtils.CONFIGURATION_BEAN_NAME_GENERATOR;
import static org.springframework.util.ClassUtils.resolveClassName;

/**
 * @Author: mowi
 * @Date: 2019-05-30
 * @Description:
 */
public class ServiceAnnotationBeanPostProcessor implements ApplicationContextAware, BeanDefinitionRegistryPostProcessor {

    private final Logger logger = LoggerFactory.getLogger(ServiceAnnotationBeanPostProcessor.class);

    private static final String SEPARATOR = ":";

    private ApplicationContext applicationContext;

    private Set<String> toScanPackages;

    private ClassLoader classLoader;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void setToScanPackages(Set<String> toScanPackages) {
        this.toScanPackages = toScanPackages;
    }

    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        this.classLoader = getClass().getClassLoader();

        if (toScanPackages != null && !toScanPackages.isEmpty()) {
            registerServiceBeans(beanDefinitionRegistry, toScanPackages);
        }
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        // TODO
    }

    private void registerServiceBeans(BeanDefinitionRegistry registry, Set<String> toScanPackages) {
        // 定义类扫描器
        DubboClassPathBeanDefinitionScanner scanner = new DubboClassPathBeanDefinitionScanner(registry);
        BeanNameGenerator beanNameGenerator = resolveBeanNameGenerator(registry);
        scanner.setBeanNameGenerator(beanNameGenerator);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Service.class));

        // 依次扫描指定的包路径
        for (String scanPackage : toScanPackages) {
            logger.info("start scan services on package " + scanPackage);

            // 注入Service本身
            scanner.scan(scanPackage);

            // 找到该包路径下已经被注册的Bean
            Set<BeanDefinitionHolder> beanDefinitionHolders = findServiceBeanDefinitionHolders(scanner, scanPackage, registry, beanNameGenerator);

            // 注册ServiceBean
            if (!CollectionUtils.isEmpty(beanDefinitionHolders)) {
                for (BeanDefinitionHolder beanDefinitionHolder : beanDefinitionHolders) {
                    registerServiceBean(beanDefinitionHolder, registry, scanner);
                }
            }
        }
    }

    private void registerServiceBean(BeanDefinitionHolder beanDefinitionHolder, BeanDefinitionRegistry registry, DubboClassPathBeanDefinitionScanner scanner) {
        Class<?> beanClass = resolveClass(beanDefinitionHolder);

        Service service = AnnotationUtils.findAnnotation(beanClass, Service.class);

        Class<?> interfaceClass = resolveServiceInterfaceClass(beanClass, service);

        String annotatedServiceBeanName = beanDefinitionHolder.getBeanName();

        // ServiceBean Definition
        AbstractBeanDefinition serviceBeanDefinition = buildServiceBeanDefinition(service, interfaceClass, annotatedServiceBeanName);

        // ServiceBean Bean name
        String beanName = generateServiceBeanName(service, interfaceClass, annotatedServiceBeanName);

        if (scanner.checkCandidate(beanName, serviceBeanDefinition)) { // check duplicated candidate bean
            registry.registerBeanDefinition(beanName, serviceBeanDefinition);

            if (logger.isInfoEnabled()) {
                logger.info("The BeanDefinition[" + serviceBeanDefinition +
                        "] of ServiceBean has been registered with name : " + beanName);
            }

        } else {

            if (logger.isWarnEnabled()) {
                logger.warn("The Duplicated BeanDefinition[" + serviceBeanDefinition +
                        "] of ServiceBean[ bean name : " + beanName +
                        "] was be found , Did @DubboComponentScan scan to same package in many times?");
            }

        }
    }

    private AbstractBeanDefinition buildServiceBeanDefinition(Service service, Class<?> interfaceClass, String annotatedServiceBeanName) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(ServiceBean.class);

        // 注入service
        builder.addPropertyReference("ref", resolveBeanReferenceName(annotatedServiceBeanName));

        // 注入接口类型
        builder.addPropertyValue("interfaceClass", interfaceClass.getName());

        return builder.getBeanDefinition();
    }

    private String generateServiceBeanName(Service service, Class<?> interfaceClass, String annotatedServiceBeanName) {

        StringBuilder beanNameBuilder = new StringBuilder(ServiceBean.class.getSimpleName());

        beanNameBuilder.append(SEPARATOR).append(annotatedServiceBeanName);

        String interfaceClassName = interfaceClass.getName();

        beanNameBuilder.append(SEPARATOR).append(interfaceClassName);

//        String version = service.version();
//
//        if (StringUtils.hasText(version)) {
//            beanNameBuilder.append(SEPARATOR).append(version);
//        }
//
//        String group = service.group();
//
//        if (StringUtils.hasText(group)) {
//            beanNameBuilder.append(SEPARATOR).append(group);
//        }

        return beanNameBuilder.toString();

    }

    private String resolveBeanReferenceName(String beanName) {
//        String resolvedBeanName = environment.resolvePlaceholders(beanName);
        return beanName;
    }

    private Class<?> resolveClass(BeanDefinitionHolder beanDefinitionHolder) {
        BeanDefinition beanDefinition = beanDefinitionHolder.getBeanDefinition();
        return resolveClass(beanDefinition);
    }

    private Class<?> resolveClass(BeanDefinition beanDefinition) {
        String beanClassName = beanDefinition.getBeanClassName();
        return resolveClassName(beanClassName, classLoader);
    }

    private Class<?> resolveServiceInterfaceClass(Class<?> annotatedServiceBeanClass, Service service) {

        Class<?> interfaceClass = service.interfaceClass();

        if (void.class.equals(interfaceClass)) {

            interfaceClass = null;

            String interfaceClassName = service.interfaceName();

            if (StringUtils.hasText(interfaceClassName)) {
                if (ClassUtils.isPresent(interfaceClassName, classLoader)) {
                    interfaceClass = resolveClassName(interfaceClassName, classLoader);
                }
            }

        }

        if (interfaceClass == null) {

            Class<?>[] allInterfaces = annotatedServiceBeanClass.getInterfaces();

            if (allInterfaces.length > 0) {
                interfaceClass = allInterfaces[0];
            }

        }

        Assert.notNull(interfaceClass,
                "@Service interfaceClass() or interfaceName() or interface class must be present!");

        Assert.isTrue(interfaceClass.isInterface(),
                "The type that was annotated @Service is not an interface!");

        return interfaceClass;
    }

    private Set<BeanDefinitionHolder> findServiceBeanDefinitionHolders(
            ClassPathBeanDefinitionScanner scanner, String packageToScan, BeanDefinitionRegistry registry,
            BeanNameGenerator beanNameGenerator) {

        Set<BeanDefinition> beanDefinitions = scanner.findCandidateComponents(packageToScan);

        Set<BeanDefinitionHolder> beanDefinitionHolders = new LinkedHashSet<>(beanDefinitions.size());

        for (BeanDefinition beanDefinition : beanDefinitions) {

            String beanName = beanNameGenerator.generateBeanName(beanDefinition, registry);
            BeanDefinitionHolder beanDefinitionHolder = new BeanDefinitionHolder(beanDefinition, beanName);
            beanDefinitionHolders.add(beanDefinitionHolder);

        }

        return beanDefinitionHolders;

    }

    private BeanNameGenerator resolveBeanNameGenerator(BeanDefinitionRegistry registry) {

        BeanNameGenerator beanNameGenerator = null;

        if (registry instanceof SingletonBeanRegistry) {
            SingletonBeanRegistry singletonBeanRegistry = SingletonBeanRegistry.class.cast(registry);
            beanNameGenerator = (BeanNameGenerator) singletonBeanRegistry.getSingleton(CONFIGURATION_BEAN_NAME_GENERATOR);
        }

        if (beanNameGenerator == null) {

            if (logger.isInfoEnabled()) {

                logger.info("BeanNameGenerator bean can't be found in BeanFactory with name ["
                        + CONFIGURATION_BEAN_NAME_GENERATOR + "]");
                logger.info("BeanNameGenerator will be a instance of " +
                        AnnotationBeanNameGenerator.class.getName() +
                        " , it maybe a potential problem on bean name generation.");
            }

            beanNameGenerator = new AnnotationBeanNameGenerator();

        }

        return beanNameGenerator;

    }
}
