package com.ohgiraffers.springdatajpa.menu.service;

import com.ohgiraffers.springdatajpa.menu.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import com.ohgiraffers.springdatajpa.menu.repository.MenuRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;

    // 생성자 방식의 의존성 주입
		public MenuService(MenuRepository menuRepository, ModelMapper modelMapper) {
      this.menuRepository = menuRepository;
      this.modelMapper = modelMapper;
	  }


  /** menuCode가 일치하는 메뉴를 DB에서 조회 후 반환
   * @param menuCode
   * @return 조회된 MenuDTO, 없으면 예외 발생
   * @throws IllegalArgumentException 조회 결과 없으면 예외 발생
   */
  public MenuDTO findMenuByCode(int menuCode) {

    //영속상태 ㄴ mapper로 반환
    Menu menu = menuRepository.findById(menuCode) //  menuRepository : Interface->jpa(proxy 구현) -> 조회 + 결과를 Entity
        .orElseThrow(IllegalArgumentException::new); //null이면 예외를 던져
    // menu.setMenuName("111"); // 만약 영속상태 menuname이 111로 바뀌엇을듯
    /* Menu Entity -> Menu DTO로 변환 (ModelMapper 이용) */
    return modelMapper.map(menu, MenuDTO.class);

    // MenuDTO 필드명, Menu엔티티 같은 이름끼리 대입하도록.. 내부에 그렇게 되어있음
  }

}