package com.absolutegalaber.buildz.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@Entity
@Table(name = 'branch')
@ToString(includes = ['id', 'name'])
@EqualsAndHashCode(includes = ['id', 'name'])
class Branch implements Serializable {
    @Id
    Long id

    @Basic(optional = false)
    @Column(name = 'name', nullable = false)
    String name

    @ManyToOne()
    @JoinColumn(
            name = 'project_id',
            nullable = false,
            foreignKey = @ForeignKey(name = 'fk_project_name', value = ConstraintMode.CONSTRAINT)
    )
    Project project

    @Basic(optional = false)
    @Column(name = 'active', nullable = false)
    Boolean active = true
}
