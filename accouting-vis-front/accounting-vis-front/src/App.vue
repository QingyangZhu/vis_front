<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<style>
html, body {
  margin: 0;
  padding: 0;
  width: 100%;
  height: 100%;
  overflow: hidden;
  box-sizing: border-box; /* 确保所有元素的大小都包括 padding 和 border */
}

#app {
  display: flex;
  flex-direction: column;
  justify-content: center;   /* 垂直居中 */
  align-items: center;       /* 水平居中 */
  width: 100%;
  height: 100vh;             /* 高度占满视口 */
  position: relative;
}

#app::before {
  content: '';
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(rgba(0, 0, 0, 0.4), rgba(0, 0, 0, 0.4)),
  url('@/assets/background.png') no-repeat center center;
  background-size: cover;
  z-index: -2; /* 确保背景在最底层 */
}

</style>
