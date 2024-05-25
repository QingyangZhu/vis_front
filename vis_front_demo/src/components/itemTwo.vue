<template>
    <div>
      <h2>流动比率和速动比率图表</h2>
      <select v-model="selectedYear" @change="getData">
        <option v-for="year in years" :key="year" :value="year">{{ year }}</option>
      </select>
      <div class="chart" id="ratioChart"></div>
    </div>
  </template>
  
  <script>
  import { inject, onMounted, reactive, ref } from "vue";
  
  export default {
    setup() {
      const $echarts = inject("echarts");
      const $http = inject("axios");
  
      const selectedYear = ref('2018');
      const years = ['2018', '2019', '2020', '2021', '2022', '2023'];
  
      const data = reactive({
        CRData: [],
        QRData: []
      });
  
      const chartData = reactive({
        companies: [],
        currentRatios: [],
        quickRatios: []
      });
  
      function setData() {
        chartData.companies = data.CRData.map(v => v.company);
        chartData.currentRatios = data.CRData.map(v => parseFloat(v.currentRatio) || 0);
        chartData.quickRatios = data.QRData.map(v => parseFloat(v.quickRatio) || 0);
        console.log(chartData.companies, chartData.currentRatios, chartData.quickRatios);
      }
  
      async function getData() {
        let response = await $http({ url: "/CRandQRRouter/data" });
        const CRDataForYear = response.data.CRData[selectedYear.value];
        const QRDataForYear = response.data.QRData[selectedYear.value];
  
        data.CRData = CRDataForYear;
        data.QRData = QRDataForYear;
  
        setData();
  
        let chartRatio = $echarts.init(document.getElementById("ratioChart"));
        chartRatio.setOption({
          title: {
            text: `流动比率和速动比率 (${selectedYear.value})`
          },
          grid: {
            top: "3%",
            left: "10%",
            right: "10%",
            bottom: "3%",
            containLabel: true
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'shadow'
            }
          },
          legend: {
            data: ['流动比率', '速动比率']
          },
          xAxis: [
            {
              type: 'value',
              position: 'bottom',
              axisLine: {
                lineStyle: {
                  color: "#fff"
                }
              },
              axisLabel: {
                formatter: '{value}'
              }
            },
            {
              type: 'value',
              position: 'bottom',
              axisLine: {
                lineStyle: {
                  color: "#fff"
                }
              },
              axisLabel: {
                formatter: '{value}'
              }
            }
          ],
          yAxis: {
            type: 'category',
            data: chartData.companies,
            axisLine: {
              lineStyle: {
                color: "#fff"
              }
            }
          },
          series: [
            {
              name: '流动比率',
              type: 'bar',
              data: chartData.currentRatios,
              xAxisIndex: 0,
              itemStyle: {
                normal: {
                  barBorderRadius: [0, 20, 20, 0],
                  color: new $echarts.graphic.LinearGradient(0, 0, 1, 0, [
                    { offset: 0, color: "#005eaa" },
                    { offset: 0.5, color: "#339ca8" },
                    { offset: 1, color: "#cda819" }
                  ])
                }
              }
            },
            {
              name: '速动比率',
              type: 'bar',
              data: chartData.quickRatios,
              xAxisIndex: 1,
              itemStyle: {
                normal: {
                  barBorderRadius: [20, 0, 0, 20],
                  color: new $echarts.graphic.LinearGradient(1, 0, 0, 0, [
                    { offset: 0, color: "#ff4500" },
                    { offset: 0.5, color: "#ff8c00" },
                    { offset: 1, color: "#ffd700" }
                  ])
                }
              }
            }
          ]
        });
      }
  
      onMounted(() => {
        getData();
      });
  
      return {
        selectedYear,
        years,
        data,
        chartData,
        getData,
        setData
      };
    }
  };
  </script>
  
  <style>
  .chart {
    height: 500px;
    width: 100%;
  }
  select {
    margin-bottom: 20px;
  }
  </style>
  