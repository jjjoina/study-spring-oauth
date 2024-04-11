package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity	// spring security filter(현재 클래스)가 spring security filter chain에 등록된다.
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(CsrfConfigurer::disable);
		http.authorizeHttpRequests(authorize ->
			authorize
				// 인증된 사용자만 접근 가능
				.requestMatchers("/user/**").authenticated()
				// ADMIN, MANAGER만 접근 가능
				.requestMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER")
				// ADMIN만 접근 가능
				.requestMatchers("/admin/**").hasAnyRole("ADMIN")
				// 나머지 path는 모든 권한이 허용됨
				.anyRequest().permitAll()
		);
		// 권한이 필요한 페이지 접근시 /login으로 이동
		http.formLogin(formLogin ->
			formLogin.loginPage("/login"));

		return http.build();
	}
}
