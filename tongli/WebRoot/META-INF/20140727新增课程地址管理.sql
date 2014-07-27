drop table if exists tc_addres;

create table tc_addres
(
   id                   national varchar(64) not null comment 'id',
   pic_url              national varchar(250) comment '标题图',
   detail_info          national varchar(250) comment '课程详情',
   title                national varchar(150) comment '名称',
   addres               national varchar(255) comment '地址',
   note                 national varchar(250) comment '备注',
   date_created         datetime default '2000-00-00 00:00:00' comment '数据输入日期',
   create_id            national varchar(50) comment '建立者ID',
   create_ip            national varchar(50) comment '建立者IP',
   last_updated         datetime default '2000-00-00 00:00:00' comment '资料更新日期',
   update_id            national varchar(50) comment '修改者ID',
   update_ip            national varchar(50) comment '修改者IP',
   del_flag             national char(1) default '0' comment '0否1是',
   version              int(11) default 0 comment 'VERSION',
   primary key (id)
);

alter table tc_addres comment '课程地址';

drop table if exists tc_course_vs_addres;

create table tc_course_vs_addres
(
   course_id            national varchar(64) not null comment '课程编号',
   addres_id            varchar(64) not null comment '地址编号',
   primary key (course_id, addres_id)
);

alter table tc_course_vs_addres comment '课程与上课地点关联关系';
