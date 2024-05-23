<template>
  <div id="CR">
    <div id="CRC"></div>
  </div>
</template>

<script>
import * as echarts from "echarts";
export default {
    mounted(){
        var myChart = echarts.init(document.getElementById('CRC'));
        myChart.setOption({
            title: {
    text: '2018-2023各公司流动比率'
  },
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'cross',
      label: {
        backgroundColor: '#6a7985'
      }
    }
  },
  legend: {
    data: ['蔚来', '理想', '比亚迪', '零跑', '小鹏汽车']
  },
  toolbox: {
    feature: {
      saveAsImage: {}
    }
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
  },
  xAxis: [
    {
      type: 'category',
      boundaryGap: false,
      data: ['2018', '2019', '2020', '2021', '2022','2023']
    }
  ],
  yAxis: [
    {
      type: 'value'
    }
  ],
  series: [
    {
      name: '蔚来',
      type: 'line',
      stack: 'Total',
      areaStyle: {},
      emphasis: {
        focus: 'series'
      },
      data: [1.42,0.52,3.31,2.18,1.29,0]
    },
    {
      name: '理想',
      type: 'line',
      stack: 'Total',
      areaStyle: {},
      emphasis: {
        focus: 'series'
      },
      data: [1.31,1.08,7.28,4.33,2.45,1.57]
    },
    {
      name: '比亚迪',
      type: 'line',
      stack: 'Total',
      areaStyle: {},
      emphasis: {
        focus: 'series'
      },
      data: [0.99,0.99,1.05,0.97,0.72,0]
    },
    {
      name: '零跑',
      type: 'line',
      stack: 'Total',
      areaStyle: {},
      emphasis: {
        focus: 'series'
      },
      data: [0,1.00,1.60,2.07,1.47,0]
    },
    {
      name: '小鹏汽车',
      type: 'line',
      stack: 'Total',
      label: {
        show: true,
        position: 'top'
      },
      areaStyle: {},
      emphasis: {
        focus: 'series'
      },
      data: [6.28,1.50,5.06,2.71,1.81,0]
    }
  ]
            
        })
    }


}
</script>

<style>
#CRC{
    width: 1200px;
    height: 400px;
    border: 1px solid #000;
}

</style>