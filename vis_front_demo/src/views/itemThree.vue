<template>
    <div>
      <h2>总资产周转率、存货周转率、应收账款周转率气泡图</h2>
      <div>
        <label for="attribute">选择属性：</label>
        <select v-model="selectedAttribute" @change="getData">
          <option value="totalAssetTurnover">总资产周转率</option>
          <option value="inventoryTurnover">存货周转率</option>
          <option value="receivablesTurnover">应收账款周转率</option>
        </select>
      </div>
      <div class="chart" id="bubbleChart"></div>
    </div>
  </template>
  
  <script>
  import { ref, reactive, onMounted } from 'vue';
  import axios from 'axios';
  import * as echarts from 'echarts';
  
  export default {
    setup() {
      const selectedAttribute = ref('totalAssetTurnover');
      const chart = ref(null);
  
      const data = reactive({
        TTData: {},
        ITData: {},
        RTData: {}
      });
  
      const companyColors = {
        '蔚来': '#ff0000',
        '理想': '#00ff00',
        '比亚迪': '#0000ff',
        '零跑': '#ffff00',
        '小鹏汽车': '#ff00ff'
      };
  
      const getData = async () => {
        const response = await axios.get('/three/data');
        data.TTData = response.data.TTData;
        data.ITData = response.data.ITData;
        data.RTData = response.data.RTData;
        const attributeData = {
          'totalAssetTurnover': data.TTData,
          'inventoryTurnover': data.ITData,
          'receivablesTurnover': data.RTData
        };
        const formattedData = formatData(attributeData[selectedAttribute.value]);
        renderChart(formattedData);
      };
  
      const formatData = (data) => {
        const years = ['2018', '2019', '2020', '2021', '2022', '2023'];
        const formattedData = [];
        years.forEach((year, yearIndex) => {
          data[year].forEach(item => {
            if (item[selectedAttribute.value]) {
              formattedData.push([yearIndex, item.company, parseFloat(item[selectedAttribute.value]), companyColors[item.company]]);
            }
          });
        });
        return formattedData;
      };
  
      const renderChart = (data) => {
        const years = ['2018', '2019', '2020', '2021', '2022', '2023'];
        const companies = Array.from(new Set(data.map(item => item[1])));
  
        const seriesData = companies.map(company => {
          return {
            name: company,
            type: 'scatter',
            data: data.filter(item => item[1] === company).map(item => [item[0], item[2]]),
            itemStyle: {
              color: companyColors[company]
            },
            symbolSize: function (data) {
              return data[1] * 10;
            },
            emphasis: {
              label: {
                show: true,
                formatter: function (params) {
                  return `${company} (${years[params.data[0]]}): ${params.data[1]}`;
                },
                position: 'top'
              }
            }
          };
        });
  
        const option = {
          tooltip: {
            trigger: 'item',
            formatter: function (params) {
              return `${params.seriesName}<br/>${years[params.data[0]]}: ${params.data[1]}`;
            }
          },
          xAxis: {
            type: 'category',
            data: years,
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
          series: seriesData
        };
  
        if (!chart.value) {
          chart.value = echarts.init(document.getElementById('bubbleChart'));
        }
  
        chart.value.setOption(option);
      };
  
      onMounted(() => {
        getData();
      });
  
      return {
        selectedAttribute,
        getData
      };
    }
  };
  </script>
  
  <style>
  .chart {
    height: 400px;
    width: 100%;
    margin-top: 20px;
  }
  select {
    margin-bottom: 20px;
  }
  </style>
  