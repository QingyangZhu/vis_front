<template>
  <div class="admin-page-container">
    <h2 class="page-header">财务指标管理</h2>

    <div class="actions-bar">
      <button @click="showAddModal = true" class="action-button add-button">新增指标</button>
    </div>

    <div class="content-card">
      <h3>指标列表</h3>
      <div v-if="loading" class="loading-indicator">正在加载...</div>
      <div v-if="error" class="error-message">{{ error }}</div>
      <table v-if="indicators.length > 0 && !loading" class="data-table">
        <thead>
        <tr>
          <th>ID</th>
          <th>指标名称</th>
          <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="indicator in indicators" :key="indicator.indicatorId">
          <td>{{ indicator.indicatorId }}</td>
          <td>{{ indicator.indicatorName }}</td>
          <td>
            <button @click="editIndicator(indicator)" class="action-button edit-button">编辑</button>
            <button @click="confirmDeleteIndicator(indicator.indicatorId)" class="action-button delete-button">删除</button>
          </td>
        </tr>
        </tbody>
      </table>
      <p v-if="indicators.length === 0 && !loading && !error">暂无财务指标数据。</p>
    </div>

    <div v-if="showAddModal || editingIndicator" class="modal-overlay">
      <div class="modal-content">
        <h3>{{ editingIndicator ? '编辑指标' : '新增指标' }}</h3>
        <form @submit.prevent="editingIndicator ? handleUpdateIndicator() : handleAddIndicator()">
          <div>
            <label for="indicatorName">指标名称:</label>
            <input type="text" id="indicatorName" v-model="indicatorForm.indicatorName" required />
          </div>
          <div class="modal-actions">
            <button type="submit" class="action-button">{{ editingIndicator ? '更新' : '保存' }}</button>
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

const API_BASE_URL = 'http://localhost:8080/admin';

const indicators = ref([]);
const loading = ref(false);
const error = ref(null);
const formError = ref(null);

const showAddModal = ref(false);
const editingIndicator = ref(null);
const indicatorForm = reactive({
  indicatorId: null,
  indicatorName: '',
});

const fetchIndicators = async () => {
  loading.value = true;
  error.value = null;
  try {
    const response = await axios.get(`${API_BASE_URL}/financial-indicators`);
    indicators.value = response.data;
  } catch (err) {
    error.value = '获取财务指标列表失败。';
    console.error(err);
  } finally {
    loading.value = false;
  }
};

const handleAddIndicator = async () => {
  formError.value = null;
  if (!indicatorForm.indicatorName.trim()) {
    formError.value = '指标名称不能为空。';
    return;
  }
  try {
    await axios.post(`${API_BASE_URL}/financial-indicators`, { indicatorName: indicatorForm.indicatorName });
    closeModal();
    fetchIndicators();
  } catch (err) {
    formError.value = `新增指标失败: ${err.response?.data?.message || err.message}`;
    console.error(err);
  }
};

const handleUpdateIndicator = async () => {
  formError.value = null;
  if (!indicatorForm.indicatorName.trim()) {
    formError.value = '指标名称不能为空。';
    return;
  }
  try {
    await axios.put(`${API_BASE_URL}/financial-indicators/${indicatorForm.indicatorId}`, { indicatorName: indicatorForm.indicatorName });
    closeModal();
    fetchIndicators();
  } catch (err) {
    formError.value = `更新指标失败: ${err.response?.data?.message || err.message}`;
    console.error(err);
  }
};

const editIndicator = (indicator) => {
  editingIndicator.value = indicator;
  indicatorForm.indicatorId = indicator.indicatorId;
  indicatorForm.indicatorName = indicator.indicatorName;
  showAddModal.value = false;
};

const confirmDeleteIndicator = async (indicatorId) => {
  if (window.confirm(`确定要删除 ID 为 ${indicatorId} 的指标吗？删除后相关的财务数据可能出现问题。`)) {
    try {
      await axios.delete(`${API_BASE_URL}/financial-indicators/${indicatorId}`);
      fetchIndicators();
    } catch (err) {
      alert(`删除指标失败: ${err.response?.data?.message || err.message}`);
      console.error(err);
    }
  }
};

const closeModal = () => {
  showAddModal.value = false;
  editingIndicator.value = null;
  indicatorForm.indicatorId = null;
  indicatorForm.indicatorName = '';
  formError.value = null;
};

onMounted(fetchIndicators);
</script>

<style scoped>
/* 样式与 CompanyManagementView.vue 类似，可以提取到全局或复用 */
.admin-page-container { padding: 10px; }
.page-header { font-size: 22px; color: #333; margin-bottom: 20px; font-weight: 500; }
.content-card { background-color: #fff; padding: 24px; border-radius: 4px; box-shadow: 0 2px 12px 0 rgba(0,0,0,.1); }
.loading-indicator, .error-message { margin-top: 10px; padding: 10px; border-radius: 4px; }
.loading-indicator { color: #909399; }
.error-message { color: #f56c6c; background-color: #fef0f0; border: 1px solid #fbc4c4; }
.actions-bar { margin-bottom: 20px; }
.action-button { padding: 8px 15px; border: none; border-radius: 4px; cursor: pointer; font-size: 14px; margin-right: 8px; transition: background-color 0.2s; }
.add-button { background-color: #409eff; color: white; }
.add-button:hover { background-color: #66b1ff; }
.edit-button { background-color: #e6a23c; color: white; }
.edit-button:hover { background-color: #ebb563; }
.delete-button { background-color: #f56c6c; color: white; }
.delete-button:hover { background-color: #f78989; }
.cancel-button { background-color: #909399; color: white; }
.cancel-button:hover { background-color: #a6a9ad; }
.data-table { width: 100%; border-collapse: collapse; margin-top: 15px; }
.data-table th, .data-table td { border: 1px solid #ebeef5; padding: 10px 12px; text-align: left; font-size: 14px; }
.data-table th { background-color: #f5f7fa; color: #909399; font-weight: 500; }
.data-table tr:hover { background-color: #ecf5ff; }
.modal-overlay { position: fixed; top: 0; left: 0; width: 100%; height: 100%; background-color: rgba(0, 0, 0, 0.5); display: flex; justify-content: center; align-items: center; z-index: 1000; }
.modal-content { background-color: white; padding: 25px; border-radius: 8px; box-shadow: 0 5px 15px rgba(0,0,0,.3); width: 400px; }
.modal-content h3 { margin-top: 0; margin-bottom: 20px; color: #303133; }
.modal-content div { margin-bottom: 15px; }
.modal-content label { display: block; margin-bottom: 5px; font-weight: 500; color: #606266; }
.modal-content input[type="text"] { width: calc(100% - 20px); padding: 10px; border: 1px solid #dcdfe6; border-radius: 4px; box-sizing: border-box;}
.modal-actions { margin-top: 20px; text-align: right; }
.modal-actions .action-button { margin-left: 10px; }
</style>