<template>
  <div id="app">
    <!-- 路由出口 -->
    <router-view></router-view>
  </div>
</template>

<style>
/* 全局样式重置 */
html, body {
  margin: 0;
  padding: 0;
  width: 100%;
  height: 100%;
  overflow: hidden; /* 防止滚动条出现 */
}

/* 主容器 */
#app {
  position: relative;
  width: 100%;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

/* 背景图片设置 */
#app::before {
  content: '';
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: url('@/assets/background.png') no-repeat center center;
  background-size: cover;
  z-index: -1; /* 将背景置于底层 */
}

</style>