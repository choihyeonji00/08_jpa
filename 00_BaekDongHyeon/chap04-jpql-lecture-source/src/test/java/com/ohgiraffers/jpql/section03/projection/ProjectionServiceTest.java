package com.ohgiraffers.jpql.section03.projection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProjectionServiceTest {

  @Autowired
  private ProjectionService service;

  @DisplayName("단일 엔티티 프로젝션 테스트")
  @Test
  public void testSingleEntityProjection() {
    //given
    //when
    List<Menu> menuList = service.singleEntityProjection();

    //then
    Assertions.assertNotNull(menuList);
  }
}