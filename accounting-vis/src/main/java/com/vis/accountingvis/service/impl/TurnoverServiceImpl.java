package com.vis.accountingvis.service.impl;

import com.vis.accountingvis.dto.TurnoverDTO;
import com.vis.accountingvis.mapper.TurnoverMapper;
import com.vis.accountingvis.service.TurnoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnoverServiceImpl implements TurnoverService {

    private final TurnoverMapper turnoverMapper;

    @Autowired
    public TurnoverServiceImpl(TurnoverMapper turnoverMapper) {
        this.turnoverMapper = turnoverMapper;
    }

    @Override
    public List<TurnoverDTO> getTurnoverData(String indicatorName) {
        return turnoverMapper.getTurnoverData(indicatorName);
    }
}
