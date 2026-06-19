import axios from "axios";
import { ElMessage } from "element-plus";

const request = axios.create({
  baseURL: "/api",
  timeout: 10000
});

request.interceptors.request.use(config => {
  const token = localStorage.getItem("token");
  if (token) {
    config.headers.token = token;
  }
  return config;
});

request.interceptors.response.use(
  response => {
    const result = response.data;
    if (result.code === 0) {
      ElMessage.error(result.message || "操作失败");
      return Promise.reject(result);
    }
    return result.data;
  },
  error => {
    if (error.response?.status === 401) {
      localStorage.clear();
      location.href = "/login";
    }
    ElMessage.error(error.response?.data?.message || "请求失败");
    return Promise.reject(error);
  }
);

export default request;
