package com.vis.accountingvis.service.impl;

import com.vis.accountingvis.dto.GrowthDTO;
import com.vis.accountingvis.mapper.GrowthMetricsMapper;
import com.vis.accountingvis.service.GrowthMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrowthMetricsServiceImpl implements GrowthMetricsService {

    private final GrowthMetricsMapper growthMetricsMapper;

    @Autowired
    public GrowthMetricsServiceImpl(GrowthMetricsMapper growthMetricsMapper) {
        this.growthMetricsMapper = growthMetricsMapper;
    }

    @Override
    public List<GrowthDTO> getGrowthData(String metric) {
        return growthMetricsMapper.getGrowthData(metric);
    }
}
