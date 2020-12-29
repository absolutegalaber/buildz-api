package com.absolutegalaber.buildz.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@Entity
@Table(
        name = 'environment',
        indexes = [
                @Index(name = 'unq_env_name', columnList = 'name', unique = true)
        ]
)
@ToString(includes = ['id', 'name'])
@EqualsAndHashCode(includes = ['id', 'name'])
class Environment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @Basic(optional = false)
    String name

    @OneToMany(mappedBy = 'environment', cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Artifact> artifacts = []
}
