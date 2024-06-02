// 引入 Vue 框架
import Vue from 'vue'
import App from './App'
import router from './router'
// 引入 flexible 用于移动端适配
import "lib-flexible/flexible.js"

// 引入 ant-design-vue 组件库和样式
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';

// 全局使用 ant-design-vue
Vue.use(Antd);

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
