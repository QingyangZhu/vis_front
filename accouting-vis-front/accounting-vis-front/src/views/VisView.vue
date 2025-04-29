<template>
  <div class="header">
    <HeaderInfo/>
  </div>
  <div class="main-layout">

    <!-- 左边图表区域 -->
    <div class="charts-area">
      <div class="charts-row">
        <ChartOne />
        <ChartTwo />
        <ChartThree />
      </div>
      <div class="charts-row">
        <ChartFour />
        <ChartFive />
        <ChartSix />
      </div>
    </div>

    <!-- 右边聊天区域 -->
    <div class="chat-area">
      <ChatWindow />
    </div>

  </div>
</template>

<script setup>
import HeaderInfo from "@/components/HeaderInfo.vue";
import ChartOne from '@/components/ChartOne.vue'
import ChartTwo from '@/components/ChartTwo.vue'
import ChartThree from '@/components/ChartThree.vue'
import ChartFour from '@/components/ChartFour.vue'
import ChartFive from '@/components/ChartFive.vue'
import ChartSix from '@/components/ChartSix.vue'
import ChatWindow from '@/components/ChatWindow.vue'
</script>

<style scoped>
/* 主布局 */
.main-layout {
  width: 100vw; /* 直接用视口宽度 */
  height: 100vh; /* 也确保高度全屏 */
  display: flex;
  flex-direction: row;
  box-sizing: border-box;
  overflow: hidden;
}
/* 左边图表区 */
.charts-area {
  flex: 7;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  gap: 20px;
  padding: 20px;
  overflow-y: auto;
}

/* 每一行放三个图表 */
.charts-row {
  display: flex;
  justify-content: space-between;
  gap: 20px;
}

/* 统一每个图表大小 */
.charts-row > * {
  flex: 1 1 calc(33.33% - 20px);
  max-width: calc(33.33% - 20px);
}

/* 右边聊天区 */
.chat-area {
  flex: 3;
  border-radius: 8px;
  background-color: rgba(21, 16, 16, 0.5);
  padding: 20px;
  overflow-y: auto;
  display: flex;
  align-items: center; /* 垂直居中聊天框 */
  justify-content: center; /* 水平居中聊天框 */
  box-shadow: -4px 0 8px rgba(0,0,0,0.5); /* 左侧阴影 */
  border-left: 1px solid rgba(21, 16, 16, 0.5);
}
</style>
