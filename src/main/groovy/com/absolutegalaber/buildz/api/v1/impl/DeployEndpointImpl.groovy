package com.absolutegalaber.buildz.api.v1.impl

import com.absolutegalaber.buildz.api.v1.DeployEndpoint
import com.absolutegalaber.buildz.domain.DeployLabel
import com.absolutegalaber.buildz.domain.exception.DataNotFoundException
import com.absolutegalaber.buildz.domain.exception.InvalidRequestException
import com.absolutegalaber.buildz.domain.view.DeployView
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
    List<DeployView> list(String serverName) throws DataNotFoundException {
        deployService.byServer(serverName)
    }

    @Override
    DeployView get(Long deployId) throws DataNotFoundException {
        deployService.byId(deployId)
    }

    @Override
    DeployView register(RegisterDeployEvent event) throws InvalidRequestException {
        deployService.register(event)
    }

    @Override
    DeployView addLabels(Long deployId, List<DeployLabel> deployLabels) throws InvalidRequestException {
        Map<String, String> labels = [:]
        deployLabels.each {
            labels.&put(it.key, it.value)
        }
        deployService.addLabels(deployId, labels)
    }
}
