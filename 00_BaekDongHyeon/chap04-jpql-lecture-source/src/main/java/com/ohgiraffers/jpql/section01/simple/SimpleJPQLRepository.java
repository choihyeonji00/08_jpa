package com.ohgiraffers.jpql.section01.simple;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/* JPQL
* - 엔티티 중심으로 개발 가능한 객체 지향 쿼리
* - 특정 DBMS에 의존하지 않음(SQL 실행 시 각 DBMS에 맞는 SQL 형태로 변경)
* */

@Repository
public class SimpleJPQLRepository {

  @PersistenceContext
  private EntityManager manager;

  /* TypedQuery를 이용한 단일 컬럼 조회 */
	public String selectSingleMenuByTypedQuery() {
	  String jpql 
	    = "SELECT m.menuName FROM Section01Menu as m WHERE m.menuCode = 8";
                              /*Entity Name*/


    TypedQuery<String> query = manager.createQuery(jpql, String.class);


	  String resultMenuName =
        query.getSingleResult(); // 결과 1행 얻어오기
        // 만약 없거나 1행 초과 시 예외 발생
	
	  return resultMenuName;
	}

  /*  Query를 이용한 단일 컬럼 조회 */
  public Object selectSingleMenuByQuery() {
    String jpql
        = "SELECT m.menuName FROM Section01Menu as m WHERE m.menuCode = 8";

    Query query = manager.createQuery(jpql);  //결과 값의 타입을 명시하지 않음
    Object resultMenuName = query.getSingleResult();//결과 값은 Object로 반환

    return resultMenuName;
  }

  /* TypedQuery를 이용한 단일행 조회 */
  public Menu selectSingleRowByTypedQuery() {
    String jpql
        = "SELECT m FROM Section01Menu m WHERE m.menuCode = 8";

    //반환 타입을 row와 매핑할 엔티티 타입으로 설정
    TypedQuery<Menu> query = manager.createQuery(jpql, Menu.class);

    Menu resultMenu = query.getSingleResult();

    return resultMenu;
  }

  /*  TypedQuery를 이용한 다중행 조회 */
  public List<Menu> selectMultipleRowByTypedQuery() {
    String jpql = "SELECT m FROM Section01Menu m";

    //반환 타입을 row와 매핑할 엔티티 타입으로 설정
    TypedQuery<Menu> query = manager.createQuery(jpql, Menu.class);

    List<Menu> resultMenuList = query.getResultList();

    return resultMenuList;
  }

  /* Query를 이용한 다중행 조회 */
  public List<Menu> selectMultipleRowByQuery() {
    String jpql = "SELECT m FROM Section01Menu m";

    Query query = manager.createQuery(jpql);

    List<Menu> resultMenuList = query.getResultList();

    return resultMenuList;
  }

  /* distinct를 활용한 조회 */
  public List<Integer> selectUsingDistinct() {
    String jpql = "SELECT DISTINCT m.categoryCode FROM Section01Menu m";

    TypedQuery<Integer> query = manager.createQuery(jpql, Integer.class);
    List<Integer> resultCategoryList = query.getResultList();

    return resultCategoryList;
  }

  /* in 연산자를 활용한 조회 */
  public List<Menu> selectUsingIn() {
    String jpql
        = "SELECT m FROM Section01Menu m WHERE m.categoryCode IN (11, 12)";
    List<Menu> resultMenuList
        = manager.createQuery(jpql, Menu.class).getResultList();

    return resultMenuList;
  }


  /* like 연산자를 활용한 조회 */
  public List<Menu> selectUsingLike() {
    String jpql
        = "SELECT m FROM Section01Menu m WHERE m.menuName LIKE '%마%'";
    List<Menu> resultMenuList
        = manager.createQuery(jpql, Menu.class).getResultList();

    return resultMenuList;
  }
}