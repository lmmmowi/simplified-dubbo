package demo;

import demo.config.DubboConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author: mowi
 * @Date: 2019-05-30
 * @Description:
 */
public class SimplifiedDubboDemo {

    public static void main(String[] args) {
//        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("dubbo.xml");
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(DubboConfiguration.class);
        applicationContext.start();
    }
}
