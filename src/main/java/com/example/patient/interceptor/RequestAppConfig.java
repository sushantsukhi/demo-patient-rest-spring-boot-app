package com.example.patient.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class RequestAppConfig implements WebMvcConfigurer {

	@Autowired
	private CustomRequestInterceptor customRequestInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(customRequestInterceptor);
		//.addPathPatterns("**/v1/patient/**");
	}
}