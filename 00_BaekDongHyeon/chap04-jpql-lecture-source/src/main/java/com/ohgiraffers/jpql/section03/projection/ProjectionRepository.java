package com.ohgiraffers.jpql.section03.projection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectionRepository {

  @PersistenceContext
  private EntityManager manager;

  /* 엔티티 프로젝션 */
	public List<Menu> singleEntityProjection() {

    // SELECT절에 엔티티 이름(또는 별칭) 작성
	  String jpql = "SELECT m FROM Section03Menu m";
	  List<Menu> menuList 
	    = manager.createQuery(jpql, Menu.class).getResultList();
	
	  return menuList;
	}

  /* 임베디드 타입 프로젝션 */
  public List<MenuInfo> embeddedTypeProjection() {

    // 메뉴명, 메뉴 가격을 조회
    String jpql = "SELECT m.menuInfo FROM EmbeddedMenu m";


    // 조회 결과를 MenuInfo 타입으로 저장,
    // 영속성 컨텍스트에서 관리 X(왜? MenuInfo에는 @Entity 어노테이션 없음 == 엔티티 아님)
    List<MenuInfo> resultMenuInfo
        = manager.createQuery(jpql, MenuInfo.class).getResultList();

    return resultMenuInfo;
  }


  /* 스칼라 타입 프로젝션 */
  public List<String> scalarTypeProjectionByTypedQuery(){
    String jpql = "SELECT c.categoryName FROM Section03Category c";

    List<String> resultList
        = manager.createQuery(jpql, String.class).getResultList();

    return resultList;
  }


  /* 스칼라 타입을 여러 개 조회할 경우 Object[] 타입으로 반환 받으면 된다
  * */
  public List<Object[]> scalarTypeProjectionByQuery() {
    String jpql
        = "SELECT c.categoryCode, c.categoryName FROM Section03Category c";
    List<Object[]> resultList = manager.createQuery(jpql).getResultList();

    return resultList;
  }


  /* new 명령어를 활용한 프로젝션 */
  public List<CategoryInfo> newCommandProjection() {
    String jpql
        = "SELECT new com.ohgiraffers.jpql.section03.projection.CategoryInfo"
        + "(c.categoryCode, c.categoryName) FROM Section03Category c";
    
    List<CategoryInfo> resultList
        = manager.createQuery(jpql, CategoryInfo.class).getResultList();

    return resultList;
  }


}