CREATE TABLE `bookings` (
    `id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `date_time` DATETIME NOT NULL,
    `creation_timestamp` TIMESTAMP NOT NULL);