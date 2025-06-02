import { createRouter, createWebHistory } from 'vue-router'
import CorsTest from "@/views/CorsTest.vue";
import VisView from "@/views/VisView.vue";
import AdminLayout from "@/views/admin/AdminLayout.vue";
import DashboardView from "@/components/admin/DashboardView.vue";
import UserManagementView from "@/components/admin/UserManagementView.vue";
import CompanyManagementView from "@/components/admin/CompanyManagementView.vue";
import IndicatorManagementView from "@/components/admin/IndicatorManagementView.vue";
import FinancialDataManagementView from "@/components/admin/FinancialDataManagementView.vue";
import CategoryManagementView from "@/components/admin/CategoryManagementView.vue";

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
    // 后台管理模块路由
    {
      path: '/admin',
      name: 'Admin',
      component: AdminLayout, // 后台管理的整体布局
      redirect: '/admin/dashboard', // 默认重定向到后台首页
      children: [
        {
          path: 'dashboard',
          name: 'AdminDashboard',
          component: DashboardView,
          meta: { title: '仪表盘' }
        },
        {
          path: 'users',
          name: 'AdminUserManagement',
          component: UserManagementView,
          meta: { title: '用户管理' }
        },
        {
          path: 'companies',
          name: 'AdminCompanyManagement',
          component: CompanyManagementView,
          meta: { title: '公司管理' }
        },
        {
          path: 'indicators',
          name: 'AdminIndicatorManagement',
          component: IndicatorManagementView,
          meta: { title: '指标管理' }
        },
        {
          path: 'financial-data',
          name: 'AdminFinancialDataManagement',
          component: FinancialDataManagementView,
          meta: { title: '财务数据管理' }
        },
        {
          path: 'categories',
          name: 'AdminCategoryManagement',
          component: CategoryManagementView,
          meta: { title: '类别管理' }
        }
      ]
    }
  ],
})

// 全局前置守卫示例 (可选，用于标题更新或权限校验)
router.beforeEach((to, from, next) => {
  // 更新页面标题
  if (to.meta && to.meta.title) {
    document.title = `${to.meta.title} - 后台管理`;
  } else if (to.name === 'VisView') {
    document.title = '财务可视化大屏';
  } else {
    document.title = '财务可视化系统';
  }

  // 简单的权限校验示例 (实际项目会更复杂)
  // const isAuthenticated = !!localStorage.getItem('admin-token'); // 假设 token 存在 localStorage
  // if (to.matched.some(record => record.meta.requiresAuth) && !isAuthenticated) {
  //   next({ name: 'AdminLogin' }); // 假设有一个 AdminLogin 路由
  // } else {
  //   next();
  // }
  next(); // 暂时允许所有导航
});


// 处理 App.vue 全局样式切换的逻辑
router.afterEach((to) => {
  if (to.path.startsWith('/admin')) {
    document.documentElement.classList.add('admin-active');
    document.body.classList.add('admin-active');
    document.documentElement.classList.remove('vis-active');
    document.body.classList.remove('vis-active');
  } else {
    document.documentElement.classList.remove('admin-active');
    document.body.classList.remove('admin-active');
    document.documentElement.classList.add('vis-active'); // 假设 VisView 有自己的激活类
    document.body.classList.add('vis-active');
  }
});

export default router
