<template>
  <div class="module" @mouseover="handleMouseOver" @mouseleave="handleMouseLeave">
    <div class="select-wrapper">
      <select
          v-model="selectedCompany"
          class="custom-select"
          @change="fetchData"
          :disabled="loading"
      >
        <option
            v-for="company in companyList"
            :value="company.companyName"
        >
          {{ company.companyName }}
        </option>
      </select>
      <h2 class="legend">市盈率、市净率、市销率、股息率</h2>
    </div>

    <div v-if="loading" class="status-container loading">
      <i class="el-icon-loading"></i>
      数据加载中...
    </div>

    <div v-if="error" class="status-container error">
      <i class="el-icon-error"></i>
      {{ error }}
    </div>

    <div
        ref="chart"
        v-show="!loading && !error"
        style="width: 100%; height: 400px;"
    ></div>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'
import axios from 'axios'
import * as echarts from 'echarts'

// 修改响应式状态
const selectedCompany = ref('')
const companyList = ref([])
const loading = ref(false)
const error = ref('')
const chart = ref(null)
let myChart = null

// API配置
const api = axios.create({
  baseURL: '/api/',
  timeout: 10000
})

// 初始化图表
const initChart = () => {
  myChart = echarts.init(chart.value)
  window.addEventListener('resize', handleResize)
}

// 获取公司列表
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

// 获取指标数据
const fetchData = async () => {
  try {
    loading.value = true
    error.value = ''

    // 修正：正确编码请求参数
    const response = await api.get('/company-metrics/search', {
      params: {
        name: selectedCompany.value
      }
    })
    processMetrics(response.data)
  } catch (err) {
    handleError(err, '获取财务指标失败')
  } finally {
    loading.value = false
  }
}

// 处理指标数据
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
    // 修正：使用Number转换并保留两位小数
    data: rawData.map(item =>
        item[metric] !== null ? Number(item[metric]).toFixed(2) : null
    )
  }))

  const option = {
    title: {
      text: selectedCompany.value,
      textStyle: {
        fontSize: 18,
        fontWeight: 'bold',
        color: '#333'
      }
    },
    tooltip: {
      trigger: 'axis',
      formatter: params => params.map(p => `
        ${p.marker}
        <strong>${p.seriesName}</strong><br/>
        年份: ${p.name}<br/>
        值: ${p.value ?? '无数据'}
      `).join('')
    },
    legend: {
      data: Object.values(chineseNames),
      top: 30
    },
    xAxis: {
      type: 'category',
      data: years,
      axisLabel: { rotate: 45 }
    },
    yAxis: { type: 'value' },
    series,
    dataZoom: [{
      type: 'inside',
      start: 0,
      end: 100
    }]
  }

  myChart.setOption(option, true)
}

// 错误处理
const handleError = (err, message) => {
  console.error(message, err)
  error.value = `${message}: ${err.response?.data?.message || err.message}`
  myChart?.clear()
}

// 生命周期管理
onMounted(async () => {
  initChart()
  await fetchCompanies()
})

onBeforeUnmount(() => {
  window.removeEventListener('resize', handleResize)
  myChart?.dispose()
})

// 窗口调整
const handleResize = () => myChart?.resize()
</script>

<style scoped>
/* 保持原有样式不变 */
.module {
  position: relative;
  background-color: rgba(255, 255, 255, 0.85);
  padding: 20px;
  margin: 10px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s ease-in-out;
}

.status-container {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  padding: 20px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  gap: 10px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.loading {
  background: rgba(255, 255, 255, 0.9);
  color: #666;
}

.error {
  background: #fef0f0;
  color: #f56c6c;
  border: 1px solid #f56c6c;
}

.custom-select:disabled {
  background-color: #f5f5f5;
  cursor: not-allowed;
}
</style>