<template>
  <div class="main-page">
    <HeaderInfo />
    <div class="dashboard">
      <ChartOne/>
      <ChartTwo></ChartTwo>
    </div>
  </div>
</template>

<script setup>
import HeaderInfo from "@/components/HeadInfo.vue";
import ChartOne from "@/components/chartOne.vue";
import ChartTwo from "@/components/ChartTwo.vue";

</script>

<style scoped>
.dashboard {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  padding: 20px;
  justify-content: space-between; /* 确保模块在容器中均匀分布 */
}
</style>