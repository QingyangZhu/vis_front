package com.vis.accountingvis.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class TurnoverDTO {
    private String companyName;
    private Integer year;
    private BigDecimal value;
}
