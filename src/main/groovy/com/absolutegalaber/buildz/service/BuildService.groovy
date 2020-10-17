package com.absolutegalaber.buildz.service

import com.absolutegalaber.buildz.domain.*
import com.absolutegalaber.buildz.repository.BuildLabelRepository
import com.absolutegalaber.buildz.repository.BuildRepository
import com.absolutegalaber.buildz.repository.EnvironmentRepository
import org.springframework.data.domain.Page
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import static com.absolutegalaber.buildz.repository.QuerySpecs.*

@Service
@Transactional
class BuildService {
    private final BuildRepository buildRepository
    private final BuildLabelRepository buildLabelRepository
    private final EnvironmentRepository environmentRepository

    BuildService(BuildRepository buildRepository, BuildLabelRepository buildLabelRepository, EnvironmentRepository environmentRepository) {
        this.buildRepository = buildRepository
        this.buildLabelRepository = buildLabelRepository
        this.environmentRepository = environmentRepository
    }

    Optional<Build> byId(Long id) {
        return buildRepository.findById(id)
    }

    Build create(String project, String branch, Long buildNumber) {
        Build build = of(project, branch, buildNumber).orElse(
                new Build(
                        project: project,
                        branch: branch,
                        buildNumber: buildNumber
                )
        )
        return buildRepository.save(build)
    }

    Build addLabels(Long buildId, List<BuildLabel> labels) throws InvalidRequestException {
        Build build = byId(buildId).orElseThrow({ ->
            new InvalidRequestException("No Build found for buildId=" + buildId)
        })
        labels.forEach({ buildLabel ->
            build.getLabels().add(new BuildLabel(
                    key: buildLabel.getKey(),
                    value: buildLabel.getValue(),
                    build: build
            ))
        })
        buildRepository.save(build)
    }

    private Optional<Build> of(String project, String branch, Long buildNumber) {
        buildRepository.findOne(
                buildQuery(project, branch, buildNumber)
        )
    }

    BuildSearchResult search(BuildSearch search) {
        BuildSearchResult.fromPageResult(
                buildRepository.findAll(toSpecification(search), search.page())
        )
    }

    private Specification<Build> toSpecification(BuildSearch search) {
        Specification<Build> theQuerySpecification = allBuilds()
        if (!search.getLabels().isEmpty()) {
            Set<BuildLabel> labelsToSearch = labelsToInclude(search.getLabels());
            if (labelsToSearch.isEmpty()) {
                return noBuilds()
            } else {
                Specification<Build> subSpec = noBuilds()
                labelsToSearch.each {
                    subSpec = subSpec.or(hasLabel(it))
                }
                theQuerySpecification = theQuerySpecification.and(subSpec)
            }
        }

        if (search.project) {
            theQuerySpecification = theQuerySpecification.and(
                    whereProject(search.project)
            )
        }
        if (search.branch) {
            theQuerySpecification = theQuerySpecification.and(
                    whereBranch(search.branch)
            )
        }
        if (search.minBuildNumber) {
            theQuerySpecification = theQuerySpecification.and(
                    whereMinBuildNumber(search.minBuildNumber)
            )
        }
        if (search.maxBuildNumber) {
            theQuerySpecification = theQuerySpecification.and(
                    whereMaxBuildNumber(search.maxBuildNumber)
            )
        }
        theQuerySpecification
    }


    Optional<Build> latestArtifact(Artifact artifact) {
        BuildSearch theSearch = BuildSearch.fromArtifact(artifact)
        Page<Build> searchResult = buildRepository.findAll(toSpecification(theSearch), theSearch.page())
        searchResult.isEmpty() ? Optional.empty() : Optional.of(searchResult.getContent().first())
    }

    private Set<BuildLabel> labelsToInclude(Map<String, String> labels) {
        Set<BuildLabel> toReturn = []
        labels.each {
            toReturn.addAll(
                    buildLabelRepository.findAll(labelsWithKeyAndValue(it.key, it.value))
            )
        }
        toReturn
    }

    EnvironmentBuilds ofEnvironment(String environmentName) throws InvalidRequestException {
        Optional<Environment> environment = environmentRepository.findOne(environmentWithName(environmentName))
        if (environment.isEmpty()) {
            throw new InvalidRequestException("No Environment found with name=" + environmentName)
        }
        EnvironmentBuilds builds = new EnvironmentBuilds();
        environment.get().getArtifacts().each { Artifact theArtifact ->
            latestArtifact(theArtifact)
                    .ifPresent(builds.&add)
        }
        return builds;
    }
}
