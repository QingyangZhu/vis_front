package com.vis.accountingvis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.vis.accountingvis.entity.Company;

public interface CompanyService extends IService<Company> {
    Company getCompanyByName(String name);
}
