<template>
  <div class="module" @mouseover="handleMouseOver" @mouseleave="handleMouseLeave">
    <header style="text-align: center;"><h2 class="header-title">综合评估</h2></header>
    <div ref="radarChart" id="main" style="width: 100%; height: 500px; "></div>
  </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: 'CarouselModule',
  methods: {
    handleMouseOver() {
      this.$el.style.transform = 'scale(1.05)';
    },
    handleMouseLeave() {
      this.$el.style.transform = 'scale(1)';
    },
    drawRadarChart() {
      const chartDom = this.$refs.radarChart;
      const myChart = echarts.init(chartDom);

      const option = {
        tooltip: {
          trigger: 'item',
          formatter: function(params) {
            return `${params.seriesName} <br/>${params.name}: ${params.value}`;
          }
        },
        legend: {
          data: ['蔚来', '理想', '比亚迪', '零跑', '小鹏汽车'],
          textStyle: { color: '#fff' }  // Legend text color
        },
        radar: {
          indicator: [
            { name: '股票价值', max: 7 },
            { name: '偿债', max: 7 },
            { name: '盈利', max: 7 },
            { name: '成长', max: 7 },
            { name: '运营', max: 7 }
          ],
          name: {
            textStyle: {
              color: '#fff'  // Indicator name color
            }
          },
          splitLine: { lineStyle: { color: '#fff' } },  // Grid lines color
          axisLine: { lineStyle: { color: '#fff' } }    // Axis line color
        },
        series: [
          {
            name: 'Attributes',
            type: 'radar',
            data: [
              {
                value: [2.56, 3.74, 1.58, 4.63, 2.41],  // Data for 蔚来
                name: '蔚来'
              },
              {
                value: [3.44, 5.12, 2.93, 3.59, 4.83],  // Data for 理想
                name: '理想'
              },
              {
                value: [5.34, 4.21, 6.34, 6.89, 5.66],  // Data for 比亚迪
                name: '比亚迪'
              },
              {
                value: [1.87, 3.58, 1.23, 2.93, 3.45],  // Data for 零跑
                name: '零跑'
              },
              {
                value: [3.67, 3.45, 1.98, 3.21, 4.33],  // Data for 小鹏汽车
                name: '小鹏汽车'
              }
            ]
          }
        ]
      };

      myChart.setOption(option);
    }
  },
  mounted() {
    this.drawRadarChart();
  }
}
</script>


<style scoped>
/* 统一模块、状态提示、选择器样式 */
@import '@/styles/module-common.css';
</style>