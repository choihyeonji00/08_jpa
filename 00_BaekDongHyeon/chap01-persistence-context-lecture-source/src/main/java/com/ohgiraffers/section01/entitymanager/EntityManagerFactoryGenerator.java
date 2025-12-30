package com.ohgiraffers.section01.entitymanager;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerFactoryGenerator {

  private static EntityManagerFactory factory
      = Persistence.createEntityManagerFactory("jpatest");

  // 생성자를 private으로 작성
  // -> 외부에서 해당 객체를 만들 수 없게 함
  private EntityManagerFactoryGenerator(){}

  // 만들어 놓은 factory 객체 하나만 얻어갈 수 있게 함
  // == 싱글톤 패턴
  public static EntityManagerFactory getInstance(){
    return factory;
  }
}
