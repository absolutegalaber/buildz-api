package com.absolutegalaber.buildz.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@Entity
@Table(
        name = 'project'
)
@ToString(includes = ['id'])
@EqualsAndHashCode(includes = ['id'])
class Project implements Serializable{
    @Id
    String id

    @OneToMany(
            mappedBy = 'project',
            cascade = CascadeType.ALL, fetch = FetchType.EAGER
    )
    List<Branch> branches = new ArrayList<>()
}
