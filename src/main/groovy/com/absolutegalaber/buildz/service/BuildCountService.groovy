package com.absolutegalaber.buildz.service

import com.absolutegalaber.buildz.domain.BuildCount
import com.absolutegalaber.buildz.repository.BuildCountRepository
import org.springframework.lang.NonNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import static com.absolutegalaber.buildz.repository.QuerySpecs.buildCountQuery

@Service
@Transactional
class BuildCountService {
    private final BuildCountRepository buildCountRepository

    BuildCountService(BuildCountRepository buildCountRepository) {
        this.buildCountRepository = buildCountRepository
    }

    BuildCount next(String project, String branch) {
        BuildCount theCount = of(project, branch)
                .orElse(
                        new BuildCount(
                                project: project,
                                branch: branch,
                                counter: 0
                        )
                )

        theCount.increment()
        buildCountRepository.save(theCount)
    }

    BuildCount current(String project, String branch) {
        of(project, branch).orElse(
                new BuildCount(
                        project: project,
                        branch: branch,
                        counter: 0
                )
        )
    }

    BuildCount set(String project, String branch, Long buildCount) {
        BuildCount theCount = of(project, branch)
                .orElse(
                        new BuildCount(
                                project: project,
                                branch: branch,
                                counter: 0
                        )
                )
        theCount.setCounter(buildCount)
        buildCountRepository.save(theCount)
    }

    private Optional<BuildCount> of(@NonNull String project, @NonNull String branch) {
        buildCountRepository.findOne(
                buildCountQuery(project, branch)
        )
    }
}
