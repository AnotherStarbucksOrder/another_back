package com.starbucksorder.another_back.service;

import com.starbucksorder.another_back.dto.admin.ReqAdminPageAndLimitDto;
import com.starbucksorder.another_back.dto.admin.request.option.ReqAdminOptionDto;
import com.starbucksorder.another_back.dto.admin.response.CMRespAdminDto;
import com.starbucksorder.another_back.dto.admin.response.option.RespAdminOptionDto;
import com.starbucksorder.another_back.dto.user.response.menu.RespOnlyMenuIdAdnName;
import com.starbucksorder.another_back.entity.Menu;
import com.starbucksorder.another_back.entity.Option;
import com.starbucksorder.another_back.entity.OptionDetail;
import com.starbucksorder.another_back.repository.OptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OptionService {
    @Autowired
    private OptionMapper optionMapper;
    @Autowired
    private DuplicateService duplicateService;

    // 옵션 추가
    @Transactional(rollbackFor = SQLException.class)
    public boolean addOption(ReqAdminOptionDto dto) {
        // 중복검사
        duplicateService.isDuplicateName("option", dto.getOptionName());
        Option option = dto.toEntity();

        try {
            optionMapper.save(option);
            for (OptionDetail detail : dto.getOptionDetail()) {
                detail.setOptionId(option.getOptionId());
                optionMapper.detailSave(detail);
            }

//            for (Map.Entry<String, Integer> entry : dto.getOptionDetail().entrySet()) {
////                optionMapper.detailSave(OptionDetail.builder().optionId(option.getOptionId()).optionDetailValue(entry.getKey()).optionDetailPrice(entry.getValue()).build());
//                optionMapper.detailSave(dto.toDetailEntity(option.getOptionId(),entry.getKey(),entry.getValue()));
//            }
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("데이터베이스 요청 오류" + e.getMessage());
        }
        return true;
    }

    // 옵션 전체 불러오기
    public CMRespAdminDto getAllOptions(ReqAdminPageAndLimitDto dto) {
        Long startIndex = (dto.getPage() - 1) * dto.getLimit();
        List<RespAdminOptionDto> respAdminOptionDtos = optionMapper.getAll(startIndex, dto.getLimit()).stream().map(Option::toOptionAllDto).collect(Collectors.toList());
        return new CMRespAdminDto(optionMapper.getCount(), respAdminOptionDtos);
    }

    // 옵션에 해당하는 메뉴들 불러오기
//    public List<RespOnlyMenuIdAdnName> getAllByOptionIds(ReqAdminOptionsDto dto) {
    public List<RespOnlyMenuIdAdnName> getAllByOptionIds(List<Long> ids) {
//        return optionMapper.getAllByOptionId(dto.getOptionIds()).stream().map(Menu::toRespOnlyIdAndNameDto).collect(Collectors.toList());
        return optionMapper.getAllByOptionId(ids).stream().map(Menu::toRespOnlyIdAndNameDto).collect(Collectors.toList());
    }

    // 옵션 단 건 조회
    public Option getById(Long optionId) {
        return optionMapper.findByOptionId(optionId);
    }

    // 옵션 삭제만
    public boolean delete(Long optionId) {
        Map<String, Object> map = new HashMap<>();
        map.put("optionId", optionId);
        map.put("successCount", 0);
        optionMapper.deleteByOptionId(map);
        return (Integer) map.get("successCount") > 0;
    }

    // 옵션 수정
    @Transactional(rollbackFor = SQLException.class)
    public boolean update(ReqAdminOptionDto dto) {
        Option option = dto.toEntity();
        optionMapper.update(option);
        optionMapper.deleteDetailById(option.getOptionId());
        for (OptionDetail detail : dto.getOptionDetail()) {
            detail.setOptionId(option.getOptionId());
            optionMapper.detailSave(detail);
        }
        /*for (Map.Entry<String, Integer> entry : dto.getOptionDetail().entrySet()) {
            optionMapper.detailSave(dto.toDetailEntity(option.getOptionId(),entry.getKey(),entry.getValue()));
        }*/
        return true;
    }

    // 옵션 상태수정
    public boolean updateStatus(Long optionId) {
        return optionMapper.updateStatus(optionId) > 0;
    }
}
