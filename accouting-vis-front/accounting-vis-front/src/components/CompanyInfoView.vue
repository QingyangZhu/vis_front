<template>
  <div class="company-introduction-wrapper">
    <h3>公司简介</h3>

    <div class="selector-container">
      <label for="company-select" class="select-label">选择公司:</label>
      <select id="company-select" v-model="selectedCompanyName" @change="handleCompanySelectionChange" :disabled="loadingCompanies || companiesList.length === 0">
        <option disabled value="">
          {{ loadingCompanies ? '加载中...' : (companiesList.length === 0 && !errorCompanies ? '暂无公司可选' : '请选择一家公司') }}
        </option>
        <option v-for="company in companiesList" :key="company.id || company.companyName" :value="company.companyName">
          {{ company.companyName }}
        </option>
      </select>
    </div>
    <div v-if="!loadingCompanies && errorCompanies" class="error-text-small">{{ errorCompanies }}</div>

    <div class="introduction-display-area">
      <div v-if="loadingIntroduction" class="loading-text">简介加载中...</div>
      <div v-else-if="errorIntroduction" class="error-text">{{ errorIntroduction }}</div>
      <p v-else-if="selectedCompanyName && introductionText" class="intro-content">{{ introductionText }}</p>
      <p v-else-if="selectedCompanyName && !introductionText && !loadingIntroduction && !errorIntroduction" class="info-text">
        该公司暂无简介信息。
      </p>
      <p v-else-if="!selectedCompanyName && !loadingCompanies && !errorCompanies" class="info-text">
        请从上方选择一家公司以查看其简介。
      </p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios'; // 引入 axios

// 公司列表状态
const companiesList = ref([]);
const loadingCompanies = ref(false);
const errorCompanies = ref('');

// 选中公司及其简介的状态
const selectedCompanyName = ref('');
const introductionText = ref(''); // 重命名 introduction 为 introductionText 以更清晰
const loadingIntroduction = ref(false);
const errorIntroduction = ref('');

// API 基础URL (参考你提供的图表组件，它也使用了 /api 前缀)
const API_BASE_URL = '/api';

// 获取所有公司列表 (使用 axios)
async function fetchAllCompanies() {
  loadingCompanies.value = true;
  errorCompanies.value = '';
  companiesList.value = [];
  try {
    const response = await axios.get(`${API_BASE_URL}/companies`);
    // 参考你的图表组件，假设公司对象有 companyName 字段
    // 同时假设有 id 字段用于 key，如果没有，则回退用 companyName
    if (response.data && Array.isArray(response.data)) {
      companiesList.value = response.data.map(c => ({
        id: c.id || c.companyName, // 优先使用 id 作为 key
        companyName: c.companyName // 用于显示和作为查询参数值
      }));
    } else {
      companiesList.value = []; // 或者抛出错误
      console.warn('获取到的公司列表格式不正确或为空:', response.data);
    }
  } catch (err) {
    console.error('获取公司列表时出错:', err);
    let message = '无法加载公司列表。';
    if (err.response && err.response.data && err.response.data.message) {
      message = err.response.data.message;
    } else if (err.message) {
      message = err.message;
    }
    errorCompanies.value = message;
  } finally {
    loadingCompanies.value = false;
  }
}

// 根据公司名称获取公司详细信息（包括简介） (使用 axios)
async function fetchCompanyDetailsByName(companyName) {
  if (!companyName) {
    introductionText.value = '';
    errorIntroduction.value = '';
    return;
  }

  loadingIntroduction.value = true;
  errorIntroduction.value = '';
  introductionText.value = '';

  try {
    const response = await axios.get(`${API_BASE_URL}/companies/search`, {
      params: { name: companyName } // 后端接口需要的是 'name' 参数
    });

    // 根据你的描述 "公司对象里有一个company_info的值记录了公司的介绍"
    if (response.data && typeof response.data.companyInfo !== 'undefined') {
      introductionText.value = response.data.companyInfo || ''; // 如果 company_info 为 null，显示空字符串
    } else if (response.data) {
      introductionText.value = ''; // 公司存在，但无 company_info
    } else {
      throw new Error('未能正确解析公司详情。');
    }
  } catch (err) {
    console.error(`获取公司 "${companyName}" 简介时出错:`, err);
    let message = `无法加载 "${companyName}" 的公司简介。`;
    if (err.response && err.response.status === 404) {
      message = '未找到该公司信息。';
    } else if (err.response && err.response.data && err.response.data.message) {
      message = err.response.data.message;
    } else if (err.message) {
      message = err.message;
    }
    errorIntroduction.value = message;
    introductionText.value = '';
  } finally {
    loadingIntroduction.value = false;
  }
}

// 下拉框选择变化时的处理函数
function handleCompanySelectionChange() {
  if (selectedCompanyName.value) {
    fetchCompanyDetailsByName(selectedCompanyName.value);
  } else {
    introductionText.value = '';
    errorIntroduction.value = '';
  }
}

// 组件挂载后，获取初始数据
onMounted(() => {
  fetchAllCompanies();
});
</script>

<style scoped>
.company-introduction-wrapper {
  padding: 15px 20px;
  background-color: rgba(40, 40, 40, 0.6);
  border-radius: 8px;
  color: #e0e0e0;
  overflow: hidden;
  min-height: 200px;
  max-height: 320px;
  display: flex;
  flex-direction: column;
  gap: 12px;
  box-sizing: border-box;
  border: 1px solid rgba(255, 255, 255, 0.1);
}

.company-introduction-wrapper h3 {
  margin: 0 0 8px 0;
  color: #ffffff;
  border-bottom: 1px solid rgba(255, 255, 255, 0.25);
  padding-bottom: 8px;
  font-size: 1.1em;
  flex-shrink: 0;
}

.selector-container {
  display: flex;
  align-items: center;
  gap: 8px;
  flex-shrink: 0;
}

.select-label {
  font-size: 0.95em;
  color: #c0c0c0;
  white-space: nowrap;
}

.selector-container select {
  flex-grow: 1;
  padding: 8px 10px;
  border-radius: 5px;
  border: 1px solid rgba(255, 255, 255, 0.2);
  background-color: rgba(0, 0, 0, 0.3);
  color: #e0e0e0;
  font-size: 0.9em;
  cursor: pointer;
  outline: none;
  transition: border-color 0.2s, box-shadow 0.2s;
}
.selector-container select:disabled {
  cursor: not-allowed;
  opacity: 0.7;
}
.selector-container select:hover:not(:disabled) {
  border-color: rgba(255, 255, 255, 0.4);
}
.selector-container select:focus:not(:disabled) {
  border-color: hsla(160, 100%, 37%, 0.8); /* Vue Green for focus */ /* */
  box-shadow: 0 0 0 2px hsla(160, 100%, 37%, 0.2); /* */
}

.introduction-display-area {
  flex-grow: 1;
  overflow-y: auto;
  min-height: 60px;
  padding-right: 5px;
}

.intro-content {
  font-size: 0.9em;
  line-height: 1.65;
  white-space: pre-wrap;
  color: #d0d0d0;
  word-break: break-word;
}

.loading-text,
.error-text,
.info-text {
  font-style: italic;
  color: #b0b0b0;
  text-align: center;
  padding: 15px 0;
  font-size: 0.9em;
}
.error-text-small {
  font-size: 0.8em;
  font-style: italic;
  color: #ff8a80;
  margin-top: -8px;
  margin-bottom: 5px;
}

.error-text {
  color: #ff8a80;
}

.introduction-display-area::-webkit-scrollbar {
  width: 6px;
}
.introduction-display-area::-webkit-scrollbar-track {
  background: rgba(0, 0, 0, 0.1);
  border-radius: 3px;
}
.introduction-display-area::-webkit-scrollbar-thumb {
  background: rgba(255, 255, 255, 0.3);
  border-radius: 3px;
}
.introduction-display-area::-webkit-scrollbar-thumb:hover {
  background: rgba(255, 255, 255, 0.4);
}
</style>