<template>
  <table v-if="categories.length > 0 && !loading" class="data-table">
    <thead>
    <tr>
      <th>ID</th>
      <th>类别名称</th>
      <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="category in categories" :key="category.categoryId">
      <td>{{ category.categoryId }}</td>
      <td>{{ category.categoryName }}</td>
      <td>
        <button @click="editCategory(category)" class="action-button edit-button">编辑</button>
        <button @click="confirmDeleteCategory(category.categoryId)" class="action-button delete-button">删除</button>
      </td>
    </tr>
    </tbody>
  </table>
  <div v-if="showAddModal || editingCategory" class="modal-overlay">
    <div class="modal-content">
      <h3>{{ editingCategory ? '编辑类别' : '新增类别' }}</h3>
      <form @submit.prevent="editingCategory ? handleUpdateCategory() : handleAddCategory()">
        <div>
          <label for="categoryNameInput">类别名称:</label> <input type="text" id="categoryNameInput" v-model="categoryForm.categoryName" required />
        </div>
        <div class="modal-actions">
          <button type="submit" class="action-button">{{ editingCategory ? '更新' : '保存' }}</button>
          <button type="button" @click="closeModal" class="action-button cancel-button">取消</button>
        </div>
        <p v-if="formError" class="error-message">{{ formError }}</p>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, reactive } from 'vue';
import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/admin';

const categories = ref([]);
const loading = ref(false);
const error = ref(null);
const formError = ref(null);

const showAddModal = ref(false);
const editingCategory = ref(null);
// 修改表单模型属性为驼峰式
const categoryForm = reactive({
  categoryId: null,
  categoryName: '',
});

const fetchCategories = async () => {
  loading.value = true;
  error.value = null;
  try {
    const response = await axios.get(`${API_BASE_URL}/categories`);
    categories.value = response.data;
  } catch (err) {
    error.value = '获取公司类别列表失败。';
    console.error(err);
  } finally {
    loading.value = false;
  }
};

const handleAddCategory = async () => {
  formError.value = null;
  if (!categoryForm.categoryName.trim()) {
    formError.value = '类别名称不能为空。';
    return;
  }
  try {
    // 发送给后端的 payload 也应使用后端期望的键名
    const payload = { categoryName: categoryForm.categoryName };
    await axios.post(`${API_BASE_URL}/categories`, payload);
    closeModal();
    fetchCategories();
  } catch (err) {
    formError.value = `新增类别失败: ${err.response?.data?.message || err.message}`;
    console.error(err);
  }
};

const handleUpdateCategory = async () => {
  formError.value = null;
  if (!categoryForm.categoryName.trim()) {
    formError.value = '类别名称不能为空。';
    return;
  }
  try {
    const payload = { categoryName: categoryForm.categoryName };
    // 使用 categoryId 进行更新
    await axios.put(`<span class="math-inline">\{API\_BASE\_URL\}/categories/</span>{categoryForm.categoryId}`, payload);
    closeModal();
    fetchCategories();
  } catch (err) {
    formError.value = `更新类别失败: ${err.response?.data?.message || err.message}`;
    console.error(err);
  }
};

const editCategory = (category) => {
  editingCategory.value = category;
  // 使用驼峰式属性
  categoryForm.categoryId = category.categoryId;
  categoryForm.categoryName = category.categoryName;
  showAddModal.value = false;
};

const confirmDeleteCategory = async (categoryId) => { // 参数也改为 categoryId
  if (window.confirm(`确定要删除 ID 为 ${categoryId} 的类别吗？`)) {
    try {
      await axios.delete(`<span class="math-inline">\{API\_BASE\_URL\}/categories/</span>{categoryId}`);
      fetchCategories();
    } catch (err) {
      alert(`删除类别失败: ${err.response?.data?.message || err.message}`);
      console.error(err);
    }
  }
};

const closeModal = () => {
  showAddModal.value = false;
  editingCategory.value = null;
  // 重置表单模型为驼峰式
  categoryForm.categoryId = null;
  categoryForm.categoryName = '';
  formError.value = null;
};

onMounted(fetchCategories);
</script>

<style scoped>
/* 样式部分与之前一致，此处省略 */
.admin-page-container { padding: 10px; }
.page-header { font-size: 22px; color: #333; margin-bottom: 20px; font-weight: 500; }
.content-card { background-color: #fff; padding: 24px; border-radius: 4px; box-shadow: 0 2px 12px 0 rgba(0,0,0,.1); }
.loading-indicator, .error-message { margin-top: 10px; padding: 10px; border-radius: 4px; }
.loading-indicator { color: #909399; }
.error-message { color: #f56c6c; background-color: #fef0f0; border: 1px solid #fbc4c4; }
.actions-bar { margin-bottom: 20px; }
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
.modal-content { background-color: white; padding: 25px; border-radius: 8px; box-shadow: 0 5px 15px rgba(0,0,0,.3); width: 400px; }
.modal-content h3 { margin-top: 0; margin-bottom: 20px; color: #303133; }
.modal-content div { margin-bottom: 15px; }
.modal-content label { display: block; margin-bottom: 5px; font-weight: 500; color: #606266; }
.modal-content input[type="text"] { width: calc(100% - 20px); padding: 10px; border: 1px solid #dcdfe6; border-radius: 4px; box-sizing: border-box;}
.modal-actions { margin-top: 20px; text-align: right; }
.modal-actions .action-button { margin-left: 10px; }
</style>