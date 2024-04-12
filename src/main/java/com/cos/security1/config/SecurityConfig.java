package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity	// spring security filter(현재 클래스)가 spring security filter chain에 등록된다.
// secured 어노테이션 활성화, preAuthorize, postAuthorize 어노테이션 활성화
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

	@Bean	// 해당 메서드가 return하는 object를 IoC로 등록해준다.
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}

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
			formLogin
				.loginPage("/loginForm")
				// login 주소가 호출되면 security가 낚아채서 대신 로그인을 진행해준다.
				.loginProcessingUrl("/login")
				// 로그인에 성공하면 /로 이동
				// 특정 페이지로 접근 중에 로그인하면 다시 그 페이지로 보내준다.
				.defaultSuccessUrl("/")
		);

		return http.build();
	}
}
