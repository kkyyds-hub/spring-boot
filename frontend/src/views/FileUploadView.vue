<template>
  <section class="page library-page">
    <div class="page-head">
      <h2>公共资料库</h2>
      <el-button v-if="records.length" class="round-upload" type="primary" circle :icon="UploadFilled" @click="dialogVisible = true" />
    </div>

    <el-upload v-if="!records.length" drag :http-request="handleUpload" :show-file-list="false" class="library-drop">
      <el-icon class="upload-icon"><UploadFilled /></el-icon>
      <div>点击或拖拽上传公共学习资料</div>
    </el-upload>

    <div v-else class="library-grid">
      <div v-for="item in records" :key="item.id" class="library-item">
        <div class="file-icon">{{ suffix(item.originalName) }}</div>
        <div class="file-info">
          <h3>{{ item.originalName }}</h3>
          <p>上传人：{{ item.uploadUser }}</p>
          <p>大小：{{ formatSize(item.fileSize) }}</p>
          <p>{{ item.createTime }}</p>
        </div>
        <el-button type="primary" plain @click="openFile(item.fileUrl)">打开</el-button>
      </div>
    </div>

    <el-dialog v-model="dialogVisible" title="上传公共学习资料" width="560px">
      <el-upload drag :http-request="handleUpload" :show-file-list="false">
        <el-icon class="upload-icon"><UploadFilled /></el-icon>
        <div>点击或拖拽文件到这里上传</div>
      </el-upload>
    </el-dialog>
  </section>
</template>

<script setup>
import { onMounted, ref } from "vue";
import { UploadFilled } from "@element-plus/icons-vue";
import { ElMessage } from "element-plus";
import { listFiles, uploadFile } from "../api";

const records = ref([]);
const dialogVisible = ref(false);

async function load() {
  records.value = await listFiles();
}

async function handleUpload(option) {
  const data = new FormData();
  data.append("file", option.file);
  const result = await uploadFile(data);
  records.value.unshift(result);
  dialogVisible.value = false;
  ElMessage.success("上传成功");
}

function suffix(name) {
  const text = name.includes(".") ? name.substring(name.lastIndexOf(".") + 1) : "FILE";
  return text.slice(0, 4).toUpperCase();
}

function formatSize(size) {
  if (size < 1024) return `${size} B`;
  if (size < 1024 * 1024) return `${(size / 1024).toFixed(1)} KB`;
  return `${(size / 1024 / 1024).toFixed(1)} MB`;
}

function openFile(url) {
  window.open(url, "_blank");
}

onMounted(load);
</script>
