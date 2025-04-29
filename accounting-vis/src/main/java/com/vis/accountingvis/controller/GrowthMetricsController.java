package com.vis.accountingvis.controller;

import com.vis.accountingvis.dto.GrowthDTO;
import com.vis.accountingvis.service.GrowthMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/growth-metrics")
public class GrowthMetricsController {

    private final GrowthMetricsService growthMetricsService;

    @Autowired
    public GrowthMetricsController(GrowthMetricsService growthMetricsService) {
        this.growthMetricsService = growthMetricsService;
    }

    @GetMapping("/search")
    public List<GrowthDTO> getGrowthMetrics(@RequestParam("metric") String metric) {
        return growthMetricsService.getGrowthData(metric);
    }
}
