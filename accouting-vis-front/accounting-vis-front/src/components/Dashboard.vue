<template>
  <div class="dashboard">
    <slot />
  </div>
</template>

<style scoped>
.dashboard {
  display: grid;
  grid-template-columns: repeat(3, 1fr); /* 3列布局 */
  gap: 30px;
  padding: 40px 60px;
  max-width: 1600px;
  margin: 0 auto;
}

/* 小屏幕适配，每行一个图表 */
@media (max-width: 1024px) {
  .dashboard {
    grid-template-columns: 1fr; /* 一列布局 */
  }
}

/* 模块样式 */
.module {
  background: rgba(0, 0, 0, 0.5);
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0 8px 16px rgba(0,0,0,0.3);
  transition: transform 0.3s ease;
  display: flex;
  flex-direction: column;
}

.module:hover {
  transform: scale(1.05); /* 鼠标悬停效果 */
}
</style>
