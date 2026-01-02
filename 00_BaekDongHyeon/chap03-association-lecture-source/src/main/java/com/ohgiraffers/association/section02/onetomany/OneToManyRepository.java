package com.ohgiraffers.association.section02.onetomany;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OneToManyRepository {

  @PersistenceContext
  private EntityManager entityManager;

  public Category find(int categoryCode) {
    return entityManager.find(Category.class, categoryCode);
  }

  public void regist(Category category) {
    entityManager.persist(category);
  }

  // N+1 문제 확인용
  public List<Category> findAll(){
    String jpql = "SELECT c FROM category_and_menu c";
    return entityManager
        .createQuery(jpql, Category.class)
        .getResultList();
  }





}