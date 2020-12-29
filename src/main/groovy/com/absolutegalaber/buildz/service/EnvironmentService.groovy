package com.absolutegalaber.buildz.service

import com.absolutegalaber.buildz.api.model.IArtifact
import com.absolutegalaber.buildz.api.model.IEnvironment
import com.absolutegalaber.buildz.domain.Artifact
import com.absolutegalaber.buildz.domain.Environment
import com.absolutegalaber.buildz.domain.exception.InvalidRequestException
import com.absolutegalaber.buildz.repository.ArtifactRepository
import com.absolutegalaber.buildz.repository.EnvironmentRepository
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import static com.absolutegalaber.buildz.repository.QuerySpecs.environmentWithName

@Service
@Transactional
class EnvironmentService {
    private final EnvironmentRepository environmentRepository
    private final ArtifactRepository artifactRepository

    EnvironmentService(EnvironmentRepository environmentRepository, ArtifactRepository artifactRepository) {
        this.environmentRepository = environmentRepository
        this.artifactRepository = artifactRepository
    }

    Optional<Environment> byName(String name) {
        environmentRepository.findOne(
                environmentWithName(name)
        )
    }

    Environment save(IEnvironment toSave) throws InvalidRequestException {
        Environment theEnvironment
        if (toSave.getId() != null) {
            theEnvironment = update(toSave)
        } else {
            theEnvironment = insert(toSave)
        }
        toSave.getArtifacts().each { IArtifact artifact ->
            Artifact newArtifact = new Artifact(
                    project: artifact.project,
                    branch: artifact.branch,
                    environment: theEnvironment
            )
            newArtifact.labels = artifact.labels
            newArtifact.environment = theEnvironment
            theEnvironment.artifacts.add(newArtifact)
            artifactRepository.save(newArtifact)
        }
        theEnvironment
    }

    Environment update(IEnvironment toUpdate) throws InvalidRequestException {
        Environment theEnvironment = environmentRepository.findById(toUpdate.id)
                .orElseThrow({ -> new InvalidRequestException("Environment with id='${toUpdate.id}' not found") })
        theEnvironment.name = toUpdate.name
        theEnvironment.artifacts.forEach({ Artifact artifact ->
            artifactRepository.delete(artifact)
        })
        theEnvironment.getArtifacts().clear()
        theEnvironment
    }

    Environment insert(IEnvironment toInsert) throws InvalidRequestException {
        if (byName(toInsert.getName()).isPresent()) {
            throw new InvalidRequestException("Environment with name='" + toInsert.getName() + "' already exists")
        }
        Environment theEnvironment = new Environment()
        theEnvironment.setName(toInsert.name)
        environmentRepository.save(theEnvironment)
    }

    void delete(String name) {
        byName(name).ifPresent(environmentRepository.&delete)
    }

    List<String> allEnvironments() {
        environmentRepository.findAll(
                Sort.by('name')
        ).collect { it.name }
    }
}
