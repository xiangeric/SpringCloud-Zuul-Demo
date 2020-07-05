insert  into `zuul_route`(`id`,`path`,`service_id`,`url`,`strip_prefix`,`retryable`,`enabled`,`description`) values ('baidu','/baidu/**',NULL,'http://www.baidu.com',1,0,1,'重定向百度');
insert  into `zuul_route`(`id`,`path`,`service_id`,`url`,`strip_prefix`,`retryable`,`enabled`,`description`) values ('client','/client/**',NULL,'http://localhost:6060',1,0,1,'url');
