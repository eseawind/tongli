drop table if exists tc_classes;

create table tc_classes
(
   id                   national varchar(50) not null comment 'ID',
   name                 national varchar(50) not null comment '班级名称',
   version              int(11) default 0 comment 'VERSION',
   sort_num             bigint(20) comment '序号',
   keyword              national varchar(250) comment '关键字',
   brief_info           national varchar(200) comment '摘要',
   detail_info          national varchar(250) comment '内容',
   note                 national varchar(250) comment '备注',
   date_created         datetime default '2000-00-00 00:00:00' comment '数据输入日期',
   create_id            national varchar(50) comment '建立者ID',
   create_ip            national varchar(50) comment '建立者IP',
   last_updated         datetime default '2000-00-00 00:00:00' comment '资料更新日期',
   update_id            national varchar(50) comment '修改者ID',
   update_ip            national varchar(50) comment '修改者IP',
   del_flag             national char(1) default '0' comment '0否1是',
   pic_url              national varchar(250) comment 'pic_url',
   primary key (name, id)
);

alter table tc_classes comment '班级信息表';
drop table if exists tc_classes_vs_student;

create table tc_classes_vs_student
(
   student_id           national varchar(50) not null comment '学员id',
   classes_id           national varchar(50) not null comment '班级id',
   date_created         datetime default '2000-00-00 00:00:00' comment '数据输入日期',
   create_id            national varchar(50) comment '建立者ID',
   create_ip            national varchar(50) comment '建立者IP',
   primary key (classes_id, student_id)
);

alter table tc_classes_vs_student comment '会员_学员关联表';
