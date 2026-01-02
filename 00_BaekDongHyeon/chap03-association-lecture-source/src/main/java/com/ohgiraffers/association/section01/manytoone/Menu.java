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
  * - 다대일 관계(N:1) 매핑에 사용
  * - "여러 개의 메뉴"가 "하나의 카테고리"에 속할 수 있는 관계
  * - cascade = CascadeType.PERSIST : 자식이 저장(persist)될 때 부모도 같이 저장(persist)
  *
  * - fetch = FetchType.EAGER (@ManyToOne 기본 값)
  *  - Menu Entity 조회 시 바로 Category도 Join해서 같이 조회
  *
  * - fetch = FetchType.LAZY
  *  - Menu Entity 조회 시 일단 Menu만 조회 (Category 조회 X)
  *  - Menu 내부 Category가 사용되는 시점에 조회를 시작
  *
  * @JoinColumn
  * - 조인을 위한 외래키(FK) 컬럼 매핑
  * */
  @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
//  @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
  @JoinColumn(name = "categoryCode")
  private Category category;


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