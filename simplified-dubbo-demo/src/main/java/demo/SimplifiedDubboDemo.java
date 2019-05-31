package demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: mowi
 * @Date: 2019-05-30
 * @Description:
 */
public class SimplifiedDubboDemo {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("dubbo.xml");
        applicationContext.start();
    }
}
