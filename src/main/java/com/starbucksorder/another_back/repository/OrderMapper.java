package com.starbucksorder.another_back.repository;

import com.starbucksorder.another_back.dto.admin.response.Sales.RespAdminDashBoard;
import com.starbucksorder.another_back.dto.admin.response.Sales.RespAdminSaleAndMenusDto;
import com.starbucksorder.another_back.dto.admin.response.Sales.RespAdminSaleListDto;
import com.starbucksorder.another_back.dto.admin.response.Sales.RespSaleDto;
import com.starbucksorder.another_back.domain.Order;
import org.apache.ibatis.annotations.Mapper;

import java.time.Year;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {
    // 주문 저장
    int save(Order order);

    // 주문취소(주문상태바꾸기)
    int updateStatus(Long orderId);

    // FIXME: 사용되지 않는 로직인 거 같은데 확인 후 삭제하기
    // Long findOrderIdByUserId(Long userId);

    // 날짜를 기준으로 order 전체 들고오기
    List<Order> findByDate(Map<String, Object> map);

    // 총 매출
    RespSaleDto findByDateForSale(Year selectYear);

    // 대시보드 월별 조회
    List<RespAdminDashBoard.RespMonthly> monthlyRevenue(Year selectYear);

    // 대시보드 최다 품목 판매 순위
    List<RespAdminDashBoard.RespMostMenu> mostMenus(Year selectYear);

    // 관리자 매출관리 조회
    List<RespAdminSaleListDto> getSale(Map<String, Object> map);

    // 날짜에 해당되는 갯수 다 들고오기
    int countByDate(Map<String, Object> map);

    // 매출관리에서 사용될 totalCount
    int totalSaleCount(Map<String, Object> map);

    Order findOrderById(Long orderId);

    List<Integer> getYearCount();
    // 관리자 총 매출 상세보기
    List<RespAdminSaleAndMenusDto.wtf> getByDate(Map<String,Object> map);

    List<RespAdminSaleAndMenusDto.Menus> getMostMenus(Map<String,Object> map);
}
