create table `build_count`
(
    `id`      bigint(20)   not null auto_increment,
    `branch`  varchar(255) not null,
    `counter` bigint(20)   not null,
    `project` varchar(255) not null,
    primary key (`id`)
)
    engine = innodb
    default charset = utf8;
create unique index `idx_project_branch` on `build_count` (`project`, `branch`);

create table `build`
(
    `id`           bigint(20)   not null auto_increment,
    `branch`       varchar(255) not null,
    `build_number` bigint(20)   not null,
    `project`      varchar(255) not null,
    primary key (`id`)
)
    engine = innodb
    default charset = utf8;
create unique index `idx_project_branch` on `build` (`project`, `branch`, `build_number`);



create table `build_label`
(
    `id`          bigint(20)    not null auto_increment,
    `label_key`   varchar(255)  not null,
    `label_value` varchar(255) not null,
    `build_id`    bigint(20)    not null,
    primary key (`id`),
    key `fk_build` (`build_id`),
    constraint `fk_build` foreign key (`build_id`) references `build` (`id`)
)
    engine = innodb
    default charset = utf8;
create index `idx_build_label_key` on build_label (label_key);

create table `environment`
(
    `id`   bigint(20)   not null auto_increment,
    `name` varchar(255) not null,
    primary key (`id`)
)
    engine = innodb
    default charset = utf8;
create unique index `unq_env_name` on `environment` (`name`);


create table `artifact`
(
    `id`             bigint(20) not null auto_increment,
    `branch`         varchar(255) default null,
    `project`        varchar(255) default null,
    `environment_id` bigint(20) not null,
    primary key (`id`),
    key `fk_environment` (`environment_id`),
    constraint `fk_environment` foreign key (`environment_id`) references `environment` (`id`)
)
    engine = innodb
    default charset = utf8;

create table `artifact_labels`
(
    `artifact_id` bigint(20)   not null,
    `labels`      varchar(255)          default null,
    `labels_key`  varchar(255) not null default '',
    primary key (`artifact_id`, `labels_key`),
    constraint `fk_artifact` foreign key (`artifact_id`) references `artifact` (`id`)
)
    engine = innodb
    default charset = utf8;
