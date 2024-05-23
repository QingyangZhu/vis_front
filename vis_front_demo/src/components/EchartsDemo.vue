<template>
    <div id="echartsdemo">
        <h1>Hello echarts</h1>
        <div id="main"></div>
    </div>
</template>

<script>
import * as echarts from "echarts";
export default {
    mounted() {
         // 基于准备好的dom，初始化echarts实例
      var myChart = echarts.init(document.getElementById('main'));
         // 指定图表的配置项和数据
         myChart.setOption({
            title: {
                text: 'ECharts 入门示例'
            },
            topline:{},
            legend: {
                data:['销量']
            },
            xAxis: {
                data: ['衬衫', '羊毛衫', '雪纺衫', '裤子', '高跟鞋', '袜子']
            },
            yAxis: {},
            series: [{
                name: '销量',
                type: 'bar',
                data: [5, 20, 36, 10, 10, 20]
            }],
         })
    }
}
</script>

<style>
#main{
    width: 600px;
    height: 400px;
    border: 1px solid #000;
}

</style>