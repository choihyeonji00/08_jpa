package com.ohgiraffers.springdatajpa.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 언제 Bean으로 등록? Component의 자식 컴포넌트 스캔할때 Bean으로 등록됨.
public class MainController {
  // 서버로 /, /main 주소 요청이 오면 이 메서드로 주소 매핑해줌
	@GetMapping(value = {"/", "/main"})  // == RequestMapping(value = {"/", "/main"})
	public String main() {
		return "main/main";
	}
	
}