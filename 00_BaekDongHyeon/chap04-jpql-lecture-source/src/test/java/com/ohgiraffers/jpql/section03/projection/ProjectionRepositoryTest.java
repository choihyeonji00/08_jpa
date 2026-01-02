package com.ohgiraffers.jpql.section03.projection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ProjectionRepositoryTest {

  @Autowired
  private ProjectionRepository repository;
  
	@DisplayName("임베디드 타입 프로젝션 테스트")
	@Test
	public void testEmbeddedTypeProjection() {
	  //given
	  //when
	  List<MenuInfo> menuInfoList = repository.embeddedTypeProjection();
	
	  //then
	  Assertions.assertNotNull(menuInfoList);
	  menuInfoList.forEach(System.out::println);
	}


  @DisplayName("TypedQuery를 이용한 스칼라 타입 프로젝션 테스트")
  @Test
  public void testScalarTypeProjectionByTypedQuery() {
    //given
    //when
    List<String> categoryNameList
        = repository.scalarTypeProjectionByTypedQuery();

    //then
    Assertions.assertNotNull(categoryNameList);
    categoryNameList.forEach(System.out::println);
  }

  @DisplayName("Query를 이용한 스칼라 타입 프로젝션 테스트")
  @Test
  public void testScalarTypeProjectionByQuery() {
    //given
    //when
    List<Object[]> categoryList = repository.scalarTypeProjectionByQuery();

    //then
    Assertions.assertNotNull(categoryList);
    categoryList.forEach(
        row -> {
          for(Object column : row) {
            System.out.print(column + " ");
          }
          System.out.println();
        }
    );
  }


  @DisplayName("new 명령어를 활용한 프로젝션 테스트")
  @Test
  public void testNewCommandProjection() {
    //given
    //when
    List<CategoryInfo> categoryInfoList = repository.newCommandProjection();

    //then
    Assertions.assertNotNull(categoryInfoList);
    categoryInfoList.forEach(System.out::println);
  }
}