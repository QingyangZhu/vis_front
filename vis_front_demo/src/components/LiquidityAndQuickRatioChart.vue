<template>
    <div id="CQR">
        <div id="CAQRC"></div>
    </div>
</template>

<script>
import * as echarts from "echarts";
import { data2018, data2019, data2020, data2021, data2022, data2023 } from "../data/data";
export default {
    name: "CurrentAndQuickRatioChart",
    setup() {
        // 所有年份的数据集合
    const allData = [data2018, data2019, data2020, data2021, data2022, data2023];
    // 初始化公司数据对象
    const companiesData = {};

    // 遍历所有年份的数据
    allData.forEach(yearData => {
      yearData.forEach(companyData => {
        const companyName = Object.keys(companyData)[0];
        const liquidityRatio = companyData[companyName]['流动比率'];
        // 如果公司数据对象中还没有这家公司的键，就初始化一个数组
        if (!companiesData[companyName]) {
          companiesData[companyName] = [];
        }
        // 将流动比率添加到相应公司的数组中
        companiesData[companyName].push(liquidityRatio);
      });
    });
    // 返回数据供模板使用
    return { companiesData };
    },
    data() {
        const companies = data2018.map(item => Object.keys(item)[0]);
        const liquidityRatios2018 = data2018.map(item => {
            const companyData = Object.values(item)[0];
            return companyData.流动比率;
        });
        const liquidityRatios2019 = data2019.map(item => {
            const companyData = Object.values(item)[0];
            return companyData.流动比率;
        });
        const liquidityRatios2020 = data2020.map(item => {
            const companyData = Object.values(item)[0];
            return companyData.流动比率;
        });
        const liquidityRatios2021 = data2021.map(item => {
            const companyData = Object.values(item)[0];
            return companyData.流动比率;
        });
        const liquidityRatios2022 = data2022.map(item => {
            const companyData = Object.values(item)[0];
            return companyData.流动比率;
        });
        const liquidityRatios2023 = data2023.map(item => {
            const companyData = Object.values(item)[0];
            return companyData.流动比率;
        });
        return {
            companies,
            liquidityRatios2018,
            liquidityRatios2019,
            liquidityRatios2020,
            liquidityRatios2021,
            liquidityRatios2022,
            liquidityRatios2023
        };
    },
    mounted() {
        this.drawChart();
    },
    methods: {
        drawChart() {
            var myChart = echarts.init(document.getElementById("CAQRC"));
            myChart.setOption({
                title: {
                    text: '2018-2023各公司流动比率'
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data: this.companies
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                toolbox: {
                    feature: {
                        saveAsImage: {}
                    }
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: ['2018', '2019', '2020', '2021', '2022', '2023']
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name: this.companies[0],
                        type: 'line',
                        stack: 'Total',
                        data: [this.liquidityRatios2018[0], this.liquidityRatios2019[0], this.liquidityRatios2020[0],
                        this.liquidityRatios2021[0], this.liquidityRatios2022[0], this.liquidityRatios2023[0]]
                    },
                    {
                        name: this.companies[1],
                        type: 'line',
                        stack: 'Total',
                        data: [this.liquidityRatios2018[1], this.liquidityRatios2019[1], this.liquidityRatios2020[1],
                        this.liquidityRatios2021[1], this.liquidityRatios2022[1], this.liquidityRatios2023[1]]
                    },
                    {
                        name: this.companies[2],
                        type: 'line',
                        stack: 'Total',
                        data: [this.liquidityRatios2018[2], this.liquidityRatios2019[2], this.liquidityRatios2020[2],
                        this.liquidityRatios2021[2], this.liquidityRatios2022[2], this.liquidityRatios2023[2]]
                    },
                    {
                        name: this.companies[3],
                        type: 'line',
                        stack: 'Total',
                        data: [this.liquidityRatios2018[3], this.liquidityRatios2019[3], this.liquidityRatios2020[3],
                        this.liquidityRatios2021[3], this.liquidityRatios2022[3], this.liquidityRatios2023[3]]
                    },
                    {
                        name: this.companies[4],
                        type: 'line',
                        stack: 'Total',
                        data: [this.liquidityRatios2018[4], this.liquidityRatios2019[4], this.liquidityRatios2020[4],
                        this.liquidityRatios2021[4], this.liquidityRatios2022[4], this.liquidityRatios2023[4]]
                    }
                ]
            });
        },
    },
};
</script>
<style>
#CAQRC {
    width: 1200px;
    height: 400px;
    border: 1px solid #000;
}
</style>