package com.gennadykulikov.peopledbweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@SpringBootApplication
public class PeopledbWebApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(PeopledbWebApplication.class, args);
	}

	@Bean
	public SessionLocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
			slr.setDefaultLocale(Locale.getDefault());
            return slr;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor(){
		return new LocaleChangeInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}
}
