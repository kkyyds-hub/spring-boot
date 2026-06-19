<template>
  <section class="page library-page">
    <div class="page-head">
      <h2>公共资料库</h2>
    </div>

    <el-upload v-if="!records.length" drag :http-request="handleUpload" :show-file-list="false" class="library-drop">
      <el-icon class="upload-icon"><UploadFilled /></el-icon>
      <div>点击或拖拽上传公共学习资料</div>
    </el-upload>

    <template v-else>
      <el-table :data="pageRecords" border>
        <el-table-column prop="originalName" label="文件名" min-width="240" show-overflow-tooltip />
        <el-table-column label="大小" width="110">
          <template #default="{ row }">{{ formatSize(row.fileSize) }}</template>
        </el-table-column>
        <el-table-column prop="uploadUser" label="上传人" width="120" />
        <el-table-column prop="createTime" label="上传时间" width="190" />
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="openFile(row.fileUrl)">打开</el-button>
            <el-button link type="danger" @click="remove(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        class="pager"
        background
        layout="total, sizes, prev, pager, next"
        v-model:current-page="page"
        v-model:page-size="pageSize"
        :total="records.length"
      />
    </template>

    <el-button class="floating-upload" type="primary" circle :icon="UploadFilled" @click="dialogVisible = true" />

    <el-dialog v-model="dialogVisible" title="上传公共学习资料" width="560px">
      <el-upload drag :http-request="handleUpload" :show-file-list="false">
        <el-icon class="upload-icon"><UploadFilled /></el-icon>
        <div>点击或拖拽文件到这里上传</div>
      </el-upload>
    </el-dialog>
  </section>
</template>

<script setup>
import { computed, onMounted, ref } from "vue";
import { UploadFilled } from "@element-plus/icons-vue";
import { ElMessage, ElMessageBox } from "element-plus";
import { deleteFile, listFiles, uploadFile } from "../api";

const records = ref([]);
const dialogVisible = ref(false);
const page = ref(1);
const pageSize = ref(8);

const pageRecords = computed(() => {
  const start = (page.value - 1) * pageSize.value;
  return records.value.slice(start, start + pageSize.value);
});

async function load() {
  records.value = await listFiles();
}

async function handleUpload(option) {
  const data = new FormData();
  data.append("file", option.file);
  await uploadFile(data);
  dialogVisible.value = false;
  ElMessage.success("上传成功");
  await load();
}

function formatSize(size) {
  if (size < 1024) return `${size} B`;
  if (size < 1024 * 1024) return `${(size / 1024).toFixed(1)} KB`;
  return `${(size / 1024 / 1024).toFixed(1)} MB`;
}

function openFile(url) {
  window.open(url, "_blank");
}

async function remove(row) {
  await ElMessageBox.confirm(`确认删除 ${row.originalName}？`, "删除确认", { type: "warning" });
  await deleteFile(row.id);
  ElMessage.success("删除成功");
  await load();
}

onMounted(load);
</script>
