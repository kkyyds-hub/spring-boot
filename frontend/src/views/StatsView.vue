<template>
  <section class="page">
    <div class="page-head">
      <h2>数据统计</h2>
      <el-button :icon="Refresh" @click="load">刷新</el-button>
    </div>
    <div class="charts">
      <div ref="categoryEl" class="chart"></div>
      <div ref="monthEl" class="chart"></div>
      <div ref="statusEl" class="chart"></div>
    </div>
  </section>
</template>

<script setup>
import { onMounted, ref } from "vue";
import * as echarts from "echarts";
import { Refresh } from "@element-plus/icons-vue";
import { getStats } from "../api";

const categoryEl = ref(null);
const monthEl = ref(null);
const statusEl = ref(null);
let categoryChart;
let monthChart;
let statusChart;

async function load() {
  const data = await getStats();
  categoryChart ||= echarts.init(categoryEl.value);
  monthChart ||= echarts.init(monthEl.value);
  statusChart ||= echarts.init(statusEl.value);
  categoryChart.setOption({
    title: { text: "资料分类数量" },
    tooltip: {},
    xAxis: { type: "category", data: data.categoryStats.map(item => item.name) },
    yAxis: { type: "value" },
    series: [{ type: "bar", data: data.categoryStats.map(item => item.value), itemStyle: { color: "#2f7d6b" } }]
  });
  monthChart.setOption({
    title: { text: "每月新增资料" },
    tooltip: { trigger: "axis" },
    xAxis: { type: "category", data: data.monthStats.map(item => item.name) },
    yAxis: { type: "value" },
    series: [{ type: "line", smooth: true, data: data.monthStats.map(item => item.value), itemStyle: { color: "#b05a2a" } }]
  });
  statusChart.setOption({
    title: { text: "学习状态统计" },
    tooltip: { trigger: "item" },
    series: [{
      type: "pie",
      radius: "60%",
      data: data.statusStats,
      color: ["#8a6f3d", "#2f7d6b", "#b05a2a"]
    }]
  });
}

onMounted(load);
</script>
