package com.ohgiraffers.section01.entitymanager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class EntityManagerGenerator {

  public static EntityManager getInstance(){

    // EntityManagerFactoryGenerator에서 팩토리 객체 얻어오기
    EntityManagerFactory factory
        = EntityManagerFactoryGenerator.getInstance();
  
    // EntityManager 객체를 생성하여 반환
    // -> 요청 마다 새로운 EntityManager 객체가 생성됨
    return factory.createEntityManager();

  }

}
