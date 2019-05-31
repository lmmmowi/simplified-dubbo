package demo.provider;

import com.lmmmowi.simplified.dubbo.config.annotation.Service;
import demo.service.HelloService;

/**
 * @Author: mowi
 * @Date: 2019-05-30
 * @Description:
 */
@Service
public class HelloServiceImpl implements HelloService {

    public String hello(String name) {
        return "hello " + name;
    }
}
