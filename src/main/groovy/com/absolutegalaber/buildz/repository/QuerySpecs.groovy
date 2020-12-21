package com.absolutegalaber.buildz.repository

import com.absolutegalaber.buildz.domain.*
import org.springframework.data.jpa.domain.Specification
import org.springframework.lang.NonNull

import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Join
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

class QuerySpecs {
    static Specification<BuildCount> buildCountQuery(@NonNull String project, @NonNull String branch) {
        new Specification<BuildCount>() {
            @Override
            Predicate toPredicate(Root<BuildCount> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get(BuildCount_.project), project),
                        criteriaBuilder.equal(root.get(BuildCount_.branch), branch)
                )
            }
        }
    }

    static Specification<Build> buildQuery(String project, String branch, Long buildNumber) {
        new Specification<Build>() {
            @Override
            Predicate toPredicate(Root<Build> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get(Build_.project), project),
                        criteriaBuilder.equal(root.get(Build_.branch), branch),
                        criteriaBuilder.equal(root.get(Build_.buildNumber), buildNumber),
                )
            }
        }
    }

    static Specification<Build> allBuilds() {
        new Specification<Build>() {
            @Override
            Predicate toPredicate(Root<Build> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaBuilder.conjunction()
            }
        }
    }

    static Specification<Build> noBuilds() {
        new Specification<Build>() {
            @Override
            Predicate toPredicate(Root<Build> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaBuilder.disjunction()
            }
        }
    }

    static Specification<Build> whereProject(String project) {
        new Specification<Build>() {
            @Override
            Predicate toPredicate(Root<Build> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaBuilder.equal(root.get(Build_.project), project)
            }
        }
    }

    static Specification<Build> whereBranch(String branch) {
        new Specification<Build>() {
            @Override
            Predicate toPredicate(Root<Build> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaBuilder.equal(root.get(Build_.branch), branch)
            }
        }
    }

    static Specification<Build> whereMinBuildNumber(Long buildNumber) {
        new Specification<Build>() {
            @Override
            Predicate toPredicate(Root<Build> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaBuilder.gt(root.get(Build_.buildNumber), buildNumber)
            }
        }
    }

    static Specification<Build> whereMaxBuildNumber(Long buildNumber) {
        new Specification<Build>() {
            @Override
            Predicate toPredicate(Root<Build> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaBuilder.lt(root.get(Build_.buildNumber), buildNumber)
            }
        }
    }


    static Specification<BuildLabel> labelsWithKeyAndValue(String key, String value) {
        new Specification<BuildLabel>() {
            @Override
            Predicate toPredicate(Root<BuildLabel> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get(BuildLabel_.key), key),
                        criteriaBuilder.equal(root.get(BuildLabel_.value), value)
                )
            }
        }
    }

    static Specification<Build> hasLabel(BuildLabel label) {
        new Specification<Build>() {
            @Override
            Predicate toPredicate(Root<Build> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaBuilder.isMember(label, root.get(Build_.labels))
            }
        }
    }

    static Specification<Environment> environmentWithName(String name) {
        new Specification<Environment>() {
            @Override
            Predicate toPredicate(Root<Environment> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaBuilder.equal(root.get(Environment_.name), name)
            }
        }
    }

    static Specification<Branch> ofProject(Project project, Boolean includeInactive) {
        new Specification<Branch>() {
            @Override
            Predicate toPredicate(Root<Branch> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate ofProject = criteriaBuilder.equal(root.get(Branch_.project), project)
                if (includeInactive) {
                    return ofProject
                }
                criteriaBuilder.and(
                        ofProject,
                        criteriaBuilder.equal(root.get(Branch_.active), true)
                )
            }
        }
    }

    static Specification<Branch> ofProjectWithName(Project project, String branchName) {
        new Specification<Branch>() {
            @Override
            Predicate toPredicate(Root<Branch> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get(Branch_.project), project),
                        criteriaBuilder.equal(root.get(Branch_.name), branchName)
                )
            }
        }
    }

    static Specification<Project> allRelevantProjects(Boolean includeInactive) {
        new Specification<Project>() {
            @Override
            Predicate toPredicate(Root<Project> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if (includeInactive) {
                    return criteriaBuilder.conjunction()
                }
                criteriaBuilder.equal(root.get(Project_.active), true)
            }
        }
    }

    /**
     * Generates a way to search the database for a specific Server based on
     * the name of said Server
     *
     * @param name  the name of the Server
     * @return      the Server with the provided name
     */
    static Specification<Server> serverWithName(String name) {
        new Specification<Server>() {
            @Override
            Predicate toPredicate(Root<Server> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaBuilder.equal(root.get(Server_.name), name)
            }
        }
    }

    /**
     * Generates a way to search the database for a List of Deploys based on the Server to which the Deploys are
     * registered
     *
     * @param serverName    a name of a Server
     * @return              the Deploys deployed on the Server
     */
    // TODO Update to return DeployView
    static Specification<Deploy> deploysOnServer(Server server) {
        new Specification<Deploy>() {
            @Override
            Predicate toPredicate(Root<Deploy> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaBuilder.equal(root.get(Deploy_.server), server)
            }
        }
    }

    /**
     * Generates a way to search the database for a specific Deploy based on
     * the id of said Deploy
     * @param id    the ID of the Deploy
     * @return      the Deploy with the provided ID
     */
    // TODO Update to return DeployView
    static Specification<Deploy> deployWithId(long id) {
        new Specification<Deploy>() {
            @Override
            Predicate toPredicate(Root<Deploy> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaBuilder.equal(root.get(Deploy_.id), id)
            }
        }
    }

    /**
     * Generates a way to search the database for a specific Build based on
     * the project, branch, and build number of that Build
     *
     * @param project       the project of the requested Build
     * @param branch        the branch of the requested Build
     * @param buildNumber   the buildNumber of the requested Build
     * @return              the Build with the provided project, branch, and
     *                      buildNumber
     */
    static Specification<Build> buildWithProjectBranchAndNumber(
            String project,
            String branch,
            Long buildNumber
    ) {
        new Specification<Build>() {
            @Override
            Predicate toPredicate(Root<Build> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get(Build_.project), project),
                        criteriaBuilder.equal(root.get(Build_.branch), branch),
                        criteriaBuilder.equal(root.get(Build_.buildNumber), buildNumber)
                )
            }
        }
    }
}
