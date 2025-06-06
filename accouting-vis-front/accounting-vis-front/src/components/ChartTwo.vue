<template>
  <div class="module" @mouseover="handleMouseOver" @mouseleave="handleMouseLeave">
    <div class="select-wrapper">
      <select v-model="selectedCompany" class="custom-select" @change="fetchData" :disabled="loading">
        <option v-for="company in companyList" :value="company.companyName">{{ company.companyName }}</option>
      </select>
      <h2 class="legend">市盈率、市净率、市销率、股息率</h2>
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

const selectedCompany = ref('')
const companyList = ref([])
const chart = ref(null)
const myChart = ref(null)
const loading = ref(false)
const error = ref('')

const api = axios.create({
  baseURL: '/api/',
  timeout: 10000
})

onMounted(async () => {
  await nextTick()
  initChart()
  await fetchCompanies()
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

const fetchCompanies = async () => {
  try {
    loading.value = true
    const response = await api.get('/companies')
    companyList.value = response.data
    if (companyList.value.length > 0) {
      selectedCompany.value = companyList.value[0].companyName
      await fetchData()
    }
  } catch (err) {
    handleError(err, '获取公司列表失败')
  } finally {
    loading.value = false
  }
}

const fetchData = async () => {
  try {
    loading.value = true
    error.value = ''
    const response = await api.get('/company-metrics/search', { params: { name: selectedCompany.value } })
    processMetrics(response.data)
  } catch (err) {
    handleError(err, '获取财务指标失败')
  } finally {
    loading.value = false
  }
}

const processMetrics = (rawData) => {
  if (!rawData || rawData.length === 0) {
    error.value = '暂无相关数据'
    myChart?.clear()
    return
  }

  const years = rawData.map(item => item.year.toString())
  const metrics = ['peRatio', 'pbRatio', 'psRatio', 'dividendYield']
  const chineseNames = {
    peRatio: '市盈率',
    pbRatio: '市净率',
    psRatio: '市销率',
    dividendYield: '股息率'
  }

  const series = metrics.map(metric => ({
    name: chineseNames[metric],
    type: 'line',
    smooth: true,
    symbol: 'circle',
    symbolSize: 8,
    data: rawData.map(item => item[metric] !== null ? Number(item[metric]).toFixed(2) : null)
  }))

  const option = {
    color: ['#4e79a7', '#f28e2c', '#e15759', '#76b7b2'],
    backgroundColor: 'transparent',
    title: {
      text: selectedCompany.value,
      textStyle: { fontSize: 18, fontWeight: 'bold', color: '#fff' }
    },
    tooltip: {
      trigger: 'axis',
      backgroundColor: 'rgba(50,50,50,0.7)',
      textStyle: { color: '#fff' }
    },
    legend: {
      data: Object.values(chineseNames),
      top: 30,
      textStyle: { color: '#ddd' }
    },
    xAxis: {
      type: 'category',
      data: years,
      axisLabel: { color: '#ddd', rotate: 45 },
      axisLine: { lineStyle: { color: '#ccc' } }
    },
    yAxis: {
      type: 'value',
      axisLabel: { color: '#ddd' },
      splitLine: { lineStyle: { color: 'rgba(255,255,255,0.1)' } }
    },
    series,
    dataZoom: [{ type: 'inside', start: 0, end: 100 }]
  }

  myChart.value.setOption(option, true)
}

const handleError = (err, message) => {
  console.error(message, err)
  error.value = `${message}: ${err.response?.data?.message || err.message}`
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
@import '@/styles/module-common.css';
</style>
