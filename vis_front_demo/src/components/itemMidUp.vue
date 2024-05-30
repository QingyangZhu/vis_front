<template>
    <div>
        <h2>市盈率、市净率、市销率、 股息率变化情况</h2>
        <div class="containerMidUP">
    <div class="sidebarMidUP">
      <button
        v-for="company in companies"
        :key="company"
        @click="changeCompany(company)"
      >
        {{ company }}
      </button>
    </div>
    <div class="chartContainerMidUP">
      <div class="chartMidUP" ref="chartMidUP"></div>
    </div>
  </div>
    </div>
  
</template>
  
  <script>
import { ref, onMounted, inject } from "vue";

export default {
  setup() {
    const echarts = inject("echarts");
    const axios = inject("axios");
    const chartInstance = ref(null);
    const companies = ["蔚来", "理想", "比亚迪", "零跑", "小鹏汽车"];
    const currentCompany = ref("蔚来");

    const getData = async () => {
      const response = await axios.get("/mid/data");
      const formattedData = formatData(response.data, currentCompany.value);
      updateChart(formattedData);
    };

    const formatData = (data, company) => {
      const years = Object.keys(data.PERData);
      const priceEarnings = years.map((year) => {
        const item = data.PERData[year].find(
          (item) => item.company === company
        );
        return item ? item.priceEarningsRatio : null;
      });
      const priceBook = years.map((year) => {
        const item = data.PBRData[year].find(
          (item) => item.company === company
        );
        return item ? item.priceBookRatio : null;
      });
      const priceSales = years.map((year) => {
        const item = data.PSRData[year].find(
          (item) => item.company === company
        );
        return item ? item.priceSalesRatio : null;
      });
      const dividendYield = years.map((year) => {
        const item = data.DYData[year].find((item) => item.company === company);
        return item ? item.dividendYield : null;
      });
      return {
        years,
        priceEarnings,
        priceBook,
        priceSales,
        dividendYield,
      };
    };

    const updateChart = ({
      years,
      priceEarnings,
      priceBook,
      priceSales,
      dividendYield,
    }) => {
      const options = {
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "cross",
          },
          
        },
        legend: {
          data: ["市盈率", "市净率", "市销率", "股息率"],
        },
        xAxis: {
          type: "category",
          boundaryGap: false,
          data: years,
          axisLine: {
            lineStyle: {
              color: "#fff"
            }
          }
        },
        yAxis: {
          type: "value",
          axisLine: {
            lineStyle: {
              color: "#fff"
            }
          }
        },
        series: [
          {
            name: "市盈率",
            type: "line",
            stack: "total",
            data: priceEarnings,
          },
          {
            name: "市净率",
            type: "line",
            stack: "total",
            data: priceBook,
          },
          {
            name: "市销率",
            type: "line",
            stack: "total",
            data: priceSales,
          },
          {
            name: "股息率",
            type: "line",
            stack: "total",
            data: dividendYield,
          },
        ],
      };

      if (!chartInstance.value) {
        chartInstance.value = echarts.init(
          document.querySelector(".chartMidUP")
        );
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
      changeCompany,
    };
  },
};
</script>
  
  <style>
.containerMidUP {
  display: flex;
  flex-direction: row;
}
.sidebarMidUP {
    margin-top:.5rem;
  flex: 0.5;
  display: flex;
  flex-direction: column;
  padding: .125rem;
}
.chartContainerMidUP {
  flex: 3;
}
.chartMidUP {
  height: 5rem;
  width: 100%;
}
button {
  margin-bottom: .125rem;
}
</style>
  