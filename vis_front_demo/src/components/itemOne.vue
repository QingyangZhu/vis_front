<template>
  <div>
    <h2>图1</h2>
    <div class="chart" id="oneChart"></div>
  </div>
</template>

<script>
import { inject, onMounted, reactive } from "vue";
export default {
  setup() {
    let $echarts = inject("echarts");
    let $http = inject("axios");

    let data = reactive({}); // 变量
    let chartData = reactive({
      xData: [],
      yData: [],
    });

    function setData() {
      chartData.xData = data.data.chartData.chartData.map((v) => v.title);
      chartData.yData = data.data.chartData.chartData.map((v) => v.num);
      //console.log(chartData.xData, chartData.yData);
    }

    async function getData() {
      let response = await $http({ url: "/one/data" });
      data.data = response.data; // 确保响应式更新
      //console.log(data.data.chartData);
    }

    onMounted(() => {
        let chartOne = $echarts.init(document.getElementById("oneChart"));
      // 获取数据
      getData().then(() => {
        
        // 等先获取数据之后再处理数据
        setData();
        // 生命周期钩子中直接调用echarts的初始化方法等
        
        chartOne.setOption({
          grid:{
            top:"3%",
            left:"1%",
            right:"6%",
            bottom:"3%",
            containLabel:true

          },
          xAxis: {
            type: "value",
            axisLine:{
              lineStyle:{
                color:"#fff"
              }
            }
          },
          yAxis: {
            type: "category",
            data: chartData.xData,
             axisLine:{
              lineStyle:{
                color:"#fff"
              }
            }
          },
          series: [
            {
              data: chartData.yData,
              type: "bar",
              itemStyle: {
                normal: {
                  barBorderRadius:[0,20,20,0],
                  color: new $echarts.graphic.LinearGradient(0, 0, 1, 0, [
                    {
                      offset: 0,
                      color: "#005eaa",
                    },
                    {
                      offset: 0.5,
                      color: "#339ca8",
                    },
                    {
                      offset: 1,
                      color: "#cda819",
                    },
                  ]),
                },
              },
            },
          ],
        });
      });
    });

    return {
      getData,
      data,
      chartData,
      setData,
    };
  },
};
</script>

<style>
.chart{
  height: 4rem;
}
</style>