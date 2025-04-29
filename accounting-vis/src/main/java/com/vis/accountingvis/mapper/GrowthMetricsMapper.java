package com.vis.accountingvis.mapper;

import com.vis.accountingvis.dto.GrowthDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface GrowthMetricsMapper {
    List<GrowthDTO> getGrowthData(@Param("metric") String metric);
}
