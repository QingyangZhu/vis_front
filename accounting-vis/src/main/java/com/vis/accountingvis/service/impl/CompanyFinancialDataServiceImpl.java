package com.vis.accountingvis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vis.accountingvis.entity.CompanyFinancialData;
import com.vis.accountingvis.mapper.CompanyFinancialDataMapper;
import com.vis.accountingvis.service.CompanyFinancialDataService;
import org.springframework.stereotype.Service; // 新增导入
import java.util.List; // 新增导入

@Service // 添加 @Service 注解
public class CompanyFinancialDataServiceImpl extends ServiceImpl<CompanyFinancialDataMapper, CompanyFinancialData> implements CompanyFinancialDataService {

    @Override
    public List<CompanyFinancialData> findByCompanyIdAndYear(Integer companyId, Integer year) {
        // CompanyFinancialDataMapper 中已定义 selectByCompanyAndYear
        // return baseMapper.selectByCompanyAndYear(companyId, year);
        // 或者使用 QueryWrapper (如果 baseMapper 没有该方法或想统一风格)
        return list(new QueryWrapper<CompanyFinancialData>()
                .eq("company_id", companyId)
                .eq("year", year));
    }
}