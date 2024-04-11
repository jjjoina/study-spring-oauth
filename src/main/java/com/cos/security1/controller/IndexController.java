package com.cos.security1.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.security1.entity.User;
import com.cos.security1.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Controller        // view를 return
@RequiredArgsConstructor
public class IndexController {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	// localhost:8080
	// localhost:8080/
	@GetMapping({"", "/"})
	public String index() {
		// Mustache라는 템플릿 엔진 사용
		// 머스테치 기본 폴더 : src/main/resources
		return "index";
	}

	@GetMapping("/user")
	public @ResponseBody String user() {
		return "user";
	}

	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "admin";
	}

	@GetMapping("/manager")
	public @ResponseBody String manager() {
		return "manager";
	}

	@GetMapping("/loginForm")
	public String loginForm() {
		return "loginForm";
	}

	@GetMapping("/joinForm")
	public String joinForm() {
		return "joinForm";
	}

	@PostMapping("/join")
	public String join(User user) {
		System.out.println(user);
		user.setRole("ROLE_USER");

		// 패스워드 암호화
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassword);

		userRepository.save(user);
		return "redirect:/loginForm";
	}
}
