<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script setup>
// 如果之前有 setup 内容，保留它
</script>

/* src/App.vue */
<style>
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
  /* 移除 display: flex 和相关的居中属性，这些应由 VisView 自己处理 */
  /* 移除 ::before 的背景，这个也应由 VisView 自己处理 */
}

/* VisView 特有样式应该在 VisView.vue 中或通过 body.vis-active 控制 */
body.vis-active {
  overflow: hidden; /* 大屏通常不需要滚动条 */
}
body.vis-active #app {
  /* 如果 VisView 需要 flex 居中，在这里或 VisView.vue 内部实现 */
  /* 例如，VisView.vue 的根元素可以设置 flex 居中 */
}
body.vis-active #app::before { /* 假设这个背景属于 VisView */
  content: '';
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(rgba(0, 0, 0, 0.4), rgba(0, 0, 0, 0.4)),
  url('@/assets/bg_black.png') no-repeat center center;
  background-size: cover;
  z-index: -2;
}


/* Admin 特有样式 */
body.admin-active {
  overflow: auto; /* 后台界面可能需要滚动 */
  background-color: #f0f2f5; /* 也可以在 AdminLayout.vue 中设置 */
}
body.admin-active #app::before { /* 确保 VisView 的背景在 admin 模式下不显示 */
  display: none;
}
/* Admin 模式下，#app 通常是 display: block (默认) 或其他适合 AdminLayout 的设置 */
body.admin-active #app {
  display: block; /* 确保覆盖 VisView 的 flex 设置 */
}
</style>