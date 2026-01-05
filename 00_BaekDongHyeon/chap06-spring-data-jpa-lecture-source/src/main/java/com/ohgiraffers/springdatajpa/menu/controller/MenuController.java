package com.ohgiraffers.springdatajpa.menu.controller;

import com.ohgiraffers.springdatajpa.common.Pagination;
import com.ohgiraffers.springdatajpa.common.PagingButton;
import com.ohgiraffers.springdatajpa.menu.dto.CategoryDTO;
import com.ohgiraffers.springdatajpa.menu.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/menu") // /menu 시작하는 요청 매핑
@Slf4j // log를 찍을 수 있는 Logger 객체 생성 구문을 필드에 추가하는 어노테이션
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

  /**
   * 전체 메뉴 조회
   * @param model
   * @return
   */
  @GetMapping("/list")
  public String findMenuList(
      Model model, @PageableDefault Pageable pageable
  ){
    /* @PageableDefault 어노테이션
    * - Pageable 객체를 생성할 때 기본값 적용하는 어노테이션
    * - 기본값 : size = 10, page = 0, sort = {}, direction = Sort.Direction.ASC
    * */

    // 방법 1. 전체 조회
    //List<MenuDTO> menuList = menuService.findMenuList();

    // 방법 2. 페이징 처리 적용
    Page<MenuDTO> menuList = menuService.findMenuList(pageable);

    /* 페이징 처리가 반영되도록 하는 버튼 객체 생성 */
    PagingButton paging = Pagination.getPagingButtonInfo(menuList);
    model.addAttribute("paging", paging);
    model.addAttribute("menuList", menuList);

    // 로그 레벨 5가지
    // trace < debug < info < warning < error < fatal(사용 불가)
    log.info("pageable : {}", pageable);

    log.info("조회한 내용 목록 : {}", menuList.getContent());
    log.info("총 페이지 수 : {}", menuList.getTotalPages());
    log.info("총 메뉴 수 : {}", menuList.getTotalElements());
    log.info("해당 페이지에 표시 될 요소 수 : {}", menuList.getSize());
    log.info("해당 페이지에 실제 요소 수:{}",menuList.getNumberOfElements());
    log.info("첫 페이지 여부 : {}", menuList.isFirst());
    log.info("마지막 페이지 여부 : {}", menuList.isLast());
    log.info("정렬 방식 : {}", menuList.getSort());
    log.info("여러 페이지 중 현재 인덱스 : {}", menuList.getNumber());


    // ViewResolver가 접두사, 접미사가 추가된 경로로 forward
    // prefix : /templates/
    // suffix : .html
    return "menu/list";
  }


  /**
   * queryMethod 페이지 조회
   * - 반환형이 void인 경우
   *   요청 주소 "menu/querymethod" 로 forward
   */
  @GetMapping("/querymethod")
  public void queryMethodPage(){}

  /**
   * 입력된 가격을 초과하는 메뉴 조회
   * @param menuPrice
   * @param model
   * @return
   */
  @GetMapping("/search")
  public String findByMenuPrice(
      @RequestParam("menuPrice") Integer menuPrice,
      Model model
  ){
    List<MenuDTO> menuList
        = menuService.findByMenuPrice(menuPrice);

    model.addAttribute("menuList", menuList);

    return "menu/searchResult";
  }


  @GetMapping("/regist")
  public void registPage(){}


  /* 카테고리 목록 조회 (비동기) */
  @GetMapping("/category")
  @ResponseBody // 응답 데이터를 body에 담아서 그대로 전달하겠다는 의미
                // View Resolver 사용 X
  public List<CategoryDTO> findCategoryList(){
    return menuService.findAllCategory();
  }


  /* 메뉴 추가 */
  @PostMapping("/regist")
  public String registMenu(@ModelAttribute MenuDTO menuDTO){
    menuService.registMenu(menuDTO);
    return "redirect:/menu/list";
  }

  @GetMapping("/modify")
  public void modifyPage() {}


  /* 메뉴 수정 */
  @PostMapping("/modify")
  public String modifyMenu(@ModelAttribute MenuDTO menuDTO){
    menuService.modifyMenu(menuDTO);
    return "redirect:/menu/" + menuDTO.getMenuCode();
  }


  @GetMapping("/delete")
  public void deletePage() {}

  @PostMapping("/delete")
  public String deleteMenu(@RequestParam("menuCode") int menuCode) {
    menuService.deleteMenu(menuCode);
    return "redirect:/menu/list";
  }



}
