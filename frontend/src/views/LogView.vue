<template>
  <section class="page">
    <div class="page-head">
      <h2>操作日志</h2>
      <el-button :icon="Refresh" @click="load">刷新</el-button>
    </div>
    <el-table :data="records" border>
      <el-table-column prop="operator" label="操作人" width="120" />
      <el-table-column prop="operationType" label="操作类型" width="130" />
      <el-table-column prop="operationContent" label="操作内容" min-width="260" />
      <el-table-column prop="operationTime" label="操作时间" width="190" />
    </el-table>
    <el-pagination
      class="pager"
      background
      layout="total, sizes, prev, pager, next"
      v-model:current-page="query.page"
      v-model:page-size="query.pageSize"
      :total="total"
      @current-change="load"
      @size-change="load"
    />
  </section>
</template>

<script setup>
import { onMounted, reactive, ref } from "vue";
import { Refresh } from "@element-plus/icons-vue";
import { listLogs } from "../api";

const records = ref([]);
const total = ref(0);
const query = reactive({ page: 1, pageSize: 10 });

async function load() {
  const data = await listLogs(query);
  records.value = data.records;
  total.value = data.total;
}

onMounted(load);
</script>
