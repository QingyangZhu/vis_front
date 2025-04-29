package com.vis.accountingvis.controller;

import com.vis.accountingvis.dto.TurnoverDTO;
import com.vis.accountingvis.service.TurnoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turnover-metrics")
public class TurnoverController {

    private final TurnoverService turnoverService;

    @Autowired
    public TurnoverController(TurnoverService turnoverService) {
        this.turnoverService = turnoverService;
    }

    @GetMapping("/search")
    public List<TurnoverDTO> getTurnoverData(@RequestParam("indicator") String indicator) {
        return turnoverService.getTurnoverData(indicator);
    }
}
