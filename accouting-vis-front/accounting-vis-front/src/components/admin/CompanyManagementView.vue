<template>
  <div class="admin-page-container">
    <h2 class="page-header">公司管理</h2>

    <div class="actions-bar">
      <button @click="showAddModal = true" class="action-button add-button">新增公司</button>
    </div>

    <div class="content-card">
      <h3>公司列表</h3>
      <div v-if="loading" class="loading-indicator">正在加载...</div>
      <div v-if="error" class="error-message">{{ error }}</div>
      <table v-if="companies.length > 0 && !loading" class="data-table">
        <thead>
        <tr>
          <th>ID</th>
          <th>公司名称</th>
          <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="company in companies" :key="company.companyId">
          <td>{{ company.companyId }}</td>
          <td>{{ company.companyName }}</td>
          <td>
            <button @click="editCompany(company)" class="action-button edit-button">编辑</button>
            <button @click="confirmDeleteCompany(company.companyId)" class="action-button delete-button">删除</button>
          </td>
        </tr>
        </tbody>
      </table>
      <p v-if="companies.length === 0 && !loading && !error">暂无公司数据。</p>
    </div>

    <div v-if="showAddModal || editingCompany" class="modal-overlay">
      <div class="modal-content">
        <h3>{{ editingCompany ? '编辑公司' : '新增公司' }}</h3>
        <form @submit.prevent="editingCompany ? handleUpdateCompany() : handleAddCompany()">
          <div>
            <label for="companyName">公司名称:</label>
            <input type="text" id="companyName" v-model="companyForm.companyName" required />
          </div>
          <div class="modal-actions">
            <button type="submit" class="action-button">{{ editingCompany ? '更新' : '保存' }}</button>
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
import axios from 'axios'; // 确保已安装 axios

const API_BASE_URL = 'http://localhost:8080/admin'; // 您的后端 API 地址

const companies = ref([]);
const loading = ref(false);
const error = ref(null);
const formError = ref(null);

const showAddModal = ref(false);
const editingCompany = ref(null); // 用于存储正在编辑的公司对象
const companyForm = reactive({
  companyId: null,
  companyName: '',
});

const fetchCompanies = async () => {
  loading.value = true;
  error.value = null;
  try {
    const response = await axios.get(`${API_BASE_URL}/companies`);
    companies.value = response.data;
  } catch (err) {
    console.error("获取公司列表失败:", err);
    error.value = '获取公司列表失败，请稍后再试。';
  } finally {
    loading.value = false;
  }
};

const handleAddCompany = async () => {
  formError.value = null;
  if (!companyForm.companyName.trim()) {
    formError.value = '公司名称不能为空。';
    return;
  }
  try {
    const payload = { companyName: companyForm.companyName };
    await axios.post(`${API_BASE_URL}/companies`, payload);
    closeModal();
    fetchCompanies(); // 重新加载列表
  } catch (err) {
    console.error("新增公司失败:", err);
    formError.value = `新增公司失败: ${err.response?.data?.message || err.message || '未知错误'}`;
  }
};

const handleUpdateCompany = async () => {
  formError.value = null;
  if (!companyForm.companyName.trim()) {
    formError.value = '公司名称不能为空。';
    return;
  }
  if (!editingCompany.value || !companyForm.companyId) return;
  try {
    const payload = { companyName: companyForm.companyName };
    await axios.put(`${API_BASE_URL}/companies/${companyForm.companyId}`, payload);
    closeModal();
    fetchCompanies(); // 重新加载列表
  } catch (err) {
    console.error("更新公司失败:", err);
    formError.value = `更新公司失败: ${err.response?.data?.message || err.message || '未知错误'}`;
  }
};

const editCompany = (company) => {
  editingCompany.value = company;
  companyForm.companyId = company.companyId;
  companyForm.companyName = company.companyName;
  showAddModal.value = false; // 如果新增模态框开着，先关掉
};

const confirmDeleteCompany = async (companyId) => {
  if (window.confirm(`确定要删除 ID 为 ${companyId} 的公司吗？`)) {
    try {
      await axios.delete(`${API_BASE_URL}/companies/${companyId}`);
      fetchCompanies(); // 重新加载列表
    } catch (err) {
      console.error("删除公司失败:", err);
      alert(`删除公司失败: ${err.response?.data?.message || err.message || '未知错误'}`);
    }
  }
};

const closeModal = () => {
  showAddModal.value = false;
  editingCompany.value = null;
  companyForm.companyId = null;
  companyForm.companyName = '';
  formError.value = null;
};

onMounted(() => {
  fetchCompanies();
});
</script>

<style scoped>
/* 通用页面和卡片样式 */
.admin-page-container {
  padding: 10px;
}
.page-header {
  font-size: 22px;
  color: #333;
  margin-bottom: 20px;
  font-weight: 500;
}
.content-card {
  background-color: #fff;
  padding: 24px;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
}
.loading-indicator, .error-message {
  margin-top: 10px;
  padding: 10px;
  border-radius: 4px;
}
.loading-indicator {
  color: #909399;
}
.error-message {
  color: #f56c6c;
  background-color: #fef0f0;
  border: 1px solid #fbc4c4;
}

/* 操作栏 */
.actions-bar {
  margin-bottom: 20px;
}
.action-button {
  padding: 8px 15px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  margin-right: 8px;
  transition: background-color 0.2s;
}
.add-button {
  background-color: #409eff;
  color: white;
}
.add-button:hover {
  background-color: #66b1ff;
}
.edit-button {
  background-color: #e6a23c;
  color: white;
}
.edit-button:hover {
  background-color: #ebb563;
}
.delete-button {
  background-color: #f56c6c;
  color: white;
}
.delete-button:hover {
  background-color: #f78989;
}
.cancel-button {
  background-color: #909399;
  color: white;
}
.cancel-button:hover {
  background-color: #a6a9ad;
}


/* 表格样式 */
.data-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 15px;
}
.data-table th, .data-table td {
  border: 1px solid #ebeef5;
  padding: 10px 12px;
  text-align: left;
  font-size: 14px;
}
.data-table th {
  background-color: #f5f7fa;
  color: #909399;
  font-weight: 500;
}
.data-table tr:hover {
  background-color: #ecf5ff;
}

/* 简易模态框样式 */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}
.modal-content {
  background-color: white;
  padding: 25px;
  border-radius: 8px;
  box-shadow: 0 5px 15px rgba(0,0,0,.3);
  width: 400px; /* 或根据内容调整 */
}
.modal-content h3 {
  margin-top: 0;
  margin-bottom: 20px;
  color: #303133;
}
.modal-content div {
  margin-bottom: 15px;
}
.modal-content label {
  display: block;
  margin-bottom: 5px;
  font-weight: 500;
  color: #606266;
}
.modal-content input[type="text"],
.modal-content input[type="number"],
.modal-content select {
  width: calc(100% - 20px);
  padding: 10px;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  box-sizing: border-box;
}
.modal-actions {
  margin-top: 20px;
  text-align: right;
}
.modal-actions .action-button {
  margin-left: 10px;
}
</style>