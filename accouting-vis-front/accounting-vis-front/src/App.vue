<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script setup>
// 如果之前有 setup 内容，保留它
</script>

<style>
/* 全局基础重置 */
html, body {
  margin: 0;
  padding: 0;
  width: 100%;
  height: 100%;
  box-sizing: border-box;
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

#app {
  width: 100%;
  height: 100%;
  position: relative; /* 保持 relative 以便伪元素定位 */
}

/* 可视化大屏特定样式 (当 body 有 .vis-active 类时) */
body.vis-active {
  overflow: hidden; /* 大屏通常不需要滚动条 */
}

#app:has(> .vis-view-wrapper), /* 如果 VisView 有一个特定的包装器类 */
body.vis-active #app { /* 或者通过 body 类来控制 */
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

body.vis-active #app::before {
  content: '';
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(rgba(0, 0, 0, 0.4), rgba(0, 0, 0, 0.4)),
  url('@/assets/background.png') no-repeat center center; /* 确保路径正确 */
  background-size: cover;
  z-index: -2; /* 确保背景在最底层 */
}


/* 后台管理特定样式 (当 body 有 .admin-active 类时) */
body.admin-active {
  overflow: auto; /* 后台界面可能需要滚动 */
  background-color: #f0f2f5; /* 后台常用的浅灰色背景, 也可由 AdminLayout 设置 */
}

/* 如果 #app::before 对 admin 页面有干扰，可以隐藏它 */
body.admin-active #app::before {
  display: none;
}

/* Admin 模式下 #app 通常不需要 flex 居中 */
body.admin-active #app {
  display: block; /* 或者其他适合 admin 布局的 display 属性 */
}
</style>