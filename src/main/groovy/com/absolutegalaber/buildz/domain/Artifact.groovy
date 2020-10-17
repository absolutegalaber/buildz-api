package com.absolutegalaber.buildz.domain


import javax.persistence.*

@Entity
@Table(name = 'artifact')
class Artifact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @ManyToOne(optional = false)
    @JoinColumn(name = 'environment_id', nullable = false)
    Environment environment

    @Basic(optional = false)
    @Column(name = 'project')
    String project

    @Basic(optional = false)
    @Column(name = 'branch')
    String branch

    @ElementCollection
    @CollectionTable(name = 'artifact_labels', joinColumns = @JoinColumn(name = 'artifact_id'))
    Map<String, String> labels = [:]
}
