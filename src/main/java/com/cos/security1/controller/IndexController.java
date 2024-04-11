package com.cos.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller		// view를 return
public class IndexController {

	// localhost:8080
	// localhost:8080/
	@GetMapping({"", "/"})
	public String index() {
		// Mustache라는 템플릿 엔진 사용
		// 머스테치 기본 폴더 : src/main/resources
		return "index";
	}
}
