<template>
  <nav class="sidebar-nav">
    <div class="logo-container">
      ADMIN
    </div>
    <ul>
      <li>
        <router-link to="/admin/dashboard">仪表盘</router-link>
      </li>
      <li>
        <router-link to="/admin/users">用户管理</router-link>
      </li>
      <li>
        <router-link to="/admin/companies">公司管理</router-link>
      </li>
      <li>
        <router-link to="/admin/indicators">指标管理</router-link>
      </li>
      <li>
        <router-link to="/admin/financial-data">财务数据管理</router-link>
      </li>
      <li>
        <router-link to="/admin/categories">类别管理</router-link>
      </li>
      <li>
        <router-link to="/">返回可视化大屏</router-link>
      </li>
    </ul>
  </nav>
</template>

<script setup>
// No script needed for this simple example
</script>

<style scoped>
.sidebar-nav {
  padding-top: 20px;
}

.logo-container {
  padding: 10px 20px;
  font-size: 24px;
  font-weight: bold;
  text-align: center;
  margin-bottom: 20px;
  color: #fff; /* 或者您的品牌色 */
}

ul {
  list-style-type: none;
  padding: 0;
  margin: 0;
}

li a {
  display: block;
  padding: 12px 20px;
  text-decoration: none;
  color: rgba(255, 255, 255, 0.75);
  transition: background-color 0.3s, color 0.3s;
}

li a:hover,
li a.router-link-exact-active { /* 当前激活的链接 */
  background-color: #1890ff; /* Ant Design 主题色 */
  color: #fff;
}
</style>