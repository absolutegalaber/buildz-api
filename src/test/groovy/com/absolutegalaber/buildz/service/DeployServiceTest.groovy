package com.absolutegalaber.buildz.service

import com.absolutegalaber.buildz.BaseBuildzSpec
import com.absolutegalaber.buildz.domain.Deploy
import com.absolutegalaber.buildz.domain.DeployLabel
import com.absolutegalaber.buildz.domain.Server
import com.absolutegalaber.buildz.domain.exception.DataNotFoundException
import com.absolutegalaber.buildz.domain.exception.InvalidRequestException
import com.absolutegalaber.buildz.events.RegisterDeployEvent
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Subject
import spock.lang.Unroll

class DeployServiceTest extends BaseBuildzSpec {
    @Subject
    @Autowired
    DeployService deployService

    @Subject
    @Autowired
    ServerService serverService

    @Unroll('#message')
    def 'Successful byServer calls'() {
        when:
        List<Deploy> deploys = deployService.byServer(serverName)

        then:
        deploys.size() == size

        where:
        serverName      | size | message
        'Empty Server'  | 0    | 'Fetching all deploys from server with no deploys'
        'Test Server 1' | 2    | 'Fetching all deploys from server with deploys'
    }

    @Unroll('#message')
    def 'Failing byServer calls'() {
        when:
        deployService.byServer(serverName)

        then:
        thrown(exception)

        where:
        serverName     | exception               | message
        'Not A Server' | InvalidRequestException | 'Fetching all deploys from a non-existent server'
    }

    @Unroll('#message')
    def 'ById'() {
        when:
        boolean found
        try {
            deployService.byId(id)
            found = true

        } catch (DataNotFoundException ignored) {
            found = false
        }
        then:
        found == expected

        where:
        id  | expected | message
        -1L | false    | 'ById did not find non-existent Deploy'
        1L  | true     | 'ById found existing Deploy'
    }

    @Unroll('#message')
    def "Valid register calls"() {
        when:
        Deploy newDeploy = deployService.register(new RegisterDeployEvent(
                serverName: serverName,
                project: project,
                branch: branch,
                buildNumber: buildNumber,
                labels: labels
        ))
        deployService.byId(newDeploy.getId())

        then:
        notThrown(DataNotFoundException)

        where:
        serverName      | project   | branch | buildNumber | labels                               | message
        'Test Server 1' | 'backend' | 'main' | 1           | null                                 | 'Register a valid Deploy with no labels'
        'Test Server 1' | 'backend' | 'main' | 1           | ['key': 'value']                     | 'Register a valid Deploy with one label'
        'Test Server 1' | 'backend' | 'main' | 1           | ['key1': 'value1', 'key2': 'value2'] | 'Register a valid Deploy with two valid labels'
        'Test Server 1' | 'backend' | 'main' | 1           | ['key': 'value', 'key': 'value']     | 'Register a valid Deploy with duplicate labels'
        'New Server'    | 'backend' | 'main' | 1           | null                                 | 'Register a valid Deploy to a new Server with no labels'
        'New Server'    | 'backend' | 'main' | 1           | ['key': 'value']                     | 'Register a valid Deploy to a new Server with one label'
        'New Server'    | 'backend' | 'main' | 1           | ['key1': 'value1', 'key2': 'value2'] | 'Register a valid Deploy to a new Server with two valid labels'
        'New Server'    | 'backend' | 'main' | 1           | ['key': 'value', 'key': 'value']     | 'Register a valid Deploy to a new Server with duplicate labels'
    }

    @Unroll('#message')
    def "Invalid register calls"() {
        when:
        deployService.register(new RegisterDeployEvent(
                serverName: serverName,
                project: project,
                branch: branch,
                buildNumber: buildNumber,
                labels: labels
        ))

        then:
        thrown(InvalidRequestException)

        where:
        serverName | project   | branch | buildNumber | labels | message
        null       | 'backend' | 'main' | 1           | null   | 'Failed to register a Deploy without a Server Name'
        ''         | 'backend' | 'main' | 1           | null   | 'Failed to register a Deploy with an empty Server Name'
        ' '        | 'backend' | 'main' | 1           | null   | 'Failed to register a Deploy with an invalid Server Name'
    }

    @Unroll('#message')
    def "Register calls with missing Build"() {
        when:
        deployService.register(new RegisterDeployEvent(
                serverName: serverName,
                project: project,
                branch: branch,
                buildNumber: buildNumber,
                labels: labels
        ))

        then:
        thrown(DataNotFoundException)

        where:
        serverName      | project   | branch | buildNumber | labels | message
        'Test Server 1' | 'invalid' | 'main' | 1           | null   | 'Failed to register a Deploy with invalid project'
        'Test Server 1' | 'backend' | 'nope' | 1           | null   | 'Failed to register a Deploy with invalid branch'
        'Test Server 1' | 'backend' | 'main' | -1          | null   | 'Failed to register a Deploy with invalid buildNumber'
    }

    @Unroll('#message')
    def "Add labels to a Deploy"() {
        when:
        Deploy newDeploy = deployService.register(new RegisterDeployEvent(
                serverName: 'Test Server 1',
                project: 'backend',
                branch: 'main',
                buildNumber: 1,
                labels: existingLabels
        ))
        deployService.addLabels(newDeploy.getId(), newLabels)

        then:
        // First fetch the Deploy from the DB to ensure that the Deploy in the DB has been updated.
        def foundCount = deployService.byId(newDeploy.getId()).getLabels().stream().filter({
                // Then filter out the DeployLabels by checking if the key plus its value exists in either the existing or
                // newLabels Maps.
            DeployLabel label ->
                (existingLabels != null && existingLabels[label.getKey()] == label.getValue()) ||
                        (newLabels != null && newLabels[label.getKey()] == label.getValue())

        }).count()

        def existingCount = existingLabels != null ? existingLabels.size() : 0
        def newCount = newLabels != null ? newLabels.size() : 0

        assert foundCount == (existingCount + newCount)


        where:
        existingLabels                                   | newLabels                                        | message
        null                                             | null                                             | 'Was able to add no new labels to an existing deploy with no labels'
        null                                             | [] as TreeMap                                    | 'Was able to add empty labels to an existing deploy with no labels'
        null                                             | ['newKey': 'newValue']                           | 'Was able to add a single label to an existing deploy with no labels'
        null                                             | ['newKey1': 'newValue1', 'newKey2': 'newValue2'] | 'Was able to add multiple labels to an existing deploy with no labels'
        [] as TreeMap                                    | null                                             | 'Was able to add no new labels to an existing deploy with empty labels'
        [] as TreeMap                                    | [] as TreeMap                                    | 'Was able to add empty labels to an existing deploy with empty labels'
        [] as TreeMap                                    | ['newKey': 'newValue']                           | 'Was able to add a single label to an existing deploy with empty labels'
        [] as TreeMap                                    | ['newKey1': 'newValue1', 'newKey2': 'newValue2'] | 'Was able to add multiple labels to an existing deploy with empty labels'
        ['oldKey': 'oldValue']                           | null                                             | 'Was able to add no new labels to an existing deploy with one label'
        ['oldKey': 'oldValue']                           | [] as TreeMap                                    | 'Was able to add empty labels to an existing deploy with one labels'
        ['oldKey': 'oldValue']                           | ['newKey': 'newValue']                           | 'Was able to add a single label to an existing deploy with one label'
        ['oldKey': 'oldValue']                           | ['newKey1': 'newValue1', 'newKey2': 'newValue2'] | 'Was able to add multiple labels to an existing deploy with one label'
        ['oldKey1': 'oldValue1', 'oldKey2': 'oldValue2'] | null                                             | 'Was able to add no new labels to an existing deploy with multiple labels'
        ['oldKey1': 'oldValue1', 'oldKey2': 'oldValue2'] | [] as TreeMap                                    | 'Was able to add empty labels to an existing deploy with multiple labels'
        ['oldKey1': 'oldValue1', 'oldKey2': 'oldValue2'] | ['newKey': 'newValue']                           | 'Was able to add a single label to an existing deploy with multiple labels'
        ['oldKey1': 'oldValue1', 'oldKey2': 'oldValue2'] | ['newKey1': 'newValue1', 'newKey2': 'newValue2'] | 'Was able to add multiple labels to an existing deploy with multiple labels'
        // TODO expand test to handle overriding labels
    }

    @Unroll('#message')
    def 'Valid Server reservation via Deploy tests'() {
        when:
        def serverName = 'Test Server 1'
        deployService.register(new RegisterDeployEvent(
                serverName: serverName,
                project: 'backend',
                branch: 'main',
                buildNumber: 1,
                reservedBy: by,
                reservationNote: note
        ))
        Server server = serverService.byName(serverName).get()

        then:
        // First check to see if the reservation is expected to exist
        def reservation = server.getReservation()
        def valid = (reservation != null) == expected
        // Now check if the reservation was found...
        if (reservation != null) {
            // ... and if so make sure it matches the provided by and notes params
            valid = valid && reservation.getBy() == by && reservation.getNote() == note
        }

        valid

        where:
        by       | note   | expected | message
        'Person' | null   | true     | 'Reserved a Server by "Person" without a note'
        'Person' | 'note' | true     | 'Reserved a Server by "Person" with a note'
        null     | null   | false    | 'Failed to create Reservation because the "by" variable is null (without note)'
        ''       | null   | false    | 'Failed to create Reservation because the "by" variable is not set (without note)'
        ' '      | null   | false    | 'Failed to create Reservation because the "by" variable is empty (without note)'
        null     | ''     | false    | 'Failed to create Reservation because the "by" variable is null (with a not set note)'
        ''       | ''     | false    | 'Failed to create Reservation because the "by" variable is not set (with a not set note)'
        ' '      | ''     | false    | 'Failed to create Reservation because the "by" variable is empty (with a not set note)'
        null     | ' '    | false    | 'Failed to create Reservation because the "by" variable is null (with empty note)'
        ''       | ' '    | false    | 'Failed to create Reservation because the "by" variable is not set (with an empty note)'
        ' '      | ' '    | false    | 'Failed to create Reservation because the "by" variable is empty (with an empty note)'
    }
}
