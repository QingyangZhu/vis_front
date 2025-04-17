package com.vis.accountingvis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vis.accountingvis.entity.FinancialIndicator;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FinancialIndicatorMapper extends BaseMapper<FinancialIndicator> {
    // 根据指标名称查询（类似CompanyMapper的命名风格）
    FinancialIndicator selectByIndicatorName(String indicatorName);
}