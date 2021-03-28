package com.absolutegalaber.buildz.api.v1.impl

import com.absolutegalaber.buildz.api.model.IDeploy
import com.absolutegalaber.buildz.api.v1.DeployEndpoint
import com.absolutegalaber.buildz.domain.DeploySearch
import com.absolutegalaber.buildz.domain.DeploySearchResult
import com.absolutegalaber.buildz.domain.exception.DataNotFoundException
import com.absolutegalaber.buildz.domain.exception.FutureDateException
import com.absolutegalaber.buildz.domain.exception.InvalidRequestException
import com.absolutegalaber.buildz.events.RegisterDeployEvent
import com.absolutegalaber.buildz.service.DeployService
import com.absolutegalaber.buildz.service.ServerService
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
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
    DeploySearchResult search(DeploySearch deploySearch) throws DataNotFoundException {
        deployService.search(deploySearch)
    }

    @Override
    IDeploy onServerAt(
            String serverName,
            Date deployedAt
    ) throws DataNotFoundException, InvalidRequestException, FutureDateException {
        IDeploy.of(
                deployService.onServerAt(serverName, deployedAt)
        )
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
