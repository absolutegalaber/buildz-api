package com.absolutegalaber.buildz.api.v1.impl

import com.absolutegalaber.buildz.api.model.IDeploy
import com.absolutegalaber.buildz.api.v1.DeployEndpoint
import com.absolutegalaber.buildz.domain.DeploySearch
import com.absolutegalaber.buildz.domain.DeploySearchResult
import com.absolutegalaber.buildz.domain.exception.DataNotFoundException
import com.absolutegalaber.buildz.domain.exception.InvalidRequestException
import com.absolutegalaber.buildz.events.RegisterDeployEvent
import com.absolutegalaber.buildz.service.DeployService
import com.absolutegalaber.buildz.service.ServerService
import org.springframework.web.bind.annotation.RestController

import javax.transaction.Transactional

/**
 * Implementation of {@link DeployEndpoint}
 */
@RestController
@Transactional
class DeployEndpointImpl implements DeployEndpoint {
    private final DeployService deployService
    private final ServerService serverService

    DeployEndpointImpl(DeployService deployService, ServerService serverService) {
        this.deployService = deployService
        this.serverService = serverService
    }

    @Override
    DeploySearchResult search(
            String serverName,
            DeploySearch deploySearch
    ) throws DataNotFoundException {
        deploySearch.serverName = serverName

        deployService.search(deploySearch)
    }

    @Override
    IDeploy get(Long deployId) throws DataNotFoundException {
        IDeploy.of(
                deployService.byId(deployId)
        )
    }

    @Override
    IDeploy register(RegisterDeployEvent event) throws InvalidRequestException {
        IDeploy.of(
                deployService.register(event)
        )
    }

    @Override
    IDeploy addLabels(Long deployId, Map<String, String> deployLabels) throws InvalidRequestException {
        IDeploy.of(
                deployService.addLabels(deployId, deployLabels)
        )
    }
}
