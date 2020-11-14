package com.absolutegalaber.buildz.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@Entity
@Table(
        name = 'build',
        indexes = [
                @Index(name = 'unq_idx_project_branch', columnList = 'project,branch,build_number')
        ]
)
@ToString(includes = ['id', 'project', 'branch', 'buildNumber'])
@EqualsAndHashCode(includes = ['id', 'project', 'branch', 'buildNumber'])
class Build implements Serializable {
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
