package com.vis.accountingvis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vis.accountingvis.dto.AnalysisDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AnalysisDTOMapper
        extends BaseMapper<AnalysisDTO> {

    List<AnalysisDTO> getFinancialAnalysis(@Param("indicatorName") String indicatorName);
}
