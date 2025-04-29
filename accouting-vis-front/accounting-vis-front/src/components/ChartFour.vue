<template>
  <div class="module" @mouseover="handleMouseOver" @mouseleave="handleMouseLeave">
    <div class="select-wrapper">
      <select v-model="selectedYear" class="custom-select" @change="updateChart" :disabled="loading">
        <option v-for="year in years" :key="year" :value="year">{{ year }}</option>
      </select>
      <h2 class="legend">净利率、净资产收益率、总资产报酬率</h2>
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
import * as echarts from 'echarts'
import axios from 'axios'

const chart = ref(null)
const myChart = ref(null)
const loading = ref(false)
const error = ref('')

const selectedYear = ref('2018')
const years = [2018, 2019, 2020, 2021, 2022]

const companyList = ref([]) // 动态公司列表
const rawData = ref({
  '净利率': [],
  '净资产收益率': [],
  '总资产报酬率': []
})

const api = axios.create({
  baseURL: '/api/',
  timeout: 10000
})

onMounted(async () => {
  await nextTick()
  initChart()
  await fetchCompanies()
  await fetchAllData()
  myChart.value.resize()
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
    loading.value = true
    const response = await api.get('/companies')
    companyList.value = response.data.map(c => c.companyName)
  } catch (err) {
    handleError(err, '获取公司列表失败')
  } finally {
    loading.value = false
  }
}

// 获取各指标数据
const fetchAllData = async () => {
  try {
    loading.value = true
    error.value = ''
    const [profitMarginRes, roeRes, roaRes] = await Promise.all([
      api.get('/analysis/profit-margin'),
      api.get('/analysis/roe'),
      api.get('/analysis/roa')
    ])

    rawData.value['净利率'] = profitMarginRes.data
    rawData.value['净资产收益率'] = roeRes.data
    rawData.value['总资产报酬率'] = roaRes.data

    updateChart()
  } catch (err) {
    handleError(err, '获取财务数据失败')
  } finally {
    loading.value = false
  }
}

// 更新图表
const updateChart = () => {
  if (!myChart.value) return

  const selected = selectedYear.value
  const getData = (indicatorName) => {
    return companyList.value.map(company => {
      const record = rawData.value[indicatorName]?.find(d => d.companyName === company && d.year === Number(selected))
      return record ? Number(record.value).toFixed(2) : null
    })
  }

  const option = {
    color: ['#4e79a7', '#f28e2c', '#e15759'],
    backgroundColor: 'transparent',
    tooltip: {
      trigger: 'axis',
      backgroundColor: 'rgba(50,50,50,0.7)',
      textStyle: { color: '#fff' }
    },
    legend: {
      data: ['净利率', '净资产收益率', '总资产报酬率'],
      textStyle: { color: '#ddd' }
    },
    grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
    xAxis: {
      type: 'category',
      data: companyList.value,
      axisLine: { lineStyle: { color: '#ccc' } },
      axisLabel: { color: '#ddd' }
    },
    yAxis: {
      type: 'value',
      axisLine: { lineStyle: { color: '#ccc' } },
      axisLabel: { color: '#ddd' },
      splitLine: { lineStyle: { color: 'rgba(255,255,255,0.1)' } }
    },
    series: [
      { name: '净利率', type: 'bar', data: getData('净利率') },
      { name: '净资产收益率', type: 'bar', data: getData('净资产收益率') },
      { name: '总资产报酬率', type: 'bar', data: getData('总资产报酬率') }
    ]
  }

  myChart.value.setOption(option, true)
}

// 错误处理
const handleError = (err, customMessage = '发生错误') => {
  console.error(customMessage, err)
  error.value = `${customMessage}: ${err.response?.data?.message || err.message}`
  myChart.value?.clear()
}
</script>

<style scoped>
/* 统一模块、状态提示、选择器样式 */
@import '@/styles/module-common.css';
</style>