<template>
    <div>
      <h2>净利率、净资产收益率、总资产报酬率变化情况</h2>
      <select v-model="selectedCompany" @change="getData">
        <option v-for="company in companies" :key="company" :value="company">{{ company }}</option>
      </select>
      <div class="chartFour" id="financeChart"></div>
    </div>
  </template>
  
  <script>
  import { inject, onMounted, reactive, ref } from "vue";
  
  export default {
    setup() {
      const $echarts = inject("echarts");
      const $http = inject("axios");
  
      const selectedCompany = ref('A');
      const companies = ['A', 'B', 'C', 'D', 'E'];
  
      const financeData = reactive({
        TARData: [],
        NARData: [],
        NPMData: []
      });
  
      function setData() {
        // 过滤对应公司的数据
        let filteredData = {
          netAssetReturnRates: [],
          netProfitMargins: [],
          totalAssetReturnRates: []
        };
  
        for (let year of ['2018', '2019', '2020', '2021', '2022', '2023']) {
          const tar = financeData.TARData[year].find(d => d.company === selectedCompany.value);
          const nar = financeData.NARData[year].find(d => d.company === selectedCompany.value);
          const npm = financeData.NPMData[year].find(d => d.company === selectedCompany.value);
  
          filteredData.netAssetReturnRates.push(nar ? nar.netAssetReturnRate : 0);
          filteredData.netProfitMargins.push(npm ? parseFloat(npm.netProfitMargin) : 0);
          filteredData.totalAssetReturnRates.push(tar ? tar.totalAssetReturnRate : 0);
        }
  
        return filteredData;
      }
  
      async function getData() {
        let response = await $http({ url: "/four/data" });
        financeData.TARData = response.data.TARData;
        financeData.NARData = response.data.NARData;
        financeData.NPMData = response.data.NPMData;
  
        const filteredData = setData();
        
        let financeChart = $echarts.init(document.getElementById("financeChart"));
        financeChart.setOption({
          /* title: {
            text: `公司财务数据 (${selectedCompany.value})`
          }, */
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'shadow'
            }
          },
          legend: {
            data: ['净资产收益率', '净利率', '总资产报酬率']
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
          },
          xAxis: {
            type: 'category',
            data: ['2018', '2019', '2020', '2021', '2022', '2023'],
            axisLine: {
            lineStyle: {
              color: "#fff"
            }
          }
          },
          yAxis: {
            type: 'value',
            axisLine: {
            lineStyle: {
              color: "#fff"
            }
          }
          },
          series: [
            {
              name: '净资产收益率',
              type: 'bar',
              stack: '总量',
              data: filteredData.netAssetReturnRates,
              color: '#3398DB'
            },
            {
              name: '净利率',
              type: 'bar',
              stack: '总量',
              data: filteredData.netProfitMargins,
              color: '#5470C6'
            },
            {
              name: '总资产报酬率',
              type: 'bar',
              stack: '总量',
              data: filteredData.totalAssetReturnRates,
              color: '#91CC75'
            }
          ]
        });
      }
  
      onMounted(() => {
        getData();
      });
  
      return {
        selectedCompany,
        companies,
        getData
      };
    }
  };
  </script>
  
  <style>
  .chartFour {
    height: 4rem;  
    width: 100%;
  }
  select {
    margin-bottom: .125rem;
  }
  </style>
  