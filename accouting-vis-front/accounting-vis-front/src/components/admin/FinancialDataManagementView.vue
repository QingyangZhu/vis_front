<template>
  <div class="admin-page-container">
    <h2 class="page-header">公司财务数据管理</h2>

    <div class="actions-bar">
      <button @click="openAddModal" class="action-button add-button">新增财务数据</button>
    </div>
    <div class="filter-bar content-card">
      <h4>筛选数据</h4>
      <label for="filterCompany">公司:</label>
      <select id="filterCompany" v-model="filter.companyId" @change="fetchFinancialData">
        <option :value="null">所有公司</option>
        <option v-for="company in companiesForFilter" :key="company.companyId" :value="company.companyId">
          {{ company.companyName }}
        </option>
      </select>
      <label for="filterYear">年份:</label>
      <input type="number" id="filterYear" v-model.number="filter.year" @input="fetchFinancialDataDebounced" placeholder="例如: 2023"/>
      <button @click="resetFiltersAndFetch" class="action-button">重置筛选</button>
    </div>


    <div class="content-card">
      <h3>财务数据列表</h3>
      <div v-if="loading" class="loading-indicator">正在加载...</div>
      <div v-if="error" class="error-message">{{ error }}</div>
      <table v-if="financialData.length > 0 && !loading" class="data-table">
        <thead>
        <tr>
          <th>Data ID</th>
          <th>公司 ID</th>
          <th>年份</th>
          <th>指标 ID</th>
          <th>数据值</th>
          <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="dataItem in financialData" :key="dataItem.dataId">
          <td>{{ dataItem.dataId }}</td>
          <td>{{ dataItem.companyId }}</td>
          <td>{{ dataItem.year }}</td>
          <td>{{ dataItem.indicatorId }}</td>
          <td>{{ dataItem.financialDataValue }}</td>
          <td>
            <button @click="editFinancialData(dataItem)" class="action-button edit-button">编辑</button>
            <button @click="confirmDeleteFinancialData(dataItem.dataId)" class="action-button delete-button">删除</button>
          </td>
        </tr>
        </tbody>
      </table>
      <p v-if="financialData.length === 0 && !loading && !error">暂无财务数据，或当前筛选条件下无数据。</p>
    </div>

    <div v-if="showModal" class="modal-overlay">
      <div class="modal-content">
        <h3>{{ editingData ? '编辑财务数据' : '新增财务数据' }}</h3>
        <form @submit.prevent="editingData ? handleUpdateData() : handleAddData()">
          <div>
            <label for="companyId">公司:</label>
            <select id="companyId" v-model.number="dataForm.companyId" required :disabled="!!editingData">
              <option disabled value="">请选择公司</option>
              <option v-for="company in companiesForFilter" :key="company.companyId" :value="company.companyId">
                {{ company.companyName }} (ID: {{ company.companyId }})
              </option>
            </select>
          </div>
          <div>
            <label for="indicatorId">指标:</label>
            <select id="indicatorId" v-model.number="dataForm.indicatorId" required :disabled="!!editingData">
              <option disabled value="">请选择指标</option>
              <option v-for="indicator in indicatorsForFilter" :key="indicator.indicatorId" :value="indicator.indicatorId">
                {{ indicator.indicatorName }} (ID: {{ indicator.indicatorId }})
              </option>
            </select>
          </div>
          <div>
            <label for="year">年份:</label>
            <input type="number" id="year" v-model.number="dataForm.year" required :disabled="!!editingData" placeholder="例如: 2023"/>
          </div>
          <div>
            <label for="financialDataValue">数据值:</label>
            <input type="number" step="any" id="financialDataValue" v-model.number="dataForm.financialDataValue" required />
          </div>
          <div class="modal-actions">
            <button type="submit" class="action-button">{{ editingData ? '更新' : '保存' }}</button>
            <button type="button" @click="closeModal" class="action-button cancel-button">取消</button>
          </div>
          <p v-if="formError" class="error-message">{{ formError }}</p>
        </form>
      </div>
    </div>

  </div>
</template>

<script setup>
import { ref, onMounted, reactive } from 'vue';
import axios from 'axios';
import debounce from 'lodash/debounce'; // 需要安装 lodash: npm install lodash

const API_BASE_URL = 'http://localhost:8080/admin';

const financialData = ref([]);
const companiesForFilter = ref([]); // 用于下拉选择公司
const indicatorsForFilter = ref([]); // 用于下拉选择指标

const loading = ref(false);
const error = ref(null);
const formError = ref(null);

const showModal = ref(false);
const editingData = ref(null); // 存储正在编辑的财务数据对象

const dataForm = reactive({
  dataId: null,
  companyId: null,
  indicatorId: null,
  year: null,
  financialDataValue: null,
});

const filter = reactive({
  companyId: null,
  year: null,
});

// 获取用于筛选和表单的公司列表
const fetchCompaniesForFilter = async () => {
  try {
    const response = await axios.get(`${API_BASE_URL}/companies`);
    companiesForFilter.value = response.data;
  } catch (err) {
    console.error("获取公司列表失败(供筛选):", err);
  }
};

// 获取用于筛选和表单的指标列表
const fetchIndicatorsForFilter = async () => {
  try {
    const response = await axios.get(`${API_BASE_URL}/financial-indicators`);
    indicatorsForFilter.value = response.data;
  } catch (err) {
    console.error("获取指标列表失败(供筛选):", err);
  }
};


const fetchFinancialData = async () => {
  loading.value = true;
  error.value = null;
  financialData.value = []; // 清空现有数据
  try {
    let url = `${API_BASE_URL}/company-financial-data`;
    const params = {};
    if (filter.companyId !== null && filter.companyId !== '') {
      params.companyId = filter.companyId;
    }
    if (filter.year !== null && filter.year !== '') {
      params.year = filter.year;
    }
    // 只有当筛选条件存在时才添加查询参数
    if (Object.keys(params).length > 0) {
      const queryParams = new URLSearchParams(params).toString();
      url = `${url}/query?${queryParams}`; // 后端接口需要支持 /query?companyId=X&year=Y
    }


    const response = await axios.get(url);
    financialData.value = response.data;
  } catch (err) {
    error.value = `获取财务数据列表失败: ${err.response?.data?.message || err.message}`;
    console.error(err);
  } finally {
    loading.value = false;
  }
};

const fetchFinancialDataDebounced = debounce(fetchFinancialData, 500);

const resetFiltersAndFetch = () => {
  filter.companyId = null;
  filter.year = null;
  fetchFinancialData();
};


const openAddModal = () => {
  editingData.value = null;
  dataForm.dataId = null;
  dataForm.companyId = null;
  dataForm.indicatorId = null;
  dataForm.year = new Date().getFullYear(); // 默认当年
  dataForm.financialDataValue = null;
  formError.value = null;
  showModal.value = true;
};

const handleAddData = async () => {
  formError.value = null;
  if (dataForm.companyId == null || dataForm.indicatorId == null || dataForm.year == null || dataForm.financialDataValue == null) {
    formError.value = "公司、指标、年份和数据值均不能为空。";
    return;
  }
  try {
    const payload = { ...dataForm };
    delete payload.dataId; // 新增时不需要 dataId
    await axios.post(`${API_BASE_URL}/company-financial-data`, payload);
    closeModal();
    fetchFinancialData(); // 重新加载列表
  } catch (err) {
    formError.value = `新增财务数据失败: ${err.response?.data || err.message}`;
    console.error("新增财务数据失败:", err);
  }
};

const editFinancialData = (dataItem) => {
  editingData.value = dataItem;
  dataForm.dataId = dataItem.dataId;
  dataForm.companyId = dataItem.companyId;
  dataForm.indicatorId = dataItem.indicatorId;
  dataForm.year = dataItem.year;
  dataForm.financialDataValue = dataItem.financialDataValue;
  formError.value = null;
  showModal.value = true;
};

const handleUpdateData = async () => {
  formError.value = null;
  if (dataForm.financialDataValue == null) {
    formError.value = "数据值不能为空。";
    return;
  }
  if (!editingData.value || !dataForm.dataId) return;
  try {
    // 更新时通常只更新 financialDataValue，其他字段作为联合主键部分不应修改
    const payload = { financialDataValue: dataForm.financialDataValue };
    await axios.put(`${API_BASE_URL}/company-financial-data/${dataForm.dataId}`, payload);
    closeModal();
    fetchFinancialData(); // 重新加载列表
  } catch (err) {
    formError.value = `更新财务数据失败: ${err.response?.data || err.message}`;
    console.error("更新财务数据失败:", err);
  }
};

const confirmDeleteFinancialData = async (dataId) => {
  if (window.confirm(`确定要删除 Data ID 为 ${dataId} 的财务数据吗？`)) {
    try {
      await axios.delete(`${API_BASE_URL}/company-financial-data/${dataId}`);
      fetchFinancialData(); // 重新加载列表
    } catch (err) {
      alert(`删除财务数据失败: ${err.response?.data?.message || err.message}`);
      console.error(err);
    }
  }
};

const closeModal = () => {
  showModal.value = false;
  editingData.value = null;
  formError.value = null;
  // 不重置表单，以便下次打开时保留上次输入 (如果需要重置则取消注释下面)
  // dataForm.companyId = null;
  // dataForm.indicatorId = null;
  // dataForm.year = null;
  // dataForm.financialDataValue = null;
};

onMounted(() => {
  fetchCompaniesForFilter();
  fetchIndicatorsForFilter();
  fetchFinancialData();
});
</script>

<style scoped>
/* 样式与 CompanyManagementView.vue 类似 */
.admin-page-container { padding: 10px; }
.page-header { font-size: 22px; color: #333; margin-bottom: 20px; font-weight: 500; }
.actions-bar { margin-bottom: 20px; }
.filter-bar {
  margin-bottom: 20px;
  padding: 15px;
  display: flex;
  gap: 15px;
  align-items: center;
}
.filter-bar label {
  margin-right: 5px;
}
.filter-bar input[type="number"], .filter-bar select {
  padding: 8px;
  border-radius: 4px;
  border: 1px solid #dcdfe6;
}
.content-card { background-color: #fff; padding: 24px; border-radius: 4px; box-shadow: 0 2px 12px 0 rgba(0,0,0,.1); }
.loading-indicator, .error-message { margin-top: 10px; padding: 10px; border-radius: 4px; }
.loading-indicator { color: #909399; }
.error-message { color: #f56c6c; background-color: #fef0f0; border: 1px solid #fbc4c4; }
.action-button { padding: 8px 15px; border: none; border-radius: 4px; cursor: pointer; font-size: 14px; margin-right: 8px; transition: background-color 0.2s; }
.add-button { background-color: #409eff; color: white; }
.edit-button { background-color: #e6a23c; color: white; }
.delete-button { background-color: #f56c6c; color: white; }
.cancel-button { background-color: #909399; color: white; }
.data-table { width: 100%; border-collapse: collapse; margin-top: 15px; }
.data-table th, .data-table td { border: 1px solid #ebeef5; padding: 10px 12px; text-align: left; font-size: 14px; }
.data-table th { background-color: #f5f7fa; color: #909399; font-weight: 500; }
.data-table tr:hover { background-color: #ecf5ff; }
.modal-overlay { position: fixed; top: 0; left: 0; width: 100%; height: 100%; background-color: rgba(0, 0, 0, 0.5); display: flex; justify-content: center; align-items: center; z-index: 1000; }
.modal-content { background-color: white; padding: 25px; border-radius: 8px; box-shadow: 0 5px 15px rgba(0,0,0,.3); width: 450px; } /* 稍微宽一点 */
.modal-content h3 { margin-top: 0; margin-bottom: 20px; color: #303133; }
.modal-content div { margin-bottom: 15px; }
.modal-content label { display: block; margin-bottom: 5px; font-weight: 500; color: #606266; }
.modal-content input[type="text"], .modal-content input[type="number"], .modal-content select { width: calc(100% - 22px); padding: 10px; border: 1px solid #dcdfe6; border-radius: 4px; box-sizing: border-box;}
.modal-actions { margin-top: 20px; text-align: right; }
.modal-actions .action-button { margin-left: 10px; }
</style>