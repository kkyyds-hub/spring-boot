drop database if exists study_system_simple;
create database study_system_simple default character set utf8mb4 collate utf8mb4_unicode_ci;
use study_system_simple;

create table user (
    id bigint primary key auto_increment,
    username varchar(50) not null unique,
    password varchar(64) not null,
    name varchar(50) not null,
    create_time datetime not null
) comment '用户表';

create table study_material (
    id bigint primary key auto_increment,
    user_id bigint not null,
    title varchar(100) not null,
    category varchar(50) not null,
    status varchar(20) not null default '学习中',
    important tinyint not null default 0,
    content text,
    create_time datetime not null,
    update_time datetime not null
) comment '学习资料主表';

create table material_category (
    id bigint primary key auto_increment,
    user_id bigint not null,
    name varchar(50) not null unique,
    sort int not null default 0,
    create_time datetime not null
) comment '资料分类表';

create table material_note (
    id bigint primary key auto_increment,
    material_id bigint not null,
    create_user varchar(50) not null,
    note_title varchar(100) not null,
    note_content text,
    create_time datetime not null,
    update_time datetime not null,
    index idx_material_id(material_id)
) comment '资料笔记表';

create table upload_file (
    id bigint primary key auto_increment,
    user_id bigint not null,
    material_id bigint,
    original_name varchar(200) not null,
    file_name varchar(200) not null,
    file_url varchar(500) not null,
    file_size bigint not null,
    upload_user varchar(50) not null,
    create_time datetime not null
) comment '上传文件记录表';

create table operation_log (
    id bigint primary key auto_increment,
    user_id bigint not null,
    operator varchar(50) not null,
    operation_type varchar(50) not null,
    operation_content varchar(500) not null,
    operation_time datetime not null
) comment '操作日志表';

insert into user(username, password, name, create_time)
values ('admin', 'e10adc3949ba59abbe56e057f20f883e', '管理员', now());

insert into material_category(user_id, name, sort, create_time) values
(1, 'Java', 1, now()),
(1, '后端', 2, now()),
(1, '前端', 3, now()),
(1, '数据库', 4, now());

insert into study_material(user_id, title, category, status, important, content, create_time, update_time) values
(1, 'Java 基础复习', 'Java', '学习中', 1, '面向对象、集合、异常、IO 是期末答辩常见基础点。', date_sub(now(), interval 2 month), date_sub(now(), interval 2 month)),
(1, 'Spring Boot 接口开发', '后端', '已完成', 1, 'Controller 接收请求，Service 处理业务，Mapper 访问数据库。', date_sub(now(), interval 1 month), date_sub(now(), interval 1 month)),
(1, 'Vue 页面整理', '前端', '未开始', 0, '使用 Vue、Element Plus 和 ECharts 完成基础页面。', now(), now());

insert into material_note(material_id, create_user, note_title, note_content, create_time, update_time) values
(1, '管理员', '集合重点', 'List 有序可重复，Set 不重复，Map 保存键值对。', date_sub(now(), interval 2 month), date_sub(now(), interval 2 month)),
(1, '管理员', '异常重点', '运行时异常和编译时异常要能区分。', date_sub(now(), interval 2 month), date_sub(now(), interval 2 month)),
(2, '管理员', '三层架构', 'Controller、Service、Mapper 职责分开，答辩时沿接口链路讲。', date_sub(now(), interval 1 month), date_sub(now(), interval 1 month)),
(3, '管理员', '图表展示', '统计接口返回数据，前端 ECharts 负责展示。', now(), now());
