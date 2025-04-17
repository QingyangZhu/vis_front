<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
import {provide} from 'vue'
import * as echarts from 'echarts'
import axios from 'axios'

//设置基准路径
axios.defaults.baseURL = "http://localhost:8888"

export default {
  setup() {
    provide("echarts",echarts)
    provide("axios",axios)
  },
}
</script>

<style>
body{
  background: url(~@/assets/background.png) top center no-repeat;
  background-size: cover;
}
</style>
