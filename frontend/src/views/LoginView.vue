<template>
  <main class="login-page">
    <section class="login-panel">
      <div class="brand">
        <h1>学习资料管理系统</h1>
        <p>期末答辩精简版</p>
      </div>
      <el-tabs v-model="mode" stretch>
        <el-tab-pane label="登录" name="login">
          <el-form :model="loginForm" class="login-form" @keyup.enter="submitLogin">
            <el-form-item>
              <el-input v-model="loginForm.username" placeholder="用户名" size="large" clearable />
            </el-form-item>
            <el-form-item>
              <el-input v-model="loginForm.password" placeholder="密码" size="large" type="password" show-password />
            </el-form-item>
            <el-button type="primary" size="large" :loading="loading" @click="submitLogin">登录</el-button>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="注册" name="register">
          <el-form :model="registerForm" class="login-form" @keyup.enter="submitRegister">
            <el-form-item>
              <el-input v-model="registerForm.username" placeholder="用户名" size="large" clearable />
            </el-form-item>
            <el-form-item>
              <el-input v-model="registerForm.name" placeholder="姓名" size="large" clearable />
            </el-form-item>
            <el-form-item>
              <el-input v-model="registerForm.password" placeholder="密码" size="large" type="password" show-password />
            </el-form-item>
            <el-button type="primary" size="large" :loading="loading" @click="submitRegister">注册</el-button>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </section>
  </main>
</template>

<script setup>
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import { login, register } from "../api";

const router = useRouter();
const mode = ref("login");
const loading = ref(false);
const loginForm = reactive({ username: "admin", password: "123456" });
const registerForm = reactive({ username: "", name: "", password: "" });

async function submitLogin() {
  loading.value = true;
  try {
    const user = await login(loginForm);
    localStorage.setItem("token", user.token);
    localStorage.setItem("user", JSON.stringify(user));
    ElMessage.success("登录成功");
    router.push("/materials");
  } finally {
    loading.value = false;
  }
}

async function submitRegister() {
  loading.value = true;
  try {
    await register(registerForm);
    loginForm.username = registerForm.username;
    loginForm.password = registerForm.password;
    ElMessage.success("注册成功，请登录");
    mode.value = "login";
  } finally {
    loading.value = false;
  }
}
</script>
