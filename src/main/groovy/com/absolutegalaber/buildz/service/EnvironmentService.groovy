package com.absolutegalaber.buildz.service

import com.absolutegalaber.buildz.domain.Artifact
import com.absolutegalaber.buildz.domain.Environment
import com.absolutegalaber.buildz.domain.InvalidRequestException
import com.absolutegalaber.buildz.repository.ArtifactRepository
import com.absolutegalaber.buildz.repository.EnvironmentRepository
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
        return environmentRepository.findOne(
                environmentWithName(name)
        )
    }

    Environment create(String name) throws InvalidRequestException {
        //check name to avoid unique value constraint failure from db.
        if (byName(name).isPresent()) {
            throw new InvalidRequestException("Environment with name=" + name + "already present")
        }
        Environment environment = new Environment()
        environment.setName(name)
        return environmentRepository.save(environment)
    }

    Environment save(Environment environment) throws InvalidRequestException {
        if (environment.getId() != null) {
            //an update ==> we don't mess around here and clean the depending side environment.artifact
            //obviously this could be handled better...
            Environment current = environmentRepository.findById(environment.getId()).get()
            current.getArtifacts().each { Artifact artifact ->
                artifactRepository.delete(artifact)
            }
            current.getArtifacts().clear()
        } else {
            //an insert -> check uniqueness of name
            if (byName(environment.getName()).isPresent()) {
                throw new InvalidRequestException("Environment with name=" + environment.getName() + "already present")
            }
        }
        return environmentRepository.save(environment)
    }

    void delete(String name) {
        byName(name).ifPresent(environmentRepository.&delete)
    }
}
