package com.absolutegalaber.buildz.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@Entity
@Table(
        name = 'build_count',
        uniqueConstraints = [
                @UniqueConstraint(name = 'UNIQUE_PROJECT_BRANCH', columnNames = ['project', 'branch'])
        ]
)
@ToString(includes = ['id', 'project', 'branch', 'counter'])
@EqualsAndHashCode(includes = ['id', 'project', 'branch', 'counter'])
class BuildCount {
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
        counter++;
    }
}
