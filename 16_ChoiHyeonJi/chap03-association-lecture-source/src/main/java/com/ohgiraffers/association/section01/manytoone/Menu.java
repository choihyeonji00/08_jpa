package com.ohgiraffers.association.section01.manytoone;

import jakarta.persistence.*;

@Entity(name = "menu_and_category")
@Table(name = "tbl_menu")
public class Menu {

  @Id
  private int menuCode;

  private String menuName;

  private int menuPrice;

  private String orderableStatus;

/* @ManyToOne
*  - 다대일 관계(N:1) 매핑에 사용
*  - "여러개의 메뉴"가 하나의 카테고리에 속할 수 있는 관계
*  - cascade = CascadeType.PERSIST : 자식이 저장(persist)될 때 부모도 같이 저장(persist)
*
* - fetch = FetchType.EAGER (@ManyToOne 기본 값)
* - Menu Entity 조회 시 바로 Category도 join해서 같이 조회
*
* - fetch = FetchType.LAZY   LAZY 잘 쓰면 성능향상에 도움됨
*   - Menu Entity 조회 시 일단 Menu만 조회 (Category 조회 x)
*   - Menu 내부 Category가 사용되는 시점에 조회를 시작
*
* @ JoinColumn
* - 조인을 위한 외래키(FK) 컬럼 매핑
* */
  @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER) //Many == 현재 엔티티 메뉴를 가리킴 메뉴는 카테고리에 속할 수 있음.
  @JoinColumn(name = "categoryCode")
  private Category category;

  // tbl_menu(N) -----<- tbl_category(1) 1대 다
  // menu_code = 10 몇번 카테고린지 조인해서 찾아보겠다.
  // ref_cate_code , category_code, category_name

  // menu입장에서 보는거임 그래서 다 == menu


  protected Menu() {
  }

  public Menu(
      int menuCode, String menuName, int menuPrice,
      String orderableStatus, Category category
  ) {
    this.menuCode = menuCode;
    this.menuName = menuName;
    this.menuPrice = menuPrice;
    this.orderableStatus = orderableStatus;
    this.category = category;
  }

  public int getMenuCode() {
    return menuCode;
  }

  public String getMenuName() {
    return menuName;
  }

  public int getMenuPrice() {
    return menuPrice;
  }

  public String getOrderableStatus() {
    return orderableStatus;
  }

  public Category getCategory() {
    return category;
  }


  @Override
  public String toString() {
    return "Menu{" +
        "menuCode=" + menuCode +
        ", menuName='" + menuName + '\'' +
        ", menuPrice=" + menuPrice +
        ", orderableStatus='" + orderableStatus + '\'' +
        ", category=" + category +
        '}';
  }
}