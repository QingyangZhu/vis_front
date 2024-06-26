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
      <div class="chartThree" id="bubbleChart"></div>
    </div>
  </template>
  
  <script>
  import { ref, reactive, onMounted, inject } from 'vue';
  
  export default {
    setup() {
      const echarts = inject('echarts');
      const axios = inject('axios');
  
      const selectedAttribute = ref('totalAssetTurnover');
      const chart = ref(null);
  
      const data = reactive({
        TTData: {},
        ITData: {},
        RTData: {}
      });
  
      const companyColors = {
        'A': '#ff0000',
        'B': '#00ff00',
        'C': '#0000ff',
        'D': '#ffff00',
        'E': '#ff00ff'
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
          legend: {
            data: companies,
            textStyle: {
              color: '#fff'
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
  .chartThree {
    height: 4rem;
    width: 100%;
    margin-top: .125rem;
  }
  select {
    margin-bottom: .125rem;
  }
  </style>
  