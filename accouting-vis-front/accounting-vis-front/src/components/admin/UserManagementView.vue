<template>
  <div class="admin-page-container">
    <h2 class="page-header">用户管理</h2>

    <div class="content-card">
      <h3>用户列表</h3>
      <div v-if="loading" class="loading-indicator">正在加载用户数据...</div>
      <div v-if="error" class="error-message">{{ error }}</div>

      <table v-if="users.length > 0 && !loading" class="data-table">
        <thead>
        <tr>
          <th>用户ID (UID)</th>
          <th>用户名</th>
          <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="user in users" :key="user.uid">
          <td>{{ user.uid }}</td>
          <td>{{ user.username }}</td>
          <td>
            <button
                @click="confirmDeleteUser(user.uid, user.username)"
                class="action-button delete-button"
                :disabled="deletingUser === user.uid"
            >
              {{ deletingUser === user.uid ? '删除中...' : '删除' }}
            </button>
          </td>
        </tr>
        </tbody>
      </table>
      <p v-if="users.length === 0 && !loading && !error">暂无用户数据。</p>
    </div>

  </div>
</template>

<script setup>
import { ref, onMounted, reactive } from 'vue';
import axios from 'axios'; // 确保已安装 axios

const API_BASE_URL = 'http://localhost:8080/admin'; // 您的后端 API 地址

const users = ref([]);
const loading = ref(false);
const error = ref(null);
const formError = ref(null); // 用于模态框表单错误
const deletingUser = ref(null); // 存储正在删除的用户ID，用于禁用按钮

// const showUserModal = ref(false);
// const editingUser = ref(null); // 用于存储正在编辑的用户对象
// const userForm = reactive({
//   uid: null,
//   username: '',
//   password: '', // 仅在新增时使用
// });

const fetchUsers = async () => {
  loading.value = true;
  error.value = null;
  try {
    // 调用后端 /admin/users 接口获取 UserResponseDTO 列表
    const response = await axios.get(`${API_BASE_URL}/users`);
    users.value = response.data;
  } catch (err) {
    console.error("获取用户列表失败:", err);
    error.value = `获取用户列表失败: ${err.response?.data || err.message || '请稍后再试。'}`;
  } finally {
    loading.value = false;
  }
};

const confirmDeleteUser = async (userId, username) => {
  if (deletingUser.value) return; // 防止重复点击

  if (window.confirm(`确定要删除用户 "${username}" (ID: ${userId}) 吗？此操作不可恢复。`)) {
    deletingUser.value = userId;
    error.value = null; // 清除之前的错误信息
    try {
      await axios.delete(`${API_BASE_URL}/users/${userId}`);
      // alert('用户删除成功！'); // 可选的成功提示
      fetchUsers(); // 重新加载用户列表
    } catch (err) {
      console.error(`删除用户 ${userId} 失败:`, err);
      error.value = `删除用户失败: ${err.response?.data || err.message || '未知错误'}`;
      // alert(`删除用户失败: ${error.value}`); // 可选的错误提示
    } finally {
      deletingUser.value = null;
    }
  }
};

// --- 以下是新增/编辑用户的占位逻辑，如果需要可以取消注释并完善 ---
// const openAddUserModal = () => {
//   editingUser.value = null;
//   userForm.uid = null;
//   userForm.username = '';
//   userForm.password = '';
//   formError.value = null;
//   showUserModal.value = true;
// };

// const openEditUserModal = (user) => {
//   editingUser.value = user;
//   userForm.uid = user.uid;
//   userForm.username = user.username;
//   userForm.password = ''; // 编辑时不直接显示密码，通常是重置密码功能
//   formError.value = null;
//   showUserModal.value = true;
// };

// const handleAddUser = async () => {
//   // 后端需要支持 /admin/users 的 POST 请求来创建用户
//   formError.value = null;
//   if (!userForm.username.trim() || !userForm.password.trim()) {
//     formError.value = "用户名和密码不能为空。";
//     return;
//   }
//   try {
//     // 注意：后端注册接口通常是 /auth/register，后台新增用户接口可能不同
//     // 此处假设有一个 /admin/users POST 接口
//     await axios.post(`${API_BASE_URL}/users`, { username: userForm.username, password: userForm.password });
//     closeUserModal();
//     fetchUsers();
//   } catch (err) {
//     formError.value = `新增用户失败: ${err.response?.data?.message || err.message}`;
//     console.error(err);
//   }
// };

// const handleUpdateUser = async () => {
//   // 后端需要支持 /admin/users/{uid} 的 PUT 请求来更新用户信息
//   // 更新时通常不直接更新密码，或提供单独的重置密码功能
//   formError.value = null;
//   if (!userForm.username.trim()) {
//     formError.value = "用户名不能为空。";
//     return;
//   }
//   try {
//     await axios.put(`${API_BASE_URL}/users/${userForm.uid}`, { username: userForm.username /* 可能还有其他字段 */ });
//     closeUserModal();
//     fetchUsers();
//   } catch (err) {
//     formError.value = `更新用户失败: ${err.response?.data?.message || err.message}`;
//     console.error(err);
//   }
// };

// const closeUserModal = () => {
//   showUserModal.value = false;
//   editingUser.value = null;
//   formError.value = null;
// };

onMounted(() => {
  fetchUsers();
});
</script>

<style scoped>
/* 样式可以从 CompanyManagementView.vue 或其他类似视图中复用 */
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
  transition: background-color 0.2s, opacity 0.2s;
}
.action-button:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.add-button { background-color: #409eff; color: white; }
.add-button:hover:not(:disabled) { background-color: #66b1ff; }

.edit-button { background-color: #e6a23c; color: white; }
.edit-button:hover:not(:disabled) { background-color: #ebb563; }

.delete-button { background-color: #f56c6c; color: white; }
.delete-button:hover:not(:disabled) { background-color: #f78989; }

.cancel-button { background-color: #909399; color: white; }
.cancel-button:hover:not(:disabled) { background-color: #a6a9ad; }

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

/* 简易模态框样式 (如果启用) */
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
  width: 400px;
}
.modal-content h3 { margin-top: 0; margin-bottom: 20px; color: #303133; }
.modal-content div { margin-bottom: 15px; }
.modal-content label { display: block; margin-bottom: 5px; font-weight: 500; color: #606266; }
.modal-content input[type="text"], .modal-content input[type="password"] {
  width: calc(100% - 20px);
  padding: 10px;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  box-sizing: border-box;
}
.modal-actions { margin-top: 20px; text-align: right; }
.modal-actions .action-button { margin-left: 10px; }
</style>