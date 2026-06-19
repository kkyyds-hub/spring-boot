<template>
  <section class="page">
    <div class="page-head">
      <h2>学习资料管理</h2>
      <el-button type="primary" :icon="Plus" @click="openAdd">新增资料</el-button>
    </div>

    <el-form :inline="true" :model="query" class="search-bar">
      <el-form-item label="标题">
        <el-input v-model="query.title" placeholder="模糊查询" clearable />
      </el-form-item>
      <el-form-item label="分类">
        <el-select v-model="query.category" placeholder="全部分类" clearable>
          <el-option v-for="item in categories" :key="item.id" :label="item.name" :value="item.name" />
        </el-select>
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="query.status" placeholder="全部状态" clearable>
          <el-option v-for="item in statusOptions" :key="item" :label="item" :value="item" />
        </el-select>
      </el-form-item>
      <el-form-item label="重点">
        <el-select v-model="query.important" placeholder="全部" clearable>
          <el-option label="重点" :value="true" />
          <el-option label="普通" :value="false" />
        </el-select>
      </el-form-item>
      <el-form-item label="开始">
        <el-date-picker v-model="query.startDate" value-format="YYYY-MM-DD" type="date" placeholder="创建开始" />
      </el-form-item>
      <el-form-item label="结束">
        <el-date-picker v-model="query.endDate" value-format="YYYY-MM-DD" type="date" placeholder="创建结束" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" :icon="Search" @click="load">查询</el-button>
        <el-button @click="reset">重置</el-button>
      </el-form-item>
    </el-form>

    <div class="table-tools">
      <el-button type="danger" :disabled="!selected.length" :icon="Delete" @click="batchDelete">批量删除</el-button>
      <span>已选 {{ selected.length }} 条</span>
    </div>

    <el-table :data="records" border @selection-change="rows => selected = rows">
      <el-table-column type="selection" width="48" />
      <el-table-column prop="title" label="标题" min-width="180" />
      <el-table-column prop="category" label="分类" width="110" />
      <el-table-column prop="status" label="状态" width="100" />
      <el-table-column label="重点" width="90">
        <template #default="{ row }">
          <el-tag v-if="row.important" type="danger">重点</el-tag>
          <el-tag v-else type="info">普通</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="180" />
      <el-table-column label="操作" width="230" fixed="right">
        <template #default="{ row }">
          <el-button link type="primary" @click="showDetail(row.id)">详情</el-button>
          <el-button link type="primary" @click="openEdit(row.id)">编辑</el-button>
          <el-button link type="danger" @click="remove(row)">删除</el-button>
        </template>
      </el-table-column>
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

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑资料' : '新增资料'" width="760px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="标题">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="分类">
          <el-select v-model="form.category" placeholder="选择分类">
            <el-option v-for="item in categories" :key="item.id" :label="item.name" :value="item.name" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio-button v-for="item in statusOptions" :key="item" :label="item" />
          </el-radio-group>
        </el-form-item>
        <el-form-item label="重点">
          <el-switch v-model="form.important" active-text="重点" inactive-text="普通" />
        </el-form-item>
        <el-form-item label="内容">
          <el-input v-model="form.content" type="textarea" :rows="4" />
        </el-form-item>
        <el-form-item label="笔记">
          <div class="notes-editor">
            <div v-for="(note, index) in form.notes" :key="index" class="note-row">
              <el-input v-model="note.noteTitle" placeholder="笔记标题" />
              <el-input v-model="note.noteContent" placeholder="笔记内容" />
              <el-button :icon="Delete" circle @click="form.notes.splice(index, 1)" />
            </div>
            <el-button :icon="Plus" @click="form.notes.push({ noteTitle: '', noteContent: '' })">增加笔记</el-button>
          </div>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save">保存</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="detailVisible" title="资料详情" width="760px">
      <div v-if="detail" class="detail">
        <h3>{{ detail.title }}</h3>
        <p><b>分类：</b>{{ detail.category }} <b>状态：</b>{{ detail.status }} <b>重点：</b>{{ detail.important ? "是" : "否" }}</p>
        <p><b>内容：</b>{{ detail.content }}</p>
        <el-divider />
        <div class="detail-head">
          <h4>附件</h4>
          <el-upload :http-request="handleDetailUpload" :show-file-list="false">
            <el-button :icon="Upload">上传附件</el-button>
          </el-upload>
        </div>
        <el-table :data="detail.files || []" border size="small">
          <el-table-column prop="originalName" label="文件名" min-width="180" />
          <el-table-column prop="uploadUser" label="上传人" width="100" />
          <el-table-column prop="createTime" label="上传时间" width="180" />
          <el-table-column label="操作" width="130">
            <template #default="{ row }">
              <el-button link type="primary" @click="openFile(row.fileUrl)">打开</el-button>
              <el-button link type="danger" @click="removeFile(row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-divider />
        <h4>笔记</h4>
        <el-timeline>
          <el-timeline-item v-for="note in detail.notes" :key="note.id" :timestamp="note.updateTime">
            <b>{{ note.noteTitle }}</b>
            <span class="note-user">添加人：{{ note.createUser }}</span>
            <p>{{ note.noteContent }}</p>
          </el-timeline-item>
        </el-timeline>
      </div>
    </el-dialog>
  </section>
</template>

<script setup>
import { onMounted, reactive, ref } from "vue";
import { Delete, Plus, Search, Upload } from "@element-plus/icons-vue";
import { ElMessage, ElMessageBox } from "element-plus";
import {
  addMaterial,
  deleteFile,
  deleteMaterial,
  deleteMaterials,
  getMaterial,
  listCategories,
  listMaterials,
  updateMaterial,
  uploadFile
} from "../api";

const statusOptions = ["未开始", "学习中", "已完成"];
const records = ref([]);
const categories = ref([]);
const total = ref(0);
const selected = ref([]);
const dialogVisible = ref(false);
const detailVisible = ref(false);
const detail = ref(null);
const query = reactive({ page: 1, pageSize: 10, title: "", category: "", status: "", important: "", startDate: "", endDate: "" });
const form = reactive({ id: null, title: "", category: "", status: "学习中", important: false, content: "", notes: [] });

function fillForm(data = {}) {
  form.id = data.id || null;
  form.title = data.title || "";
  form.category = data.category || categories.value[0]?.name || "";
  form.status = data.status || "学习中";
  form.important = Boolean(data.important);
  form.content = data.content || "";
  form.notes = data.notes?.length ? data.notes.map(n => ({ noteTitle: n.noteTitle, noteContent: n.noteContent })) : [{ noteTitle: "", noteContent: "" }];
}

async function loadCategories() {
  categories.value = await listCategories();
}

async function load() {
  const params = { ...query, important: query.important === "" ? undefined : query.important };
  const data = await listMaterials(params);
  records.value = data.records;
  total.value = data.total;
}

function reset() {
  Object.assign(query, { page: 1, pageSize: 10, title: "", category: "", status: "", important: "", startDate: "", endDate: "" });
  load();
}

function openAdd() {
  fillForm();
  dialogVisible.value = true;
}

async function openEdit(id) {
  fillForm(await getMaterial(id));
  dialogVisible.value = true;
}

async function save() {
  const payload = {
    title: form.title,
    category: form.category,
    status: form.status,
    important: form.important,
    content: form.content,
    notes: form.notes.filter(n => n.noteTitle || n.noteContent)
  };
  if (form.id) {
    await updateMaterial(form.id, payload);
  } else {
    await addMaterial(payload);
  }
  ElMessage.success("保存成功");
  dialogVisible.value = false;
  load();
}

async function remove(row) {
  await ElMessageBox.confirm(`确认删除 ${row.title}？`, "删除确认", { type: "warning" });
  await deleteMaterial(row.id);
  ElMessage.success("删除成功");
  load();
}

async function batchDelete() {
  await ElMessageBox.confirm("确认批量删除所选资料？", "批量删除", { type: "warning" });
  await deleteMaterials(selected.value.map(item => item.id));
  ElMessage.success("批量删除成功");
  load();
}

async function showDetail(id) {
  detail.value = await getMaterial(id);
  detailVisible.value = true;
}

async function handleDetailUpload(option) {
  const data = new FormData();
  data.append("file", option.file);
  data.append("materialId", detail.value.id);
  await uploadFile(data);
  ElMessage.success("附件上传成功");
  detail.value = await getMaterial(detail.value.id);
}

function openFile(url) {
  window.open(url, "_blank");
}

async function removeFile(row) {
  await deleteFile(row.id);
  ElMessage.success("附件已删除");
  detail.value = await getMaterial(detail.value.id);
}

onMounted(async () => {
  await loadCategories();
  await load();
});
</script>
