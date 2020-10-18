package com.absolutegalaber.buildz.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@Entity
@Table(
        name = 'build',
        uniqueConstraints = [
                @UniqueConstraint(name = 'UNIQUE_BUILD', columnNames = ['project', 'branch', 'build_number'])
        ]

)
@ToString(includes = ['id', 'project', 'branch', 'buildNumber'])
@EqualsAndHashCode(includes = ['id', 'project', 'branch', 'buildNumber'])
class Build {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @Basic(optional = false)
    @Column(name = 'project')
    String project

    @Basic(optional = false)
    @Column(name = 'branch')
    String branch

    @Basic(optional = false)
    @Column(name = 'build_number')
    Long buildNumber

    @OneToMany(mappedBy = "build", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<BuildLabel> labels = []
}
