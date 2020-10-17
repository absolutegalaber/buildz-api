package com.absolutegalaber.buildz.domain


import javax.persistence.*

@Entity
@Table(
        name = 'environment',
        uniqueConstraints = [
                @UniqueConstraint(name = 'UNIQUE_ENVIRONMENT_NAME', columnNames = 'name')
        ]
)
class Environment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @Basic(optional = false)
    String name

    @OneToMany(mappedBy = 'environment', cascade = CascadeType.ALL)
    Set<Artifact> artifacts = []
}
