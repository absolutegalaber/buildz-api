package com.absolutegalaber.buildz.domain

import com.fasterxml.jackson.annotation.JsonBackReference
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

/**
 * An Entity which represents a Label that will be associated to a specific
 * Deploy which is being tracked by the Buildz System.
 */
@Entity
@Table(
        name = 'deploy_label',
        indexes = [
                @Index(name = 'idx_deploy_label_value', columnList = 'label_key')
        ]
)
@ToString(includes = ['deploy', 'key', 'value'])
@EqualsAndHashCode(includes = ['key', 'value'])
class DeployLabel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @ManyToOne(targetEntity = Build, optional = false)
    @JoinColumn(name = 'deploy_id', nullable = false, foreignKey = @ForeignKey(name = 'fk_deploy', value = ConstraintMode.CONSTRAINT))
    @JsonBackReference
    Deploy deploy

    @Basic(optional = false)
    @Column(name = 'label_key', nullable = false)
    String key

    @Basic(optional = false)
    @Column(name = 'label_value', nullable = false)
    String value
}
