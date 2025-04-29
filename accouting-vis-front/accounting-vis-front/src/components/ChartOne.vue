<template>
  <div class="module" @mouseover="handleMouseOver" @mouseleave="handleMouseLeave">
    <div class="select-wrapper">
      <label>选择分析指标：</label>
      <select v-model="selectedIndicator" class="custom-select" @change="handleIndicatorChange" :disabled="loading">
        <option v-for="indicator in indicators" :key="indicator" :value="indicator">
          {{ indicatorMap[indicator] || indicator }}
        </option>
      </select>
    </div>

    <div v-if="loading" class="status-container loading">
      <i class="el-icon-loading"></i> 数据加载中...
    </div>

    <div v-if="error" class="status-container error">
      <i class="el-icon-error"></i> {{ error }}
    </div>

    <div ref="chart" v-show="!loading && !error" style="width: 100%; height: 400px;"></div>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount, nextTick } from 'vue'
import axios from 'axios'
import * as echarts from 'echarts'

const chart = ref(null)
const myChart = ref(null)
const loading = ref(false)
const error = ref('')
const selectedIndicator = ref('liquidity-ratio')
const indicators = ref(['liquidity-ratio', 'current-ratio', 'roe'])
const indicatorMap = {
  'liquidity-ratio': '流动比率',
  'current-ratio': '速动比率',
  'roe': '净资产收益率'
}

onMounted(async () => {
  await nextTick()
  initChart()
  fetchData()
  myChart.value?.resize()
})

onBeforeUnmount(() => {
  window.removeEventListener('resize', handleResize)
  myChart?.dispose()
})

const initChart = () => {
  myChart.value = echarts.init(chart.value)
  window.addEventListener('resize', handleResize)
}

const handleResize = () => {
  myChart?.resize()
}

const handleIndicatorChange = () => {
  fetchData()
}

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

const processData = (rawData) => {
  if (!rawData || rawData.length === 0) {
    error.value = '暂无相关数据'
    myChart.value?.clear()
    return
  }

  const companies = [...new Set(rawData.map(item => item.companyName))]
  const years = [...new Set(rawData.map(item => item.year))].sort((a, b) => a - b)

  const seriesData = companies.map(company => ({
    name: company,
    type: 'line',
    smooth: true,
    symbol: 'circle',
    symbolSize: 8,
    data: years.map(year => {
      const record = rawData.find(d => d.companyName === company && d.year === year)
      return record ? Number(record.value).toFixed(2) : null
    })
  }))

  const option = {
    color: ['#4e79a7', '#f28e2c', '#e15759', '#76b7b2'],
    backgroundColor: 'transparent',
    title: {
      text: `${indicatorMap[selectedIndicator.value]}趋势分析`,
      left: 'center',
      textStyle: { fontSize: 18, fontWeight: 'bold', color: '#fff' }
    },
    tooltip: {
      trigger: 'axis',
      backgroundColor: 'rgba(50,50,50,0.7)',
      textStyle: { color: '#fff' }
    },
    legend: {
      data: companies,
      top: 40,
      textStyle: { color: '#ddd' }
    },
    grid: {
      top: 100,
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: years,
      axisLabel: { color: '#ddd', rotate: 45 },
      axisLine: { lineStyle: { color: '#ccc' } }
    },
    yAxis: {
      type: 'value',
      name: indicatorMap[selectedIndicator.value],
      axisLabel: { color: '#ddd' },
      splitLine: { lineStyle: { color: 'rgba(255,255,255,0.1)' } }
    },
    series: seriesData,
    dataZoom: [{ type: 'inside', start: 0, end: 100 }]
  }

  myChart.value.setOption(option, true)
}

const handleError = (error) => {
  console.error('数据获取失败:', error)
  error.value = `数据加载失败: ${error.response?.data?.message || error.message}`
  myChart?.clear()
}

const handleMouseOver = () => {
  chart.value.parentElement.style.transform = 'scale(1.05)'
}
const handleMouseLeave = () => {
  chart.value.parentElement.style.transform = 'scale(1)'
}
</script>

<style scoped>
/* 统一模块、状态提示、选择器样式 */
@import '@/styles/module-common.css';
</style>
