package com.vis.accountingvis.mapper;

import com.vis.accountingvis.dto.TurnoverDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TurnoverMapper {
    List<TurnoverDTO> getTurnoverData(@Param("indicatorName") String indicatorName);
}
