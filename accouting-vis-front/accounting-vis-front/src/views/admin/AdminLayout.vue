<template>
  <div class="admin-layout">
    <SidebarNav class="admin-sidebar" />
    <main class="admin-main-content">
      <router-view />
    </main>
  </div>
</template>

<script setup>
import SidebarNav from '@/components/admin/SidebarNav.vue'
</script>

<style scoped>
.admin-layout {
  display: flex;
  width: 100vw;
  height: 100vh;
  background-color: #f0f2f5; /* 后台常用的浅灰色背景 */
  color: #333; /* 与您的 VisView 暗色主题形成对比 */
  overflow: hidden; /* 防止内部滚动影响布局 */
}

.admin-sidebar {
  width: 220px; /* 侧边栏宽度 */
  flex-shrink: 0; /* 防止侧边栏被压缩 */
  background-color: #001529; /* 深色侧边栏背景 */
  color: #fff;
  height: 100%;
  overflow-y: auto;
}

.admin-main-content {
  flex-grow: 1;
  padding: 20px;
  overflow-y: auto; /* 内容区域允许滚动 */
  height: 100%;
  box-sizing: border-box;
}
</style>