package com.absolutegalaber.buildz.events

/**
 * An Event Object which contains
 * all the necessary user
 * knowable information to
 * register a Deploy in
 * the Buildz System.
 */
class RegisterDeployEvent {

    // The name of the Server to which this Deploy was deployed
    String serverName

    // Project, branch, and buildNumber will be used to find the Build which was deployed by this Deploy
    String project
    String branch
    Long buildNumber

    // This string:string map will be used to generate the DeployLabels
    Map<String,String> labels = [:]

    String reservedBy;
    String reservationNote;
}
