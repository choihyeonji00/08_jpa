package com.ohgiraffers.springdatajpa.menu.service;

import com.ohgiraffers.springdatajpa.menu.dto.CategoryDTO;
import com.ohgiraffers.springdatajpa.menu.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.entity.Category;
import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import com.ohgiraffers.springdatajpa.menu.repository.CategoryRepository;
import com.ohgiraffers.springdatajpa.menu.repository.MenuRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    // 생성자 방식의 의존성 주입
		public MenuService(MenuRepository menuRepository, ModelMapper modelMapper,
                       CategoryRepository categoryRepository) {
      this.menuRepository = menuRepository;
      this.modelMapper = modelMapper;
      this.categoryRepository = categoryRepository;
	  }


  /** menuCode가 일치하는 메뉴를 DB에서 조회 후 반환
   * @param menuCode
   * @return 조회된 MenuDTO, 없으면 예외 발생
   * @throws IllegalArgumentException 조회 결과 없으면 예외 발생
   */
  public MenuDTO findMenuByCode(int menuCode) {

    //영속상태 ㄴ mapper로 반환
    Menu menu = menuRepository.findById(menuCode) //  menuRepository : Interface->jpa(proxy 구현) -> 조회 + 결과를 Entity
        .orElseThrow(IllegalArgumentException::new); //null이면 예외를 던져
    // menu.setMenuName("111"); // 만약 영속상태 menuname이 111로 바뀌엇을듯

    /* Menu Entity -> Menu DTO로 변환 (ModelMapper 이용) */
    return modelMapper.map(menu, MenuDTO.class);

    // MenuDTO 필드명, Menu엔티티 같은 이름끼리 대입하도록.. 내부에 그렇게 되어있음
  }

  /* 전체 메뉴 조회 서비스 */
  // 엔티티와 dto(컨트롤러 서비스 왔ㅅ다갔다하는 계층 이동용) 차이 쓰고 써야함
  // Entity는 DB를 위한 객체이고, DTO는 통신을 위한 객체
  public List<MenuDTO> findMenuList() {

    List<Menu> menuList
        = menuRepository.findAll(Sort.by("menuCode").descending());

    // entity -> dto 변환
    // 꺼낼 수 있는 상태가 됨
    return menuList.stream()
        // map 기존 스트림 -> 새로운 스트림 만들 때 사용 // modelMapper 앞에 return이 숨겨짐 람다식이니까 {}
        .map(menu -> modelMapper.map(menu, MenuDTO.class))

        .collect(Collectors.toList()); // 스트림했더니 메뉴만 들어가있는 스트림이 디ㅚㅁ
  }

  /* 3. 전체 메뉴 조회 서비스 + 페이징 */
  public Page<MenuDTO> findMenuList(Pageable pageable) {

    // Pageable은 Spring data에서 제공하는 페이징 처리 클래스
    // - pageNumber : 0 == 1페이지
    // - pageSize : 한 페이지에 보여질 데이터의 개수
    // - sort : 정렬 방식

    /* page 파라미터가 Pageable의 number 값으로 넘어오는데
     * 해당 값이 조회시에는 인덱스 기준이 되어야 해서 -1 처리가 필요하다.
     * */
    pageable = PageRequest.of(
        pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
        pageable.getPageSize(),
        Sort.by("menuCode").descending()
    );

    Page<Menu> menuList = menuRepository.findAll(pageable);

    // entity -> dto 변환
    return menuList.map(menu -> modelMapper.map(menu, MenuDTO.class));
  }

  /* 4. 메뉴 가격 초과 조회*/
  public List<MenuDTO> findByMenuPrice(Integer menuPrice){
//    List<Menu> menuList = menuRepository.findByMenuPriceGreaterThan(menuPrice, Sort.by("menuPrice").descending());
    List<Menu> menuList = menuRepository.findByMenuPriceGreaterThanEqualOrderByMenuPriceDesc(menuPrice);

    //entity -> dto로 변환하여 반환
    return menuList.stream()
        .map(menu -> modelMapper.map(menu, MenuDTO.class))
        .toList();
  }

  /* 5. JPQL 또는 Native Query를 이용한 카테고리 목록 조회 */
  public List<CategoryDTO> findAllCategory(){
    List<Category> categoryList
        = categoryRepository.findAllCategory();

    return categoryList.stream()
        .map(category -> modelMapper.map(category, CategoryDTO.class))
        .toList();

  }
  /* 6. Menu 추가 (INSERT (DML)) -- 트랜잭션으로 관리함 */
  @Transactional // 이 서비스 메서드에서 예외 발생 롤백 안하면 커밋
  public void registMenu(MenuDTO menuDTO) {
    // dto -> entity 변환 후 DB 저장
    // ( 내부적으로 Menu 엔티티를 엔티티 매니저가 persist() )
    menuRepository.save(modelMapper.map(menuDTO, Menu.class));
  }

  /* 7. 메뉴 수정 (엔티티 필드 값 수정)
  * 1) 영속 상태 엔티티 준비 == menuCode가 일치하는 엔티티 먼저 조회 (조회된 엔티티는 자동으로 영속 상태)
  * 2) 영속 상태 엔티티의 필드를 수정 후 commit -> DB에 수정된 내용이 반영
  * */
  @Transactional
  public void modifyMenu(MenuDTO menuDTO) {
    // 1) menuCode가 일치하는 메뉴 엔티티 조회  조회했는데 결과가 null == 예외를 던지겠당!
    // 조회 결과가 null이면 예외 던짐
    Menu foundMenu = menuRepository.findById(menuDTO.getMenuCode()).orElseThrow(IllegalArgumentException::new);

    // 2) 영속상태 엔티티의 필드 수정
    /* setter 사용 (지양)
     * 이름 수정 메서드를 정의하여 사용 */
    foundMenu.modifyMenuName(menuDTO.getMenuName());
  }

  /* 8. 메뉴 삭제 */
  @Transactional
  public void deleteMenu(int menuCode) {
    menuRepository.deleteById(menuCode); //deleteById id 일치하는거찾아서 지움
  }
}