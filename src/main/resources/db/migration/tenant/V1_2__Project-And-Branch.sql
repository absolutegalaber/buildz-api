create table `project`
(
    `id` varchar(255) not null,
    `active`     tinyint(1)   not null default 1,
    primary key (`id`)
)
    engine = innodb
    default charset = utf8;

create table `branch`
(
    `id`         bigint(20)   not null auto_increment,
    `name`       varchar(255) not null,
    `project_id` varchar(255) not null,
    `active`     tinyint(1)   not null default 1,
    primary key (`id`),
    constraint `fk_project_name` foreign key (`project_id`) references `project` (`id`)
)
    engine = innodb
    default charset = utf8;

-- collect the current projects from the builds data
insert into `project`(`id`)
select distinct `project`
from `build`;

-- collect the current branches from the builds data
insert into `branch`(`project_id`, `name`, `active`)
select distinct x.`project`, x.`branch`, 1
from `build` x;