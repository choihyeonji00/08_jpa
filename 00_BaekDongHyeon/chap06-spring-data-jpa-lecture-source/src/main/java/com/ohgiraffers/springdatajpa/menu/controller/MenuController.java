package com.ohgiraffers.springdatajpa.menu.controller;

import com.ohgiraffers.springdatajpa.menu.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/menu") // /menu 시작하는 요청 매핑
public class MenuController {

  private final MenuService menuService;

  // 생성자 방식 의존성 주입
  public MenuController(MenuService menuService) {
    this.menuService = menuService;
  }

  @GetMapping("/{menuCode}")
  public String findMenuByCode(
      @PathVariable("menuCode") int menuCode, Model model){

    MenuDTO menu = menuService.findMenuByCode(menuCode);

    model.addAttribute("menu", menu);

    return "menu/detail"; // forward
  }
  
}
