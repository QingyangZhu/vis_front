<template>
  <div class="header">
    <HeaderInfo/>
  </div>
  <div class="main-layout">

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

    <div class="right-functional-area">
      <CompanyIntroduction />
      <ChatWindow class="ai-assistant-window"/> </div>

  </div>
</template>

<script setup>
import HeaderInfo from "@/components/HeaderInfo.vue";
import ChartOne from '@/components/ChartOne.vue';
import ChartTwo from '@/components/ChartTwo.vue';
import ChartThree from '@/components/ChartThree.vue';
import ChartFour from '@/components/ChartFour.vue';
import ChartFive from '@/components/ChartFive.vue';
import ChartSix from '@/components/ChartSix.vue';
import CompanyIntroduction from '@/components/CompanyInfoView.vue'; // 新增引入
import ChatWindow from '@/components/ChatWindow.vue';
</script>

<style scoped>
/* 主布局 */
.main-layout {
  width: 100vw; /* 直接用视口宽度 */
  height: calc(100vh - var(--header-height, 60px)); /* 假设header有一个高度，你需要实际定义或传递 */
  /* 如果 HeaderInfo 是固定定位或绝对定位，则这里可以是 100vh */
  display: flex;
  flex-direction: row;
  box-sizing: border-box;
  overflow: hidden; /* 防止内部滚动影响外层 */
  /* background-color: #1e1e1e; */ /* 示例深色背景，根据你的主题调整 */
}

/* 左边图表区 - 保持不变 */
.charts-area {
  flex: 7; /* 占据70%的宽度 */
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  gap: 20px; /* 行之间的间距 */
  padding: 20px;
  overflow-y: auto; /* 允许图表区域内部滚动 */
  box-sizing: border-box;
}

/* 每一行放三个图表 - 保持不变 */
.charts-row {
  display: flex;
  /* justify-content: space-between; */ /* 使用gap替代，更均匀 */
  gap: 20px; /* 图表之间的横向间距 */
}

/* 统一每个图表大小 - 保持不变 */
/* 注意：如果图表组件本身没有设置宽度，这很重要 */
.charts-row > * {
  flex: 1 1 calc(33.333% - (40px / 3)); /* (40px = 2 * 20px gap) / 3 图表 */
  /* max-width: calc(33.333% - (40px / 3)); */ /* 可以省略，flex-basis已处理 */
  /* 确保图表组件内部能正确响应flex布局 */
  min-height: 200px; /* 给图表一个最小高度示例 */
  background-color: rgba(30, 30, 30, 0.3); /* 示例图表背景 */
  border-radius: 6px;
}

/* 右边功能区 (替代原 .chat-area) */
.right-functional-area {
  flex: 3; /* 占据30%的宽度 */
  display: flex;
  flex-direction: column; /* 使公司简介和AI助手垂直排列 */
  gap: 15px; /* 公司简介和AI助手之间的间距 */
  padding: 20px;
  /* background-color: rgba(21, 16, 16, 0.5); */ /* 可以给整个右侧区域一个统一背景 */
  /* 移除原 chat-area 的 align-items 和 justify-content，因为现在是多模块垂直排列 */
  box-shadow: -3px 0 6px rgba(0,0,0,0.2); /* 左侧阴影，调整得柔和一些 */
  border-left: 1px solid rgba(0,0,0,0.1); /* 左边框，调整颜色 */
  overflow: hidden; /* 防止内部模块溢出导致滚动条出现在这里 */
  box-sizing: border-box;
}

/* 为AI助手(ChatWindow)特定调整，使其填满剩余空间 */
.ai-assistant-window {
  flex-grow: 1; /* AI助手窗口占据剩余的垂直空间 */
  background-color: rgba(21, 16, 16, 0.5); /* 保持AI助手的原背景 */
  border-radius: 8px;
  padding: 15px; /* ChatWindow 可能也需要自己的内边距 */
  overflow-y: auto; /* AI助手内容本身可以滚动 */
  display: flex; /* 如果ChatWindow内部需要flex布局 */
  flex-direction: column; /* 如果ChatWindow内部是垂直布局 */
  box-sizing: border-box;
}

/* 如果HeaderInfo是文档流的一部分，需要为main-layout减去其高度 */
/* 假设.header的高度可以通过CSS变量 --header-height 传递，或者直接写死 */
/* 例如: .header { height: 60px; } */
/* :root { --header-height: 60px; } 或在父组件定义 */
/* 如果HeaderInfo组件是绝对定位或固定定位，则main-layout的height: 100vh就可以 */

</style>