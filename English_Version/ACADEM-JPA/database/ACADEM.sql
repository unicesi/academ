SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `academ` ;
CREATE SCHEMA IF NOT EXISTS `academ` DEFAULT CHARACTER SET latin1 ;
USE `academ` ;

-- -----------------------------------------------------
-- Table `academ`.`NivelesDeConocimiento`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `academ`.`NivelesDeConocimiento` ;

CREATE  TABLE IF NOT EXISTS `academ`.`NivelesDeConocimiento` (
  `id` INT(11) NOT NULL ,
  `descripcion` VARCHAR(200) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `academ`.`Temas`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `academ`.`Temas` ;

CREATE  TABLE IF NOT EXISTS `academ`.`Temas` (
  `id` VARCHAR(10) NOT NULL ,
  `nombre` VARCHAR(100) NOT NULL ,
  `descripcion` LONGTEXT NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `academ`.`Perfiles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `academ`.`Perfiles` ;

CREATE  TABLE IF NOT EXISTS `academ`.`Perfiles` (
  `nombre` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`nombre`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `academ`.`Usuarios`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `academ`.`Usuarios` ;

CREATE  TABLE IF NOT EXISTS `academ`.`Usuarios` (
  `nombre` VARCHAR(25) NOT NULL ,
  `contraseña` VARCHAR(25) NOT NULL ,
  `perfil` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`nombre`) ,
  INDEX `fk_Usuarios_Perfiles1_idx` (`perfil` ASC) ,
  CONSTRAINT `fk_Usuarios_Perfiles1`
    FOREIGN KEY (`perfil` )
    REFERENCES `academ`.`Perfiles` (`nombre` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `academ`.`Programas`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `academ`.`Programas` ;

CREATE  TABLE IF NOT EXISTS `academ`.`Programas` (
  `codigo` INT(11) NOT NULL ,
  `nombre` VARCHAR(100) NOT NULL ,
  `descripcion` VARCHAR(200) NOT NULL ,
  PRIMARY KEY (`codigo`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `academ`.`Evaluaciones`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `academ`.`Evaluaciones` ;

CREATE  TABLE IF NOT EXISTS `academ`.`Evaluaciones` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `fechaInicial` DATETIME NOT NULL ,
  `fechaFinal` DATETIME NOT NULL ,
  `programa` INT(11) NOT NULL ,
  `propietario` VARCHAR(25) NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `FK_Evaluaciones_Programas1_idx` (`programa` ASC) ,
  INDEX `FK_Evaluaciones_Usuarios1_idx` (`propietario` ASC) ,
  CONSTRAINT `FK_Evaluaciones_Programas1`
    FOREIGN KEY (`programa` )
    REFERENCES `academ`.`Programas` (`codigo` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_Evaluaciones_Usuarios1`
    FOREIGN KEY (`propietario` )
    REFERENCES `academ`.`Usuarios` (`nombre` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `academ`.`Usuarios_Evaluaciones`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `academ`.`Usuarios_Evaluaciones` ;

CREATE  TABLE IF NOT EXISTS `academ`.`Usuarios_Evaluaciones` (
  `usuario` VARCHAR(25) NOT NULL ,
  `evaluacion` INT(11) NOT NULL ,
  PRIMARY KEY (`usuario`, `evaluacion`) ,
  INDEX `FK_Usuarios_Roles_Usuarios1_idx` (`usuario` ASC) ,
  INDEX `fk_Usuarios_Evaluaciones_Evaluaciones1_idx` (`evaluacion` ASC) ,
  CONSTRAINT `FK_Us_Roles_Usuarios1`
    FOREIGN KEY (`usuario` )
    REFERENCES `academ`.`Usuarios` (`nombre` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Usuarios_Evaluaciones_Evaluaciones1`
    FOREIGN KEY (`evaluacion` )
    REFERENCES `academ`.`Evaluaciones` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `academ`.`Calificaciones`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `academ`.`Calificaciones` ;

CREATE  TABLE IF NOT EXISTS `academ`.`Calificaciones` (
  `evaluador` VARCHAR(25) NOT NULL ,
  `evaluacion` INT(11) NOT NULL ,
  `nivelDeConocimiento` INT(11) NOT NULL ,
  `tema` VARCHAR(10) NOT NULL ,
  PRIMARY KEY (`evaluacion`, `nivelDeConocimiento`, `tema`, `evaluador`) ,
  INDEX `FK_Calificaciones_NivelesDeConocimiento1_idx` (`nivelDeConocimiento` ASC) ,
  INDEX `FK_Calificaciones_Temas1_idx` (`tema` ASC) ,
  INDEX `fk_Calificaciones_Evaluadores1_idx` (`evaluador` ASC, `evaluacion` ASC) ,
  CONSTRAINT `FK_Calificaciones_NivelesDeConocimiento1`
    FOREIGN KEY (`nivelDeConocimiento` )
    REFERENCES `academ`.`NivelesDeConocimiento` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_Calificaciones_Temas1`
    FOREIGN KEY (`tema` )
    REFERENCES `academ`.`Temas` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Calificaciones_Evaluadores1`
    FOREIGN KEY (`evaluador` , `evaluacion` )
    REFERENCES `academ`.`Usuarios_Evaluaciones` (`usuario` , `evaluacion` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `academ`.`Roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `academ`.`Roles` ;

CREATE  TABLE IF NOT EXISTS `academ`.`Roles` (
  `nombre` VARCHAR(25) NOT NULL ,
  `evaluacion` INT(11) NOT NULL ,
  PRIMARY KEY (`nombre`, `evaluacion`) ,
  INDEX `fk_Roles_Evaluaciones1_idx` (`evaluacion` ASC) ,
  CONSTRAINT `fk_Roles_Evaluaciones1`
    FOREIGN KEY (`evaluacion` )
    REFERENCES `academ`.`Evaluaciones` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `academ`.`Calificaciones_Roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `academ`.`Calificaciones_Roles` ;

CREATE  TABLE IF NOT EXISTS `academ`.`Calificaciones_Roles` (
  `evaluacion` INT(11) NOT NULL ,
  `nivelDeConocimiento` INT(11) NOT NULL ,
  `tema` VARCHAR(10) NOT NULL ,
  `evaluador` VARCHAR(25) NOT NULL ,
  `rol` VARCHAR(25) NOT NULL ,
  PRIMARY KEY (`evaluacion`, `nivelDeConocimiento`, `tema`, `evaluador`, `rol`) ,
  INDEX `fk_Calificaciones_Roles_Roles1_idx` (`rol` ASC) ,
  CONSTRAINT `fk_Calificaciones_Roles_Calificaciones1`
    FOREIGN KEY (`evaluacion` , `nivelDeConocimiento` , `tema` , `evaluador` )
    REFERENCES `academ`.`Calificaciones` (`evaluacion` , `nivelDeConocimiento` , `tema` , `evaluador` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Calificaciones_Roles_Roles1`
    FOREIGN KEY (`rol` )
    REFERENCES `academ`.`Roles` (`nombre` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `academ`.`FactoresDeImpacto`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `academ`.`FactoresDeImpacto` ;

CREATE  TABLE IF NOT EXISTS `academ`.`FactoresDeImpacto` (
  `rol` VARCHAR(25) NOT NULL ,
  `evaluacion` INT(11) NOT NULL ,
  `tema` VARCHAR(10) NOT NULL ,
  `factorDeImpacto` DECIMAL(3,2) NULL ,
  PRIMARY KEY (`rol`, `evaluacion`, `tema`) ,
  INDEX `fk_FactoresDeImpacto_Temas1_idx` (`tema` ASC) ,
  CONSTRAINT `fk_FactoresDeImpacto_Roles1`
    FOREIGN KEY (`rol` , `evaluacion` )
    REFERENCES `academ`.`Roles` (`nombre` , `evaluacion` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_FactoresDeImpacto_Temas1`
    FOREIGN KEY (`tema` )
    REFERENCES `academ`.`Temas` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `academ` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `academ`.`NivelesDeConocimiento`
-- -----------------------------------------------------
START TRANSACTION;
USE `academ`;
INSERT INTO `academ`.`NivelesDeConocimiento` (`id`, `descripcion`) VALUES (0, 'No tener experiencia o no haber sido expuesto a');
INSERT INTO `academ`.`NivelesDeConocimiento` (`id`, `descripcion`) VALUES (1, 'Tener experiencia o haber sido expuesto a');
INSERT INTO `academ`.`NivelesDeConocimiento` (`id`, `descripcion`) VALUES (2, 'Ser capaz de participar en y contribuir');
INSERT INTO `academ`.`NivelesDeConocimiento` (`id`, `descripcion`) VALUES (3, 'Ser capaz de entender y explicar');
INSERT INTO `academ`.`NivelesDeConocimiento` (`id`, `descripcion`) VALUES (4, 'Ser experto en la practica o realizacion de');

COMMIT;

-- -----------------------------------------------------
-- Data for table `academ`.`Temas`
-- -----------------------------------------------------
START TRANSACTION;
USE `academ`;
INSERT INTO `academ`.`Temas` (`id`, `nombre`, `descripcion`) VALUES ('2.1.1', 'Identificación y formulación de problemas', 'Datos y síntomas. Suposiciones y fuentes de sesgo. Fijación de prioridades de temas en el contexto de los objetivos generales. Un plan de ataque (incorporando soluciones modelo, analíticas y numéricas, análisis cuantitativo, expermimentación y consideración de la incertidumbre).');
INSERT INTO `academ`.`Temas` (`id`, `nombre`, `descripcion`) VALUES ('2.1.2', 'Modelos', 'Suposiciones para simplificar sistenas y entornos complejos. Modelos conceptuales y cualitativos. Modelos cuantitativos y simulaciones.');

COMMIT;

-- -----------------------------------------------------
-- Data for table `academ`.`Perfiles`
-- -----------------------------------------------------
START TRANSACTION;
USE `academ`;
INSERT INTO `academ`.`Perfiles` (`nombre`) VALUES ('Administrador');
INSERT INTO `academ`.`Perfiles` (`nombre`) VALUES ('Propietario');
INSERT INTO `academ`.`Perfiles` (`nombre`) VALUES ('Evaluador');

COMMIT;

-- -----------------------------------------------------
-- Data for table `academ`.`Usuarios`
-- -----------------------------------------------------
START TRANSACTION;
USE `academ`;
INSERT INTO `academ`.`Usuarios` (`nombre`, `contraseña`, `perfil`) VALUES ('alvaro', '123', 'Administrador');
INSERT INTO `academ`.`Usuarios` (`nombre`, `contraseña`, `perfil`) VALUES ('glondono', '123', 'Propietario');
INSERT INTO `academ`.`Usuarios` (`nombre`, `contraseña`, `perfil`) VALUES ('hfarboleda', '123', 'Evaluador');

COMMIT;

-- -----------------------------------------------------
-- Data for table `academ`.`Programas`
-- -----------------------------------------------------
START TRANSACTION;
USE `academ`;
INSERT INTO `academ`.`Programas` (`codigo`, `nombre`, `descripcion`) VALUES (3, 'Ingenieria de Sistemas', 'Ingenieria de Sistemas');
INSERT INTO `academ`.`Programas` (`codigo`, `nombre`, `descripcion`) VALUES (7, 'Ingenieria Telematica', 'Ingenieria Telematica');

COMMIT;

-- -----------------------------------------------------
-- Data for table `academ`.`Evaluaciones`
-- -----------------------------------------------------
START TRANSACTION;
USE `academ`;
INSERT INTO `academ`.`Evaluaciones` (`id`, `fechaInicial`, `fechaFinal`, `programa`, `propietario`) VALUES (1, '2013-03-19 00:00:00', '2013-03-21 00:00:00', 3, 'glondono');

COMMIT;

-- -----------------------------------------------------
-- Data for table `academ`.`Usuarios_Evaluaciones`
-- -----------------------------------------------------
START TRANSACTION;
USE `academ`;
INSERT INTO `academ`.`Usuarios_Evaluaciones` (`usuario`, `evaluacion`) VALUES ('alvaro', 1);
INSERT INTO `academ`.`Usuarios_Evaluaciones` (`usuario`, `evaluacion`) VALUES ('hfarboleda', 1);

COMMIT;

-- -----------------------------------------------------
-- Data for table `academ`.`Calificaciones`
-- -----------------------------------------------------
START TRANSACTION;
USE `academ`;
INSERT INTO `academ`.`Calificaciones` (`evaluador`, `evaluacion`, `nivelDeConocimiento`, `tema`) VALUES ('hfarboleda', 1, 1, '2.1.2');
INSERT INTO `academ`.`Calificaciones` (`evaluador`, `evaluacion`, `nivelDeConocimiento`, `tema`) VALUES ('hfarboleda', 1, 4, '2.1.1');

COMMIT;

-- -----------------------------------------------------
-- Data for table `academ`.`Roles`
-- -----------------------------------------------------
START TRANSACTION;
USE `academ`;
INSERT INTO `academ`.`Roles` (`nombre`, `evaluacion`) VALUES ('Docente', 1);
INSERT INTO `academ`.`Roles` (`nombre`, `evaluacion`) VALUES ('Industria', 1);
INSERT INTO `academ`.`Roles` (`nombre`, `evaluacion`) VALUES ('Egresado', 1);

COMMIT;
