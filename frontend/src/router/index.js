import { createRouter, createWebHistory } from "vue-router";
import LoginView from "../views/LoginView.vue";
import MainLayout from "../views/MainLayout.vue";
import MaterialView from "../views/MaterialView.vue";
import CategoryView from "../views/CategoryView.vue";
import FileUploadView from "../views/FileUploadView.vue";
import StatsView from "../views/StatsView.vue";
import LogView from "../views/LogView.vue";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: "/login", component: LoginView },
    {
      path: "/",
      component: MainLayout,
      redirect: "/materials",
      children: [
        { path: "materials", component: MaterialView },
        { path: "categories", component: CategoryView },
        { path: "files", component: FileUploadView },
        { path: "stats", component: StatsView },
        { path: "logs", component: LogView }
      ]
    }
  ]
});

router.beforeEach((to, from, next) => {
  if (to.path !== "/login" && !localStorage.getItem("token")) {
    next("/login");
    return;
  }
  next();
});

export default router;
