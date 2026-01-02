package com.ohgiraffers.springdatajpa.menu.repository;

import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<엔티티, 엔티티 ID 타입>
// - Spring Data JPA의 핵심 인터페이스
// - DB CRUD를 간편하게 처리할 수 있게 해주는 Repository 인터페이스
public interface MenuRepository extends JpaRepository<Menu,Integer> {

}
