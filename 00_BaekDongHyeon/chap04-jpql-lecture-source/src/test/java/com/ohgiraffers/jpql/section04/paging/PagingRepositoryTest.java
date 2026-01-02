package com.ohgiraffers.jpql.section04.paging;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class PagingRepositoryTest {

  @Autowired
  private PagingRepository repository;
  
	@DisplayName("페이징 API를 이용한 조회 테스트")
	@Test
	public void testUsingPagingAPI() {
	  //given
	  int offset = 15;     // 조회를 건너뛸 행 수
	  int limit = 5;       // 조회할 최대 행 수
	
	  //when
	  List<Menu> menuList = repository.usingPagingAPI(offset, limit);
	
	  //then
	  Assertions.assertTrue(menuList.size() > 0 && menuList.size() < 6);
	  menuList.forEach(System.out::println);
	}
}