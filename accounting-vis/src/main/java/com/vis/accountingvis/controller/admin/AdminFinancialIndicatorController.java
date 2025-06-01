package com.vis.accountingvis.controller.admin;

import com.vis.accountingvis.entity.FinancialIndicator;
import com.vis.accountingvis.service.FinancialIndicatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/financial-indicators")
public class AdminFinancialIndicatorController {

    private final FinancialIndicatorService financialIndicatorService;

    @Autowired
    public AdminFinancialIndicatorController(FinancialIndicatorService financialIndicatorService) {
        this.financialIndicatorService = financialIndicatorService;
    }

    @PostMapping
    public ResponseEntity<FinancialIndicator> createFinancialIndicator(@RequestBody FinancialIndicator indicator) {
        if (indicator.getIndicatorName() == null || indicator.getIndicatorName().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        // 可选: 检查指标名称是否已存在
        // FinancialIndicator existing = financialIndicatorService.getOne(new QueryWrapper<FinancialIndicator>().eq("indicator_name", indicator.getIndicatorName()));
        // if (existing != null) {
        //    return ResponseEntity.status(HttpStatus.CONFLICT).build();
        // }
        boolean saved = financialIndicatorService.save(indicator);
        return saved ? ResponseEntity.status(HttpStatus.CREATED).body(indicator) : ResponseEntity.internalServerError().build();
    }

    @GetMapping
    public ResponseEntity<List<FinancialIndicator>> getAllFinancialIndicators() {
        return ResponseEntity.ok(financialIndicatorService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FinancialIndicator> getFinancialIndicatorById(@PathVariable Integer id) {
        FinancialIndicator indicator = financialIndicatorService.getById(id);
        return indicator != null ? ResponseEntity.ok(indicator) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<FinancialIndicator> updateFinancialIndicator(@PathVariable Integer id, @RequestBody FinancialIndicator indicatorDetails) {
        if (indicatorDetails.getIndicatorName() == null || indicatorDetails.getIndicatorName().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        FinancialIndicator existingIndicator = financialIndicatorService.getById(id);
        if (existingIndicator == null) {
            return ResponseEntity.notFound().build();
        }
        // 可选: 检查更新后的名称是否与其他指标冲突
        indicatorDetails.setIndicatorId(id);
        boolean updated = financialIndicatorService.updateById(indicatorDetails);
        return updated ? ResponseEntity.ok(financialIndicatorService.getById(id)) : ResponseEntity.internalServerError().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFinancialIndicator(@PathVariable Integer id) {
        // 考虑: 删除指标前是否需要检查关联数据 (如 CompanyFinancialData)
        boolean deleted = financialIndicatorService.removeById(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}