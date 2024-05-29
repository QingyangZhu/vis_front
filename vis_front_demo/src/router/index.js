import Vue from 'vue'
import Router from 'vue-router'


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/page',
      name: 'About',
      component: () => import('../views/homePage.vue')
      },
      // 设置路由重定向
      {
      path:"/",
      redirect:"/page"
      }
  ]
})
