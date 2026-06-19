<template>
  <el-container class="app-shell">
    <el-aside width="220px" class="side">
      <div class="side-title">学习系统</div>
      <el-menu :default-active="$route.path" router class="side-menu">
        <el-menu-item index="/materials">
          <el-icon><Collection /></el-icon>
          <span>学习资料</span>
        </el-menu-item>
        <el-menu-item index="/categories">
          <el-icon><Collection /></el-icon>
          <span>分类管理</span>
        </el-menu-item>
        <el-menu-item index="/files">
          <el-icon><UploadFilled /></el-icon>
          <span>公共资料库</span>
        </el-menu-item>
        <el-menu-item index="/stats">
          <el-icon><TrendCharts /></el-icon>
          <span>数据统计</span>
        </el-menu-item>
        <el-menu-item index="/logs">
          <el-icon><Document /></el-icon>
          <span>操作日志</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="topbar">
        <span>学习资料管理系统</span>
        <div class="user-box">
          <span>{{ user.name }}</span>
          <el-button text @click="logout">退出</el-button>
        </div>
      </el-header>
      <el-main class="main">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed } from "vue";
import { useRouter } from "vue-router";
import { Collection, Document, TrendCharts, UploadFilled } from "@element-plus/icons-vue";

const router = useRouter();
const user = computed(() => JSON.parse(localStorage.getItem("user") || "{}"));

function logout() {
  localStorage.clear();
  router.push("/login");
}
</script>
