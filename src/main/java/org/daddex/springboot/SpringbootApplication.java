package org.daddex.springboot;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ImportResource("classpath:msContext.xml")
public class SpringbootApplication {
    private static final String CAMEL_URL_MAPPING = "/api/*";

    private static final String CAMEL_SERVLET_NAME = "CamelServlet";
	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}

	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		ServletRegistrationBean registration = new ServletRegistrationBean();
		registration.setEnabled(true);		
		registration.setServlet(new org.apache.cxf.transport.servlet.CXFServlet());
		registration.setUrlMappings(Arrays.asList(CAMEL_URL_MAPPING));
		registration.setName(CAMEL_SERVLET_NAME);
		registration.addUrlMappings(CAMEL_URL_MAPPING);
		return registration;

	}
	
}
