package com.yincheng.game.job;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = {"com.yincheng.game"})
@MapperScan("com.yincheng.game.dao.mapper")
@EnableTransactionManagement
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class JobApplication {
}
