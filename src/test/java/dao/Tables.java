package dao;

public class Tables {

    public static final String CREATE_TABLE_CLIENT = "CREATE TABLE IF NOT EXISTS `testDB`.`client` (\n" +
            "                                               `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,\n" +
            "                                               `login` VARCHAR(255) NOT NULL,\n" +
            "    `password` VARCHAR(255) NOT NULL,\n" +
            "    `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
            "    `client_status_id` INT UNSIGNED NOT NULL,\n" +
            "    `role_id` INT UNSIGNED NOT NULL,\n" +
            "    PRIMARY KEY (`id`),\n" +
            "    UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,\n" +
            "    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,\n" +
            "    INDEX `fk_client_client_status1_idx` (`client_status_id` ASC) VISIBLE,\n" +
            "    INDEX `fk_client_role1_idx` (`role_id` ASC) VISIBLE,\n" +
            "    CONSTRAINT `fk_client_client_status1`\n" +
            "    FOREIGN KEY (`client_status_id`)\n" +
            "    REFERENCES `bank`.`client_status` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION,\n" +
            "    CONSTRAINT `fk_client_role1`\n" +
            "    FOREIGN KEY (`role_id`)\n" +
            "    REFERENCES `bank`.`role` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION)\n" +
            "    ENGINE = InnoDB\n" +
            "    DEFAULT CHARACTER SET = utf8;";
    public static final String CREATE_TABLE_ROLE_CLIENT = "CREATE  table `testDB`.`role`(id INT PRIMARY KEY, " +
            "role VARCHAR(45) UNIQUE)";
    public static final String CREATE_TABLE_CLIENT_STATUS = "CREATE table `testDB`.`client_status`(id INT PRIMARY KEY," +
            "status VARCHAR(45) UNIQUE )";
    public static final String CREATE_TABLE_CARD_STATUS = "CREATE TABLE IF NOT EXISTS `testDB`.`card_status` (\n" +
            "                                                    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,\n" +
            "                                                    `status` VARCHAR(45) NOT NULL,\n" +
            "    PRIMARY KEY (`id`),\n" +
            "    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,\n" +
            "    UNIQUE INDEX `status_UNIQUE` (`status` ASC) VISIBLE)\n" +
            "    ENGINE = InnoDB\n" +
            "    DEFAULT CHARACTER SET = utf8;";
    public static final String CREATE_TABLE_CARD = "CREATE TABLE IF NOT EXISTS `testDB`.`card` (\n" +
            "                                             `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,\n" +
            "                                             `name` VARCHAR(255) NOT NULL,\n" +
            "    `balance` DECIMAL(12,2) NOT NULL,\n" +
            "    `card_status_id` INT UNSIGNED NOT NULL,\n" +
            "    `client_id` INT UNSIGNED NOT NULL,\n" +
            "    `name_custom` VARCHAR(255) NOT NULL,\n" +
            "    PRIMARY KEY (`id`),\n" +
            "    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,\n" +
            "    UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,\n" +
            "    INDEX `fk_card_card_status_idx` (`card_status_id` ASC) VISIBLE,\n" +
            "    INDEX `fk_card_client1_idx` (`client_id` ASC) VISIBLE,\n" +
            "    CONSTRAINT `fk_card_card_status`\n" +
            "    FOREIGN KEY (`card_status_id`)\n" +
            "    REFERENCES `bank`.`card_status` (`id`),\n" +
            "    CONSTRAINT `fk_card_client1`\n" +
            "    FOREIGN KEY (`client_id`)\n" +
            "    REFERENCES `bank`.`client` (`id`))\n" +
            "    ENGINE = InnoDB\n" +
            "    DEFAULT CHARACTER SET = utf8;";
    public static final String CREATE_TABLE_BILL_STATUS = "CREATE TABLE IF NOT EXISTS `testDB`.`bill_status` (\n" +
            "                                                    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,\n" +
            "                                                    `status` VARCHAR(45) NOT NULL,\n" +
            "    PRIMARY KEY (`id`),\n" +
            "    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,\n" +
            "    UNIQUE INDEX `status_UNIQUE` (`status` ASC) VISIBLE)\n" +
            "    ENGINE = InnoDB\n" +
            "    DEFAULT CHARACTER SET = utf8;";
    public static final String  CREATE_TABLE_BILL = "CREATE TABLE IF NOT EXISTS `testDB`.`bill` (\n" +
            "                                             `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,\n" +
            "                                             `sum` DECIMAL(12,2) NOT NULL,\n" +
            "                                             `date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n" +
            "                                             `card_id` INT UNSIGNED NOT NULL,\n" +
            "                                             `bill_status_id` INT UNSIGNED NOT NULL,\n" +
            "`recipient` VARCHAR(45),"+
            "                                             PRIMARY KEY (`id`),\n" +
            "    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,\n" +
            "    INDEX `fk_bill_card1_idx` (`card_id` ASC) VISIBLE,\n" +
            "    INDEX `fk_bill_bill_status1_idx` (`bill_status_id` ASC) VISIBLE,\n" +
            "    CONSTRAINT `fk_bill_bill_status1`\n" +
            "    FOREIGN KEY (`bill_status_id`)\n" +
            "    REFERENCES `bank`.`bill_status` (`id`),\n" +
            "    CONSTRAINT `fk_bill_card1`\n" +
            "    FOREIGN KEY (`card_id`)\n" +
            "    REFERENCES `bank`.`card` (`id`))\n" +
            "    ENGINE = InnoDB\n" +
            "    DEFAULT CHARACTER SET = utf8;";
}
