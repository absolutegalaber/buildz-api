package com.absolutegalaber.buildz.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@Entity
@Table(
        name = 'build_count',
        indexes = [
                @Index(name = 'unq_idx_project_branch', columnList = 'project,branch', unique = true)
        ]
)
@ToString(includes = ['id', 'project', 'branch', 'counter'])
@EqualsAndHashCode(includes = ['id', 'project', 'branch', 'counter'])
class BuildCount implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @Column(name = 'project')
    String project

    @Column(name = 'branch')
    String branch

    @Column(name = 'counter')
    Long counter

    void increment() {
        counter++
    }
}
