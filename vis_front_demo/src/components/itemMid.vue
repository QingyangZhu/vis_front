<template>
  <div class="containerMid">
    <h2>公司财务增长数据</h2>
    <div class="sidebar">
      <button v-for="company in companies" :key="company" @click="changeCompany(company)">
        {{ company }}
      </button>
    </div>
    <div class="chartContainer">
      <div class="chartMid" ref="chartMid"></div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted, inject } from 'vue';

export default {
  setup() {
    const echarts = inject('echarts');
    const axios = inject('axios');
    const chartInstance = ref(null);
    const companies = ['蔚来', '理想', '比亚迪', '零跑', '小鹏汽车'];
    const currentCompany = ref('蔚来');

    const getData = async () => {
      const response = await axios.get('/five/data');
      const formattedData = formatData(response.data, currentCompany.value);
      updateChart(formattedData);
    };

    const formatData = (data, company) => {
      const years = Object.keys(data.SGRData);
      const salesGrowth = years.map(year => {
        const item = data.SGRData[year].find(item => item.company === company);
        return item ? item.salesGrowthRate : null;
      });
      const netProfitGrowth = years.map(year => {
        const item = data.NPGData[year].find(item => item.company === company);
        return item ? item.netProfitGrowthRate : null;
      });
      const totalAssetGrowth = years.map(year => {
        const item = data.TAGData[year].find(item => item.company === company);
        return item ? item.totalAssetGrowthRate : null;
      });
      return {
        years,
        salesGrowth,
        netProfitGrowth,
        totalAssetGrowth
      };
    };

    const updateChart = ({years, salesGrowth, netProfitGrowth, totalAssetGrowth}) => {
      const options = {
        angleAxis: {
          type: 'category',
          data: years,
          axisLine: {
            lineStyle: {
              color: "#fff"
            }
          }
        },
        radiusAxis: {
          axisLine: {
            lineStyle: {
              color: "#fff"
            }
          }
        },
        polar: {},
        series: [
          {
            name: '销售增长率',
            type: 'bar',
            stack: 'total',
            data: salesGrowth,
            coordinateSystem: 'polar'
          },
          {
            name: '净利润增长率',
            type: 'bar',
            stack: 'total',
            data: netProfitGrowth,
            coordinateSystem: 'polar'
          },
          {
            name: '总资产增长率',
            type: 'bar',
            stack: 'total',
            data: totalAssetGrowth,
            coordinateSystem: 'polar'
          }
        ],
        legend: {
          show: true,
          orient: 'vertical',
          left: 'right',
          top:'center',
          data: ['销售增长率', '净利润增长率', '总资产增长率'],
          textStyle: {
            color: '#fff'
          }
        }
      };

      if (!chartInstance.value) {
        chartInstance.value = echarts.init(document.querySelector('.chartMid'));
      }

      chartInstance.value.setOption(options);
    };

    const changeCompany = (company) => {
      currentCompany.value = company;
      getData();
    };

    onMounted(() => {
      getData();
    });

    return {
      companies,
      changeCompany
    };
  }
};
</script>

<style>
.containerMid {
  display: flex;
  flex-direction: row;
}
.sidebar {
  margin-top:.5rem;
  flex: 0.5;
  display: flex;
  flex-direction: column;
  padding: .125rem;
}
.chartContainer {
  margin-top:.25rem;
  flex: 3;
}
.chartMid {
  height: 4rem; 
  width: 100%;
}
button {
  margin-top:.125rem;
  margin-bottom: .125rem; 
}
</style>
