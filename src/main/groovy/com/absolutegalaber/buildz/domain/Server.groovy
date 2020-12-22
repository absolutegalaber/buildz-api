package com.absolutegalaber.buildz.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.Embedded
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Index;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Basic;

/**
 * An Entity which represents a Server that should be tracked by the Buildz
 * System.
 */
@Entity
@Table(
        name = 'server',
        indexes = [
                @Index(name = 'unq_server_name', columnList = 'name', unique = true)
        ]
)
@ToString(includes = ['id', 'name'])
@EqualsAndHashCode(includes = ['id', 'name'])
class Server implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id // TODO change id to name

    @Basic(optional = false)
    String name
}
