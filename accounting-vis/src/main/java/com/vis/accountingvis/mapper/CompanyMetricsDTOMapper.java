package com.vis.accountingvis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vis.accountingvis.dto.CompanyMetricsDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.management.MXBean;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Mapper
public interface  CompanyMetricsDTOMapper extends BaseMapper<CompanyMetricsDTO> {

    List<CompanyMetricsDTO> getCompanyMetricsByCompanyName(@Param("company") String company);
}
