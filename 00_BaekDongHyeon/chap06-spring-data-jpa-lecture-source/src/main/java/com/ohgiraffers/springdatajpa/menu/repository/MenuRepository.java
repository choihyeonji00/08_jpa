package com.ohgiraffers.springdatajpa.menu.repository;

import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// JpaRepository<엔티티, 엔티티 ID 타입>
// - Spring Data JPA의 핵심 인터페이스
// - DB CRUD를 간편하게 처리할 수 있게 해주는 Repository 인터페이스
public interface MenuRepository extends JpaRepository<Menu,Integer> {

  /* Spring Data JPA 쿼리 메서드 */

  // 전달 받은 가격을 초과하는 메뉴 목록 조회 + 정렬
  List<Menu> findByMenuPriceGreaterThan(Integer menuPrice, Sort sort);

  // 전달 받은 가격 이상의 메뉴 목록 조회 + 정렬
  List<Menu> findByMenuPriceGreaterThanEqualOrderByMenuPriceDesc(Integer menuPrice);
  
}
