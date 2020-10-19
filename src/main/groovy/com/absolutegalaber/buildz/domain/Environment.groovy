package com.absolutegalaber.buildz.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@Entity
@Table(
        name = 'environment',
        uniqueConstraints = [
                @UniqueConstraint(name = 'UNIQUE_ENVIRONMENT_NAME', columnNames = 'name')
        ]
)
@ToString(includes = ['id', 'name'])
@EqualsAndHashCode(includes = ['id', 'name'])
class Environment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @Basic(optional = false)
    String name

    @OneToMany(mappedBy = 'environment', cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<Artifact> artifacts = []
}
