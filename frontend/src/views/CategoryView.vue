<template>
  <section class="page">
    <div class="page-head">
      <h2>分类管理</h2>
      <el-button type="primary" :icon="Plus" @click="openAdd">新增分类</el-button>
    </div>

    <el-table :data="records" border>
      <el-table-column prop="name" label="分类名称" min-width="180" />
      <el-table-column prop="sort" label="排序" width="120" />
      <el-table-column prop="createTime" label="创建时间" width="190" />
      <el-table-column label="操作" width="160">
        <template #default="{ row }">
          <el-button link type="primary" @click="openEdit(row)">编辑</el-button>
          <el-button link type="danger" @click="remove(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑分类' : '新增分类'" width="460px">
      <el-form :model="form" label-width="90px">
        <el-form-item label="分类名称">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="form.sort" :min="0" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save">保存</el-button>
      </template>
    </el-dialog>
  </section>
</template>

<script setup>
import { onMounted, reactive, ref } from "vue";
import { Plus } from "@element-plus/icons-vue";
import { ElMessage, ElMessageBox } from "element-plus";
import { addCategory, deleteCategory, listCategories, updateCategory } from "../api";

const records = ref([]);
const dialogVisible = ref(false);
const form = reactive({ id: null, name: "", sort: 0 });

async function load() {
  records.value = await listCategories();
}

function openAdd() {
  Object.assign(form, { id: null, name: "", sort: 0 });
  dialogVisible.value = true;
}

function openEdit(row) {
  Object.assign(form, { id: row.id, name: row.name, sort: row.sort });
  dialogVisible.value = true;
}

async function save() {
  const data = { name: form.name, sort: form.sort };
  if (form.id) {
    await updateCategory(form.id, data);
  } else {
    await addCategory(data);
  }
  ElMessage.success("保存成功");
  dialogVisible.value = false;
  load();
}

async function remove(row) {
  await ElMessageBox.confirm(`确认删除分类 ${row.name}？`, "删除确认", { type: "warning" });
  await deleteCategory(row.id);
  ElMessage.success("删除成功");
  load();
}

onMounted(load);
</script>
