package com.starbucksorder.another_back.repository;

import com.starbucksorder.another_back.domain.Menu;
import com.starbucksorder.another_back.domain.Option;
import com.starbucksorder.another_back.domain.OptionDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface OptionMapper {
    // 중복검사를 위한 것
    Option findByOptionName(String optionName);

    // 옵션 이름 추가
    int save(Option option);

    // 부가옵션 추가
    int detailSave(OptionDetail optionDetail);

    // 전체 불러오기
    List<Option> getAll(Long startIndex, Long limit);

    // 전체 갯수 불러오기
    int getCount();

    // 옵션 아이디에 해당하는 정보 불러오기 (디테일까지)
    Option findByOptionId(Long optionId);

    // 옵션아이디에 해당하는 메뉴 전체 불러오기
    List<Menu> getAllByOptionId(List<Long> optionIds);

    // 옵션 삭제
    int deleteByOptionId(Map<String, Object> map);

    // 옵션 수정
    int update(Option option);

    // 옵션 수정에 대한 디테일 삭제
    int deleteDetailById(Long optionId);

    // 상태수정
    int updateStatus(Long optionId);
}
