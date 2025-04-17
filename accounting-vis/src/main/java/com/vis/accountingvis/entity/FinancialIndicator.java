package com.vis.accountingvis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("financial_indicators")  // 指定对应数据库表名
public class FinancialIndicator {

    @TableId(value = "Indicator_ID", type = IdType.AUTO)  // 自增主键
    private Integer indicatorId;

    @TableField("Indicator_Name")  // 显式映射字段名
    private String indicatorName;

    // Getters & Setters
    public Integer getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(Integer indicatorId) {
        this.indicatorId = indicatorId;
    }

    public String getIndicatorName() {
        return indicatorName;
    }

    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }

    // 可选toString方法（便于调试）
    @Override
    public String toString() {
        return "FinancialIndicator{" +
                "indicatorId=" + indicatorId +
                ", indicatorName='" + indicatorName + '\'' +
                '}';
    }
}