<template>
  <div class="module" @mouseover="handleMouseOver" @mouseleave="handleMouseLeave">
    <div class="select-wrapper">
      <select v-model="selectedIndicator" class="custom-select" @change="fetchData" :disabled="loading">
        <option v-for="indicator in indicators" :key="indicator" :value="indicator">
          {{ indicator }}
        </option>
      </select>
      <h2 class="legend">存货周转率、总资产周转率、应收账款周转率</h2>
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

const selectedIndicator = ref('存货周转率')
const indicators = ['存货周转率', '总资产周转率', '应收账款周转率']

const years = ['2018', '2019', '2020', '2021', '2022', '2023']
const companies = ref([]) // 公司列表动态拉取
const financialData = ref({}) // 财务数据

// 生命周期
onMounted(async () => {
  await nextTick()
  initChart()
  await fetchCompanies()
  await fetchData()
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

// 获取财务数据
const fetchData = async () => {
  try {
    loading.value = true
    error.value = ''

    const response = await axios.get('/api/turnover-metrics/search', {
      params: { indicator: selectedIndicator.value }
    })

    processData(response.data)
  } catch (err) {
    handleError(err, '获取周转率数据失败')
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

  // 格式化成 {公司: [每年对应的value]}
  const formattedData = {}
  companies.value.forEach(company => {
    formattedData[company] = Array(years.length).fill(0) // 初始填充0，避免null
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

  const selectedData = financialData.value

  const series = companies.value.map(company => ({
    name: company,
    type: 'scatter',
    data: years.map((year, index) => {
      const value = selectedData[company]?.[index] ?? 0
      return [year, company, value]
    }),
    symbolSize: (val) => val[2] ? Math.sqrt(val[2]) * 6 : 0,
    emphasis: { label: { show: false } }
  }))

  const option = {
    backgroundColor: 'transparent',
    color: ['#4e79a7', '#f28e2c', '#e15759', '#76b7b2', '#59a14f', '#edc949'],
    tooltip: {
      trigger: 'item',
      backgroundColor: 'rgba(50,50,50,0.8)',
      textStyle: { color: '#fff' },
      formatter: params => `
        <strong>${params.seriesName}</strong><br/>
        年份: ${params.data[0]}<br/>
        ${selectedIndicator.value}: ${params.data[2]}
      `
    },
    legend: {
      data: companies.value,
      top: 30,
      textStyle: { color: '#ddd' }
    },
    grid: { top: 80, bottom: 80, left: 60, right: 60 },
    visualMap: {
      show: true,
      right: 10,
      top: 60,
      dimension: 2,
      min: 0,
      max: 20,
      text: ['高', '低'],
      textStyle: { color: '#ccc' },
      inRange: { symbolSize: [10, 50] }
    },
    xAxis: {
      type: 'category',
      name: '年份',
      data: years,
      axisLabel: { color: '#ddd' },
      axisLine: { lineStyle: { color: '#888' } }
    },
    yAxis: {
      type: 'category',
      name: '公司',
      data: companies.value,
      axisLabel: { color: '#ddd' },
      axisLine: { lineStyle: { color: '#888' } }
    },
    series
  }

  myChart.value.setOption(option, true)
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
