package com.ohgiraffers.association.section02.onetomany;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OneToManyServiceTest {

  @Autowired
  private OneToManyService oneToManyService;

  @DisplayName("1:N 연관관계 객체 그래프 탐색을 이용한 조회 테스트")
  @Test
  void oneToManyFindTest() {
    //given
    int categoryCode = 10;

    //when
    Category category = oneToManyService.findCategory(categoryCode);

    //then
    Assertions.assertNotNull(category);
  }


  private static Stream<Arguments> getMenuInfo() {
    return Stream.of(
        Arguments.of(321, "스파게티 돈가스", 30000, 321, "분식퓨전", "Y")
    );
  }

  @DisplayName("1:N 연관관계 객체 삽입 테스트")
  @ParameterizedTest
  @MethodSource("getMenuInfo")
  void oneToManyInsertTest(
      int menuCode, String menuName, int menuPrice,
      int categoryCode, String categoryName, String orderableStatus
  ) {
    //given
    CategoryDTO categoryInfo = new CategoryDTO(
        categoryCode, categoryName, null, null
    );

    List<MenuDTO> menuList = new ArrayList<>();
    MenuDTO menuInfo = new MenuDTO(
        menuCode, menuName, menuPrice,categoryCode, orderableStatus
    );
    menuList.add(menuInfo);

    categoryInfo.setMenuList(menuList);

    //when
    //then
    Assertions.assertDoesNotThrow(
        () -> oneToManyService.registMenu(categoryInfo)
    );
  }


  @DisplayName("N+1 문제 확인")
  @Test
  void nPlusOneTest(){
    
    /* N + 1 문제
    * - Category 조회 : 1회
    * - Category별 Menu 조회 : 14회
    * 
    * - 의도하지 않은 추가 쿼리가 14회 더 발생해서 DB 실행 성능 저하
    * */
    Assertions.assertDoesNotThrow(
        ()-> oneToManyService.checkNPlusOne()
    );
  }


}