# 学习资料管理系统（期末答辩精简版）

这是一个一天内能讲完链路的学习系统精简项目，保留清晰三层架构，不使用 MyBatis-Plus。

## 技术栈

- 后端：Spring Boot 2.7 + MyBatis + MySQL + JWT
- 前端：Vue 3 + Element Plus + ECharts
- 数据库：MySQL

## 模块对应关系

| 期末模块 | 实现位置 |
| --- | --- |
| 登录认证模块 | `AuthController`、`AuthService`、`AuthServiceImpl`、`UserMapper`，包含登录和注册 |
| 登录拦截模块 | `TokenInterceptor`、`WebConfig` |
| 信息管理模块 | `MaterialController`、`MaterialService`、`MaterialServiceImpl`、`MaterialMapper`、`NoteMapper` |
| 文件上传模块 | `FileController`、`FileService`、`FileServiceImpl`、`CloudFileServiceImpl`、前端公共资料库 `FileUploadView.vue` |
| 数据统计模块 | `StatsController`、`StatsService`、`StatsServiceImpl`、前端 `StatsView.vue` |
| 操作日志模块 | `LogController`、`LogService`、`LogServiceImpl`、`OperationLogMapper` |

## 扩展示例功能

这些功能用于增加演示时长，仍然围绕学习资料管理：

| 功能 | 实现位置 |
| --- | --- |
| 资料分类管理 | `CategoryController`、`CategoryService`、`CategoryServiceImpl`、`CategoryMapper`、前端 `CategoryView.vue` |
| 学习状态和重点标记 | `study_material.status`、`study_material.important`、前端资料表单和筛选区 |
| 资料附件关联 | `upload_file.material_id`、`FileServiceImpl`、资料详情页附件上传和列表 |

系统数据共享。不同账号可以看到同一批分类、资料、公共资料库文件、附件和日志；新增笔记会记录添加人，操作日志会记录操作人。

## 数据库初始化

在 MySQL 中执行：

```bash
mysql -uroot -p < database/init.sql
```

如果本机 root 没有密码，可以使用：

```bash
mysql -uroot < database/init.sql
```

数据库名：`study_system_simple`

初始账号：

- 用户名：`admin`
- 密码：`123456`

## 后端启动

```bash
cd /Users/kk/期末java/study-system-simple
./mvnw spring-boot:run
```

后端地址：

- `http://localhost:18080`
- 登录接口：`POST http://localhost:18080/login`

如需指定数据库账号密码：

```bash
DB_USERNAME=root DB_PASSWORD=你的密码 ./mvnw spring-boot:run
```

如需启用阿里云 OSS 上传：

```bash
ALIYUN_OSS_ENDPOINT=https://oss-cn-hangzhou.aliyuncs.com \
ALIYUN_OSS_BUCKET=awedkd \
ALIYUN_OSS_ACCESS_KEY_ID=你的AccessKeyID \
ALIYUN_OSS_ACCESS_KEY_SECRET=你的AccessKeySecret \
./mvnw spring-boot:run
```

OSS 配置为空时，文件会自动保存到本地 `uploads` 目录。

## 前端启动

```bash
cd /Users/kk/期末java/study-system-simple/frontend
npm install
npm run dev
```

前端地址：

- `http://localhost:15180`

## 启动顺序

1. 导入 `database/init.sql`
2. 启动后端 `./mvnw spring-boot:run`
3. 启动前端 `npm run dev`
4. 浏览器访问 `http://localhost:15180`

## 停止命令

在对应终端按 `Ctrl+C` 停止前端或后端。

如果后台运行，可按端口停止：

```bash
lsof -ti:18080 | xargs kill
lsof -ti:15180 | xargs kill
```

## 代码讲解主线

以新增资料为例：

`MaterialView.vue` 提交表单 -> `MaterialController.add` 接收请求 -> `MaterialService` 定义业务接口 -> `MaterialServiceImpl.add` 处理资料、笔记和日志 -> `MaterialMapper.xml` 写入 `study_material` -> `NoteMapper.xml` 写入 `material_note` -> `OperationLogMapper.xml` 写入 `operation_log`。

登录后的接口都会先经过 `TokenInterceptor`，校验请求头中的 `token` 或 `Authorization`。

公共资料库上传链路：

`FileUploadView.vue` 选择文件 -> `FileController.upload` 接收文件 -> `FileServiceImpl.upload` 保存上传记录和日志 -> `CloudFileServiceImpl.upload` 判断 OSS 配置，配置完整时上传阿里云 OSS，否则保存本地 `uploads`。

`upload_file.material_id` 为空表示公共资料库文件，不为空表示某条学习资料的附件。

## 推荐演示顺序

1. 登录系统。
2. 在登录页演示注册一个新用户，再切回登录。
3. 进入分类管理，新增或修改一个分类。
4. 进入学习资料，新增资料并选择分类、状态和重点标记。
5. 在资料详情里查看笔记。
6. 在资料详情里上传附件，展示 OSS 或本地上传结果。
7. 用状态、重点、分类筛选资料。
8. 进入数据统计，查看分类统计、每月新增统计、学习状态统计。
9. 进入操作日志，查看新增、修改、上传等记录。
