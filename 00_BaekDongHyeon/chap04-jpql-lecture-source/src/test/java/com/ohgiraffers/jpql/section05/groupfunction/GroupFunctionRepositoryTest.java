package com.ohgiraffers.jpql.section05.groupfunction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GroupFunctionRepositoryTest {

  @Autowired
  private GroupFunctionRepository repository;

  @DisplayName("특정 카테고리에 등록된 메뉴 수 조회")
  @Test
  public void testCountMenuOfCategory() {
    //given
    int categoryCode = 4;

    //when
    long countOfMenu = repository.countMenuOfCategory(categoryCode);

    //then
    Assertions.assertTrue(countOfMenu >= 0);
    System.out.println("[ countOfMenu ] " + countOfMenu);
  }


  @DisplayName("COUNT를 제외한 다른 그룹 함수의 조회결과가 없는 경우 테스트")
  @Test
  public void testOthersWithNoResult() {
    //given
    int categoryCode = 44;

    //when
//  long sumOfPrice = repository.otherWithNoResult(categoryCode);

    //then
//  Assertions.assertTrue(sumOfPrice >= 0);
    Assertions.assertDoesNotThrow(
        () -> { Long sumOfPrice = repository.otherWithNoResult(categoryCode); }
    );
  }

  @DisplayName("GROUP BY절과 HAVING절을 사용한 조회 테스트")
  @Test
  public void testSelectByGroupbyHaving() {
    //given
//  int minPrice = 50000;
    long minPrice = 50000L;

    //when
    List<Object[]> sumPriceOfCategoryList
        = repository.selectByGroupByHaving(minPrice);

    //then
    Assertions.assertNotNull(sumPriceOfCategoryList);
    sumPriceOfCategoryList.forEach(
        row -> {
          for(Object column : row) {
            System.out.print(column + " ");
          }
          System.out.println();
        }
    );
  }
}