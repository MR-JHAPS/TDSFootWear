package com.vatManager.vatManager.config;

import java.io.IOException;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class SpaWebMvcConfigurer implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		registry.addResourceHandler("/**")
				.addResourceLocations("classpath:/static/")
				.resourceChain(true)
				.addResolver(new PathResourceResolver() {
							@Override
							protected Resource getResource(String resourcePath, Resource location) throws IOException {
								Resource requestedResource = location.createRelative(resourcePath);
							
							// If the resource exists (like a .js, .css, or image), return it.
	                        // If it doesn't exist and isn't an API call, return index.html
	                        return (requestedResource.exists() && requestedResource.isReadable()) 
	                                ? requestedResource 
	                                : new ClassPathResource("/static/index.html");
							}
						});
	
	
	
	
	}
	
	
	
}//Ends class
