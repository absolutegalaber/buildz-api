-- a build count
INSERT INTO build_count (id, branch, counter, project) VALUES (1, 'master', 1, 'buildz-backend');

-- some builds
INSERT INTO build (id, branch, build_number, project) VALUES (1, 'master', 1, 'buildz-backend');
INSERT INTO build (id, branch, build_number, project) VALUES (2, 'master', 2, 'buildz-backend');
INSERT INTO build (id, branch, build_number, project) VALUES (3, 'next', 3, 'buildz-backend');
INSERT INTO build (id, branch, build_number, project) VALUES (4, 'next', 4, 'buildz-backend');

INSERT INTO build (id, branch, build_number, project) VALUES (5, 'master', 1, 'buildz-frontend');
INSERT INTO build (id, branch, build_number, project) VALUES (6, 'master', 2, 'buildz-frontend');
INSERT INTO build (id, branch, build_number, project) VALUES (7, 'next', 1, 'buildz-frontend');
INSERT INTO build (id, branch, build_number, project) VALUES (8, 'master', 4, 'buildz-frontend');
INSERT INTO build (id, branch, build_number, project) VALUES (9, 'master', 5, 'buildz-frontend');

INSERT INTO build (id, branch, build_number, project) VALUES (10, 'master', 10, 'buildz-backoffice');
INSERT INTO build (id, branch, build_number, project) VALUES (11, 'master', 11, 'buildz-backoffice');
INSERT INTO build (id, branch, build_number, project) VALUES (12, 'master', 12, 'buildz-backoffice');
INSERT INTO build (id, branch, build_number, project) VALUES (13, 'master', 13, 'buildz-backoffice');
INSERT INTO build (id, branch, build_number, project) VALUES (14, 'master', 14, 'buildz-backoffice');
INSERT INTO build (id, branch, build_number, project) VALUES (15, 'master', 15, 'buildz-backoffice');
INSERT INTO build (id, branch, build_number, project) VALUES (16, 'next', 1, 'buildz-backoffice');
INSERT INTO build (id, branch, build_number, project) VALUES (17, 'next', 2, 'buildz-backoffice');
INSERT INTO build (id, branch, build_number, project) VALUES (18, 'next', 3, 'buildz-backoffice');
INSERT INTO build (id, branch, build_number, project) VALUES (19, 'next', 4, 'buildz-backoffice');
INSERT INTO build (id, branch, build_number, project) VALUES (20, 'next', 5, 'buildz-backoffice');
INSERT INTO build (id, branch, build_number, project) VALUES (21, 'next', 6, 'buildz-backoffice');

-- some labels
INSERT INTO build_label (id, label_key, label_value, build_id) VALUES (1, 'technical_branch', 'master', 1);
INSERT INTO build_label (id, label_key, label_value, build_id) VALUES (2, 'git_revision', '19cfc933d6f276ced6ea6e5903e0bd2c28613def', 1);

INSERT INTO build_label (id, label_key, label_value, build_id) VALUES (3, 'technical_branch', 'master', 2);
INSERT INTO build_label (id, label_key, label_value, build_id) VALUES (4, 'git_revision', '29cfc933d6f276ced6ea6e5903e0bd2c28613def', 2);

INSERT INTO build_label (id, label_key, label_value, build_id) VALUES (5, 'technical_branch', 'feature/some-feature', 3);
INSERT INTO build_label (id, label_key, label_value, build_id) VALUES (6, 'git_revision', '39cfc933d6f276ced6ea6e5903e0bd2c28613def', 3);

INSERT INTO build_label (id, label_key, label_value, build_id) VALUES (7, 'technical_branch', 'feature/some-other-feature', 4);
INSERT INTO build_label (id, label_key, label_value, build_id) VALUES (8, 'git_revision', '49cfc933d6f276ced6ea6e5903e0bd2c28613def', 4);

INSERT INTO build_label (id, label_key, label_value, build_id) VALUES (9, 'technical_branch', 'feature/some-other-feature', 5);
INSERT INTO build_label (id, label_key, label_value, build_id) VALUES (10, 'git_revision', '59cfc933d6f276ced6ea6e5903e0bd2c28613def', 5);

-- environments
INSERT INTO environment (id, name) VALUES (1, 'master-test-stage-1');

INSERT INTO environment (id, name) VALUES (2, 'feature-test-stage-1');
INSERT INTO artifact (id, branch, project, environment_id) VALUES (1, NULL, 'buildz-backend', 2);
INSERT INTO artifact_labels (artifact_id, labels, labels_key) VALUES (1, 'feature/some-other-feature', 'technical_branch');

INSERT INTO artifact (id, branch, project, environment_id) VALUES (2, NULL, 'buildz-frontend', 2);
INSERT INTO artifact_labels (artifact_id, labels, labels_key) VALUES (2, 'feature/some-other-feature', 'technical_branch');
