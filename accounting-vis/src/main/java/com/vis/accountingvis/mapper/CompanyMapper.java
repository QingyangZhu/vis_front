package com.vis.accountingvis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vis.accountingvis.entity.Company;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CompanyMapper extends BaseMapper<Company> {
    Company selectCompanyByName(String name);
}
