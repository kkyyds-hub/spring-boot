<template>
  <section class="page">
    <div class="page-head">
      <h2>文件上传</h2>
    </div>

    <el-upload drag :http-request="handleUpload" :show-file-list="false">
      <el-icon class="upload-icon"><UploadFilled /></el-icon>
      <div>点击或拖拽文件到这里上传</div>
    </el-upload>

    <el-table v-if="records.length" :data="records" border class="upload-table">
      <el-table-column prop="originalName" label="文件名" min-width="180" />
      <el-table-column prop="uploadUser" label="上传人" width="120" />
      <el-table-column prop="createTime" label="上传时间" width="180" />
      <el-table-column prop="fileUrl" label="访问地址" min-width="260" show-overflow-tooltip />
      <el-table-column label="操作" width="90">
        <template #default="{ row }">
          <el-button link type="primary" @click="openFile(row.fileUrl)">打开</el-button>
        </template>
      </el-table-column>
    </el-table>
  </section>
</template>

<script setup>
import { ref } from "vue";
import { UploadFilled } from "@element-plus/icons-vue";
import { ElMessage } from "element-plus";
import { uploadFile } from "../api";

const records = ref([]);

async function handleUpload(option) {
  const data = new FormData();
  data.append("file", option.file);
  const result = await uploadFile(data);
  records.value.unshift(result);
  ElMessage.success("上传成功");
}

function openFile(url) {
  window.open(url, "_blank");
}
</script>
