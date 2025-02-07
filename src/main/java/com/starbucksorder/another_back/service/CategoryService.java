package com.starbucksorder.another_back.service;

import com.starbucksorder.another_back.dto.admin.ReqAdminPageAndLimitDto;
import com.starbucksorder.another_back.dto.admin.request.category.ReqAdminCategoryDto;
import com.starbucksorder.another_back.dto.admin.request.category.ReqAdminIncludeMenuByCategoryDto;
import com.starbucksorder.another_back.dto.admin.response.CMRespAdminDto;
import com.starbucksorder.another_back.dto.admin.response.category.RespAdminCategoryDto;
import com.starbucksorder.another_back.dto.admin.response.category.RespAdminOneItems;
import com.starbucksorder.another_back.dto.user.response.category.RespCategoryDto;
import com.starbucksorder.another_back.dto.user.response.menu.RespOnlyMenuIdAdnName;
import com.starbucksorder.another_back.domain.Category;
import com.starbucksorder.another_back.domain.Menu;
import com.starbucksorder.another_back.exception.DuplicateNameException;
import com.starbucksorder.another_back.repository.CategoryMapper;
import com.starbucksorder.another_back.repository.MenuCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private MenuCategoryMapper menuCategoryMapper;

    @Autowired
    private DuplicateService duplicateService;

    public RespCategoryDto getCategory() {
        List<Category> categoryList = categoryMapper.findAllByEnable();

        return RespCategoryDto.builder()
                .categories(categoryList)
                .build();

    }

    // NOTE: 관리자 카테고리 기능

    // 카테고리추가
    public Boolean add(ReqAdminCategoryDto dto) {
        // 카테고리명 중복검사
        duplicateService.isDuplicateName("category", dto.getCategoryName());

        return categoryMapper.save(dto.toEntity()) > 0;
    }

    // 해당 카테고리에 메뉴들 포함시키기
    public Boolean includeMenusByCategoryId(ReqAdminIncludeMenuByCategoryDto dto) {
        // 우선 삭제
        menuCategoryMapper.deleteByCategoryId(dto.getCategoryId());
        if (dto.getCategoryId() == null) {
            return true;
        }
        if (dto.getMenuIds() != null && dto.getMenuIds().size() > 0) {
            menuCategoryMapper.save(dto.getCategoryId(), dto.getMenuIds());
        }
        return true;
    }

    // 카테고리 삭제
    public boolean delete(Long categoryId) {
        Map<String, Object> map = new HashMap<>();
        map.put("categoryId", categoryId);
        map.put("successCount", 0);
        categoryMapper.deleteById(map);
        return (Integer) map.get("successCount") > 0;
    }

    // 카테고리 수정
    public boolean update(ReqAdminCategoryDto dto) {
        try {
            categoryMapper.update(dto.toEntity());
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateNameException("Category name already exist");
        }
        return true;
    }

    // 카테고리 상태수정
    public void updateStatus(Long categoryId) {
        categoryMapper.updateStatus(categoryId);
    }

    // 카테고리 전체조회
    public CMRespAdminDto getAllCategories(ReqAdminPageAndLimitDto dto) {
        Long startIndex = (dto.getPage() - 1) * dto.getLimit();
        List<RespAdminCategoryDto> respAdminCategoryDtos = categoryMapper.getAllLimit(startIndex, dto.getLimit()).stream().map(Category::toCategories).collect(Collectors.toList());
        return new CMRespAdminDto(categoryMapper.getCount(), respAdminCategoryDtos);
    }

    // 카테고리id에 해당하는 메뉴 조회
    public RespAdminOneItems getCategoryById(Long categoryId) {
        Category category = menuCategoryMapper.findAllByCategoryId(categoryId);
        List<RespOnlyMenuIdAdnName> menus = new ArrayList<>();
        if (!category.getMenuList().isEmpty()) {
            menus = category.getMenuList().stream().map(Menu::toRespOnlyIdAndNameDto).collect(Collectors.toList());
        }
        return new RespAdminOneItems(category.toCategories(), menus);
    }

}
