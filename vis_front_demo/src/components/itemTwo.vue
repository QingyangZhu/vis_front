<template>
    <div>
      <h2>流动比率和速动比率图表</h2>
      <select v-model="selectedYear" @change="getData">
        <option v-for="year in years" :key="year" :value="year">{{ year }}</option>
      </select>
      <div class="chartTwo" id="ratioChart"></div>
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
        //console.log(chartData.companies, chartData.currentRatios, chartData.quickRatios);
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
            //text: `流动比率和速动比率 (${selectedYear.value})`
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
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
          },
          xAxis: {
            type: 'value',
            axisLine: {
              lineStyle: {
                color: "#fff"
              }
            }
          },
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
              label: {
                show: true,
                position: 'insideRight'
              },
              itemStyle: {
                color: '#339ca8'
              },
              data: chartData.currentRatios
            },
            {
              name: '速动比率',
              type: 'bar',
              label: {
                show: true,
                position: 'insideRight'
              },
              itemStyle: {
                color: '#cda819'
              },
              data: chartData.quickRatios
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
  .chartTwo {
    height: 4rem;
    width: 100%;
  }
  select {
    margin-bottom: .125rem;
  }
  </style>
  