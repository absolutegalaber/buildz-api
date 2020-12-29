package com.absolutegalaber.buildz.api.model

import com.absolutegalaber.buildz.domain.Deploy
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes = ['id', 'deployedAt', 'build'])
class IDeploy implements Serializable {
    Long id
    Date deployedAt
    IBuild build
    Map<String, String> labels = [:]

    static IDeploy of(Deploy deploy) {
        IDeploy toReturn = new IDeploy(
                id: deploy.id,
                deployedAt: deploy.deployedAt,
                build: IBuild.of(deploy.build),
        )
        deploy.labels.each {
            toReturn.labels.put(it.key, it.value)
        }
        toReturn
    }
}
