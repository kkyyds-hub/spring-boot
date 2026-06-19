import request from "./request";

export const login = data => request.post("/login", data);
export const register = data => request.post("/register", data);
export const listMaterials = params => request.get("/materials", { params });
export const getMaterial = id => request.get(`/materials/${id}`);
export const addMaterial = data => request.post("/materials", data);
export const updateMaterial = (id, data) => request.put(`/materials/${id}`, data);
export const deleteMaterial = id => request.delete(`/materials/${id}`);
export const deleteMaterials = ids => request.delete("/materials/batch", { data: ids });
export const uploadFile = formData => request.post("/files/upload", formData);
export const deleteFile = id => request.delete(`/files/${id}`);
export const getStats = () => request.get("/stats");
export const listLogs = params => request.get("/logs", { params });
export const listCategories = () => request.get("/categories");
export const addCategory = data => request.post("/categories", data);
export const updateCategory = (id, data) => request.put(`/categories/${id}`, data);
export const deleteCategory = id => request.delete(`/categories/${id}`);
