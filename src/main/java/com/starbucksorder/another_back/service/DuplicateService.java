package com.starbucksorder.another_back.service;

import com.starbucksorder.another_back.aspect.LogAspect;
import com.starbucksorder.another_back.aspect.annotation.Log;
import com.starbucksorder.another_back.exception.DuplicateNameException;
import com.starbucksorder.another_back.mapper.MenuMapper;
import com.starbucksorder.another_back.repository.CategoryMapper;
import com.starbucksorder.another_back.repository.OptionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DuplicateService {

    private final OptionMapper optionMapper;
    private final MenuMapper menuMapper;
    private final CategoryMapper categoryMapper;
    private final LogAspect logAspect;

    @Log
    public boolean isDuplicateName(String mapperName, String name) {
        boolean isDuplicate = switch (mapperName) {
            case "menu" -> menuMapper.findByMenuName(name) != null;
            case "category" -> categoryMapper.findByCategoryName(name) != null;
            case "option" -> optionMapper.findByOptionName(name) != null;
            default -> throw new IllegalArgumentException("Invalid mapper name");
        };
        if (isDuplicate) {
            throw new DuplicateNameException(name + " is Duplicate By " + mapperName);
        }
        return true;
    }
}
