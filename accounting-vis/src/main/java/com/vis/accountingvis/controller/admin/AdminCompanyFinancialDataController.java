package com.vis.accountingvis.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vis.accountingvis.entity.CompanyFinancialData;
import com.vis.accountingvis.service.CompanyFinancialDataService;
// 引入 CompanyService 和 FinancialIndicatorService 用于校验ID是否存在
import com.vis.accountingvis.service.CompanyService;
import com.vis.accountingvis.service.FinancialIndicatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/company-financial-data")
public class AdminCompanyFinancialDataController {

    private final CompanyFinancialDataService dataService;
    private final CompanyService companyService; // 用于校验 Company ID
    private final FinancialIndicatorService indicatorService; // 用于校验 Indicator ID

    @Autowired
    public AdminCompanyFinancialDataController(CompanyFinancialDataService dataService,
                                               CompanyService companyService,
                                               FinancialIndicatorService indicatorService) {
        this.dataService = dataService;
        this.companyService = companyService;
        this.indicatorService = indicatorService;
    }

    @PostMapping
    public ResponseEntity<?> createCompanyFinancialData(@RequestBody CompanyFinancialData data) {
        // 基础校验
        if (data.getCompanyId() == null || data.getIndicatorId() == null || data.getYear() == null || data.getFinancialDataValue() == null) {
            return ResponseEntity.badRequest().body("公司ID, 指标ID, 年份和数据值不能为空");
        }
        // 校验公司ID和指标ID是否存在
        if (companyService.getById(data.getCompanyId()) == null) {
            return ResponseEntity.badRequest().body("无效的公司ID: " + data.getCompanyId());
        }
        if (indicatorService.getById(data.getIndicatorId()) == null) {
            return ResponseEntity.badRequest().body("无效的指标ID: " + data.getIndicatorId());
        }

        // 校验是否已存在相同记录 (CompanyID, Year, IndicatorID 联合唯一)
        CompanyFinancialData existing = dataService.getOne(new QueryWrapper<CompanyFinancialData>()
                .eq("company_id", data.getCompanyId())
                .eq("year", data.getYear())
                .eq("indicator_id", data.getIndicatorId()));
        if (existing != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("该公司同年份同指标的数据已存在 (Data_ID: " + existing.getDataId() + ")");
        }

        boolean saved = dataService.save(data);
        return saved ? ResponseEntity.status(HttpStatus.CREATED).body(data) : ResponseEntity.internalServerError().body("保存失败");
    }

    @GetMapping
    public ResponseEntity<List<CompanyFinancialData>> getAllData(@RequestParam(required = false) Integer companyId,
                                                                 @RequestParam(required = false) Integer year) {
        if (companyId != null && year != null) {
            return ResponseEntity.ok(dataService.findByCompanyIdAndYear(companyId, year));
        }
        // 警告: 无参数调用 list() 可能返回大量数据，生产环境应分页
        return ResponseEntity.ok(dataService.list());
    }

    @GetMapping("/{dataId}")
    public ResponseEntity<CompanyFinancialData> getDataById(@PathVariable Integer dataId) {
        CompanyFinancialData data = dataService.getById(dataId);
        return data != null ? ResponseEntity.ok(data) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{dataId}")
    public ResponseEntity<?> updateCompanyFinancialData(@PathVariable Integer dataId, @RequestBody CompanyFinancialData dataDetails) {
        if (dataDetails.getFinancialDataValue() == null) {
            return ResponseEntity.badRequest().body("财务数据值不能为空");
        }
        CompanyFinancialData existingData = dataService.getById(dataId);
        if (existingData == null) {
            return ResponseEntity.notFound().build();
        }
        // 通常不允许修改 companyId, year, indicatorId，只修改 value
        existingData.setFinancialDataValue(dataDetails.getFinancialDataValue());

        boolean updated = dataService.updateById(existingData);
        return updated ? ResponseEntity.ok(dataService.getById(dataId)) : ResponseEntity.internalServerError().body("更新失败");
    }

    @DeleteMapping("/{dataId}")
    public ResponseEntity<Void> deleteCompanyFinancialData(@PathVariable Integer dataId) {
        boolean deleted = dataService.removeById(dataId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}