CREATE TABLE `patient` (
	`id` int NOT NULL AUTO_INCREMENT,
	`first_name` varchar(255) NOT NULL DEFAULT 'FNU',
	`last_name` varchar(255) NOT NULL DEFAULT 'LNU',
	`gender` varchar(255) DEFAULT 'Other',
	`age` int NOT NULL,
	`email` varchar(255),
	`phone_number` varchar(25),
	`height` DECIMAL NOT NULL,
	`weight` DECIMAL NOT NULL,
	`temp` DECIMAL NOT NULL,
	`bp` varchar(255) NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `appointment` (
	`id` int NOT NULL AUTO_INCREMENT,
	`date_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`patient_id` int NOT NULL,
	`doctor` varchar(255) NOT NULL,
	`checked` bool NOT NULL DEFAULT false,
	`check_time` DATETIME,
	`note` TEXT,
	PRIMARY KEY (`id`)
);

CREATE TABLE `medication` (
	`id` int NOT NULL AUTO_INCREMENT,
	`name` varchar(255) NOT NULL,
	`dosage` DECIMAL NOT NULL,
	`patient_id` int NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `admission` (
	`id` int NOT NULL AUTO_INCREMENT,
	`patient_id` int NOT NULL,
	`appointment_id` int NOT NULL,
	`room` int NOT NULL,
	`bed` int NOT NULL,
	`adm_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`discharged` bool NOT NULL DEFAULT false,
	`dsc_date` DATETIME,
	`doctor` varchar(255) NOT NULL,
	`dsc_notes` TEXT,
	PRIMARY KEY (`id`)
);

ALTER TABLE `appointment` ADD CONSTRAINT `appointment_fk0` FOREIGN KEY (`patient_id`) REFERENCES `patient`(`id`);

ALTER TABLE `medications` ADD CONSTRAINT `medication_fk0` FOREIGN KEY (`patient_id`) REFERENCES `patient`(`id`);

ALTER TABLE `admission` ADD CONSTRAINT `admission_fk0` FOREIGN KEY (`patient_id`) REFERENCES `patient`(`id`);

ALTER TABLE `admission` ADD CONSTRAINT `admission_fk1` FOREIGN KEY (`appointment_id`) REFERENCES `appointment`(`id`);

