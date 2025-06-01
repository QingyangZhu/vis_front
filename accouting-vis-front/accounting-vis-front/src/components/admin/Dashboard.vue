<template>
  <div class="admin-page-container">
    <h2 class="page-header">仪表盘</h2>
    <div class="content-card">
      <p>欢迎来到后台管理系统。</p>
      <p>您可以在这里查看关键指标和快速导航到其他管理模块。</p>
    </div>
  </div>
</template>

<script setup>
// 后续可添加图表或统计数据逻辑
</script>

<style scoped>
.admin-page-container {
  /* 页面容器样式 */
}
.page-header {
  font-size: 22px;
  color: #333;
  margin-bottom: 20px;
  font-weight: 500;
}
.content-card {
  background-color: #fff; /* 如果 AdminLayout.vue 中的 wrapper 不是白色背景 */
  padding: 24px;
  border-radius: 2px;
  /* box-shadow: 0 1px 3px rgba(0,0,0,.1); 如果 AdminLayout.vue 中的 wrapper 没有阴影 */
}
p {
  line-height: 1.6;
}
</style>