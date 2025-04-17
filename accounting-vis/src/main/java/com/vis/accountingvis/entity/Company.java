package com.vis.accountingvis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("companies")  // 明确映射数据库表名
public class Company {

    @TableId(value = "Company_ID", type = IdType.AUTO)  // 正确指定数据库字段名和主键策略
    private Integer companyId;  // 使用包装类型 Integer

    private String companyName;  // 字段名会自动驼峰转下划线（无需 @TableField）

    // Getters & Setters
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}