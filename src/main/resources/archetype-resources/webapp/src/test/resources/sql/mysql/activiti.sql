DROP DATABASE IF EXISTS `${db.name}`;
CREATE DATABASE `${db.name}` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `${db.name}`;

create table ACT_GE_PROPERTY (
    NAME_ varchar(64),
    VALUE_ varchar(300),
    REV_ integer,
    primary key (NAME_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

insert into ACT_GE_PROPERTY
values ('schema.version', '5.13', 1);

insert into ACT_GE_PROPERTY
values ('schema.history', 'create(5.13)', 1);

insert into ACT_GE_PROPERTY
values ('next.dbid', '1', 1);

create table ACT_GE_BYTEARRAY (
    ID_ varchar(64),
    REV_ integer,
    NAME_ varchar(255),
    DEPLOYMENT_ID_ varchar(64),
    BYTES_ LONGBLOB,
    GENERATED_ TINYINT,
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

create table ACT_RE_DEPLOYMENT (
    ID_ varchar(64),
    NAME_ varchar(255),
    CATEGORY_ varchar(255),
    DEPLOY_TIME_ timestamp,
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

create table ACT_RE_MODEL (
    ID_ varchar(64) not null,
    REV_ integer,
    NAME_ varchar(255),
    KEY_ varchar(255),
    CATEGORY_ varchar(255),
    CREATE_TIME_ timestamp null,
    LAST_UPDATE_TIME_ timestamp null,
    VERSION_ integer,
    META_INFO_ varchar(4000),
    DEPLOYMENT_ID_ varchar(64),
    EDITOR_SOURCE_VALUE_ID_ varchar(64),
    EDITOR_SOURCE_EXTRA_VALUE_ID_ varchar(64),
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

create table ACT_RU_EXECUTION (
    ID_ varchar(64),
    REV_ integer,
    PROC_INST_ID_ varchar(64),
    BUSINESS_KEY_ varchar(255),
    PARENT_ID_ varchar(64),
    PROC_DEF_ID_ varchar(64),
    SUPER_EXEC_ varchar(64),
    ACT_ID_ varchar(255),
    IS_ACTIVE_ TINYINT,
    IS_CONCURRENT_ TINYINT,
    IS_SCOPE_ TINYINT,
    IS_EVENT_SCOPE_ TINYINT,
    SUSPENSION_STATE_ integer,
    CACHED_ENT_STATE_ integer,
    primary key (ID_),
    unique ACT_UNIQ_RU_BUS_KEY (PROC_DEF_ID_, BUSINESS_KEY_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

create table ACT_RU_JOB (
    ID_ varchar(64) NOT NULL,
  REV_ integer,
    TYPE_ varchar(255) NOT NULL,
    LOCK_EXP_TIME_ timestamp NULL,
    LOCK_OWNER_ varchar(255),
    EXCLUSIVE_ boolean,
    EXECUTION_ID_ varchar(64),
    PROCESS_INSTANCE_ID_ varchar(64),
    PROC_DEF_ID_ varchar(64),
    RETRIES_ integer,
    EXCEPTION_STACK_ID_ varchar(64),
    EXCEPTION_MSG_ varchar(4000),
    DUEDATE_ timestamp NULL,
    REPEAT_ varchar(255),
    HANDLER_TYPE_ varchar(255),
    HANDLER_CFG_ varchar(4000),
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

create table ACT_RE_PROCDEF (
    ID_ varchar(64) not null,
    REV_ integer,
    CATEGORY_ varchar(255),
    NAME_ varchar(255),
    KEY_ varchar(255) not null,
    VERSION_ integer not null,
    DEPLOYMENT_ID_ varchar(64),
    RESOURCE_NAME_ varchar(4000),
    DGRM_RESOURCE_NAME_ varchar(4000),
    DESCRIPTION_ varchar(4000),
    HAS_START_FORM_KEY_ TINYINT,
    SUSPENSION_STATE_ integer,
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

create table ACT_RU_TASK (
    ID_ varchar(64),
    REV_ integer,
    EXECUTION_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    PROC_DEF_ID_ varchar(64),
    NAME_ varchar(255),
    PARENT_TASK_ID_ varchar(64),
    DESCRIPTION_ varchar(4000),
    TASK_DEF_KEY_ varchar(255),
    OWNER_ varchar(255),
    ASSIGNEE_ varchar(255),
    DELEGATION_ varchar(64),
    PRIORITY_ integer,
    CREATE_TIME_ timestamp,
    DUE_DATE_ datetime,
    SUSPENSION_STATE_ integer,
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

create table ACT_RU_IDENTITYLINK (
    ID_ varchar(64),
    REV_ integer,
    GROUP_ID_ varchar(255),
    TYPE_ varchar(255),
    USER_ID_ varchar(255),
    TASK_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    PROC_DEF_ID_ varchar(64),    
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

create table ACT_RU_VARIABLE (
    ID_ varchar(64) not null,
    REV_ integer,
    TYPE_ varchar(255) not null,
    NAME_ varchar(255) not null,
    EXECUTION_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    TASK_ID_ varchar(64),
    BYTEARRAY_ID_ varchar(64),
    DOUBLE_ double,
    LONG_ bigint,
    TEXT_ varchar(4000),
    TEXT2_ varchar(4000),
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

create table ACT_RU_EVENT_SUBSCR (
    ID_ varchar(64) not null,
    REV_ integer,
    EVENT_TYPE_ varchar(255) not null,
    EVENT_NAME_ varchar(255),
    EXECUTION_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    ACTIVITY_ID_ varchar(64),
    CONFIGURATION_ varchar(255),
    CREATED_ timestamp not null,
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

create index ACT_IDX_EXEC_BUSKEY on ACT_RU_EXECUTION(BUSINESS_KEY_);
create index ACT_IDX_TASK_CREATE on ACT_RU_TASK(CREATE_TIME_);
create index ACT_IDX_IDENT_LNK_USER on ACT_RU_IDENTITYLINK(USER_ID_);
create index ACT_IDX_IDENT_LNK_GROUP on ACT_RU_IDENTITYLINK(GROUP_ID_);
create index ACT_IDX_EVENT_SUBSCR_CONFIG_ on ACT_RU_EVENT_SUBSCR(CONFIGURATION_);
create index ACT_IDX_VARIABLE_TASK_ID on ACT_RU_VARIABLE(TASK_ID_);
create index ACT_IDX_ATHRZ_PROCEDEF on ACT_RU_IDENTITYLINK(PROC_DEF_ID_);

alter table ACT_GE_BYTEARRAY
    add constraint ACT_FK_BYTEARR_DEPL 
    foreign key (DEPLOYMENT_ID_) 
    references ACT_RE_DEPLOYMENT (ID_);

alter table ACT_RE_PROCDEF
    add constraint ACT_UNIQ_PROCDEF
    unique (KEY_,VERSION_);
    
alter table ACT_RU_EXECUTION
    add constraint ACT_FK_EXE_PROCINST 
    foreign key (PROC_INST_ID_) 
    references ACT_RU_EXECUTION (ID_) on delete cascade on update cascade;

alter table ACT_RU_EXECUTION
    add constraint ACT_FK_EXE_PARENT 
    foreign key (PARENT_ID_) 
    references ACT_RU_EXECUTION (ID_);
    
alter table ACT_RU_EXECUTION
    add constraint ACT_FK_EXE_SUPER 
    foreign key (SUPER_EXEC_) 
    references ACT_RU_EXECUTION (ID_);

alter table ACT_RU_EXECUTION
    add constraint ACT_FK_EXE_PROCDEF 
    foreign key (PROC_DEF_ID_) 
    references ACT_RE_PROCDEF (ID_);
    
alter table ACT_RU_IDENTITYLINK
    add constraint ACT_FK_TSKASS_TASK 
    foreign key (TASK_ID_) 
    references ACT_RU_TASK (ID_);
    
alter table ACT_RU_IDENTITYLINK
    add constraint ACT_FK_ATHRZ_PROCEDEF 
    foreign key (PROC_DEF_ID_) 
    references ACT_RE_PROCDEF(ID_);
    
alter table ACT_RU_IDENTITYLINK
    add constraint ACT_FK_IDL_PROCINST
    foreign key (PROC_INST_ID_) 
    references ACT_RU_EXECUTION (ID_);       
    
alter table ACT_RU_TASK
    add constraint ACT_FK_TASK_EXE
    foreign key (EXECUTION_ID_)
    references ACT_RU_EXECUTION (ID_);
    
alter table ACT_RU_TASK
    add constraint ACT_FK_TASK_PROCINST
    foreign key (PROC_INST_ID_)
    references ACT_RU_EXECUTION (ID_);
    
alter table ACT_RU_TASK
  add constraint ACT_FK_TASK_PROCDEF
  foreign key (PROC_DEF_ID_)
  references ACT_RE_PROCDEF (ID_);
  
alter table ACT_RU_VARIABLE 
    add constraint ACT_FK_VAR_EXE 
    foreign key (EXECUTION_ID_) 
    references ACT_RU_EXECUTION (ID_);

alter table ACT_RU_VARIABLE
    add constraint ACT_FK_VAR_PROCINST
    foreign key (PROC_INST_ID_)
    references ACT_RU_EXECUTION(ID_);

alter table ACT_RU_VARIABLE 
    add constraint ACT_FK_VAR_BYTEARRAY 
    foreign key (BYTEARRAY_ID_) 
    references ACT_GE_BYTEARRAY (ID_);

alter table ACT_RU_JOB 
    add constraint ACT_FK_JOB_EXCEPTION 
    foreign key (EXCEPTION_STACK_ID_) 
    references ACT_GE_BYTEARRAY (ID_);

alter table ACT_RU_EVENT_SUBSCR
    add constraint ACT_FK_EVENT_EXEC
    foreign key (EXECUTION_ID_)
    references ACT_RU_EXECUTION(ID_);
    
alter table ACT_RE_MODEL 
    add constraint ACT_FK_MODEL_SOURCE 
    foreign key (EDITOR_SOURCE_VALUE_ID_) 
    references ACT_GE_BYTEARRAY (ID_);

alter table ACT_RE_MODEL 
    add constraint ACT_FK_MODEL_SOURCE_EXTRA 
    foreign key (EDITOR_SOURCE_EXTRA_VALUE_ID_) 
    references ACT_GE_BYTEARRAY (ID_);
    
alter table ACT_RE_MODEL 
    add constraint ACT_FK_MODEL_DEPLOYMENT 
    foreign key (DEPLOYMENT_ID_) 
    references ACT_RE_DEPLOYMENT (ID_);        
create table ACT_HI_PROCINST (
    ID_ varchar(64) not null,
    PROC_INST_ID_ varchar(64) not null,
    BUSINESS_KEY_ varchar(255),
    PROC_DEF_ID_ varchar(64) not null,
    START_TIME_ datetime not null,
    END_TIME_ datetime,
    DURATION_ bigint,
    START_USER_ID_ varchar(255),
    START_ACT_ID_ varchar(255),
    END_ACT_ID_ varchar(255),
    SUPER_PROCESS_INSTANCE_ID_ varchar(64),
    DELETE_REASON_ varchar(4000),
    primary key (ID_),
    unique (PROC_INST_ID_),
    unique ACT_UNIQ_HI_BUS_KEY (PROC_DEF_ID_, BUSINESS_KEY_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

create table ACT_HI_ACTINST (
    ID_ varchar(64) not null,
    PROC_DEF_ID_ varchar(64) not null,
    PROC_INST_ID_ varchar(64) not null,
    EXECUTION_ID_ varchar(64) not null,
    ACT_ID_ varchar(255) not null,
    TASK_ID_ varchar(64),
    CALL_PROC_INST_ID_ varchar(64),
    ACT_NAME_ varchar(255),
    ACT_TYPE_ varchar(255) not null,
    ASSIGNEE_ varchar(64),
    START_TIME_ datetime not null,
    END_TIME_ datetime,
    DURATION_ bigint,
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

create table ACT_HI_TASKINST (
    ID_ varchar(64) not null,
    PROC_DEF_ID_ varchar(64),
    TASK_DEF_KEY_ varchar(255),
    PROC_INST_ID_ varchar(64),
    EXECUTION_ID_ varchar(64),
    NAME_ varchar(255),
    PARENT_TASK_ID_ varchar(64),
    DESCRIPTION_ varchar(4000),
    OWNER_ varchar(255),
    ASSIGNEE_ varchar(255),
    START_TIME_ datetime not null,
    CLAIM_TIME_ datetime,
    END_TIME_ datetime,
    DURATION_ bigint,
    DELETE_REASON_ varchar(4000),
    PRIORITY_ integer,
    DUE_DATE_ datetime,
    FORM_KEY_ varchar(255),
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

create table ACT_HI_VARINST (
    ID_ varchar(64) not null,
    PROC_INST_ID_ varchar(64),
    EXECUTION_ID_ varchar(64),
    TASK_ID_ varchar(64),
    NAME_ varchar(255) not null,
    VAR_TYPE_ varchar(100),
    REV_ integer,
    BYTEARRAY_ID_ varchar(64),
    DOUBLE_ double,
    LONG_ bigint,
    TEXT_ varchar(4000),
    TEXT2_ varchar(4000),
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

create table ACT_HI_DETAIL (
    ID_ varchar(64) not null,
    TYPE_ varchar(255) not null,
    PROC_INST_ID_ varchar(64),
    EXECUTION_ID_ varchar(64),
    TASK_ID_ varchar(64),
    ACT_INST_ID_ varchar(64),
    NAME_ varchar(255) not null,
    VAR_TYPE_ varchar(255),
    REV_ integer,
    TIME_ datetime not null,
    BYTEARRAY_ID_ varchar(64),
    DOUBLE_ double,
    LONG_ bigint,
    TEXT_ varchar(4000),
    TEXT2_ varchar(4000),
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

create table ACT_HI_COMMENT (
    ID_ varchar(64) not null,
    TYPE_ varchar(255),
    TIME_ datetime not null,
    USER_ID_ varchar(255),
    TASK_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    ACTION_ varchar(255),
    MESSAGE_ varchar(4000),
    FULL_MSG_ LONGBLOB,
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

create table ACT_HI_ATTACHMENT (
    ID_ varchar(64) not null,
    REV_ integer,
    USER_ID_ varchar(255),
    NAME_ varchar(255),
    DESCRIPTION_ varchar(4000),
    TYPE_ varchar(255),
    TASK_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    URL_ varchar(4000),
    CONTENT_ID_ varchar(64),
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

create table ACT_HI_IDENTITYLINK (
    ID_ varchar(64),
    GROUP_ID_ varchar(255),
    TYPE_ varchar(255),
    USER_ID_ varchar(255),
    TASK_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

create index ACT_IDX_HI_IDENT_LNK_USER on ACT_HI_IDENTITYLINK(USER_ID_);
create index ACT_IDX_HI_IDENT_LNK_TASK on ACT_HI_IDENTITYLINK(TASK_ID_);
create index ACT_IDX_HI_IDENT_LNK_PROCINST on ACT_HI_IDENTITYLINK(PROC_INST_ID_);
create index ACT_IDX_HI_PRO_INST_END on ACT_HI_PROCINST(END_TIME_);
create index ACT_IDX_HI_PRO_I_BUSKEY on ACT_HI_PROCINST(BUSINESS_KEY_);
create index ACT_IDX_HI_ACT_INST_START on ACT_HI_ACTINST(START_TIME_);
create index ACT_IDX_HI_ACT_INST_END on ACT_HI_ACTINST(END_TIME_);
create index ACT_IDX_HI_DETAIL_PROC_INST on ACT_HI_DETAIL(PROC_INST_ID_);
create index ACT_IDX_HI_DETAIL_ACT_INST on ACT_HI_DETAIL(ACT_INST_ID_);
create index ACT_IDX_HI_DETAIL_TIME on ACT_HI_DETAIL(TIME_);
create index ACT_IDX_HI_DETAIL_NAME on ACT_HI_DETAIL(NAME_);
create index ACT_IDX_HI_DETAIL_TASK_ID on ACT_HI_DETAIL(TASK_ID_);
create index ACT_IDX_HI_PROCVAR_PROC_INST on ACT_HI_VARINST(PROC_INST_ID_);
create index ACT_IDX_HI_PROCVAR_NAME_TYPE on ACT_HI_VARINST(NAME_, VAR_TYPE_);
create index ACT_IDX_HI_ACT_INST_PROCINST on ACT_HI_ACTINST(PROC_INST_ID_, ACT_ID_);
create index ACT_IDX_HI_ACT_INST_EXEC on ACT_HI_ACTINST(EXECUTION_ID_, ACT_ID_);
create table ACT_ID_GROUP (
    ID_ varchar(64),
    REV_ integer,
    NAME_ varchar(255),
    TYPE_ varchar(255),
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

create table ACT_ID_MEMBERSHIP (
    USER_ID_ varchar(64),
    GROUP_ID_ varchar(64),
    primary key (USER_ID_, GROUP_ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

create table ACT_ID_USER (
    ID_ varchar(64),
    REV_ integer,
    FIRST_ varchar(255),
    LAST_ varchar(255),
    EMAIL_ varchar(255),
    PWD_ varchar(255),
    PICTURE_ID_ varchar(64),
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

create table ACT_ID_INFO (
    ID_ varchar(64),
    REV_ integer,
    USER_ID_ varchar(64),
    TYPE_ varchar(64),
    KEY_ varchar(255),
    VALUE_ varchar(255),
    PASSWORD_ LONGBLOB,
    PARENT_ID_ varchar(255),
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

alter table ACT_ID_MEMBERSHIP 
    add constraint ACT_FK_MEMB_GROUP 
    foreign key (GROUP_ID_) 
    references ACT_ID_GROUP (ID_);

alter table ACT_ID_MEMBERSHIP 
    add constraint ACT_FK_MEMB_USER 
    foreign key (USER_ID_) 
    references ACT_ID_USER (ID_);

     
    
SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;


INSERT INTO `ACT_ID_GROUP` (`ID_`, `REV_`, `NAME_`, `TYPE_`) VALUES
('BranchAdmin', 1, '子公司管理员', NULL),
('BranchDM', 1, '子公司部门经理', NULL),
('BranchFM', 1, '子公司财务经理', NULL),
('BranchGM', 1, '子公司总经理', NULL),
('BranchMarket', 1, '子公司市场专员', NULL),
('BranchVM', 1, '子公司副总经理', NULL),
('PM', 1, '项目经理', NULL),
('PmcDirector', 1, '项目管理中心总监', NULL),
('President', 1, '广东日海总经理', NULL),
('ROLE_SUPERVISOR', 1, '系统管理员', NULL),
('VP', 1, '广东日海副总经理', NULL);

INSERT INTO `ACT_ID_USER` (`ID_`, `REV_`, `FIRST_`, `LAST_`, `EMAIL_`, `PWD_`, `PICTURE_ID_`) VALUES
('123456', 1, '宇', '杨', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('ah-admin', 1, '徽管理员', '安', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('cq-admin', 1, '庆管理员', '重', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('cs-admin', 1, '沙管理员', '长', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('fuzhiting', 1, '志庭', '付', 'xx@xx.com', '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('gaowenwei', 1, '文威', '高', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('gaoyunzhao', 1, '云照', '高', 'xxx@xx.com', '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('gonghui', 1, '辉', '龚', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('guiz-admin', 1, '州管理员', '贵', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('guojia', 1, '佳', '郭', 'xx@xx.com', '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('gx-admin', 1, '西管理员', '广', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('gz-admin', 1, '州管理员', '广', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('hn-admin', 1, '南管理员', '河', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('hongmianyan', 1, '棉衍', '洪', 'xxx@xx.com', '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('huangbinyu', 1, '斌宇', '黄', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('huanghuansheng', 1, '焕生', '黄', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('huangzhihua', 1, '志华', '黄', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('liangwanping', 1, '婉萍', '梁', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('liaoweili', 1, '伟利', '廖', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('lihuantang', 1, '焕棠', '李', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('liliangwei', 1, '良卫', '李', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('lipeishan', 1, '佩珊', '李', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('liqingming', 1, '庆明', '李', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('liruirong', 1, '瑞荣', '李', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('liuxiangrong', 1, '向荣', '刘', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('liyu', 1, '裕', '李', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('liyuanguang', 1, '远光', '李', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('maolongqiang', 1, '龙强', '毛', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('niuxiaofeng', 1, '晓峰', '牛', '', '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('sunsea-admin', 1, '公司管理员', '总', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('sunxiaotao', 1, '小涛', '孙', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('tianfucai', 1, '福才', '田', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('wangliang', 1, '亮', '王', 'xx@xx.com', '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('wh-admin', 1, '汉管理员', '武', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('wuyong', 1, '勇', '伍', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('xiaojianbo', 1, '剑波', '萧', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('xiaomohan', 1, '莫汉', '萧', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('xiaoyangfei', 1, '扬飞', '肖', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('xieweiyu', 1, '维宇', '谢', '', '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('xj-admin', 1, '疆管理员', '新', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('xuweihong', 1, '伟宏', '徐', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('yangdeen', 1, '德恩', '杨', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('yanghui', 1, '辉', '杨', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('yihanxia', 1, '含霞', '易', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('yijiangxia', 1, '绛霞', '易', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('yn-admin', 1, '南管理员', '云', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('zengxuxuan', 1, '旭旋', '曾', 'xx@xx.com', '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('zhanshengyan', 1, '生燕', '詹', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL),
('zhouminghong', 1, '铭洪', '周', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL);

INSERT INTO `ACT_ID_MEMBERSHIP` (`USER_ID_`, `GROUP_ID_`) VALUES
('ah-admin', 'BranchAdmin'),
('cq-admin', 'BranchAdmin'),
('cs-admin', 'BranchAdmin'),
('guiz-admin', 'BranchAdmin'),
('gx-admin', 'BranchAdmin'),
('gz-admin', 'BranchAdmin'),
('hn-admin', 'BranchAdmin'),
('sunsea-admin', 'BranchAdmin'),
('wh-admin', 'BranchAdmin'),
('xj-admin', 'BranchAdmin'),
('yn-admin', 'BranchAdmin'),
('gonghui', 'BranchDM'),
('huanghuansheng', 'BranchDM'),
('liaoweili', 'BranchDM'),
('wuyong', 'BranchDM'),
('zhouminghong', 'BranchDM'),
('liuxiangrong', 'BranchGM'),
('liyu', 'BranchMarket'),
('huanghuansheng', 'BranchVM'),
('wuyong', 'BranchVM'),
('fuzhiting', 'PM'),
('guojia', 'PM'),
('maolongqiang', 'PM'),
('xieweiyu', 'PM'),
('zengxuxuan', 'PM'),
('wangliang', 'PmcDirector'),
('gaoyunzhao', 'President'),
('123456', 'ROLE_SUPERVISOR'),
('hongmianyan', 'VP');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;