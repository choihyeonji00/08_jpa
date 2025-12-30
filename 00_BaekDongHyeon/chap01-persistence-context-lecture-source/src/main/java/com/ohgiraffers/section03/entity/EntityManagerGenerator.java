package com.ohgiraffers.section03.entity;

import com.ohgiraffers.section01.entitymanager.EntityManagerFactoryGenerator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerGenerator {

  private static EntityManagerFactory factory
      = Persistence.createEntityManagerFactory("jpatest");
  
  
  public static EntityManager getInstance(){
    // EntityManager 객체를 생성하여 반환
    // -> 요청 마다 새로운 EntityManager 객체가 생성됨
    return factory.createEntityManager();

  }

}
