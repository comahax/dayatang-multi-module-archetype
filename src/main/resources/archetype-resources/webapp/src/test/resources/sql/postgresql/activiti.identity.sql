
drop table if exists ACT_ID_INFO cascade;
drop table if exists ACT_ID_MEMBERSHIP cascade;
drop table if exists ACT_ID_GROUP cascade;
drop table if exists ACT_ID_USER cascade;
create table ACT_ID_GROUP (
    ID_ varchar(64),
    REV_ integer,
    NAME_ varchar(255),
    TYPE_ varchar(255),
    primary key (ID_)
);

create table ACT_ID_MEMBERSHIP (
    USER_ID_ varchar(64),
    GROUP_ID_ varchar(64),
    primary key (USER_ID_, GROUP_ID_)
);

create table ACT_ID_USER (
    ID_ varchar(64),
    REV_ integer,
    FIRST_ varchar(255),
    LAST_ varchar(255),
    EMAIL_ varchar(255),
    PWD_ varchar(255),
    PICTURE_ID_ varchar(64),
    primary key (ID_)
);

create table ACT_ID_INFO (
    ID_ varchar(64),
    REV_ integer,
    USER_ID_ varchar(64),
    TYPE_ varchar(64),
    KEY_ varchar(255),
    VALUE_ varchar(255),
    PASSWORD_ bytea,
    PARENT_ID_ varchar(255),
    primary key (ID_)
);

create index ACT_IDX_MEMB_GROUP on ACT_ID_MEMBERSHIP(GROUP_ID_);
alter table ACT_ID_MEMBERSHIP 
    add constraint ACT_FK_MEMB_GROUP
    foreign key (GROUP_ID_) 
    references ACT_ID_GROUP (ID_);

create index ACT_IDX_MEMB_USER on ACT_ID_MEMBERSHIP(USER_ID_);
alter table ACT_ID_MEMBERSHIP 
    add constraint ACT_FK_MEMB_USER
    foreign key (USER_ID_) 
    references ACT_ID_USER (ID_);


INSERT INTO ACT_ID_USER VALUES ('123456',1,'宇','杨',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('ah-admin',1,'徽管理员','安',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('cq-admin',1,'庆管理员','重',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('cs-admin',1,'沙管理员','长',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('gaowenwei',1,'文威','高',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('gonghui',1,'辉','龚',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('guiz-admin',1,'州管理员','贵',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('gx-admin',1,'西管理员','广',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('gz-admin',1,'州管理员','广',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('hn-admin',1,'南管理员','河',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('huangbinyu',1,'斌宇','黄',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('huanghuansheng',1,'焕生','黄',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('huangzhihua',1,'志华','黄',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('liangwanping',1,'婉萍','梁',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('liaoweili',1,'伟利','廖',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('lihuantang',1,'焕棠','李',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('liliangwei',1,'良卫','李',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('lipeishan',1,'佩珊','李',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('liqingming',1,'庆明','李',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('liruirong',1,'瑞荣','李',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('liuxiangrong',1,'向荣','刘',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('liyu',1,'裕','李',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('liyuanguang',1,'远光','李',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('maolongqiang',1,'龙强','毛',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('niuxiaofeng',1,'晓峰','牛','','7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('sunsea-admin',1,'公司管理员','总',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('sunxiaotao',1,'小涛','孙',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('tianfucai',1,'福才','田',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('wh-admin',1,'汉管理员','武',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('wuyong',1,'勇','伍',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('xiaojianbo',1,'剑波','萧',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('xiaomohan',1,'莫汉','萧',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('xiaoyangfei',1,'扬飞','肖',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('xieweiyu',1,'维宇','谢','','7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('xj-admin',1,'疆管理员','新',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('xuweihong',1,'伟宏','徐',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('yangdeen',1,'德恩','杨',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('yanghui',1,'辉','杨',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('yihanxia',1,'含霞','易',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('yijiangxia',1,'绛霞','易',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('yn-admin',1,'南管理员','云',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('zhanshengyan',1,'生燕','詹',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL),('zhouminghong',1,'铭洪','周',NULL,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL);

INSERT INTO ACT_ID_GROUP VALUES ('BranchAdmin',1,'子公司管理员',NULL),('BranchDM',1,'子公司部门经理',NULL),('BranchFM',1,'子公司财务经理',NULL),('BranchGM',1,'子公司总经理',NULL),('BranchMarket',1,'子公司市场专员',NULL),('BranchVM',1,'子公司副总经理',NULL),('PM',1,'项目经理',NULL),('President',1,'广东日海总经理',NULL),('ROLE_SUPERVISOR',1,'系统管理员',NULL);

INSERT INTO ACT_ID_MEMBERSHIP VALUES ('ah-admin','BranchAdmin'),('cq-admin','BranchAdmin'),('cs-admin','BranchAdmin'),('guiz-admin','BranchAdmin'),('gx-admin','BranchAdmin'),('gz-admin','BranchAdmin'),('hn-admin','BranchAdmin'),('sunsea-admin','BranchAdmin'),('wh-admin','BranchAdmin'),('xj-admin','BranchAdmin'),('yn-admin','BranchAdmin'),('gonghui','BranchDM'),('huanghuansheng','BranchDM'),('liaoweili','BranchDM'),('wuyong','BranchDM'),('zhouminghong','BranchDM'),('liyu','BranchMarket'),('huanghuansheng','BranchVM'),('liuxiangrong','BranchVM'),('wuyong','BranchVM'),('maolongqiang','PM'),('xieweiyu','PM'),('123456','ROLE_SUPERVISOR');
