package com.vis.accountingvis.service;

import com.vis.accountingvis.dto.TurnoverDTO;

import java.util.List;

public interface TurnoverService {
    List<TurnoverDTO> getTurnoverData(String indicatorName);
}
