package com.table.info.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 
 * static한 resource를 jsp에서 사용하기위한 config
 * static안에 들어있는 resource(js, css)들을 load해오기위해서 아래와 같은 설정이 필요함
 * 
 * @author sjh
 *
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
			.addResourceHandler("/webjars/**")
				.addResourceLocations("classpath:/static/webjars/", "classpath:/META-INF/resources/webjars/");
		registry
			.addResourceHandler("/**")
				.addResourceLocations("classpath:/static/", "classpath:/META-INF/resources/");
	}
}
