create table `tenant`
(
    `url`               varchar(255) not null,
    `tenant_name`       varchar(255) not null,
    `driver_class_name` varchar(255) not null,
    `username`          varchar(255) not null,
    `password`          varchar(255) not null, -- TODO move to more secure system. See Tenant class for more info.
    primary key (`url`)
)
    engine = innodb
    default charset = utf8;
