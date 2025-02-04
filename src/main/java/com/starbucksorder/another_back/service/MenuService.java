package com.starbucksorder.another_back.service;


import com.starbucksorder.another_back.dto.admin.ReqAdminDeleteDto;
import com.starbucksorder.another_back.dto.admin.request.menu.ReqAdminDto;
import com.starbucksorder.another_back.dto.admin.request.menu.ReqAdminMenuDto;
import com.starbucksorder.another_back.dto.admin.request.menu.ReqAdminModifyDto;
import com.starbucksorder.another_back.dto.admin.response.CMRespAdminDto;
import com.starbucksorder.another_back.dto.admin.response.menu.*;
import com.starbucksorder.another_back.dto.user.request.Order.ReqOrderItem;
import com.starbucksorder.another_back.dto.user.request.menu.ReqMenuListDto;
import com.starbucksorder.another_back.dto.user.response.menu.RespMenuDto;
import com.starbucksorder.another_back.dto.user.response.menu.RespMenuImgListDto;
import com.starbucksorder.another_back.dto.user.response.menu.RespOnlyMenuIdAdnName;
import com.starbucksorder.another_back.dto.user.response.menu.RespMenuListByCategoryIdDto;
import com.starbucksorder.another_back.entity.Category;
import com.starbucksorder.another_back.entity.Menu;
import com.starbucksorder.another_back.entity.MenuDetail;
import com.starbucksorder.another_back.entity.Option;
import com.starbucksorder.another_back.exception.DuplicateNameException;
import com.starbucksorder.another_back.mapper.MenuMapper;
import com.starbucksorder.another_back.repository.CategoryMapper;
import com.starbucksorder.another_back.repository.MenuDetailMapper;
import com.starbucksorder.another_back.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // final 필드만 자동 생성자로 주입 (Lombok)
public class MenuService {

    private final MenuMapper menuMapper;
    private final MenuRepository menuRepository;

    @Autowired private MenuDetailMapper menuDetailMapper;
    @Autowired private CategoryMapper categoryMapper;
    @Autowired private DuplicateService duplicateService;


    //  카테고리별 메뉴리스트 종류 -> 9개씩
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
//      List<OptionDetail> options = selectedMenu.getMenuDetails().getOptions().getOptions();

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
    // 관리자 메뉴 전체조회
    public List<RespOnlyMenuIdAdnName> getMenuListAll() {
        return menuMapper.getMenuList()
                .stream()
                .map(Menu::toRespOnlyIdAndNameDto)
                .toList();
    }

    // 관리자 메뉴 전체조회 및 페이지로 주기
    public CMRespAdminDto getAllMenus(ReqAdminMenuDto dto) {
        // NOTE: 사용되는 애
        Long startIndex = (dto.getPage() - 1) * dto.getLimit();
        List<Menu> menuList = menuMapper.getMenuListPageByName(startIndex, dto.getLimit(), dto.getSearchName());

        return new CMRespAdminDto(menuMapper.totalCount(dto.getSearchName()), menuList.stream().map(Menu::toPageMenuList).toList());
    }

    // 메뉴추가를 하기위한 옵션과 카테고리 불러오기
    public CMRespAdminCategoryAndOption getValueAll() {

        List<RespAdminCategories> categories = categoryMapper.FindAll().stream().map(Category::toCategoryDto).toList();
        List<RespAdminOptions> options = menuMapper.getOptionList().stream().map(Option::toOptionsDto).toList();

        return new CMRespAdminCategoryAndOption(options, categories);
    }

    // 메뉴 추가
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean addMenu(ReqAdminDto dto) {
        duplicateService.isDuplicateName("menu", dto.getMenuName());
        Menu menu = dto.toMenuEntity();
        // 메뉴 추가 순차적실행
        menuRepository.save(menu);
        // 옵션 추가
        if (dto.getOptionIds().size() > 0) {
            menuDetailMapper.save(menu.getMenuId(), dto.getOptionIds());
        }
        if (dto.getCategories() != null) {
            categoryMapper.saveByMenuId(menu.getMenuId(), dto.getCategories());
        }
        return true;
    }

    // 메뉴 상세보기
    public MenuAdminDetailRespDto getMenuDetail(Long menuId) {
        return menuMapper.menuDetailByMenuId(menuId).toMenuDetail();
    }

    // 메뉴수정
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean modifyMenu(ReqAdminModifyDto dto) {
        try {
            // 해당 메뉴가 있는 지 확인
            Menu menu = menuRepository.findById(dto.getMenuId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메뉴입니다."));
            menu.updateMenu(dto);
            // 데이터무결성 위반 예외처리
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateNameException("Duplicate MenuName");
        }
        // 기존 옵션 삭제 후, 다시 추가하기
        menuDetailMapper.deleteByMenuId(dto.getMenuId());
        // 기존 카테고리 삭제 후 다시 추가하기
        categoryMapper.deleteCategoryMenuByMenuId(dto.getMenuId());

        if (dto.getOptionIds() != null && !dto.getOptionIds().isEmpty()) {
            menuDetailMapper.save(dto.getMenuId(), dto.getOptionIds());
        }
        if (dto.getCategoryIds() != null && !dto.getCategoryIds().isEmpty()) {
            categoryMapper.saveByMenuId(dto.getMenuId(), dto.getCategoryIds());
        }
        return true;
    }

    // 메뉴 삭제
    @Transactional
    public boolean deleteMenu(ReqAdminDeleteDto dto) {
        // 메뉴 카테고리 삭제
        // 메뉴 디테일 삭제
        String ids = dto.getIds()
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        menuRepository.deleteByMenuIds(ids); // 프로시저 실행
        int successCount = menuRepository.getDeletedCount(); // 삭제 된 값 가져오기

        return successCount > 0; // 성공 여부 리턴
    }

    // 메뉴 상태 변경
    public boolean updateMenuStatus(Long menuId) {
        return menuRepository.updateMenuStatus(menuId) > 0;
    }


    public List<RespMenuImgListDto> findByIds(ReqOrderItem dto) {
        return menuMapper.findByMenuIds(dto.getItems()).stream().map(Menu::toMenuImgListDto).toList();
    }

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
}
