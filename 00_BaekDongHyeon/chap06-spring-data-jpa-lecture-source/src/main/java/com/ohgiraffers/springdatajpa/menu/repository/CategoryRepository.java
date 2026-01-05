package com.ohgiraffers.springdatajpa.menu.repository;

import com.ohgiraffers.springdatajpa.menu.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository 
	extends JpaRepository<Category, Integer> {
	
	/* JPQL 작성시에는 value만 작성해도 되지만 
	 * Native Query 작성 시에는 
	 * 반드시 nativeQuery = true 속성을 정의해야 한다. 
	 * */
	@Query(value="SELECT category_code, category_name, ref_category_code"
	            + " FROM tbl_category ORDER BY category_code ASC"
		 , nativeQuery = true)
	public List<Category> findAllCategory();
	
}