package com.absolutegalaber.buildz.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Basic
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.Table

/**
 * An Entity which represents a Deploy to a specific Server which
 * is tracked by the Buildz System.
 */
@Entity
@Table(name = 'deploy')
@ToString(includes = ['id', 'deployedAt', 'server', 'build' ])
@EqualsAndHashCode(includes = ['id', 'deployedAt', 'server', 'build', 'labels'])
class Deploy implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @Basic(optional = false)
    @Column(name = 'deployed_at')
    Date deployedAt

    @OneToOne(optional = false, targetEntity =  Build, fetch = FetchType.EAGER)
    @JoinColumn(name = 'build_id', nullable = false)
    Build build

    @OneToOne(optional = false, targetEntity = Server, fetch = FetchType.EAGER)
    @JoinColumn(name = 'server_id', nullable = false)
    Server server

    @OneToMany(mappedBy = "deploy", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<DeployLabel> labels = []
}
