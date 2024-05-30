<template>
  <div>
    <h2>资产负债率变化情况</h2>
    <div class="chartOne" id="debtToAssetRatioChart"></div>
  </div>
</template>

<script>
import { inject, onMounted, reactive } from "vue";

export default {
  setup() {
    let $echarts = inject("echarts");
    let $http = inject("axios");

    let data = reactive({ DARData: {} });
    let chartData = reactive({
      years: [],
      series: []
    });

    function setData() {
      const companies = ["蔚来", "理想", "比亚迪", "零跑", "小鹏汽车"];
      const years = ["2018", "2019", "2020", "2021", "2022", "2023"];
      chartData.years = years;

      chartData.series = companies.map(company => {
        return {
          name: company,
          type: 'line',
          data: years.map(year => {
            const yearData = data.DARData[year].find(item => item.company === company);
            return yearData ? parseFloat(yearData.debtToAssetRatio) || null : null;
          })
        };
      });
    }

    async function getData() {
      let response = await $http({ url: "/DARRouter/data" });
      data.DARData = response.data.DARData;
      setData();
    }

    onMounted(() => {
      let chart = $echarts.init(document.getElementById("debtToAssetRatioChart"));
      getData().then(() => {
        chart.setOption({
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross'
            }
          },
          legend: {
            data: ["蔚来", "理想", "比亚迪", "零跑", "小鹏汽车"],
            textStyle: {
              color: '#fff'
            }
          },
          xAxis: {
            type: 'category',
            boundaryGap: false,
            data: chartData.years,
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
          series: chartData.series
        });
      });
    });

    return {
      data,
      chartData,
      getData
    };
  }
};
</script>

<style>
.chartOne {
  height: 4rem;
  width: 100%;
}
</style>
