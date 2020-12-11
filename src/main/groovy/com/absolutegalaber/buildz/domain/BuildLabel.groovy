package com.absolutegalaber.buildz.domain


import com.fasterxml.jackson.annotation.JsonBackReference
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@Entity
@Table(
        name = 'build_label',
        indexes = [
                @Index(name = 'idx_build_label_value', columnList = 'label_key')
        ]
)
@ToString(includes = ['id', 'key', 'value'])
@EqualsAndHashCode(includes = ['id', 'key', 'value'])
class BuildLabel implements Serializable, Comparable<BuildLabel> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @ManyToOne(targetEntity = Build, optional = false)
    @JoinColumn(name = 'build_id', nullable = false, foreignKey = @ForeignKey(name = 'fk_build', value = ConstraintMode.CONSTRAINT))
    @JsonBackReference
    Build build

    @Basic(optional = false)
    @Column(name = 'label_key', nullable = false)
    String key

    @Basic(optional = false)
    @Column(name = 'label_value', nullable = false)
    String value

    @Override
    int compareTo(BuildLabel o) {
        return getKey() <=> o.getKey()
    }
}
