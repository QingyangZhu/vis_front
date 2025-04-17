package com.vis.accountingvis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vis.accountingvis.entity.CompanyFinancialData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CompanyFinancialDataMapper extends BaseMapper<CompanyFinancialData> {
    // 根据公司ID和年份组合查询
    List<CompanyFinancialData> selectByCompanyAndYear(
            @Param("companyId") Integer companyId,
            @Param("year") Integer year
    );
}