package com.starbucksorder.another_back.service;


import com.starbucksorder.another_back.aspect.annotation.NameDuplicate;
import com.starbucksorder.another_back.dto.admin.request.menu.ReqAdminDto;
import com.starbucksorder.another_back.dto.admin.request.menu.ReqAdminMenuDto;
import com.starbucksorder.another_back.dto.admin.request.menu.ReqAdminMenuListDtoAll;
import com.starbucksorder.another_back.dto.admin.request.menu.ReqAdminModifyDto;
import com.starbucksorder.another_back.dto.admin.response.menu.*;
import com.starbucksorder.another_back.dto.user.request.menu.ReqMenuListDto;
import com.starbucksorder.another_back.dto.user.response.menu.RespMenuDto;
import com.starbucksorder.another_back.dto.user.response.menu.RespMenuListByCategoryIdDto;
import com.starbucksorder.another_back.entity.Category;
import com.starbucksorder.another_back.entity.Menu;
import com.starbucksorder.another_back.entity.MenuDetail;
import com.starbucksorder.another_back.entity.Option;
import com.starbucksorder.another_back.repository.CategoryMapper;
import com.starbucksorder.another_back.repository.MenuDetailMapper;
import com.starbucksorder.another_back.repository.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private MenuDetailMapper menuDetailMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DuplicateService duplicateService;

    // 전체 메뉴리스트 조회
    /*public RespMenuListDto getMenus() {
        ArrayList<Menu> menus = menuMapper.findAllCategoryItem();
        return RespMenuListDto.builder()
                .menuList(menus)
                .build();
    }*/
    // 카테고리별 메뉴리스트 조회 -> 쓸지말지 보류
//    public List<RespMenuListByCategoryIdDto> getMenusByCategoryId(Long categoryId) {
//
//        ArrayList<Menu> menus = menuMapper.findByCategoryId(categoryId);
//
//        List<RespMenuListByCategoryIdDto> list = menus.stream()
//                .map(menu -> new RespMenuListByCategoryIdDto(menu.getMenuId()
//                        , menu.getCategoryId()
//                        , menu.getMenuName()
//                        , menu.getMenuPrice()
//                        , menu.getImgUrl()))
//                .collect(Collectors.toList());
//
//        return list;
//    }

    //  카테고리별 메뉴리스트 종류 -> 24개씩
    public RespMenuListByCategoryIdDto getMenuList(ReqMenuListDto dto) {
        Long startIndex = (dto.getPage() - 1) * dto.getLimit();
        List<Menu> menuLists = menuMapper.findAllByStartIndexAndLimit(dto.getCategoryId(), startIndex, dto.getLimit());
        Integer menuTotalCount = menuMapper.getCountAllBySearch(dto.getCategoryId());

        return RespMenuListByCategoryIdDto.builder()
                .menus(menuLists)
                .totalCount(menuTotalCount)
                .build();

    }


    // 메뉴id 별 메뉴상세정보
    public RespMenuDto getMenu(Long menuId) {
        Menu selectedMenu = menuMapper.findByMenuId(menuId);

        List<MenuDetail> details = selectedMenu.getMenuDetails();
//        List<OptionDetail> options = selectedMenu.getMenuDetails().getOptions().getOptions();


        return RespMenuDto.builder()
                .menuId(selectedMenu.getMenuId())
                .menuName(selectedMenu.getMenuName())
                .comment(selectedMenu.getComment())
                .menuPrice(selectedMenu.getMenuPrice())
                .imgUrl(selectedMenu.getImgUrl())
                .menuDetailList(details)
                .build();
    }

    // NOTE: 관리자 관련

    // 관리자 메뉴 전체조회 및 페이지로 주기
    public CMRespAdminDto getAllMenus(ReqAdminMenuDto dto) {
        // NOTE: 사용되는 애
        Long startIndex = (dto.getPage() - 1) * dto.getLimit();
        List<Menu> menuList = menuMapper.getMenuListPageByName(startIndex, dto.getLimit(), dto.getSearchName());

        return new CMRespAdminDto(menuMapper.totalCount(dto.getSearchName()), menuList.stream().map(Menu::toPageMenuList).collect(Collectors.toList()));
    }

    // 메뉴추가를 하기위한 옵션과 카테고리 불러오기
    public CMRespAdminCategoryAndOption getValueAll() {

        List<RespAdminCategories> categories = categoryMapper.FindAll().stream().map(Category::toCategoryDto).collect(Collectors.toList());
        List<RespAdminOptions> options = menuMapper.getOptionList().stream().map(Option::toOptionsDto).collect(Collectors.toList());

        System.out.println(categories);
        System.out.println(options);

        return new CMRespAdminCategoryAndOption(options, categories);
    }
    // 메뉴 추가
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean addMenu(ReqAdminDto dto) {
        duplicateService.isDuplicateName("menu", dto.getMenuName());
        Menu menu = dto.toMenuEntity();
        // 메뉴 추가 순차적실행
        menuMapper.save(menu);
        // 옵션 추가
        menuDetailMapper.save(menu.getMenuId(), dto.getOptionIds());
        categoryMapper.saveByMenuId(menu.getMenuId(), dto.getCategories());
        return true;
    }

    // 메뉴 상세보기
    public MenuAdminDetailRespDto getMenuDetail(Long menuId) {
        return menuMapper.menuDetailByMenuId(menuId).toMenuDetail();
    }

    // 메뉴수정
    @NameDuplicate
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean modifyMenu(ReqAdminModifyDto dto) {
        try {
            menuMapper.update(dto.toEntity());
        } catch (Exception e) {
            throw new RuntimeException("Duplicate MenuName");
        }
        // 기존 옵션 삭제 후, 다시 추가하기
        if (menuDetailMapper.deleteByMenuId(dto.getMenuId()) < 0) {
            throw new RuntimeException("Delete Option Error");
        }
        // 기존 카테고리 삭제 후 다시 추가하기
        if (categoryMapper.deleteCategoryById(dto.getMenuId()) < 0) {
            throw new RuntimeException("Delete Category Error");
        }
        menuDetailMapper.save(dto.getMenuId(), dto.getOptionIds());
        categoryMapper.saveByMenuId(dto.getMenuId(), dto.getCategoryIds());

        return true;
    }

    // 자소분리현상 처리 로직
    @Transactional(rollbackFor = Exception.class)
    public void modifyMenu(List<ReqAdminMenuListDtoAll> menuList) {
        for (ReqAdminMenuListDtoAll data : menuList) {
            menuMapper.updateMenuName(data.getMenuId(), data.getMenuName());
        }
        System.out.println("완료");
    }

    // 메뉴 삭제
    public boolean deleteMenu(Long menuId) {
        // 메뉴 카테고리 삭제
        // 메뉴 디테일 삭제
        return menuMapper.deleteByMenuId(menuId) > 0;
    }

    // 메뉴 상태 변경
    public boolean updateMenuStatus(Long menuId) {
        return menuMapper.updateMenuStatus(menuId) > 0;
    }
}
