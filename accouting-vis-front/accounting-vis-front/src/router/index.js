import { createRouter, createWebHistory } from 'vue-router'
import CorsTest from "@/views/CorsTest.vue";
import VisView from "@/views/VisView.vue";
import AdminLayout from "@/views/admin/AdminLayout.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'VisView',
      component: VisView,
    },
    {
      path: '/cors-test',
      component: CorsTest
    },
    ,
    // 后台管理模块路由
    {
      path: '/admin',
      name: 'Admin',
      component: AdminLayout // 后台管理的整体布局
    }
  ],
})

export default router
