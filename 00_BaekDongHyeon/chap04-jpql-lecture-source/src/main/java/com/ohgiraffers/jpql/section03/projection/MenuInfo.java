package com.ohgiraffers.jpql.section03.projection;

import jakarta.persistence.Embeddable;

@Embeddable
public class MenuInfo {
	
	private String menuName;
	private int menuPrice;
	
	public MenuInfo() {}

	public MenuInfo(String menuName, int menuPrice) {
		super();
		this.menuName = menuName;
		this.menuPrice = menuPrice;
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

	@Override
	public String toString() {
		return "MenuInfo [menuName=" + menuName + ", menuPrice=" + menuPrice + "]";
	}
	
}