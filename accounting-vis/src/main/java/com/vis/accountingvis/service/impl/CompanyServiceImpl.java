package com.vis.accountingvis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vis.accountingvis.entity.Company;
import com.vis.accountingvis.mapper.CompanyMapper;
import com.vis.accountingvis.service.CompanyService;
import org.springframework.stereotype.Service; // 新增导入

@Service // 添加 @Service 注解
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {

    @Override
    public Company getCompanyByName(String name) {
        // 使用 CompanyMapper 中已有的 selectCompanyByName 方法
        // return baseMapper.selectCompanyByName(name);
        // 或者使用 QueryWrapper (如果 baseMapper 没有该方法或想统一风格)
        return getOne(new QueryWrapper<Company>().eq("company_name", name));
    }
}