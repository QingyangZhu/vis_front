package com.vis.accountingvis.service;

import com.vis.accountingvis.dto.GrowthDTO;

import java.util.List;

public interface GrowthMetricsService {
    List<GrowthDTO> getGrowthData(String metric);
}
