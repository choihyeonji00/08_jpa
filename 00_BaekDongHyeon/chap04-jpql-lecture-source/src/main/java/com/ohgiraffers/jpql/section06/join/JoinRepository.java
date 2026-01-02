package com.ohgiraffers.jpql.section06.join;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JoinRepository {

  @PersistenceContext
  private EntityManager manager;

  /* 이너 조인 */
	public List<Menu> selectByInnerJoin() {
	  String jpql =
        "SELECT m FROM Section06Menu m JOIN m.category c";

    /* 1 + N 문제 발생  (1 == Category, N == Menu)
    *
    * [문제점]
    * - JPA Fetch 기본 전략
    *  @ManyToOne : EAGER
    *   -> Menu 조회 시 Category를 조인한 SELECT문 1회 실행
    *
    *  @OneToMany : LAZY
    *   -> Category 조회 시 Menu의 categoryCode 만큼 N회 SELECT 실행
    *
    *
    * [해결 방법]
    * 1. JPA 권장 방법
    *  - 모든 연관 관계를 LAZY로 명시적 설정
    *
    * 2. JPQL은 fetch 전략 어노테이션 설정이 적용되지 않음
    *   -> fetch join 사용
    *
    * [최종 해결 방법]
    * - 모든 연관 관계를 LAZY로 명시
    * - 필요 할 때만 fetch join 사용
    * */

	  List<Menu> menuList
	    = manager.createQuery(jpql, Menu.class).getResultList();
	
	  return menuList;
	}


  public List<Menu> selectByFetchJoin() {
    String jpql
        = "SELECT m FROM Section06Menu m JOIN FETCH m.category c";
    List<Menu> menuList
        = manager.createQuery(jpql, Menu.class).getResultList();

    return menuList;
  }

  /* 외부 조인 */
  public List<Object[]> selectByOuterJoin() {
    String jpql = "SELECT m.menuName, c.categoryName" +
        " FROM Section06Menu m" +
        " RIGHT JOIN m.category c" +
        " ORDER BY m.category.categoryCode";
    List<Object[]> menuList = manager.createQuery(jpql).getResultList();

    return menuList;
  }

  /*컬렉션 조인*/
  public List<Object[]> selectByCollectionJoin() {
    String jpql = "SELECT m.menuName, c.categoryName" +
        " FROM Section06Category c" +
        " LEFT JOIN c.menuList m";
    List<Object[]> categoryList = manager.createQuery(jpql).getResultList();

    return categoryList;
  }

  /* 세타 조인 (== 카테시안 곱 == 곱집합)
  * - 두 엔티티를 FROM절에 나열
  * - 별도 JOIN 구문 작성 X
  * - Cross Join과 동일한 결과(모든 경우의 수)
  *
  * - 일반적으로 where 조건을 추가하여 결과 수를 제한하는 것이 필요
  * - 학습 목적이나, 특별한 분석 용도가 아니면 지양
  * */
  public List<Object[]> selectByThetaJoin() {
    String jpql
        = "SELECT c.categoryName, m.menuName FROM Section06Category c, Section06Menu m";
    List<Object[]> categoryList = manager.createQuery(jpql).getResultList();

    return categoryList;
  }


}