package com.vis.accountingvis.controller.admin;

import com.vis.accountingvis.entity.Company;
import com.vis.accountingvis.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/companies")
public class AdminCompanyController {

    private final CompanyService companyService;

    @Autowired
    public AdminCompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<Company> createCompany(@RequestBody Company company) {
        // 校验公司名称等是否为空
        if (company.getCompanyName() == null || company.getCompanyName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null); // 或者返回更详细的错误信息
        }
        // 检查公司是否已存在 (可选)
        if (companyService.getCompanyByName(company.getCompanyName()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // 名称冲突
        }
        boolean saved = companyService.save(company);
        return saved ? ResponseEntity.status(HttpStatus.CREATED).body(company) : ResponseEntity.internalServerError().build();
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        return ResponseEntity.ok(companyService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Integer id) {
        Company company = companyService.getById(id);
        return company != null ? ResponseEntity.ok(company) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable Integer id, @RequestBody Company companyDetails) {
        if (companyDetails.getCompanyName() == null || companyDetails.getCompanyName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        Company existingCompany = companyService.getById(id);
        if (existingCompany == null) {
            return ResponseEntity.notFound().build();
        }
        // 检查更新后的名称是否与其他公司冲突 (可选, 且需排除自身)
        Company companyWithSameName = companyService.getCompanyByName(companyDetails.getCompanyName());
        if (companyWithSameName != null && !companyWithSameName.getCompanyId().equals(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        companyDetails.setCompanyId(id); // 确保ID被设置以更新正确的记录
        boolean updated = companyService.updateById(companyDetails);
        return updated ? ResponseEntity.ok(companyService.getById(id)) : ResponseEntity.internalServerError().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Integer id) {
        // 考虑：删除公司前是否需要检查关联数据 (如 CompanyFinancialData)
        boolean deleted = companyService.removeById(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}