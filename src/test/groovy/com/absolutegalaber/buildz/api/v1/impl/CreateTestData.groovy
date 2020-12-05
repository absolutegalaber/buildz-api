package com.absolutegalaber.buildz.api.v1.impl

import com.absolutegalaber.buildz.api.BaseRestSpec
import com.absolutegalaber.buildz.domain.Build
import com.absolutegalaber.buildz.domain.BuildLabel
import org.springframework.http.ResponseEntity
import spock.lang.Ignore

@Ignore
class CreateTestData extends BaseRestSpec {

    def "Create"() {
        given:
        String[] projects = ['backend', 'frontend', 'backoffice']
        String[] branches = ['main', 'next', 'feature']
        String CREATE_BUILD_URL = "http://localhost:${port}/api/v1/builds/create"
        String ADD_LABEL_URL = "http://localhost:${port}/api/v1/builds/add-labels"
        List<Long> buildIds = []

        when:
        projects.each { String project ->
            branches.each { String branch ->
                String branchName = 'feature'.equals(branch) ? "feature-${project}" : branch
                10.times { Integer i ->
                    Build setBuildCount = new Build(
                            project: project,
                            branch: branchName,
                            buildNumber: i + 1
                    )
                    ResponseEntity<Build> created = restTemplate.postForEntity(CREATE_BUILD_URL, setBuildCount, Build)
                    buildIds.add(created.getBody().id)
                }
            }
        }
        buildIds.each { Long buildId ->
            String uuid = UUID.randomUUID().toString()
            String rev = uuid.digest('SHA-256')
            List<BuildLabel> newLabels = [
                    new BuildLabel(key: 'jenkins-build', value: "http://my-jenkins/${uuid}"),
                    new BuildLabel(key: 'revision', value: rev),
                    new BuildLabel(key: 'integration-test', value: buildId % 2 == 0 ? 'broken' : 'ok')
            ]
            restTemplate.postForEntity("${ADD_LABEL_URL}/${buildId}", newLabels, Build)
        }

        then:
        notThrown(Exception)
    }

}
