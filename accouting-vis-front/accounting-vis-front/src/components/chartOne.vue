<template>
  <div class="analysis-container">
    <!-- 指标选择器 -->
    <div class="indicator-selector">
      <label>选择分析指标：</label>
      <select v-model="selectedIndicator" @change="handleIndicatorChange">
        <option v-for="indicator in indicators" :key="indicator" :value="indicator">
          {{ indicatorMap[indicator] || indicator }}
        </option>
      </select>
    </div>

    <!-- 图表容器 -->
    <div ref="chart" style="width: 100%; height: 600px;"></div>

    <!-- 状态提示 -->
    <div v-if="loading" class="loading">
      <i class="el-icon-loading"></i>
      数据加载中...
    </div>
    <div v-if="error" class="error">
      <i class="el-icon-error"></i>
      {{ error }}
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'
import axios from 'axios'
import * as echarts from 'echarts'

// 响应式数据
const chart = ref(null)
const loading = ref(false)
const error = ref('')
const selectedIndicator = ref('liquidity-ratio') // 默认选中指标
const indicators = ref(['liquidity-ratio', 'current-ratio', 'roe']) // 示例指标列表
const indicatorMap = {
  'liquidity-ratio': '流动比率',
  'current-ratio': '速动比率',
  'roe': '净资产收益率'
}

// ECharts实例
let myChart = null

// 生命周期钩子
onMounted(() => {
  initChart()
  fetchData()
})

onBeforeUnmount(() => {
  if (myChart) {
    myChart.dispose()
  }
})

// 初始化图表
const initChart = () => {
  myChart = echarts.init(chart.value)
  window.addEventListener('resize', handleResize)
}

// 处理窗口大小变化
const handleResize = () => {
  myChart?.resize()
}

// 指标切换处理
const handleIndicatorChange = () => {
  fetchData()
}

// 获取数据
const fetchData = async () => {
  try {
    loading.value = true
    error.value = ''

    const response = await axios.get(`/api/analysis/${selectedIndicator.value}`)
    processData(response.data)
  } catch (err) {
    handleError(err)
  } finally {
    loading.value = false
  }
}

// 错误处理
const handleError = (error) => {
  console.error('数据获取失败:', error)
  error.value = `数据加载失败: ${error.response?.data?.message || error.message}`
  myChart?.clear()
}

// 处理数据
const processData = (rawData) => {
  // 数据校验
  if (!rawData || rawData.length === 0) {
    error.value = '暂无相关数据'
    return
  }

  // 数据转换
  const companies = [...new Set(rawData.map(item => item.companyName))]
  const years = [...new Set(rawData.map(item => item.year))]
      .map(Number)
      .sort((a, b) => a - b)

  // 生成系列数据
  const seriesData = companies.map(company => ({
    name: company,
    type: 'line',
    smooth: true,
    symbol: 'circle',
    symbolSize: 8,
    data: years.map(year => {
      const record = rawData.find(d =>
          d.companyName === company && d.year === year
      )
      return record ? Number(record.value).toFixed(2) : null
    })
  }))

  // 配置图表选项
  const option = {
    title: {
      text: `${indicatorMap[selectedIndicator.value]}趋势分析`,
      left: 'center',
      textStyle: {
        fontSize: 18,
        fontWeight: 'bold'
      }
    },
    tooltip: {
      trigger: 'axis',
      formatter: params => {
        return params.map(p => `
          ${p.marker}
          <strong>${p.seriesName}</strong><br/>
          年份: ${p.name}<br/>
          值: ${p.value ?? '无数据'}
        `).join('')
      }
    },
    legend: {
      data: companies,
      top: 40,
      type: 'scroll'
    },
    grid: {
      top: 100,
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: years,
      axisLabel: {
        rotate: 45
      }
    },
    yAxis: {
      type: 'value',
      name: indicatorMap[selectedIndicator.value],
      axisLabel: {
        formatter: value => value.toFixed(2)
      }
    },
    series: seriesData,
    dataZoom: [{
      type: 'inside',
      start: 0,
      end: 100
    }, {
      type: 'slider',
      height: 20,
      bottom: 20
    }]
  }

  myChart.setOption(option, true)
}
</script>

<style scoped>
.analysis-container {
  padding: 20px;
  position: relative;
}

.indicator-selector {
  margin-bottom: 20px;
  display: flex;
  align-items: center;
  gap: 10px;
}

select {
  padding: 8px 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
}

.loading, .error {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  padding: 20px;
  background: rgba(255, 255, 255, 0.9);
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  display: flex;
  align-items: center;
  gap: 10px;
}

.error {
  color: #f56c6c;
  border: 1px solid #f56c6c;
}

.loading {
  color: #666;
}
</style>