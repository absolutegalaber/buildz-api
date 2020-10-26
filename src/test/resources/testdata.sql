delete
from artifact_labels;
delete
from artifact;
delete
from environment;
delete
from build_count;
delete
from build_label;
delete
from build;


-- a build count
INSERT INTO build_count (id, branch, counter, project)
VALUES (1, 'main', 10, 'backend'),
       (2, 'next', 10, 'backend'),
       (3, 'feature/awesome-feature', 10, 'backend'),
       (4, 'main', 10, 'frontend'),
       (5, 'next', 10, 'frontend'),
       (6, 'feature/awesome-feature', 10, 'frontend'),
       (7, 'main', 10, 'backoffice'),
       (8, 'next', 10, 'backoffice'),
       (9, 'feature/awesome-feature', 10, 'backoffice');

INSERT INTO build (`id`, `branch`, `build_number`, `project`)
VALUES (21, 'feature/awesome-feature', 1, 'backend'),
       (22, 'feature/awesome-feature', 2, 'backend'),
       (23, 'feature/awesome-feature', 3, 'backend'),
       (24, 'feature/awesome-feature', 4, 'backend'),
       (25, 'feature/awesome-feature', 5, 'backend'),
       (26, 'feature/awesome-feature', 6, 'backend'),
       (27, 'feature/awesome-feature', 7, 'backend'),
       (28, 'feature/awesome-feature', 8, 'backend'),
       (29, 'feature/awesome-feature', 9, 'backend'),
       (30, 'feature/awesome-feature', 10, 'backend'),
       (1, 'main', 1, 'backend'),
       (2, 'main', 2, 'backend'),
       (3, 'main', 3, 'backend'),
       (4, 'main', 4, 'backend'),
       (5, 'main', 5, 'backend'),
       (6, 'main', 6, 'backend'),
       (7, 'main', 7, 'backend'),
       (8, 'main', 8, 'backend'),
       (9, 'main', 9, 'backend'),
       (10, 'main', 10, 'backend'),
       (11, 'next', 1, 'backend'),
       (12, 'next', 2, 'backend'),
       (13, 'next', 3, 'backend'),
       (14, 'next', 4, 'backend'),
       (15, 'next', 5, 'backend'),
       (16, 'next', 6, 'backend'),
       (17, 'next', 7, 'backend'),
       (18, 'next', 8, 'backend'),
       (19, 'next', 9, 'backend'),
       (20, 'next', 10, 'backend'),
       (81, 'feature/awesome-feature', 1, 'backoffice'),
       (82, 'feature/awesome-feature', 2, 'backoffice'),
       (83, 'feature/awesome-feature', 3, 'backoffice'),
       (84, 'feature/awesome-feature', 4, 'backoffice'),
       (85, 'feature/awesome-feature', 5, 'backoffice'),
       (86, 'feature/awesome-feature', 6, 'backoffice'),
       (87, 'feature/awesome-feature', 7, 'backoffice'),
       (88, 'feature/awesome-feature', 8, 'backoffice'),
       (89, 'feature/awesome-feature', 9, 'backoffice'),
       (90, 'feature/awesome-feature', 10, 'backoffice'),
       (61, 'main', 1, 'backoffice'),
       (62, 'main', 2, 'backoffice'),
       (63, 'main', 3, 'backoffice'),
       (64, 'main', 4, 'backoffice'),
       (65, 'main', 5, 'backoffice'),
       (66, 'main', 6, 'backoffice'),
       (67, 'main', 7, 'backoffice'),
       (68, 'main', 8, 'backoffice'),
       (69, 'main', 9, 'backoffice'),
       (70, 'main', 10, 'backoffice'),
       (71, 'next', 1, 'backoffice'),
       (72, 'next', 2, 'backoffice'),
       (73, 'next', 3, 'backoffice'),
       (74, 'next', 4, 'backoffice'),
       (75, 'next', 5, 'backoffice'),
       (76, 'next', 6, 'backoffice'),
       (77, 'next', 7, 'backoffice'),
       (78, 'next', 8, 'backoffice'),
       (79, 'next', 9, 'backoffice'),
       (80, 'next', 10, 'backoffice'),
       (51, 'feature/awesome-feature', 1, 'frontend'),
       (52, 'feature/awesome-feature', 2, 'frontend'),
       (53, 'feature/awesome-feature', 3, 'frontend'),
       (54, 'feature/awesome-feature', 4, 'frontend'),
       (55, 'feature/awesome-feature', 5, 'frontend'),
       (56, 'feature/awesome-feature', 6, 'frontend'),
       (57, 'feature/awesome-feature', 7, 'frontend'),
       (58, 'feature/awesome-feature', 8, 'frontend'),
       (59, 'feature/awesome-feature', 9, 'frontend'),
       (60, 'feature/awesome-feature', 10, 'frontend'),
       (31, 'main', 1, 'frontend'),
       (32, 'main', 2, 'frontend'),
       (33, 'main', 3, 'frontend'),
       (34, 'main', 4, 'frontend'),
       (35, 'main', 5, 'frontend'),
       (36, 'main', 6, 'frontend'),
       (37, 'main', 7, 'frontend'),
       (38, 'main', 8, 'frontend'),
       (39, 'main', 9, 'frontend'),
       (40, 'main', 10, 'frontend'),
       (41, 'next', 1, 'frontend'),
       (42, 'next', 2, 'frontend'),
       (43, 'next', 3, 'frontend'),
       (44, 'next', 4, 'frontend'),
       (45, 'next', 5, 'frontend'),
       (46, 'next', 6, 'frontend'),
       (47, 'next', 7, 'frontend'),
       (48, 'next', 8, 'frontend'),
       (49, 'next', 9, 'frontend'),
       (50, 'next', 10, 'frontend');

INSERT INTO build_label (`id`, `label_key`, `label_value`, `build_id`)
VALUES (1, 'integration-test', 'ok', 1),
       (2, 'jenkins-build', 'http://my-jenkins/f18478bf-02b2-4165-8952-27f56329f546', 1),
       (3, 'revision', 'a5417fb77afc48ff419894f5ea2dddec458661538c3bc110bf052149e1b858e4', 1),
       (4, 'jenkins-build', 'http://my-jenkins/2dd1103a-a5f0-4842-9a47-7caa9212ca51', 2),
       (5, 'revision', 'a9713f317f788f1a6507f70c91c019553b91db5b72f5da70c75805c4396d69ef', 2),
       (6, 'integration-test', 'broken', 2),
       (7, 'jenkins-build', 'http://my-jenkins/2cebb5ab-587d-4910-87fe-d80a1a895686', 3),
       (8, 'integration-test', 'ok', 3),
       (9, 'revision', 'd8610e8fc054abb17493937e4a218df17810d939870784b6fb44f1a24519ba68', 3),
       (10, 'revision', '96a448d105d896f743c7aaeb69828fe78ff9af12e4b0c5664f2299211da5e542', 4),
       (11, 'integration-test', 'broken', 4),
       (12, 'jenkins-build', 'http://my-jenkins/48f12aa5-2922-4282-b7fe-368b457daa22', 4),
       (13, 'jenkins-build', 'http://my-jenkins/47ee11d9-28bb-44ea-9020-c7a2547b8689', 5),
       (14, 'integration-test', 'ok', 5),
       (15, 'revision', '03bc2dee1b6c422f45659dde9b05d279058495af86ccccb73b48591b7dd7f255', 5),
       (16, 'revision', 'b5a7b57421d50733d2c72b9d9ee7b566c195acb3f8e90760f73757503390eb16', 6),
       (17, 'jenkins-build', 'http://my-jenkins/409215bf-abb2-4307-ac7c-c61940312cd9', 6),
       (18, 'integration-test', 'broken', 6),
       (19, 'integration-test', 'ok', 7),
       (20, 'jenkins-build', 'http://my-jenkins/77f2500b-3f9b-4eec-acd1-181656dd438d', 7),
       (21, 'revision', '112ec6833ff8a0759e8e86af05cd4ed7686cfeb892639d6211ad21b8ead02299', 7),
       (22, 'revision', 'a01d98194c3e9c97e5301104c4fd2b511b8ed6676eb8b54015f638bf54d5ac13', 8),
       (23, 'integration-test', 'broken', 8),
       (24, 'jenkins-build', 'http://my-jenkins/8524e713-aa10-4a77-91fd-ca905e99eab3', 8),
       (25, 'revision', 'b49be719e4a0b4fbdbd3b1323c784f8a84146bae22c47ba0e9e62e7f6a4bff0c', 9),
       (26, 'integration-test', 'ok', 9),
       (27, 'jenkins-build', 'http://my-jenkins/93bd347d-3e26-4b58-a7f0-6c8f8f76e2f2', 9),
       (28, 'revision', '6540dbced436c58f38888e2db4307c5f3aa49ae86c5e103cff0cbe256c38ef27', 10),
       (29, 'integration-test', 'broken', 10),
       (30, 'jenkins-build', 'http://my-jenkins/2f74e9fd-90ea-4908-bf1f-de19fb8cd092', 10),
       (31, 'integration-test', 'ok', 11),
       (32, 'revision', '988ff20871f02e725a10378a3be71249eb18d95fec200b875ef535a95fdabdf1', 11),
       (33, 'jenkins-build', 'http://my-jenkins/0ef8be9c-6d57-412b-a891-2d1f9dee609e', 11),
       (34, 'jenkins-build', 'http://my-jenkins/1eb43ba0-3791-4163-b72c-a34121c1f029', 12),
       (35, 'revision', '8e395315992ca741b6009f7e9d6d6f8537b0031f75a2e8707e18ecb05bcca3aa', 12),
       (36, 'integration-test', 'broken', 12),
       (37, 'integration-test', 'ok', 13),
       (38, 'revision', 'ef1a85672d3c6eebff69b811c975c8372e33d3736398e528786d7f385e2bf120', 13),
       (39, 'jenkins-build', 'http://my-jenkins/a5beb4c5-f07a-42e2-8ae4-e3eff22d5f6a', 13),
       (40, 'jenkins-build', 'http://my-jenkins/509edda0-9c68-4904-8722-99b34ffc81b5', 14),
       (41, 'revision', '828013d41f4434d3bcef47a17cb5f915f4cbbf3f30f6cd95f102d2bc8a6b26f0', 14),
       (42, 'integration-test', 'broken', 14),
       (43, 'integration-test', 'ok', 15),
       (44, 'jenkins-build', 'http://my-jenkins/effb3020-21e1-4a04-9a4b-d5e1a494117e', 15),
       (45, 'revision', 'ffa15ce192621aa17a3559dea621ac9c5ba16a44650ccb87a2a8bccd3ab9987d', 15),
       (46, 'jenkins-build', 'http://my-jenkins/d6e904e8-5aea-4c93-a767-47dbbda8bd7a', 16),
       (47, 'revision', '39ddcd563129f7ebb4f93166355007aa79567bbe220c820181a508b63d1ce205', 16),
       (48, 'integration-test', 'broken', 16),
       (49, 'integration-test', 'ok', 17),
       (50, 'jenkins-build', 'http://my-jenkins/0d21beb5-bd87-4fab-bedf-f195d50637bc', 17),
       (51, 'revision', '52be25217ccd67ec112a490e82916bcf0dacb3a7d2ac267951efe95d3cd44919', 17),
       (52, 'jenkins-build', 'http://my-jenkins/27dce026-fc12-452b-b723-db44b452563c', 18),
       (53, 'revision', '12802d914f6fe68165d15a9959cece415772c1a1ab470ef3569ece955dfa8fe9', 18),
       (54, 'integration-test', 'broken', 18),
       (55, 'integration-test', 'ok', 19),
       (56, 'jenkins-build', 'http://my-jenkins/6676fbb5-11a7-407a-8165-f7e6de156de4', 19),
       (57, 'revision', 'dee3fe41cb9b50f81a014a6ce0060169e4a3a3405026b170c295d2e0b863a020', 19),
       (58, 'revision', '720ddd458eb8a53903e9a8e0077c0d92659ee1c66d87ae4a1d95097a3c88e5ac', 20),
       (59, 'jenkins-build', 'http://my-jenkins/07f458cb-47cb-4309-8203-587b06ac74f3', 20),
       (60, 'integration-test', 'broken', 20),
       (61, 'jenkins-build', 'http://my-jenkins/510bc089-b985-4a2b-b0a4-8e8cd2757b43', 21),
       (62, 'revision', '0df2a7252158c1e209fd24cdbd379494dfc9dacbab54fd62ece41137d1e69d3b', 21),
       (63, 'integration-test', 'ok', 21),
       (64, 'jenkins-build', 'http://my-jenkins/54dcc4d3-60aa-4471-b90b-889c66f42fda', 22),
       (65, 'revision', '5140c8ce7ed986163cb0b74773a0d9b3bc6a4436ae71a6e9dd718d70c89ec33e', 22),
       (66, 'integration-test', 'broken', 22),
       (67, 'jenkins-build', 'http://my-jenkins/9b94da00-7152-4c9f-966b-709c0e8e19e1', 23),
       (68, 'revision', 'a9d4e4c02714d34e047fa01be9cd9ecd6978c29bacd6c3c8bfbd332475834138', 23),
       (69, 'integration-test', 'ok', 23),
       (70, 'jenkins-build', 'http://my-jenkins/66a67f53-a6eb-473a-9563-d06a92aaa080', 24),
       (71, 'integration-test', 'broken', 24),
       (72, 'revision', 'fc91f727b7320f7258e8f520b5b605228c49a479e7dded39f0d6347df35b9f8f', 24),
       (73, 'integration-test', 'ok', 25),
       (74, 'jenkins-build', 'http://my-jenkins/92313c60-739e-4045-9625-29eef1439f24', 25),
       (75, 'revision', '02834f3e6567351a56de6f5147723e626aa86c96a24870d9fd997217a061bb6b', 25),
       (76, 'revision', '06e9cf45865eb6704d34e8013d8cd0231e94bf9012beff9457103e06acf5bcd0', 26),
       (77, 'jenkins-build', 'http://my-jenkins/ae1a23a5-f23f-4d48-814f-2ab492418297', 26),
       (78, 'integration-test', 'broken', 26),
       (79, 'integration-test', 'ok', 27),
       (80, 'jenkins-build', 'http://my-jenkins/61826320-7b58-4cd0-b7e0-af9d343a0dbf', 27),
       (81, 'revision', 'bad6c7ac67e71df281482c0168ad6680c9b89bb7bf912e9d78f96049e2ede220', 27),
       (82, 'revision', '55c8f38a3157abdaf5e714972ee99c5cb88e7ea34fd643f5167cf94d57a7e80d', 28),
       (83, 'jenkins-build', 'http://my-jenkins/d470487b-8131-4c87-bdb8-cbe0fb1e175f', 28),
       (84, 'integration-test', 'broken', 28),
       (85, 'integration-test', 'ok', 29),
       (86, 'jenkins-build', 'http://my-jenkins/0f4c3be1-6516-4405-97f1-8066729d9cc0', 29),
       (87, 'revision', '6e3099f91d0fdd5490a01c9d941ba57a77bd68bbd8b85ac50511bd953590d410', 29),
       (88, 'revision', 'a0c606ef13a45f44ec1aaddee5c89981bb279994af83af3285f1d9d480339e6c', 30),
       (89, 'jenkins-build', 'http://my-jenkins/72474512-6460-41c7-86a7-76004130d288', 30),
       (90, 'integration-test', 'broken', 30),
       (91, 'integration-test', 'ok', 31),
       (92, 'revision', 'e7a35e03937c90c5ed601566245ba039f636df0ea27d1ea0294ed70ebf678172', 31),
       (93, 'jenkins-build', 'http://my-jenkins/87263ea3-ffdc-4fb8-8a05-70348278a7ca', 31),
       (94, 'jenkins-build', 'http://my-jenkins/da3695cd-a9a6-4675-b8c1-da776ca10be6', 32),
       (95, 'integration-test', 'broken', 32),
       (96, 'revision', 'bf69750263b3de4d3a0d10dfa4e734199297050bf8ea19aba887728478a98345', 32),
       (97, 'integration-test', 'ok', 33),
       (98, 'revision', '9d9d872fd9af33e17a6f2bc3a91c8df21388cfabfffd51afae0e4c8a4a8df0c0', 33),
       (99, 'jenkins-build', 'http://my-jenkins/4d771844-e5af-4135-85ef-d2cb4e84df0f', 33),
       (100, 'jenkins-build', 'http://my-jenkins/b80ef825-704e-4c22-a245-6a6a43cdf5d0', 34),
       (101, 'integration-test', 'broken', 34),
       (102, 'revision', '5ca2b85f04735977432ec13fea0190c44edaa4004bb0d08f43390603034ac927', 34),
       (103, 'integration-test', 'ok', 35),
       (104, 'revision', 'b24c336b359371cf26ce9eec4f94d348afe45427472e7c23306d2c30d58a5e1e', 35),
       (105, 'jenkins-build', 'http://my-jenkins/176871e1-e791-4aff-b9c2-5668b9c6bb8d', 35),
       (106, 'revision', '01b3b9dfe96a96369c1faa1b76207685d2ad28374e8009a7c68e05609a3484ee', 36),
       (107, 'integration-test', 'broken', 36),
       (108, 'jenkins-build', 'http://my-jenkins/5f8d45ec-eb5e-40c7-8319-5953845e2987', 36),
       (109, 'integration-test', 'ok', 37),
       (110, 'revision', '18f4e00fae5d92991217fe985307b0f384831849305b4bb3638d8d9e99a2a22a', 37),
       (111, 'jenkins-build', 'http://my-jenkins/670a9dbd-f6fd-4951-907c-3fa028318185', 37),
       (112, 'jenkins-build', 'http://my-jenkins/f2b3f2d7-7ac1-47bf-9c23-8c463a0760ef', 38),
       (113, 'revision', '1fd12800caa7d54efe3614aa314e77843561f197b341b4943fd7684b83a20bd8', 38),
       (114, 'integration-test', 'broken', 38),
       (115, 'integration-test', 'ok', 39),
       (116, 'jenkins-build', 'http://my-jenkins/b6b87539-b171-4765-b9b9-5fbb87a0a7bd', 39),
       (117, 'revision', '18d18600eab260e2e56880a6f984d45750774b7ac8ea0c4b97df3714cdcc080e', 39),
       (118, 'revision', '7e753896248874a0a44fea434a4343763fda825d87de26d1823017800ed78385', 40),
       (119, 'jenkins-build', 'http://my-jenkins/cbcfae60-c565-4c1d-bebe-53c4d718bbc6', 40),
       (120, 'integration-test', 'broken', 40),
       (121, 'revision', 'e248fc2461d4dd1f2db80ee93fb326502acf4dd69100a1d69e27e821bd3e4e28', 41),
       (122, 'integration-test', 'ok', 41),
       (123, 'jenkins-build', 'http://my-jenkins/27cd94f4-42e1-4f98-843d-7966c530fab2', 41),
       (124, 'revision', 'fa85619bc77c4df2c01c99434c91687793cb4a98bc0b5a154574be2e07e2dcc3', 42),
       (125, 'jenkins-build', 'http://my-jenkins/9dfd54f9-955c-4299-9cbe-c97290ffdda1', 42),
       (126, 'integration-test', 'broken', 42),
       (127, 'jenkins-build', 'http://my-jenkins/e094cbcf-c39a-4479-9fb8-4287a370fe01', 43),
       (128, 'integration-test', 'ok', 43),
       (129, 'revision', '5eae6560e8dbb8bca4133fce543283c916a7d322162dfea04b4d095985ae74b4', 43),
       (130, 'jenkins-build', 'http://my-jenkins/2573c8f3-dd9a-4d25-ae80-a7c44e5b1090', 44),
       (131, 'revision', '1c9e3b7c1b0ff4c3fb4791a909468b0881c81812812eabc2571a87578f37bb23', 44),
       (132, 'integration-test', 'broken', 44),
       (133, 'revision', 'dee9c1e9ac30ee2599f6a05bdac072a13f601c997de79c9e1bf657ebb5ec3ee0', 45),
       (134, 'integration-test', 'ok', 45),
       (135, 'jenkins-build', 'http://my-jenkins/285c6ee8-0d1a-4c2c-b5b5-886b0c714d4c', 45),
       (136, 'revision', 'b5daa0961bd99601018816f22a5a8cf2d05e201e3cf86d29df769752541a111f', 46),
       (137, 'jenkins-build', 'http://my-jenkins/7284a7ea-6f90-4fc2-8b14-dfe05aba7eba', 46),
       (138, 'integration-test', 'broken', 46),
       (139, 'integration-test', 'ok', 47),
       (140, 'jenkins-build', 'http://my-jenkins/acc82535-6516-44c0-a3f0-2e3faed5fe2d', 47),
       (141, 'revision', '2aa0615b5b064ab045d55aa2a98adbc68780f8d28a6aed4039b8395a836621dc', 47),
       (142, 'revision', '0832955a37de62694aaf9509ef903de8b3f8dad29f7edf2f5e0af1b3861dcaa7', 48),
       (143, 'jenkins-build', 'http://my-jenkins/f86a1397-61ca-418a-8f70-d61ada495a50', 48),
       (144, 'integration-test', 'broken', 48),
       (145, 'jenkins-build', 'http://my-jenkins/939172e8-c0df-4af2-b9b1-10ed9c19b294', 49),
       (146, 'integration-test', 'ok', 49),
       (147, 'revision', 'c4e58ee01240cef86143869ed862a6eb054f630af118a4992110b17f440c1eb9', 49),
       (148, 'revision', 'f4e97174015f292c362a732273e2647a2161a08bfaebf124a13d4d0a3c41a438', 50),
       (149, 'jenkins-build', 'http://my-jenkins/0b2310a2-34d7-45e7-af4e-59f20adb947d', 50),
       (150, 'integration-test', 'broken', 50),
       (151, 'jenkins-build', 'http://my-jenkins/3ececa69-43cb-42b4-8f38-eed1ff15c2cc', 51),
       (152, 'integration-test', 'ok', 51),
       (153, 'revision', 'f01ea73de2a6a29c648248de892cd187c37066a56c7ce10174dd61bcab7c0426', 51),
       (154, 'jenkins-build', 'http://my-jenkins/b2f28788-d46e-4475-916c-cf02df923cf8', 52),
       (155, 'revision', '6909d5c6fc6de352ea2c38711065477a3b8da32aff5884456bb9f43c3b797c70', 52),
       (156, 'integration-test', 'broken', 52),
       (157, 'integration-test', 'ok', 53),
       (158, 'jenkins-build', 'http://my-jenkins/fabb6877-9ca1-4f11-81ae-ef4bd88a0af8', 53),
       (159, 'revision', '3ca96dad8ba2dcdd84d8d1a4a14a28aa80ad60e3cda51f2b1d83a702602c8cde', 53),
       (160, 'jenkins-build', 'http://my-jenkins/43938d7e-7f12-4e29-a937-390452928151', 54),
       (161, 'revision', 'a356e1e27a5a27f71abba28f29f444f742eb8c27adef25cb86e8fd8433339cdb', 54),
       (162, 'integration-test', 'broken', 54),
       (163, 'integration-test', 'ok', 55),
       (164, 'jenkins-build', 'http://my-jenkins/15936fb4-46f1-4a39-bc66-0b48e182fa8a', 55),
       (165, 'revision', '1f99a217c291792560da820ce50d9649e7d795c8e07868af4d658fcabca3b5a6', 55),
       (166, 'revision', '9d597f46f89662828a2fc7d8096852574b454313193b03238a89a405d0aa5d46', 56),
       (167, 'jenkins-build', 'http://my-jenkins/a5233577-17d9-4ef3-872f-bcf78b54848b', 56),
       (168, 'integration-test', 'broken', 56),
       (169, 'integration-test', 'ok', 57),
       (170, 'jenkins-build', 'http://my-jenkins/14a280bb-ca8a-4ffa-bd19-30bbe77a0bdc', 57),
       (171, 'revision', 'da11fb1310478a0a79edcd674cde456e6dce30ec377253e5a42ce0afcc5956a5', 57),
       (172, 'revision', 'cc9af9ea5117ea755080c513ee4b535cb608e955177c8c2224583d9b01b2bac3', 58),
       (173, 'integration-test', 'broken', 58),
       (174, 'jenkins-build', 'http://my-jenkins/209dba9a-87db-426d-91ea-6539b0433724', 58),
       (175, 'integration-test', 'ok', 59),
       (176, 'jenkins-build', 'http://my-jenkins/abd27866-9802-4749-858e-7e0a5d7c63bb', 59),
       (177, 'revision', '376d3442a1935d21d2f9db0aeefbe906a93d3f331c833e712141dffd705a250a', 59),
       (178, 'jenkins-build', 'http://my-jenkins/895f4bb8-906a-4532-be74-452d110f5e27', 60),
       (179, 'revision', '4222c62aa1663068b053b12d45cdd333a79e05457f10241e4d54a769af222bbc', 60),
       (180, 'integration-test', 'broken', 60),
       (181, 'integration-test', 'ok', 61),
       (182, 'revision', '4b8c468f740ef7304f833351916a49389dca308cc39c20f9f7a8cb07dec787ee', 61),
       (183, 'jenkins-build', 'http://my-jenkins/1bfa38ef-c9e0-4c5e-ae23-c5b28f4637f4', 61),
       (184, 'revision', 'd8347b3bdb3f69c666418595302585666bc409e3fe65152314e1fb8abeac17ce', 62),
       (185, 'jenkins-build', 'http://my-jenkins/2f3b2d6d-47e7-4439-8250-5d6f3fdf9053', 62),
       (186, 'integration-test', 'broken', 62),
       (187, 'jenkins-build', 'http://my-jenkins/ec05adbc-58d5-4779-be82-c7d33dfc3f32', 63),
       (188, 'integration-test', 'ok', 63),
       (189, 'revision', '602c9065deda0231bbf103bc13470ce5adf50b218f554d38b43182afbf0d5d0d', 63),
       (190, 'jenkins-build', 'http://my-jenkins/fc3e9cc8-8ce9-4607-87c1-4cebeef31386', 64),
       (191, 'revision', '8c5273a20d440f86fe1dce2fa94b5d2f65f78ea256fd9e90ed601fb7e91dc6ee', 64),
       (192, 'integration-test', 'broken', 64),
       (193, 'jenkins-build', 'http://my-jenkins/8fb27b5d-cb40-4ce8-bd39-73a4ca9a1293', 65),
       (194, 'integration-test', 'ok', 65),
       (195, 'revision', '56fa49f81cf6fc62081d630289f5355d015611691f2aca7e1fc5233e9814cb76', 65),
       (196, 'jenkins-build', 'http://my-jenkins/3051eaf8-2d46-4d4b-a9f0-f76035a727a3', 66),
       (197, 'revision', '8ffa2cf65b72a20024df55129ebdfcc1daf5def2fe64ed6c183b2599b3029eda', 66),
       (198, 'integration-test', 'broken', 66),
       (199, 'revision', '2b604179343fce3d3c4008b5d05570353555b79e283d10fd36db7f429731d03d', 67),
       (200, 'integration-test', 'ok', 67),
       (201, 'jenkins-build', 'http://my-jenkins/3d4350df-111b-47c6-ae62-265692c4623f', 67),
       (202, 'revision', '50e9459690770ceb9c6c02312424b7ef12ef40cff401141d6a0ae3447f1b293c', 68),
       (203, 'jenkins-build', 'http://my-jenkins/68fe4af8-f82e-431a-8afb-2d1a30bf2412', 68),
       (204, 'integration-test', 'broken', 68),
       (205, 'integration-test', 'ok', 69),
       (206, 'jenkins-build', 'http://my-jenkins/3647e4c3-6418-4366-b501-51a56b614f0d', 69),
       (207, 'revision', '393b201781940f45ab911f8488ceb305e6dc12d43b99ae939d5689a4011de9ab', 69),
       (208, 'jenkins-build', 'http://my-jenkins/a13de441-807d-4ec6-826f-d67db0e44fb1', 70),
       (209, 'revision', '3ef5ba0487b81a299ec6da009b7e10962bbecf93959b509e880580e57c5b955f', 70),
       (210, 'integration-test', 'broken', 70),
       (211, 'integration-test', 'ok', 71),
       (212, 'revision', '5c5ca5d998a0ecd41fd0927ee4f57d127ebeeccde1e59d1281ec4d24656787da', 71),
       (213, 'jenkins-build', 'http://my-jenkins/1396a653-a763-4669-900d-af0895af5437', 71),
       (214, 'revision', '2a2486f8d92bbc579d102ed083067729070d511bed843c7586a2d4223fcebe00', 72),
       (215, 'integration-test', 'broken', 72),
       (216, 'jenkins-build', 'http://my-jenkins/4cf33880-0ffb-402a-b615-38a799a2b257', 72),
       (217, 'integration-test', 'ok', 73),
       (218, 'jenkins-build', 'http://my-jenkins/748f47f7-cce9-462f-a61e-ff59dc14dbc3', 73),
       (219, 'revision', 'cac226908093619f3c74642dbec4c9d83ae4515b36876801313f0e5a0d25b64e', 73),
       (220, 'integration-test', 'broken', 74),
       (221, 'jenkins-build', 'http://my-jenkins/af96b8e4-58e9-4a80-9ef6-33d5e9c9ea89', 74),
       (222, 'revision', '73f0980a63bab0e5ebab38ebfd68e401f62cd1a0ae812e834abb333fceb2454d', 74),
       (223, 'jenkins-build', 'http://my-jenkins/3262c834-48c1-4eaf-9ee9-47bbbd8bfb46', 75),
       (224, 'integration-test', 'ok', 75),
       (225, 'revision', '28bf054e259d5cf41f571382f9016a86f77ac7c9b74e791ac7840fa78771176d', 75),
       (226, 'jenkins-build', 'http://my-jenkins/23b20a8e-3467-48ad-8aca-6a0d2453c809', 76),
       (227, 'revision', 'c7ca5e60fc5b62da38afcdf918cd84e415f2b737c0423917612987018ab105fc', 76),
       (228, 'integration-test', 'broken', 76),
       (229, 'jenkins-build', 'http://my-jenkins/4cf86623-41dd-473a-a79f-64fd004f773f', 77),
       (230, 'integration-test', 'ok', 77),
       (231, 'revision', '3520d8950e721569499d0a518ef116bb2bd62c2ba12c325c8851161eeca2763b', 77),
       (232, 'jenkins-build', 'http://my-jenkins/dd61f056-8591-4bd0-8987-5a0b74aa4718', 78),
       (233, 'revision', '15532518cf82477fbc642e45033c5854131f57777722d7a7c5a50d938ae37a03', 78),
       (234, 'integration-test', 'broken', 78),
       (235, 'integration-test', 'ok', 79),
       (236, 'revision', 'ddade24538369c3c1b8e1adb943acbbd608e7fe3a11267063bd5cba24949b1bd', 79),
       (237, 'jenkins-build', 'http://my-jenkins/e57355df-d072-4ff7-91e3-04a1abb0e939', 79),
       (238, 'revision', '23aa845f85c9229d58bdd1eacc73c369d880378b2818ea82531ef9706cdbfda1', 80),
       (239, 'jenkins-build', 'http://my-jenkins/49c0795b-e6c2-4e75-989c-0b76f6cf2c93', 80),
       (240, 'integration-test', 'broken', 80),
       (241, 'integration-test', 'ok', 81),
       (242, 'revision', 'b1cef5e0a58c9278f4218d928fc62aaa1c25e2d8e72be13ceb114f6434a0046b', 81),
       (243, 'jenkins-build', 'http://my-jenkins/b8f9d369-9125-4e79-a609-83142964de39', 81),
       (244, 'revision', 'b38ee8401cf3292cf27d4b2db8e3d041015205ea73029f1caf6dcd83bdc0b1b5', 82),
       (245, 'jenkins-build', 'http://my-jenkins/46c4a264-d996-431e-be3f-3aee30e5ed6d', 82),
       (246, 'integration-test', 'broken', 82),
       (247, 'integration-test', 'ok', 83),
       (248, 'revision', '72904c0eb5291801c357467e95a8c1c86e9dec12a4e9bf40e9a0ef9739d3d9cb', 83),
       (249, 'jenkins-build', 'http://my-jenkins/33206fb9-02e8-4e78-b0c3-01dcd75f2b08', 83),
       (250, 'revision', '9739ccf0b24cecbd115cb7c37cf4deb788e343c62031a73b35bff5b8a6586cc2', 84),
       (251, 'jenkins-build', 'http://my-jenkins/339bfb68-bcfc-4031-881f-a151d7678c1e', 84),
       (252, 'integration-test', 'broken', 84),
       (253, 'integration-test', 'ok', 85),
       (254, 'jenkins-build', 'http://my-jenkins/c5922c1e-f954-4fe5-ae02-19fcebac198e', 85),
       (255, 'revision', '0e4855c8cdea5f895b409da3d279eb73fbeece874b0e41742e8fa3edca18efe1', 85),
       (256, 'jenkins-build', 'http://my-jenkins/9ca85b6b-5936-4f45-a56d-513f3bc80f5a', 86),
       (257, 'revision', '3a89fbbb3a0975f9e28c6038e48920b6c90b47abf6f863390881220481ff591f', 86),
       (258, 'integration-test', 'broken', 86),
       (259, 'integration-test', 'ok', 87),
       (260, 'jenkins-build', 'http://my-jenkins/89c53ddd-6753-4f02-a942-be685c0e1df1', 87),
       (261, 'revision', 'aff00bc8412c93eb9f16226b839b53daba877a59679df3862103b9cfb0113592', 87),
       (262, 'jenkins-build', 'http://my-jenkins/4068f6d8-8611-4915-a201-497cba452cdf', 88),
       (263, 'revision', '33c2f54eb6edefb1a4a7284de0a0687adca38c7c96817bbfe3967acc0e894c11', 88),
       (264, 'integration-test', 'broken', 88),
       (265, 'revision', 'eb770afdea62ca614c17a28f0d8d3c1659bb47e12afad3f795ae225e4931f552', 89),
       (266, 'integration-test', 'ok', 89),
       (267, 'jenkins-build', 'http://my-jenkins/884577b6-e2cc-415e-b207-073a70a5d431', 89),
       (268, 'jenkins-build', 'http://my-jenkins/0bb2527f-b78a-440c-b4e7-8459d5e30e18', 90),
       (269, 'revision', 'd1e3723d11f74c32f900bac922d67ee97cb3a572da076e8aa7e84657ea19f605', 90),
       (270, 'integration-test', 'broken', 90);

-- environments
INSERT INTO environment (id, name)
VALUES (1, 'main');
INSERT INTO artifact (id, branch, project, environment_id)
VALUES (1, 'main', 'backend', 1);
INSERT INTO artifact (id, branch, project, environment_id)
VALUES (2, 'main', 'frontend', 1);


INSERT INTO environment (id, name)
VALUES (2, 'feature-test-stage-1');
