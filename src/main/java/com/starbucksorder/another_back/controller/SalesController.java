package com.starbucksorder.another_back.controller;

import com.starbucksorder.another_back.aspect.annotation.Log;
import com.starbucksorder.another_back.dto.admin.request.order.ReqAdminOrderDto;
import com.starbucksorder.another_back.service.SalesService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Year;

@RestController
@RequestMapping("/admin/sales")
public class SalesController {

    @Autowired
    private SalesService salesService;

    // NOTE: 매출 관련 컨트롤러
    @ApiOperation(value = "매출관리에서의 전체조회")
    @GetMapping()
    public ResponseEntity<?> getSales(ReqAdminOrderDto dto) {
        return ResponseEntity.ok().body(salesService.getSales(dto));
    }

    @ApiOperation(value = "관리자페이지 접속시 나올 대시보드 매출에관한 전체조회")
    @GetMapping("/manage/{selectYear}/dashboard")
    public ResponseEntity<?> getStatistics(@PathVariable(required = false) String selectYear) {
        return ResponseEntity.ok().body(salesService.getStatistics(selectYear));
    }

}