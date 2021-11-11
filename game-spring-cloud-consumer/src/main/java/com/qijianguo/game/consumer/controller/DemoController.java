package com.qijianguo.game.consumer.controller;

import com.qijianguo.game.api.service.DemoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qijianguo
 */
@Controller
@RestController
public class DemoController {

    @DubboReference(version = "1.0.0", cache = "lru", loadbalance = "leastactive")
    private DemoService demoService;

    @GetMapping("/test")
    public String test(String name) {
        return demoService.sayHello(name);
    }

}
