package com.ohgiraffers.section02.crud;

import jakarta.persistence.*;

/* Entity : 데이터베이스 테이블과 매핑되는 자바 객체
*
* - @Entity : JPA 엔티티 선언 및 JPQL 엔티티명 지정 -> 해당 클래스를 DB 테이블과 매핑
* - @Table : 실제 테이블명 매핑
* - @Id : PK 컬럼임을 선언
* - @Column : 테이블 컬럼명 매핑
* - @GeneratedValue(strategy = GenerationType.IDENTITY) : 기본키 생성 전략(AUTO_INCREMENT)
* */


@Entity(name = "Section02Menu")
@Table(name = "tbl_menu")
public class Menu {
  
  @Id // PK 컬럼임을 선언
  @Column(name = "menu_code") // 테이블 컬럼명 매핑
  @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 생성 전략(AUTO_INCREMENT)
  private int menuCode;

  @Column(name = "menu_name")
  private String menuName;

  @Column(name = "menu_price")
  private int menuPrice;

  @Column(name = "category_code")
  private int categoryCode;

  @Column(name = "orderable_status")
  private String orderableStatus;

  public Menu() { }

  public Menu(String menuName, int menuPrice, int categoryCode, String orderableStatus) {
    this.menuName = menuName;
    this.menuPrice = menuPrice;
    this.categoryCode = categoryCode;
    this.orderableStatus = orderableStatus;
  }

  public int getMenuCode() {
    return menuCode;
  }

  public void setMenuCode(int menuCode) {
    this.menuCode = menuCode;
  }

  public String getMenuName() {
    return menuName;
  }

  public void setMenuName(String menuName) {
    this.menuName = menuName;
  }

  public int getMenuPrice() {
    return menuPrice;
  }

  public void setMenuPrice(int menuPrice) {
    this.menuPrice = menuPrice;
  }

  public int getCategoryCode() {
    return categoryCode;
  }

  public void setCategoryCode(int categoryCode) {
    this.categoryCode = categoryCode;
  }

  public String getOrderableStatus() {
    return orderableStatus;
  }

  public void setOrderableStatus(String orderableStatus) {
    this.orderableStatus = orderableStatus;
  }

  @Override
  public String toString() {
    return "Menu{" +
        "menuCode=" + menuCode +
        ", menuName='" + menuName + '\'' +
        ", menuPrice=" + menuPrice +
        ", categoryCode=" + categoryCode +
        ", orderableStatus='" + orderableStatus + '\'' +
        '}';
  }
}
