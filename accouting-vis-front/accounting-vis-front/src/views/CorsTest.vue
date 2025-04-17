<template>
  <div class="test-container">
    <h1>跨域通信测试</h1>

    <!-- 测试GET请求 -->
    <div class="test-section">
      <button @click="getCompanies">测试GET请求</button>
      <div v-if="companies.length > 0">
        <h3>公司列表：</h3>
        <ul>
          <li v-for="company in companies" :key="company.companyId">
            {{ company.companyId }} - {{ company.companyName }}
          </li>
        </ul>
      </div>
    </div>

    <!-- 测试POST请求 -->
    <div class="test-section">
      <input v-model="newCompanyName" placeholder="输入公司名称" />
      <button @click="createCompany">测试POST请求</button>
      <p v-if="postResult">{{ postResult }}</p>
    </div>

    <!-- 显示错误信息 -->
    <div v-if="error" class="error-message">
      {{ error }}
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'

// 响应式数据
const companies = ref([])
const newCompanyName = ref('')
const postResult = ref('')
const error = ref('')

// 创建axios实例（配置基地址）
const api = axios.create({
  baseURL: '/api', // 使用代理配置的前缀
  timeout: 5000
})

// GET请求测试
const getCompanies = async () => {
  try {
    const response = await api.get('/companies')
    companies.value = response.data
    error.value = ''
  } catch (err) {
    error.value = `GET请求失败：${err.message}`
    console.error(err)
  }
}

// POST请求测试
const createCompany = async () => {
  if (!newCompanyName.value.trim()) {
    error.value = '公司名称不能为空'
    return
  }

  try {
    const response = await api.post('/companies', {
      companyName: newCompanyName.value
    })
    postResult.value = response.data === "创建成功" ?
        `创建成功：${newCompanyName.value}` :
        response.data
    newCompanyName.value = ''
    error.value = ''
    // 创建成功后刷新列表
    await getCompanies()
  } catch (err) {
    error.value = `POST请求失败：${err.message}`
    console.error(err)
  }
}
</script>

<style scoped>
.test-container {
  max-width: 800px;
  margin: 2rem auto;
  padding: 20px;
}

.test-section {
  margin: 2rem 0;
  padding: 1rem;
  border: 1px solid #eee;
  border-radius: 8px;
}

button {
  margin-right: 1rem;
  padding: 8px 16px;
  background: #42b983;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

input {
  padding: 8px;
  margin-right: 1rem;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.error-message {
  color: #ff4444;
  margin-top: 1rem;
}
</style>