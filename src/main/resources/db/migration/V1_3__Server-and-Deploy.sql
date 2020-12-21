create table `server`
(
    `id`        bigint(20)      not null auto_increment,
    `name`      varchar(255)    not null,
    primary key (`id`)
    )
    engine = innodb
    default charset = utf8;

create unique index `unq_server_name` on `server` (`name`);

create table `deploy`
(
    `id`            bigint(20)  not null auto_increment,
    `deployed_at`   datetime    not null,
    `build_id`      bigint(20)  not null,
    `server_id`     bigint(20)  not null,
    primary key     (`id`),
    constraint      `fk_deploy_build` foreign key (`build_id`) references `build` (`id`),
    constraint      `fk_deploy_server` foreign key (`server_id`) references `server` (`id`)
    )
    engine = innodb
    default charset = utf8;

create table `deploy_label`
(
    `id`            bigint(20)      not null auto_increment,
    `label_key`     varchar(255)    not null,
    `label_value`   varchar(255)    not null,
    `deploy_id`     bigint(20)      not null,
    primary key     (`id`),
    key `fk_deploy` (`deploy_id`),
    constraint `fk_label_deploy` foreign key (`deploy_id`) references `deploy` (`id`) on delete cascade
    )
    engine = innodb
    default charset = utf8;
create index `idx_deploy_label_key` on deploy_label (label_key);
