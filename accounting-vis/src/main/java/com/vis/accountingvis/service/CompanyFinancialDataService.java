package com.vis.accountingvis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.vis.accountingvis.entity.CompanyFinancialData;
import java.util.List;

public interface CompanyFinancialDataService extends IService<CompanyFinancialData> {
    List<CompanyFinancialData> findByCompanyIdAndYear(Integer companyId, Integer year);
}