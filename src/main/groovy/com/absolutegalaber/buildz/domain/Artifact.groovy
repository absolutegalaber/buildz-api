package com.absolutegalaber.buildz.domain

import com.fasterxml.jackson.annotation.JsonBackReference
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@Entity
@Table(name = 'artifact')
@ToString(includes = ['id', 'project', 'branch', 'labels'])
@EqualsAndHashCode(includes = ['id', 'project', 'branch', 'labels'])
class Artifact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @ManyToOne(optional = false)
    @JoinColumn(name = 'environment_id', nullable = false)
    @JsonBackReference
    Environment environment

    @Basic(optional = false)
    @Column(name = 'project')
    String project

    @Basic(optional = false)
    @Column(name = 'branch')
    String branch

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = 'artifact_labels', joinColumns = @JoinColumn(name = 'artifact_id'))
    Map<String, String> labels = [:]
}
