package com.ota.ota;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class OtaApplication extends SpringBootServletInitializer {

    /*
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(OtaApplication.class);
    }
    */
	public static void main(String[] args) {
		SpringApplication.run(OtaApplication.class, args);
	}
	
}
