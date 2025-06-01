package com.vis.accountingvis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vis.accountingvis.entity.FinancialIndicator;
import com.vis.accountingvis.mapper.FinancialIndicatorMapper;
import com.vis.accountingvis.service.FinancialIndicatorService;
import org.springframework.stereotype.Service; // 新增导入

@Service // 添加 @Service 注解
public class FinancialIndicatorServiceImpl extends ServiceImpl<FinancialIndicatorMapper, FinancialIndicator> implements FinancialIndicatorService {
    // IService 提供了基础的 save, updateById, removeById, getById, list 方法
}