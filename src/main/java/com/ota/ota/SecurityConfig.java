package com.ota.ota;


import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


   
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder();
	}

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	/*
        auth
            .eraseCredentials(true)
            .userDetailsService(userService())
            .passwordEncoder(passwordEncoder());
        */
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().permitAll();
    }
   
}

 