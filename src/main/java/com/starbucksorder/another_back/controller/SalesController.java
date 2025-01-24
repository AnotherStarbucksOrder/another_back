package com.starbucksorder.another_back.controller;

import com.starbucksorder.another_back.dto.admin.request.ReqAdminSalePageDateDto;
import com.starbucksorder.another_back.service.SalesService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class SalesController {

    @Autowired
    private SalesService salesService;

    // NOTE: 매출 관련 컨트롤러

    @Operation(summary = "매출관리-전체조회")
    @GetMapping("/sales")
    public ResponseEntity<?> getSales(ReqAdminSalePageDateDto dto) {
        return ResponseEntity.ok().body(salesService.getSales(dto));
    }

    @Operation(summary = "매출관리-날짜조회")
    @GetMapping("/sale")
    public ResponseEntity<?> getSales(String date) {
        return ResponseEntity.ok().body(salesService.getDetail(date));
    }

    @Operation(summary = "매출 관련 대시보드 전체조회", description = "선택된 년도에 대한 매출 전체조회")
    @GetMapping("/sales/manage/{selectYear}/dashboard")
    public ResponseEntity<?> getStatistics(@PathVariable(required = false) String selectYear) {
        return ResponseEntity.ok().body(salesService.getStatistics(selectYear));
    }
}