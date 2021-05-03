package com.absolutegalaber.buildz.domain

import org.springframework.boot.jdbc.DataSourceBuilder

import javax.persistence.Basic
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.sql.DataSource

@Entity
@Table(name = "tenant")
class Tenant {

    @Id
    String url

    @Column(name = "tenant_name", updatable = false, nullable = false)
    String tenantName

    @Basic(optional = false)
    @Column(name = "driver_class_name")
    String driverClassName

    @Basic(optional = false)
    String username

    // TODO this is proof-of-concept ONLY!!! CHANGE BEFORE DEPLOYING ANYWHERE
    // Thoughts on alternatives: Many (most?) modern DBs have some way to login via a key. One alternative would be use
    // these key-login feature and storing the path to this key here in this entity. For extra security this key could
    // be password locked and can only be unlocked with a password provided by the client when the login. The password
    // protected key file would then be encrypted as well (a la envelope key encryption) via a key provided by the Build
    // Buddy Admin(s).
    // Another option for when key-based login is not possible, would be reading the password from a file that is
    // encrypted similar to above (user password protected and then that file is envelope encrypted).
    // It should be noted that the additional security (user password with envelope encryption) would probably increase
    // the request time massively (might not be worth it?)
    // The final option would be to create a DB user for every user that has access to build buddy. The problem here
    // would be that a potentially huge number of DB users would need to be created, increasing the attack surface of
    // the DB and increase the complexity of the user and tenant creation and updates.
    @Basic(optional = false)
    String password

    DataSource buildDataSource() {
        DataSourceBuilder
                .create()
                .url(this.url)
                .driverClassName(this.driverClassName)
                .username(this.username)
                .password(this.password)
                .build()
    }
}
