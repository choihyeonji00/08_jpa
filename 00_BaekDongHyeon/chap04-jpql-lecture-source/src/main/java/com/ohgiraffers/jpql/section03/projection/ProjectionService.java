package com.ohgiraffers.jpql.section03.projection;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectionService {

  private final ProjectionRepository repository;

  public ProjectionService(ProjectionRepository repository) {
    this.repository= repository;
  }

	@Transactional
	public List<Menu> singleEntityProjection() {
	  List<Menu> menuList = repository.singleEntityProjection();
	  
		// 엔티티 프로젝션은 영속성 컨텍스트에서 관리하는 객체가 된다.
    
    // 영속 상태의 객체 값을 변경 -> 더티 체킹 -> commit/flush 시 실제 DB 반영
	  menuList.get(0).setMenuName("세상에서 제일 맛있는 유니콘 고기");
	
	  return menuList;
	}
}