package com.tove.enterprise_project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/admin").setViewName("adminpage");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/user").setViewName("userpage");
    }

}
