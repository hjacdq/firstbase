package com.hj.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.hj.filter.TimeFilter;
import com.hj.interceptor.TimeInterceptor;
import com.hj.listener.ListenerTest;
import com.hj.servlet.ServletTest;
/**
 * 
 * 配置类
 * @author hj
 *
 */
@Configuration
public class BeanConfig extends WebMvcConfigurerAdapter{
	
	//注入拦截器
	@Autowired
    private TimeInterceptor timeInterceptor;
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(timeInterceptor);
    }

	/* ----注册bean---- */
	
	//fastjson的配置bean
	@Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();

        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        
        //处理中文乱码问题
        List<MediaType> oFastMediaTypeList = new ArrayList<>();
        oFastMediaTypeList.add(MediaType.APPLICATION_JSON_UTF8);
        fastJsonHttpMessageConverter.setSupportedMediaTypes(oFastMediaTypeList);
        
        HttpMessageConverter<?> converter = fastJsonHttpMessageConverter;
        
        return new HttpMessageConverters(converter);

    }
	
	//servlet的配置bean
	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
	    return new ServletRegistrationBean(new ServletTest(),"/servletTest");
	}
	
	//自定义过滤器的bean
	@Bean
	public FilterRegistrationBean timeFilter() {
	    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
	    
	    TimeFilter timeFilter = new TimeFilter();
	    registrationBean.setFilter(timeFilter);
	    
	    List<String> urls = new ArrayList<>();
	    urls.add("/*");
	    registrationBean.setUrlPatterns(urls);
	    
	    return registrationBean;
	}
	
	//自定义监听器bean
	@Bean
	public ServletListenerRegistrationBean<ListenerTest> servletListenerRegistrationBean() {
	    return new ServletListenerRegistrationBean<ListenerTest>(new ListenerTest());
	}
	
}
