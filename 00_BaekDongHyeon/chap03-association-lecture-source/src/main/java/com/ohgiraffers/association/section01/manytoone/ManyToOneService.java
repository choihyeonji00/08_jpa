package com.ohgiraffers.association.section01.manytoone;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ManyToOneService {

  private ManyToOneRepository manyToOneRepository;

  public ManyToOneService(ManyToOneRepository manyToOneRepository) {
    this.manyToOneRepository = manyToOneRepository;
  }

  @Transactional // FetchType.LAZY 시 필수
  public Menu findMenu(int menuCode) {
    Menu menu = manyToOneRepository.find(menuCode); // LAZY -> Menu만 조회

    // LAZY 테스트할 때 주석 해제 하면서 확인
    System.out.println(menu.getCategory()); // LAZY -> Category 조회

    return menu;
  }

  
  public String findCategoryNameByJpql(int menuCode) {
    return manyToOneRepository.findCategoryName(menuCode);
  }


  @Transactional
  public void registMenu(MenuDTO menuInfo) {
    Menu menu = new Menu(
        menuInfo.getMenuCode(),
        menuInfo.getMenuName(),
        menuInfo.getMenuPrice(),
        menuInfo.getOrderableStatus(),
        new Category(
            menuInfo.getCategory().getCategoryCode(),
            menuInfo.getCategory().getCategoryName(),
            menuInfo.getCategory().getRefCategoryCode()
        )
    );

    manyToOneRepository.regist(menu);
  }
}