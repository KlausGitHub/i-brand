package com.zhongshang.configuration;

import com.google.common.collect.Lists;
import com.zhongshang.filter.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yangsheng
 * @date 2019-05-19
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Resource
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludeList = Lists.newArrayList();
        excludeList.add("/v1/customer/register");
        excludeList.add("/v1/customer/login");
        excludeList.add("/v1/customer/activate");

        //DiorsNiu class
        excludeList.add("/v1/brand/**");
        excludeList.add("/v1/patent/**");
        excludeList.add("/v1/upload/**");
        excludeList.add("/v1/apply/**");
        excludeList.add("/v1/config/**");
        excludeList.add("/v1/pubOrCollect/**");
        excludeList.add("/v1/service/**");

        registry.addInterceptor(loginInterceptor).addPathPatterns("/**")
                .excludePathPatterns(excludeList);
    }
}
