package com.yincheng.game;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.servlet.ServletContext;

/**
 * @author qijianguo
 */
@SpringBootApplication
@EnableScheduling
@MapperScan("com.yincheng.game.*.mapper")
public class GameApplication implements ServletContextInitializer {

    public static void main(String[] args) {

        SpringApplication.run(GameApplication.class, args);
    }

    @Override
    public void onStartup(ServletContext servletContext) {
        //设置文本缓存1M
        servletContext.setInitParameter("org.apache.tomcat.websocket.textBufferSize", Integer.toString((1024 * 1024)));
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
