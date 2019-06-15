package com.zhongshang.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

/**
 * @author yangsheng
 * @date 2019-06-15
 */
@Configuration
public class CorsConfiguration {

    private static final String DEFAULT = "*";
    private static final String ALL     = "all";
    @Value("${spring.cors.allowedOrigin}")
    private String              allowedOrigin;

    private org.springframework.web.cors.CorsConfiguration buildConfig() {
        String[] origins = allowedOrigin.split(";");
        List<String> originList = Arrays.asList(origins);
        org.springframework.web.cors.CorsConfiguration corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(originList);
        if (ALL.endsWith(allowedOrigin)) {
            corsConfiguration.addAllowedOrigin(DEFAULT);// 允许任何域名使用
        } else {
            corsConfiguration.setAllowedOrigins(originList);
        }
        corsConfiguration.setMaxAge(1728000L);
        corsConfiguration.addAllowedHeader(DEFAULT); // 允许任何头
        corsConfiguration.addAllowedMethod(DEFAULT); // 允许任何方法（post、get等）
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
}
