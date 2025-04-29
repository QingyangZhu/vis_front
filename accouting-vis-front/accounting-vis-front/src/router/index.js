import { createRouter, createWebHistory } from 'vue-router'
import CorsTest from "@/views/CorsTest.vue";
import VisView from "@/views/VisView.vue";

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
    }
  ],
})

export default router
