<template>
  <div class="module" @mouseover="handleMouseOver" @mouseleave="handleMouseLeave">
    <div class="select-wrapper">
      <select v-model="selectedMetric" class="custom-select" @change="fetchData" :disabled="loading">
        <option v-for="metric in metrics" :key="metric" :value="metric">
          {{ metric }}
        </option>
      </select>
      <h2 class="legend">销售增长率、净利润增长率、总资产增长率</h2>
    </div>

    <div v-if="loading" class="status-container loading">
      <i class="el-icon-loading"></i> 数据加载中...
    </div>

    <div v-if="error" class="status-container error">
      <i class="el-icon-error"></i> {{ error }}
    </div>

    <div ref="chart" v-show="!loading && !error" style="width: 100%; height: 500px;"></div>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount, nextTick } from 'vue'
import axios from 'axios'
import * as echarts from 'echarts'

// 状态变量
const chart = ref(null)
const myChart = ref(null)
const loading = ref(false)
const error = ref('')

const selectedMetric = ref('销售增长率')
const metrics = ['销售增长率', '净利润增长率', '总资产增长率']

const years = ['2018', '2019', '2020', '2021', '2022', '2023']
const companies = ref([])
const financialData = ref({})

// 生命周期钩子
onMounted(async () => {
  await nextTick()
  initChart()
  await fetchCompanies()
  await fetchData()
})

onBeforeUnmount(() => {
  window.removeEventListener('resize', handleResize)
  myChart.value?.dispose()
})

const initChart = () => {
  myChart.value = echarts.init(chart.value)
  window.addEventListener('resize', handleResize)
}

const handleResize = () => {
  myChart.value?.resize()
}

const handleMouseOver = () => {
  chart.value.parentElement.style.transform = 'scale(1.05)'
}
const handleMouseLeave = () => {
  chart.value.parentElement.style.transform = 'scale(1)'
}

// 获取公司列表
const fetchCompanies = async () => {
  try {
    const response = await axios.get('/api/companies')
    companies.value = response.data.map(c => c.companyName)
  } catch (err) {
    console.error('获取公司列表失败:', err)
    error.value = '获取公司列表失败'
  }
}

// 获取增长率数据
const fetchData = async () => {
  try {
    loading.value = true
    error.value = ''

    const response = await axios.get('/api/growth-metrics/search', {
      params: { metric: selectedMetric.value }
    })

    processData(response.data)
  } catch (err) {
    handleError(err, '获取增长率数据失败')
  } finally {
    loading.value = false
  }
}

// 处理数据
const processData = (data) => {
  if (!data || data.length === 0) {
    error.value = '暂无相关数据'
    myChart.value?.clear()
    return
  }

  const formattedData = {}
  companies.value.forEach(company => {
    formattedData[company] = Array(years.length).fill(null)
  })

  data.forEach(item => {
    const yearIndex = years.indexOf(item.year.toString())
    if (yearIndex !== -1 && formattedData[item.companyName]) {
      formattedData[item.companyName][yearIndex] = Number(item.value)
    }
  })

  financialData.value = formattedData
  updateChart()
}

// 更新图表
const updateChart = () => {
  if (!myChart.value) return

  const seriesData = companies.value.map(company => ({
    name: company,
    type: 'line',
    smooth: true,
    symbol: 'circle',
    symbolSize: 8,
    data: financialData.value[company] || []
  }))

  const option = {
    backgroundColor: 'transparent',
    color: ['#4e79a7', '#f28e2c', '#e15759', '#76b7b2', '#59a14f', '#edc949'],
    title: {
      text: `${selectedMetric.value}`,
      left: 'center',
      textStyle: { fontSize: 18, fontWeight: 'bold', color: '#fff' }
    },
    tooltip: {
      trigger: 'axis',
      backgroundColor: 'rgba(50,50,50,0.8)',
      textStyle: { color: '#fff' },
      formatter: params => params.map(p => `
        ${p.marker}
        <strong>${p.seriesName}</strong><br/>
        年份: ${p.name}<br/>
        增长率: ${p.value != null ? (p.value * 100).toFixed(2) + '%' : '无数据'}
      `).join('')
    },
    legend: {
      data: companies.value,
      top: 30,
      textStyle: { color: '#ddd' }
    },
    grid: { top: 100, bottom: 120, left: 60, right: 60 },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: years,
      axisLabel: { color: '#ddd', rotate: 45 },
      axisLine: { lineStyle: { color: '#888' } }
    },
    yAxis: {
      type: 'value',
      axisLabel: { color: '#ddd', formatter: value => (value * 100).toFixed(0) + '%' },
      axisLine: { lineStyle: { color: '#888' } },
      splitLine: { lineStyle: { color: 'rgba(255,255,255,0.1)' } }
    },
    brush: {
      toolbox: ['rect', 'polygon', 'lineX', 'lineY', 'keep', 'clear'],
      xAxisIndex: 0
    },
    toolbox: {
      feature: {
        brush: {
          type: ['rect', 'polygon', 'lineX', 'lineY', 'keep', 'clear']
        },
        dataView: {},
        restore: {}
      },
      right: '10%',
      top: 'bottom'
    },
    series: seriesData
  }

  myChart.value.setOption(option, true)

  // 设置 brush 选中监听器
  myChart.value.off('brushSelected') // 避免重复绑定
  myChart.value.on('brushSelected', function (params) {
    const brushed = []
    const brushComponent = params.batch[0]
    for (let sIdx = 0; sIdx < brushComponent.selected.length; sIdx++) {
      const rawIndices = brushComponent.selected[sIdx].dataIndex
      if (rawIndices && rawIndices.length) {
        brushed.push(`[系列 ${companies.value[sIdx] || sIdx}] ${rawIndices.join(', ')}`)
      }
    }
    myChart.value.setOption({
      title: {
        backgroundColor: '#333',
        text: `选中数据索引：\n${brushed.join('\n')}`,
        bottom: 0,
        right: '10%',
        width: 150,
        textStyle: {
          fontSize: 12,
          color: '#fff'
        }
      }
    })
  })
}

// 错误处理
const handleError = (err, customMessage) => {
  console.error(customMessage, err)
  error.value = `${customMessage}: ${err.response?.data?.message || err.message}`
  myChart.value?.clear()
}
</script>

<style scoped>
@import '@/styles/module-common.css';
</style>
