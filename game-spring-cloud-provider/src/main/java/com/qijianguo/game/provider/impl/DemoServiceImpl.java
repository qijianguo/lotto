package com.qijianguo.game.provider.impl;

import com.qijianguo.game.api.service.DemoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

@DubboService(version = "1.0.0", timeout = 1000, retries = 3, loadbalance = "consistencehash")
@Component
public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello(String name) {
        return "hello, " + name + " -- Provider1";
    }
}
