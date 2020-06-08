package com.example.patient.interceptor;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class CustomRequestInterceptor extends HandlerInterceptorAdapter {

	private static final Logger LOG = LoggerFactory.getLogger(CustomRequestInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

		long startTime = Instant.now().toEpochMilli();
		LOG.info("Request URL::" + request.getRequestURL().toString() + ":: Start Time=" + Instant.now());
		request.setAttribute("startTime", startTime);
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {

		long startTime = (Long) request.getAttribute("startTime");
		LOG.info("Request URL::" + request.getRequestURL().toString() + ":: Time Taken="
				+ (Instant.now().toEpochMilli() - startTime));
	}
}