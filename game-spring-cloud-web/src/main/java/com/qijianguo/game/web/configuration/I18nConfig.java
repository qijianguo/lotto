package com.qijianguo.game.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Locale;

///**
// * 国际化默认语言配置及编码方式修改
// * @author fxbin
// * @version v1.1
// * @since 2018/8/23 14:38
// * @see org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration
// * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
// */
//@Configuration
public class I18nConfig implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        //localeResolver.setCookieName("lang");
        //设置默认区域
        localeResolver.setDefaultLocale(new Locale("en", "US"));
        //设置cookie有效期.
        localeResolver.setCookieMaxAge(-1);

        return localeResolver;
    }

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(
                Charset.forName("UTF-8"));
        return converter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(responseBodyConverter());
    }
}
