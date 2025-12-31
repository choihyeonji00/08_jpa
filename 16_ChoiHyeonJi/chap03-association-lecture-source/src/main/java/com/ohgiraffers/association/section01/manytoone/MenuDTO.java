package com.ohgiraffers.association.section01.manytoone;

public class MenuDTO {

  private int menuCode;
  private String menuName;
  private int menuPrice;
  private String orderableStatus;
  private CategoryDTO category;

  public MenuDTO() {
  }

  public MenuDTO(
          int menuCode, String menuName, int menuPrice,
          String orderableStatus, CategoryDTO category
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
  
  public String getOrderableStatus() { return orderableStatus; }

  public void setOrderableStatus(String orderableStatus) {
      this.orderableStatus = orderableStatus;
  }

  public CategoryDTO getCategory() { return category; }

  public void setCategory(CategoryDTO category) { this.category = category; }

  @Override
  public String toString() {
    return "MenuDTO{" +
        "menuCode=" + menuCode +
        ", menuName='" + menuName + '\'' +
        ", menuPrice=" + menuPrice +
        ", orderableStatus='" + orderableStatus + '\'' +
        ", category=" + category +
        '}';
    }
}