package com.ohgiraffers.association.section03.bidirection;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity(name = "bidirection_category")
@Table(name = "tbl_category")
public class Category {

  @Id
  private int categoryCode;

  private String categoryName;

  private Integer refCategoryCode;

  /* mappedBy
  * - 양방향 연관 관계에서 '주인(Owner)'이 아닌 쪽에 설정한다
  *
  *     ==(FK를 가지고 있는 쪽이 주인)
  * - 주인은 외래키를 관리하는 쪽(Menu)이며
  *   주인이 아닌 쪽(Category)은 읽기만 가능하다.
  *
  * - mappedBy 속성 값은
  *   연관 관계의 주인 엔티티(Menu)의 "필드명"을 작성한다.
  * */
  @OneToMany(mappedBy = "category")
  private List<Menu> menuList;

  protected Category() {
  }

  public Category(
      int categoryCode, String categoryName, Integer refCategoryCode
  ) {
    this.categoryCode = categoryCode;
    this.categoryName = categoryName;
    this.refCategoryCode = refCategoryCode;
  }

  public Category(
      int categoryCode, String categoryName,
      Integer refCategoryCode, List<Menu> menuList
  ) {
    this.categoryCode = categoryCode;
    this.categoryName = categoryName;
    this.refCategoryCode = refCategoryCode;
    this.menuList = menuList;
  }

  public int getCategoryCode() {
    return categoryCode;
  }

  public void setCategoryCode(int categoryCode) {
    this.categoryCode = categoryCode;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  public Integer getRefCategoryCode() {
    return refCategoryCode;
  }

  public void setRefCategoryCode(Integer refCategoryCode) {
    this.refCategoryCode = refCategoryCode;
  }

  public List<Menu> getMenuList() {
    return menuList;
  }

  public void setMenuList(List<Menu> menuList) {
    this.menuList = menuList;
  }

  @Override
  public String toString() {
    return "Category [categoryCode=" + categoryCode +
        ", categoryName=" + categoryName +
        ", refCategoryCode=" + refCategoryCode + "]";
  }

}