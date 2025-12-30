package com.ohgiraffers.section03.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JPA Entity의 생명주기(Life Cycle)
 *
 * 1. 비영속(Transient) 상태
 *    - EntityManager와 관련이 없는 새로운 상태
 *    - 객체를 생성만 한 상태로, 아직 영속성 컨텍스트에 저장되지 않은 상태
 *    - 데이터베이스와 전혀 관련이 없는 순수한 자바 객체 상태
 *
 * 2. 영속(Managed/Persistent) 상태
 *    - EntityManager에 의해 관리되는 상태
 *    - 영속성 컨텍스트에 저장된 상태
 *    - 1차 캐시, 변경 감지, 지연 로딩 등의 기능을 사용할 수 있음
 *    - persist(), find(), JPQL 조회 등으로 영속 상태가 됨
 *
 * 3. 준영속(Detached) 상태
 *    - 영속성 컨텍스트에 저장되었다가 분리된 상태
 *    - detach(), clear(), close() 등으로 준영속 상태가 됨
 *    - 영속성 컨텍스트의 기능을 사용할 수 없음
 *    - merge()를 통해 다시 영속 상태로 만들 수 있음
 *
 * 4. 삭제(Removed) 상태
 *    - remove()를 호출해서 엔터티를 삭제하기로 결정한 상태
 *    - 실제 데이터베이스 삭제는 commit 시점에 실행됨
 */
class EntityLifeCycleTest {

  private EntityLifeCycle lifeCycle;

  @BeforeEach
  void setUp(){
    lifeCycle = new EntityLifeCycle();
  }


  @DisplayName("비영속 테스트")
  @ParameterizedTest
  @ValueSource(ints = {1, 2})
  void testTransient(int menuCode) {
    // when

    // foundMenu : EntityManager를 통해 DB에서 조회한 객체 -> 영속 상태
    Menu foundMenu = lifeCycle.findMenuByMenuCode(menuCode);

    // newMenu : 동일한 값을 가지지만 직접 생성한 새 객체
    //           EntityManager가 관리하지 않는 상태 -> 비영속 상태
    Menu newMenu = new Menu(
        foundMenu.getMenuCode(),
        foundMenu.getMenuName(),
        foundMenu.getMenuPrice(),
        foundMenu.getCategoryCode(),
        foundMenu.getOrderableStatus()
    );

    // 조회에 사용된 EntityManager 얻어오기
    EntityManager entityManager = lifeCycle.getManagerInstance();

    // then

    // foundMenu는 영속성 컨텍스트에서 관리 되는 영속 상태의 객체
    assertTrue(entityManager.contains(foundMenu));

    // newMenu는 영속성 컨텍스트에서 관리 되지 않는 비영속 상태의 객체
    assertFalse(entityManager.contains(newMenu));

  }



  @DisplayName("다른 엔티티 매니저가 관리하는 엔티티의 영속성 테스트")
  @ParameterizedTest
  @ValueSource(ints = {1, 2})
  void testManagedOtherEntityManager(int menuCode) {
    //when
    // lifeCycle.findMenuByMenuCode() 호출 시 마다
    // 새로운 엔티티가 생성됨 -> 서로 독립된 영속성 컨텍스트를 관리
    Menu menu1 = lifeCycle.findMenuByMenuCode(menuCode);
    Menu menu2 = lifeCycle.findMenuByMenuCode(menuCode);

    //then
    assertNotSame(menu1, menu2);
  }




  @DisplayName("같은 엔티티 매니저가 관리하는 엔티티의 영속성 테스트")
  @ParameterizedTest
  @ValueSource(ints = {1, 2})
  void testManagedSameEntityManager(int menuCode) {
    //given
    // 엔티티 매니저를 1개 얻어옴
    // -> 해당 엔티티 매니저가 관리하는 영속성 컨텍스트가 별도로 존재
    EntityManager entityManager = EntityManagerGenerator.getInstance();

    //when
    // menuCode를 이용해서 조회
    // - entityManager를 이용해서 조회 -> 영속 상태
    // - 1차 캐시에 저장됨 -> 똑같은 menuCode를 통해 조회할 경우 재사용됨
    Menu menu1 = entityManager.find(Menu.class, menuCode);

    Menu menu2 = entityManager.find(Menu.class, menuCode);

    //then
    assertEquals(menu1, menu2);
    assertSame(menu1, menu2);
  }




  @DisplayName("준영속화 detach 테스트")
  @ParameterizedTest
  @CsvSource({"11, 1000", "12, 1000"})
  void testDetachEntity(int menuCode, int menuPrice) {
    // given
    EntityManager entityManager = EntityManagerGenerator.getInstance();
    EntityTransaction entityTransaction = entityManager.getTransaction();

    // when
    entityTransaction.begin();

    // 엔티티 조회(영속 상태)
    Menu foundMenu = entityManager.find(Menu.class, menuCode);


    /* detach
     * 특정 엔티티만 준영속 상태(영속성 컨텍스트가 관리하던 객체를 관리하지 않음)로 만든다.
     */
    entityManager.detach(foundMenu);

    // (만약에..)영속 상태
    // -> tbl_menu에서 menu_code가 일치하는 행의 price가 수정됨
    //    (더티 체킹 : @Entity 객체의 변화(DB와 스냅샷 차이)를 감지)

    // (현재)준영속 상태
    // -> DB 테이블과 매핑 X, 그냥 자바 객체의 값만 변경됨
    //    (더티 체킹 X)
    foundMenu.setMenuPrice(menuPrice);


    /* flush
    * 영속성 컨텍스트의 상태를 DB로 내보낸다.
    commit하지 않은 상태이므로 rollback 가능하다.
    *
    * - 현재 영속 상태의 객체가 없으므로 아무일도 일어나지 않음
    */
    entityManager.flush(); // DB에 전달만한 상태(commit/rollback 가능)

    // then
    assertNotEquals(
        menuPrice, entityManager.find(Menu.class, menuCode).getMenuPrice()
    );

    entityTransaction.rollback();

  }

  @DisplayName("준영속화 detach 후 다시 영속화 테스트")
  @ParameterizedTest
  @CsvSource({"11, 1000", "12, 1000"})
  void testDetachAndMerge(int menuCode, int menuPrice) {
    // given
    EntityManager entityManager = EntityManagerGenerator.getInstance();
    EntityTransaction entityTransaction = entityManager.getTransaction();

    // when
    entityTransaction.begin();

    // foundMenu -> 영속 상태
    Menu foundMenu = entityManager.find(Menu.class, menuCode);

    // foundMenu -> 준영속으로 변경
    entityManager.detach(foundMenu);

    // 준영속 상태에서 menuPrice를 변경 (10000 -> 1000)
    // -> 더티 체킹의 대상이 아니여서 DB 값 수정 X
    foundMenu.setMenuPrice(menuPrice);

    /* merge
     * 파라미터로 넘어온 준영속 엔티티 객체의 식별자 값으로
     * 1차 캐시에서 엔티티 객체를 조회한다.
     * 없으면 DB에서 조회하여 1차 캐시에 저장한다.
     * 조회한 영속 엔티티 객체에 준영속 상태의 엔티티 객체의 값을 병합 한 뒤
     * 영속 엔티티 객체를 반환한다.
     * 혹은 조회 할 수 없는 데이터라면 새로 생성해서 병합한다.
     */

    entityManager.merge(foundMenu); // 준영속 -> 영속
    entityManager.flush(); // DB에 내용 전달

    Menu checkMenu = entityManager.find(Menu.class, menuCode);

    System.out.println("checkMenu = " + checkMenu);
    System.out.println("menuPrice = " + menuPrice);

    // then
    assertEquals(
        menuPrice, checkMenu.getMenuPrice()
    );

    entityTransaction.rollback();

  }



  @DisplayName("detach후 merge한 데이터 update 테스트")
  @ParameterizedTest
  @CsvSource({"11, 하양 민트초코죽", "12, 까만 딸기탕후루"})
  void testMergeUpdate(int menuCode, String menuName) {
    // given
    EntityManager entityManager = EntityManagerGenerator.getInstance();
    
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();

    Menu foundMenu = entityManager.find(Menu.class, menuCode);
    entityManager.detach(foundMenu); // 준영속 변경

    // when
    foundMenu.setMenuName(menuName); // 준영속 상태에서 값 변경

    // 준영속 객체와 1차 캐시 내용을 병합 후
    // 영속 객체를 반환 (더티 체킹 --> 변환된 부분 감지)
    Menu mergeMenu = entityManager.merge(foundMenu);
    
    transaction.commit(); // UPDATE 수행됨
    
    Menu refoundMenu = entityManager.find(Menu.class, menuCode);

    System.out.println("refoundMenu = " + refoundMenu);
    System.out.println("mergeMenu = " + mergeMenu);
    // then
    assertEquals(mergeMenu.getMenuName(), refoundMenu.getMenuName());

  }

  @DisplayName("detach 후 merge한 데이터 save 테스트")
  @Test
  void testMergeSave() {
    // given
    EntityManager entityManager = EntityManagerGenerator.getInstance();
    EntityTransaction entityTransaction = entityManager.getTransaction();
    Menu foundMenu = entityManager.find(Menu.class, 20);

    entityManager.detach(foundMenu); // 영속 -> 준영속

    // when
    entityTransaction.begin();
    foundMenu.setMenuName("치약맛 초코 아이스크림");
    foundMenu.setMenuCode(999); // PK 수정

    entityManager.merge(foundMenu);
    // 1차 캐시와 준영속 객체를 병합하여 영속 객체를 반환
    // -> 더티 체킹 -> menu_code(PK)가 999인 행이 없음 확인함
    // -> INSERT 구문 생성

    entityTransaction.commit();

    // then
    assertEquals(
        "치약맛 초코 아이스크림",
        entityManager.find(Menu.class, 999).getMenuName()
    );

  }

  @DisplayName("준영속화 clear 테스트")
  @ParameterizedTest
  @ValueSource(ints = {1, 2, 3})
  void testClearPersistenceContext(int menuCode) {
    //given
    EntityManager entityManager = EntityManagerGenerator.getInstance();
    Menu foundMenu = entityManager.find(Menu.class, menuCode);

    //when
    /* clear
     * 영속성 컨텍스트를 초기화한다
     * = 영속성 컨텍스트 내 모든 엔티티를 준영속화 시킨다.
     */
    entityManager.clear();

    //then
    Menu expectedMenu = entityManager.find(Menu.class, menuCode);

    // foundMenu == 준영속
    // expectedMenu == 영속
    assertNotSame(expectedMenu, foundMenu);
  }


  @DisplayName("준영속화 close 테스트")
  @ParameterizedTest
  @ValueSource(ints = {1, 2, 3})
  void testClosePersistenceContext(int menuCode) {
    //given
    EntityManager entityManager = EntityManagerGenerator.getInstance();
    Menu foundMenu = entityManager.find(Menu.class, menuCode);

    //when
    /* close()
     * 영속성 컨텍스트를 종료한다
     * = 영속성 컨텍스트 내 모든 객체를 준영속화 시킨다.
     */
    entityManager.close();

    //then
    assertThrows(
        IllegalStateException.class,
        () -> entityManager.find(Menu.class, menuCode)
    );
  }


  @DisplayName("영속성 엔티티 삭제 remove 테스트")
  @ParameterizedTest
  @ValueSource(ints = {1})
  void testRemoveEntity(int menuCode) {
    //given
    EntityManager entityManager = EntityManagerGenerator.getInstance();
    EntityTransaction transaction = entityManager.getTransaction();
    Menu foundMenu = entityManager.find(Menu.class, menuCode);

    //when
    transaction.begin();

    /* remove
     * 엔티티를 영속성 컨텍스트 및 데이터베이스에서 삭제한다.
     * 단, 트랜잭션을 제어하지 않으면 데이터베이스에 영구 반영되지는 않는다.
     * 트랜잭션을 커밋하는 순간
     * 영속성 컨텍스트에서 관리하는 엔티티 객체가 데이터베이스에 반영된다.
     * */
    entityManager.remove(foundMenu);

    /* flush
     * 영속성 컨텍스트의 변경 내용을 데이터베이스에 동기화하는 작업을 한다.
     * (등록/수정/삭제한 엔티티를 데이터베이스에 반영)
     */
    entityManager.flush();

    //then
    Menu refoundMenu = entityManager.find(Menu.class, menuCode);
    assertNull(refoundMenu);
    transaction.rollback();
  }
}