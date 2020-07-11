
DROP TABLE IF EXISTS `zuul_route`;

CREATE TABLE `zuul_route` (
  `id` varchar(50) NOT NULL,
  `path` varchar(255) NOT NULL,
  `service_id` varchar(50) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `strip_prefix` tinyint(1) DEFAULT '1',
  `retryable` tinyint(1) DEFAULT '0',
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  `description` varchar(255) DEFAULT NULL,
  `zuul_sensitive_headers` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
)
