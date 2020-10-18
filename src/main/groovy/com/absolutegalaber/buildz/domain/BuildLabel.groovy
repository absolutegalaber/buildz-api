package com.absolutegalaber.buildz.domain

import com.fasterxml.jackson.annotation.JsonBackReference
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@Entity
@Table(name = 'build_label')
@ToString(includes = ['id', 'key', 'value'])
@EqualsAndHashCode(includes = ['id', 'key', 'value'])
class BuildLabel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @ManyToOne(optional = false)
    @JoinColumn(name = 'build_id', nullable = false)
    @JsonBackReference
    Build build

    @Basic(optional = false)
    @Column(name = 'label_key', nullable = false)
    String key

    @Basic(optional = false)
    @Column(name = 'label_value', nullable = false)
    String value
}
