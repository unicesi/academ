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
  `factorDeImpacto` DECIMAL(5,2) NULL ,
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


-- -----------------------------------------------------
-- Table `academ`.`Materias`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `academ`.`Materias` ;

CREATE  TABLE IF NOT EXISTS `academ`.`Materias` (
  `codigo` VARCHAR(10) NOT NULL ,
  `nombre` VARCHAR(100) NULL ,
  PRIMARY KEY (`codigo`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `academ`.`Bloques`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `academ`.`Bloques` ;

CREATE  TABLE IF NOT EXISTS `academ`.`Bloques` (
  `nombre` VARCHAR(100) NOT NULL ,
  PRIMARY KEY (`nombre`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `academ`.`Materias_Bloques`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `academ`.`Materias_Bloques` ;

CREATE  TABLE IF NOT EXISTS `academ`.`Materias_Bloques` (
  `materia` VARCHAR(10) NOT NULL ,
  `bloque` VARCHAR(100) NOT NULL ,
  PRIMARY KEY (`materia`, `bloque`) ,
  INDEX `fk_Bloque_Materia_Materia1_idx` (`materia` ASC) ,
  INDEX `fk_Materias_Bloques_Bloques1_idx` (`bloque` ASC) ,
  CONSTRAINT `fk_Bloque_Materia_Materia1`
    FOREIGN KEY (`materia` )
    REFERENCES `academ`.`Materias` (`codigo` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Materias_Bloques_Bloques1`
    FOREIGN KEY (`bloque` )
    REFERENCES `academ`.`Bloques` (`nombre` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `academ`.`Competencias`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `academ`.`Competencias` ;

CREATE  TABLE IF NOT EXISTS `academ`.`Competencias` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `descripcion` LONGTEXT NOT NULL ,
  `programa` INT(11) NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_Competencias_Programas1_idx` (`programa` ASC) ,
  CONSTRAINT `fk_Competencias_Programas1`
    FOREIGN KEY (`programa` )
    REFERENCES `academ`.`Programas` (`codigo` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `academ`.`ResultadosAprendizaje`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `academ`.`ResultadosAprendizaje` ;

CREATE  TABLE IF NOT EXISTS `academ`.`ResultadosAprendizaje` (
  `programa` INT(11) NOT NULL ,
  `tema` VARCHAR(10) NOT NULL ,
  `nivelDeConocimiento` INT(11) NOT NULL ,
  `competencia` INT NULL ,
  PRIMARY KEY (`programa`, `tema`) ,
  INDEX `fk_ResultadosAprendizaje_Programas1_idx` (`programa` ASC) ,
  INDEX `fk_ResultadosAprendizaje_NivelesDeConocimiento1_idx` (`nivelDeConocimiento` ASC) ,
  INDEX `fk_ResultadosAprendizaje_Competencias1_idx` (`competencia` ASC) ,
  CONSTRAINT `fk_ResultadosAprendizaje_Temas1`
    FOREIGN KEY (`tema` )
    REFERENCES `academ`.`Temas` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ResultadosAprendizaje_Programas1`
    FOREIGN KEY (`programa` )
    REFERENCES `academ`.`Programas` (`codigo` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ResultadosAprendizaje_NivelesDeConocimiento1`
    FOREIGN KEY (`nivelDeConocimiento` )
    REFERENCES `academ`.`NivelesDeConocimiento` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ResultadosAprendizaje_Competencias1`
    FOREIGN KEY (`competencia` )
    REFERENCES `academ`.`Competencias` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `academ`.`Criterios_Rubricas`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `academ`.`Criterios_Rubricas` ;

CREATE  TABLE IF NOT EXISTS `academ`.`Criterios_Rubricas` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `descripcion` LONGTEXT NOT NULL ,
  `programa` INT(11) NOT NULL ,
  `tema` VARCHAR(10) NOT NULL ,
  PRIMARY KEY (`id`, `programa`, `tema`) ,
  INDEX `fk_Criterios_Rubrica_ResultadosAprendizaje1_idx` (`tema` ASC, `programa` ASC) ,
  CONSTRAINT `fk_Criterios_Rubrica_ResultadosAprendizaje1`
    FOREIGN KEY (`tema` , `programa` )
    REFERENCES `academ`.`ResultadosAprendizaje` (`tema` , `programa` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `academ`.`Niveles_Rubricas`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `academ`.`Niveles_Rubricas` ;

CREATE  TABLE IF NOT EXISTS `academ`.`Niveles_Rubricas` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `descripcion` LONGTEXT NOT NULL ,
  `nivel` INT NOT NULL ,
  `criterio` INT NOT NULL ,
  `programa` INT(11) NOT NULL ,
  `tema` VARCHAR(10) NOT NULL ,
  PRIMARY KEY (`id`, `criterio`, `programa`, `tema`) ,
  INDEX `fk_Niveles_Rubricas_Criterios_Rubricas1_idx` (`criterio` ASC, `programa` ASC, `tema` ASC) ,
  CONSTRAINT `fk_Niveles_Rubricas_Criterios_Rubricas1`
    FOREIGN KEY (`criterio` , `programa` , `tema` )
    REFERENCES `academ`.`Criterios_Rubricas` (`id` , `programa` , `tema` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `academ`.`Subtemas`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `academ`.`Subtemas` ;

CREATE  TABLE IF NOT EXISTS `academ`.`Subtemas` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `tema` VARCHAR(10) NOT NULL ,
  `nombre` LONGTEXT NOT NULL ,
  PRIMARY KEY (`id`, `tema`) ,
  INDEX `fk_Subtemas_Temas1_idx` (`tema` ASC) ,
  CONSTRAINT `fk_Subtemas_Temas1`
    FOREIGN KEY (`tema` )
    REFERENCES `academ`.`Temas` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `academ`.`Materias_Programas`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `academ`.`Materias_Programas` ;

CREATE  TABLE IF NOT EXISTS `academ`.`Materias_Programas` (
  `materia` VARCHAR(10) NOT NULL ,
  `programa` INT(11) NOT NULL ,
  PRIMARY KEY (`materia`, `programa`) ,
  INDEX `fk_Materias_Programas_Programas1_idx` (`programa` ASC) ,
  CONSTRAINT `fk_Materias_Programas_Materias1`
    FOREIGN KEY (`materia` )
    REFERENCES `academ`.`Materias` (`codigo` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Materias_Programas_Programas1`
    FOREIGN KEY (`programa` )
    REFERENCES `academ`.`Programas` (`codigo` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `academ`.`Bloques_Programas`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `academ`.`Bloques_Programas` ;

CREATE  TABLE IF NOT EXISTS `academ`.`Bloques_Programas` (
  `bloque` VARCHAR(100) NOT NULL ,
  `Programas_codigo` INT(11) NOT NULL ,
  PRIMARY KEY (`bloque`, `Programas_codigo`) ,
  INDEX `fk_Bloques_Programas_Programas1_idx` (`Programas_codigo` ASC) ,
  CONSTRAINT `fk_Bloques_Programas_Bloques1`
    FOREIGN KEY (`bloque` )
    REFERENCES `academ`.`Bloques` (`nombre` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Bloques_Programas_Programas1`
    FOREIGN KEY (`Programas_codigo` )
    REFERENCES `academ`.`Programas` (`codigo` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `academ`.`Usuarios_Roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `academ`.`Usuarios_Roles` ;

CREATE  TABLE IF NOT EXISTS `academ`.`Usuarios_Roles` (
  `usuario` VARCHAR(25) NOT NULL ,
  `evaluacion` INT(11) NOT NULL ,
  `rol` VARCHAR(25) NOT NULL ,
  PRIMARY KEY (`usuario`, `evaluacion`, `rol`) ,
  INDEX `fk_Usuarios_Roles_Roles1_idx` (`rol` ASC) ,
  CONSTRAINT `fk_Usuarios_Roles_Usuarios_Evaluaciones1`
    FOREIGN KEY (`usuario` , `evaluacion` )
    REFERENCES `academ`.`Usuarios_Evaluaciones` (`usuario` , `evaluacion` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Usuarios_Roles_Roles1`
    FOREIGN KEY (`rol` )
    REFERENCES `academ`.`Roles` (`nombre` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `academ`.`Alcances`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `academ`.`Alcances` ;

CREATE  TABLE IF NOT EXISTS `academ`.`Alcances` (
  `materia` VARCHAR(10) NOT NULL ,
  `bloque` VARCHAR(100) NOT NULL ,
  `programa` INT(11) NOT NULL ,
  `tema` VARCHAR(10) NOT NULL ,
  `alcance` VARCHAR(10) NULL ,
  PRIMARY KEY (`materia`, `bloque`, `programa`, `tema`) ,
  INDEX `fk_Alcance_ResultadosAprendizaje1_idx` (`programa` ASC, `tema` ASC) ,
  INDEX `fk_Alcances_Materias_Bloques1_idx` (`materia` ASC, `bloque` ASC) ,
  CONSTRAINT `fk_Alcance_ResultadosAprendizaje1`
    FOREIGN KEY (`programa` , `tema` )
    REFERENCES `academ`.`ResultadosAprendizaje` (`programa` , `tema` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Alcances_Materias_Bloques1`
    FOREIGN KEY (`materia` , `bloque` )
    REFERENCES `academ`.`Materias_Bloques` (`materia` , `bloque` )
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
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('2.1.1', 'Identificacion y Formulacion de Problemas');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('2.1.2', 'Modelado');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('2.1.3', 'Estimacion y Analisis Cualitativo');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('2.1.4', 'Analisis de la Incertidumbre');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('2.1.5', 'Solucion y Recomendacion');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('2.2.1', 'Formulacion de Hipotesis');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('2.2.2', 'Encuesta de Impresion y Literatura Electronica');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('2.2.3', 'Investigacion Experimental');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('2.2.4', 'Defensa y Prueba de la Hipotesis');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('2.3.1', 'Pensamiento Holistico');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('2.3.2', 'Surgimientos e Interacciones en Sistemas');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('2.3.3', 'Priorizacion y Enfoque');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('2.3.4', 'Intercambios, Juicios y Balance en la Resolucion');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('2.4.1', 'Iniciativa y Voluntad para la Toma de Decisiones con Presencia de Incertidumbre');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('2.4.2', 'Perseverancia, Urgencia y Voluntad de Entrega, Inventiva y Flexibilidad');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('2.4.3', 'Pensamiento Creativo');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('2.4.4', 'Pensamiento Critico ');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('2.4.5', 'Integracion del Autoconocimiento, la Metacognicion y el Conocimiento');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('2.4.6', 'Aprendizaje y Educacion Activa');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('2.4.7', 'Andministracion de Recursos y del Tiempo');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('2.5.1', 'Etica, Integridad y Responsabilidad Social');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('2.5.2', 'Comportamiento Profesional');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('2.5.3', 'Intension y Vision Proactiva en la Vida');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('2.5.4', 'Permanecer Actualizado en el Mundo de la Ingenieria');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('2.5.5', 'Equidad y Diversidad');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('2.5.6', 'Confianza y Lealtad');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('3.1.1', 'Conformando Equipos Efectivos');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('3.1.2', 'Operación en Equipo');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('3.1.3', 'Crecimiento y Evolucion del Equipo');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('3.1.4', 'Liderazgo en Equipo');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('3.1.5', 'Trabajo en Equipo Tecnico y Multidisciplinario');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('3.2.1', 'Estrategia de Comunicacion');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('3.2.10', 'Establecimiento de Redes y Conecciones Diversas');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('3.2.2', 'Estructura de la Comunicacion');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('3.2.3', 'Comunicacion Escrita');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('3.2.4', 'Comunicación Electronica/Multimedia');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('3.2.5', 'Comunicaciones Graficas');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('3.2.6', 'Presentacion Oral');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('3.2.7', 'Interrogacion, Escucha y Dialogo');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('3.2.8', 'Negociacion, Compromiso y Resolucion de Conflictos');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('3.2.9', 'Abogacia');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('3.3.1', 'Comunicacion en Ingles');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('3.3.2', 'Comunicacion en Idiomas de Comercio e Industria Regionales');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('3.3.3', 'Comunicacion en Otros Idiomas');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('4.1.1', 'Roles y Responsabilidades de los Ingenieros');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('4.1.2', 'El Impacto de la Ingenieria en la Sociedad y el Medio Ambiente');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('4.1.3', 'Reglamento de la Sociedad para la Ingenieria');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('4.1.4', 'El Contexto Cultural e Historico');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('4.1.5', 'Valores y Asuntos Contemporaneos');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('4.1.6', 'Desarrollo de una Perspectiva Global');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('4.1.7', 'Sostenibilidad y Necesidad del Desarrollo Sostenible');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('4.2.1', 'Apreciar las Diferentes Culturas Empresariales');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('4.2.2', 'Participes de la Empresa, Estrategia y Objetivos');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('4.2.3', 'Emprendimiento Tecnico');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('4.2.4', 'Trabajando en Organizaciones');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('4.2.5', 'Trabajando en Organizaciones Internacionales');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('4.2.6', 'Desarrollo y Evaluacion de Nuevas Tecnologias');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('4.2.7', 'Financiacion y Economia de Proyectos de Ingenieria');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('4.3.1', 'Entender las Necesidades y Establecer Metas');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('4.3.2', 'Definicion de Funcion, Concepto y Arquitectura');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('4.3.3', 'Ingenieria de Sistemas, Modelado e Interfaces');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('4.3.4', 'Gestion del Desarrollo del Proyecto');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('4.4.1', 'El Proceso de Diseño');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('4.4.2', 'Enfoques y Fases del Proceso de Diseño');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('4.4.3', 'Utilizacion del Conocimiento en el Diseño');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('4.4.4', 'Diseño Disciplinar');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('4.4.5', 'Diseño Multidisciplinar');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('4.4.6', 'Diseño para la sostenibilidad, seguridad, estetica, operabilidad y otros objetivos');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('4.5.1', 'Diseñando un Proceso de Implementacion Sostenible');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('4.5.2', 'Proceso de Fabricacion de Hardware');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('4.5.3', 'Proceso de Implementacion de Software');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('4.5.4', 'Integracion de Hardware y Software');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('4.5.5', 'Pruebas, Verificacion, Validacion y Certificacion');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('4.5.6', 'Gestion de la Implementacion');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('4.6.1', 'Diseñar y Optimizar Operaciones Sostenibles y Seguras');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('4.6.2', 'Entrenamiento y Operaciones');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('4.6.3', 'Apoyar el Ciclo de Vida del Sistema');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('4.6.4', 'Mejora y Evolucion del Sistema');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('4.6.5', 'Cuestiones relativas a la Eliminacion y al Fin del Ciclo de Vida ');
INSERT INTO `academ`.`Temas` (`id`, `nombre`) VALUES ('4.6.6', 'Gestion de Operaciones');

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
INSERT INTO `academ`.`Usuarios` (`nombre`, `contraseña`, `perfil`) VALUES ('glondono', '123', 'Evaluador');
INSERT INTO `academ`.`Usuarios` (`nombre`, `contraseña`, `perfil`) VALUES ('hfarboleda', '123', 'Evaluador');
INSERT INTO `academ`.`Usuarios` (`nombre`, `contraseña`, `perfil`) VALUES ('nvillega', '123', 'Propietario');

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
INSERT INTO `academ`.`Evaluaciones` (`id`, `fechaInicial`, `fechaFinal`, `programa`, `propietario`) VALUES (1, '2013-03-19 00:00:00', '2013-03-21 00:00:00', 3, 'nvillega');

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

-- -----------------------------------------------------
-- Data for table `academ`.`Subtemas`
-- -----------------------------------------------------
START TRANSACTION;
USE `academ`;
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.1.1', 'Datos y sintomas');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.1.1', 'Emisión de prioridades en el contexto de los objetivos generales');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.1.1', 'Supuestos y fuentes de inclinacion');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.1.1', 'Un plan de ataque (modelo que incorpora, soluciones analiticas y numericas, analisis cualitativo, experimentacion y consideracion de la incertidumbre)');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.1.2', 'Simulaciones y modelos cuantitativos');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.1.2', 'Supuestos para simplificar los sistemas complejos y los modelos conceptuales y cualitativos del entorno');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.1.3', 'Ordenes de magnitud, limites y tendencias');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.1.3', 'Pruebas de compatibilidad y erorres (limites, unidades, etc.) La generalizacion de soluciones analiticas');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.1.4', 'Analisis de decisiones');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.1.4', 'Informacion ambigua e incompleta');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.1.4', 'Margenes y reservas');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.1.4', 'Modelos probabilisticos y estadisticos de secuencias y eventos. Ingenieria de costo-beneficio y analisis de riesgo');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.1.5', 'Posibles mejoras en el proceso de solucion');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.1.5', 'Resultados esenciales de las soluciones y discrepancias de los datos de prueba con los resultados');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.1.5', 'Resumen de las recomendaciones');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.1.5', 'Solucion de problemas');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.2.1', 'Controles y grupos de control');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.2.1', 'Preguntas criticas para examinar. Hipotesis que deben ser probadas');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.2.2', 'Citacion de referencias');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.2.2', 'Identificacion y busqueda de la informacion usando la biblioteca, herramientas y bases de datos en linea ordenando y clasificando la informacion primaria');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.2.2', 'La calidad y la confiabilidad de la informacion');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.2.2', 'La literatura y la estrategia de medios de investigacion');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.2.2', 'Las innovaciones y lo escencial contenido en la informacion');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.2.2', 'Preguntas de investigacion sin respuesta');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.2.3', 'Datos experimentales');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.2.3', 'Datos experimentales vs. Modelos disponibles');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.2.3', 'El concepto y la estrategia experimental');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.2.3', 'Las precauciones cuando los seres humanos son usados en investigaciones experimentales con base en metodos de las ciencias sociales. Construccion experiemental');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.2.3', 'Protocolos de prueba y procedimientos experimentales. Mediciones experimentales');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.2.4', 'Conclusiones soportadas por los datos, necesidades y valores. Posibles mejoras en el proceso de descubrimiento del conocimiento');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.2.4', 'La validez estadistica de los datos');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.2.4', 'Las limitaciones de los datos utilizados');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.3.1', 'Acercamientos transdisciplinaros que aseguran que el sistema ha sido entendido desde todas las perspectivas relevantes');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.3.1', 'El contexto social, empresarial y tecnico del sistema');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.3.1', 'Las interacciones externas con el sistema, y el impacto en el comportamiento del sistema');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.3.1', 'Un sistema, su funcion y comportamiento, y sus elementos');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.3.2', 'Adaptacion evolucionaria en el tiempo');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.3.2', 'Las abstracciones necesarias para definir y modelar las entidades o elementos del sistema');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.3.2', 'Las propiedades funcionales y de comportamiento (intencionales y no intencionales) que emergen del sistema');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.3.2', 'Las relaciones importantes, interacciones e interfaces entre elementos');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.3.3', 'Asignacion de energia y recursos para resolver los problemas determinantes');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.3.3', 'Los factores determinantes en el conjunto');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.3.3', 'Todos los factores relevantes para el sistema en el conjunto');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.3.4', 'Soluciones flexibles vs. Soluciones optimas durante el ciclo de vida del sistema. Posibles mejoras en el pensamiento sistemico utilizado');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.3.4', 'Soluciones que equilibran varios factores, resolver las tenciones y optimizar el sistema en conjunto');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.3.4', 'Tensiones y factores a resolver por medio de intercambios');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.4.1', 'Desarrollo de un curso de accion');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.4.1', 'Las necesidades y oportunidades para la iniciativa');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.4.1', 'Liderazgo en nuevos esfuerzos, con inclinacion hacia decisiones adecuadas de accion, con base en la informacion a la mano');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.4.1', 'Los beneficios y riesgos potenciales de una accion o decision');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.4.2', 'Aceptacion de retroalimentacion, criticas y voluntad de reflexionar y responder');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.4.2', 'Adaptacion al cambio');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.4.2', 'Adopcion de medidas definitivas, entrega de resultados y presentacion de informes sobre las medidas');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.4.2', 'Disposicion para trabajar con otros, y para considerar y adoptar diferentes puntos de vista');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.4.2', 'Disposicion, voluntad y habilidad para trabajar independiente');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.4.2', 'El equilibrio entre la vida personal y la profesional');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.4.2', 'Haciendo uso ingenioso de los recursos de la situacion del grupo');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.4.2', 'La importancia del trabajo duro, la intensidad y la atencion a los detalles');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.4.2', 'Sentido de la responsabilidad por los resultados. Auto-confianza, coraje y entusiasmo. Determinacion para lograr los objetivos');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.4.3', 'Conceptualizacion y Abstraccion');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.4.3', 'El papel de la creatividad en el arte, la ciencia, las humanidades y la tecnologia');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.4.3', 'El proceso de invencion');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.4.3', 'Sintesis y Generalizacion');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.4.4', 'Argumentos logicos (y falacias) y soluciones');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.4.4', 'Conclusiones e implicaciones');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.4.4', 'Evidencia de apoyo, hechos e informacion');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.4.4', 'Proposito y planteamiento de los supuestos del problema o tema');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.4.4', 'Puntos de vista y teorias');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.4.4', 'Reflexion en la calidad del pensamiento');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.4.5', 'Habilidades, intereses, fortalezas y debilidades propias');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.4.5', 'La extension de las habilidades y de la responsabilidad propia, para superar debilidades importantes');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.4.5', 'La importancia de la profundidad y la amplitud del conocimiento. Indentificacion del grado de eficiencia y de la manera en que se esta pensando');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.4.5', 'Vincular los conocimientos entre si e identificar la estructura del conocimiento');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.4.6', 'Estilos de aprendizaje propios');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.4.6', 'La motivacion para el aprendizaje activo continuo. Las habilidades de aprendizaje activo');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.4.6', 'Permintiendo el aprendizaje de otros');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.4.6', 'Relaciones con tutores');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.4.7', 'La importancia y/o urgencia de las tareas. Ejecucion eficiente de las tareas');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.4.7', 'Priorizacion de tareas');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.5.1', 'El coraje moral para actuar bajo principios a pesar de la adversidad');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.5.1', 'Estandares y principios eticos propios');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.5.1', 'La posibilidad de conflicto entre imperativos profesionales y eticos');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.5.1', 'Un compromiso con el servicio');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.5.1', 'Un compromiso de ayudar a los demas y a la sociedad ampliamente');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.5.1', 'Veracidad');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.5.2', 'Cortesia profesional');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.5.2', 'Costumbres y normas internacionales de contacto interpersonal');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.5.2', 'Una conducta profesional');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.5.3', 'Aspiracion a ejercer sus potencialidades como lider');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.5.3', 'Considerar la contribucion propia a la sociedad');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.5.3', 'Inspirar a los demas');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.5.3', 'Portafolio propio de habilidades profesionales');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.5.3', 'Una vision personal hacia el futuro propio');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.5.4', 'El impacto potencial de nuevos descubrimientos cientificos');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.5.4', 'El impacto social y tecnico de nuevas tecnologias e innovaciones');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.5.4', 'Los vinculos entre la teoria y la practica de la ingenieria');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.5.4', 'Una familiaridad con las practicas/tecnologias actuales en ingenieria');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.5.5', 'Aceptar la diversidad de los grupos y de la fuerza laboral');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.5.5', 'Complacer diversos origenes');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.5.5', 'Un compromiso a tratar a los demas con equitativamente');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.5.6', 'Lealtad con los colegas y con el equipo propio');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.5.6', 'Reconocer y enfatizar las contribuciones de los demas');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '2.5.6', 'Trabajar para aportar al exito de los demas');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.1.1', 'Las etapas de la confomacion de equipos y del ciclo de vida');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.1.1', 'Las fortalezas y debilidades del equipo y de sus miembros');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.1.1', 'Los objetivos, necesidades y caracteristicas (estilos de trabajo, diferencias culturales) de los miembros individuales del equipo');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.1.1', 'Reglas y normas basicas de confidencialidad de equipo, responsabilidad e iniciativa');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.1.1', 'Roles y responsabilidades del equipo');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.1.1', 'Tareas y procesos en equipo');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.1.2', 'Capacitacion de los miembros del equipo');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.1.2', 'Comunicación efectiva (escucha activa, colaboracion, proporcion y obtencion de informacion)');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.1.2', 'La planificacion y facilitacion de reuniones eficaces');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.1.2', 'La planificacion, The planning, programacion y ejecucion de un proyecto');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.1.2', 'Mediacion, negociacion y resolucion de conflictos');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.1.2', 'Objetivos y agenda');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.1.2', 'Reglas de equipo basicas');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.1.2', 'Retroalimentacion positiva y efectiva');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.1.2', 'Solucion de problemas (creatividad y toma de decisiones en equipo)');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.1.3', 'Estrategias de refleccion, evaluacion y autoevaluacion');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.1.3', 'Estrategias para la comunicacion y presentacion de reportes en equipo');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.1.3', 'Habilidades para el crecimiento individual dentro del equipo');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.1.3', 'Habilidades para el mantenimiento y el crecimiento del equipo');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.1.4', 'En representacion del equipo a los demas');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.1.4', 'Enfoques para la motivacion (incentivos, ejemplos, reconocimientos, etc.)');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.1.4', 'Estilos de liderazgo y de facilitacion (direccion, entrenamiento, apoyo, delegacion)');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.1.4', 'Gestion de procesos en equipo');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.1.4', 'Objetivos y metas del equipo');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.1.4', 'Tutoria y asesoramiento');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.1.5', 'Colaboracion tecnica con los miembros del equipo');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.1.5', 'Entornos a distancia, distribuidos y electronicos');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.1.5', 'Equipos Interdisciplinarios (inclusion de no ingenieros) ');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.1.5', 'Equipos pequeños vs. Equipos grandes');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.1.5', 'Trabajanco con equipos y personas no tecnicas');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.1.5', 'Trabajando en equipos de diferentes tipos');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.1', 'El contenido y la organización');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.1', 'El contexto de la comunicación');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.1', 'La combinacion apropiada de medios de comunicacion');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.1', 'La situacion de comunicacion');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.1', 'Las necesidades y el caracter de la audiencia');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.1', 'Objetivos de comunicacion');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.1', 'Un estilo de comunicación (proponer, revisar, colaborar, documentar, enseñar)');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.1', 'Una estrategia de comunicacion');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.10', 'Activacion y uso de redes para lograr objetivos');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.10', 'Apreciar a las personas con diferentes habilidades, culturas o experiencias');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.10', 'Construccion de amplias redes sociales');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.10', 'Participacion y compromiso con diferentes individuos');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.2', 'Argumentos logicos persuasivos');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.2', 'Comunicaciones interdisciplinarias e interculturales');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.2', 'Concision, precision y claridad del lenguaje');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.2', 'Factores retoricos (e.g. Inclinacion de la audiencia)');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.2', 'La estructura y relacion apropiada de ideas. Evidencia de apoyo creible, relevante y precisa');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.3', 'Diferentes estilos de escritura (informal, notas informales, informes, curriculum, etc.)');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.3', 'Escribir con correcta ortografia, puntuacion y gramatica');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.3', 'Escribir con fluidez y coherencia');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.3', 'Escritura tecnica');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.3', 'Formato del documento');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.4', 'Diferentes estilos electronicos (graficos, web, etc.)');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.4', 'Las normas relacionadas con el uso de correo electronico, correo de voz y videoconferencias');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.4', 'Preparar presentaciones electronicas');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.5', 'Boscetos y diagramas');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.5', 'Construccion de tablas, graficos y diagramas');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.5', 'Diagramacion y representacion tecnica formal');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.5', 'Uso de herramientas graficas');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.6', 'Comunicaciones no verbales apropiadas (gestos, contacto visual, postura)');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.6', 'Preparacion de presentaciones y medios de apoyo con el lenguaje, estilos, sincronizacion y fluidez apropiados');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.6', 'Solucion efectiva de preguntas');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.7', 'Dialogo constructivo');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.7', 'Escuchar atentamente a los demas, con la intencion de entender');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.7', 'Hacer preguntas reflexivas a los demas');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.7', 'Procesar diversos puntos de vista');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.7', 'Reconocer ideas que pueden ser mejor que las ideas propias');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.8', 'Difundir los conflictos');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.8', 'Indentificar desacuerdos, tensiones o conflictos potenciales');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.8', 'Llegar a un acuerdo sin comprometer los principios fundamentales');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.8', 'Negociacion para encontrar acuerdos razonables');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.9', 'Ajustar el enfoque de la sustentacion dependiendo de las caracteristicas de la audiencia');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.9', 'Evaluar que tan bien se es entedido por los demas');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.9', 'Explicacion clara del punto de vista propio');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '3.2.9', 'Explicar como se llego a una conclusion o interpretacion');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.1.1', 'Las responsabilidades de los ingenieros con la sociedad y con un futuro sostenible');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.1.1', 'Los objetivos y roles dela profesion de ingeniero');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.1.2', 'El impacto de la ingenieria en sistemas medioambientales, sociales, educativos y economicos en la cultura moderna');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.1.3', 'Creacion, uso y defensa de la propiedad intelectual');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.1.3', 'El rol de la sociedad y de sus agentes para regular la ingenieria');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.1.3', 'Establecimiento de licencias y estandares por parte de sociedades profesionales');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.1.3', 'La manera en que los systemas legales y politicos regulan e influencian la ingenieria');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.1.4', 'El discurso y el analisis apropiado para la discusion del lenguaje, pensamientos y valores');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.1.4', 'La diversa naturaleza e historia de las sociedades humanas, asi como sus tradiciones literarias, filosoficas y artisticas');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.1.5', 'La importancia de los valores y asuntos politicos, sociales, legales y medioambientales contemporaneos');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.1.5', 'Los mecanismos para la expansion y difusion del conocimiento');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.1.5', 'Los procesos mediante los cuales se establecen los valores contemporaneos, y el rol propio en estos procesos');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.1.6', 'Alianzas y acuerdos Internacionales e intergubernmentales');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.1.6', 'La internacionalizacion de la actividad humana');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.1.6', 'Las similitudes y diferencias en las normas politicas, sociales, economicas, empresariales y tecnicas de varias culturas');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.1.7', 'Definicion de sostenibilidad');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.1.7', 'Necesidad de aplicar los principios de la sostenibilidad en los esfuerzos de la ingenieria');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.1.7', 'Objetivos e importancia de la sostenibilidad');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.1.7', 'Principios de la sostenibilidad');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.2.1', 'Centralizada vs. Distribuida');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.2.1', 'Ciclos de desarrollo largos vs. Cortos');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.2.1', 'Con vs. Sin participacion de trabajadores organizados');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.2.1', 'Corporativa vs. Academica vs. Gubernamental vs. Sin animo de lucro (ONG)');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.2.1', 'Grande vs. Pequeña');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.2.1', 'Investigacion y desarrollo vs. Operacionales');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.2.1', 'Las diferencias entre procesos, cultura y metricas de éxito en diferentes culturas empresariales');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.2.1', 'Madura vs. Fase de crecimiento vs. Emprendedora');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.2.1', 'Mercado vs. Politica Impulsada');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.2.2', 'Alianzas claves y relaciones con los proveedores');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.2.2', 'Estrategia empresarial y asignacion de recursos');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.2.2', 'La mision, alcance y objetivos de la empresa');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.2.2', 'Los interesados y beneficiarios de la empresa (dueños, empleados, clientes, etc.)');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.2.2', 'Obligaciones de las partes interesadas');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.2.2', 'Una base de competencias empresariales y mercados');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.2.3', 'Finanzas y organizacion emprendedora');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.2.3', 'Oportunidades emprendedoras que pueden ser abordadas por la tecnologia');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.2.3', 'Tecnologias que pueden crear nuevos productos y sistemas');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.2.4', 'Cambio, dinamismo y evolucion de las organizaciones');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.2.4', 'La funcion de gestion');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.2.4', 'Las funciones de las organizaciones funcionales y de programa');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.2.4', 'Trabajar eficientemente dentro de la jerarquia y la organización');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.2.4', 'Varios roles y responsabilidades en la organizacion');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.2.5', 'Cultura y tradicion empresarial como un reflejo de la cultura nacional');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.2.5', 'Equivalencia de titulos y grados');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.2.5', 'Regulacion gubernamental del trabajo interno');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.2.6', 'El proceso de investigacion y desarrollo tecnologico');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.2.6', 'Identificando y evaluando tecnologias');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.2.6', 'Regimenes de propiedad intelectual y de patentes');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.2.6', 'Roadmaps de desarrollo tecnologico');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.2.7', 'Financiacion del Proyecto – inversiones, rendimiento, tiempo');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.2.7', 'Impacto de los proyectos en las finanzas de la empresa, en los ingresos y en el efectivo');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.2.7', 'Metas y metricas de financieras y de gestion');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.2.7', 'Planificacion y control financiero');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.3.1', 'Competencia e informacion de evaluacion comparativa');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.3.1', 'El lenguaje/formato de los objetivos y requerimientos');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.3.1', 'Factores que establecen el contexto de los objetivos del sistema');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.3.1', 'Influencias eticas, sociales, ambientales, legales y regulatorias');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.3.1', 'La probabilidad de cambios en los factores que influencian el sistema, sus objetivos y disponibilidad de recursos');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.3.1', 'Metas iniciales del proyecto (basadas en necesidades, oportunidades y otras influencias)');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.3.1', 'Metricas del desempeño del sistema');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.3.1', 'Necesidades ambientales');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.3.1', 'Necesidades del cliente y del mercado');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.3.1', 'Necesidades y Oportunidades');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.3.1', 'Objetivos y requerimientos del sistema');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.3.1', 'Objetivos, estrategias, capacidades y alianzas de la empresa');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.3.1', 'Oportunidades que se derivan de nuevas tecnologias o de necesidades latentes');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.3.1', 'Requisito de integridad y consistencia');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.3.2', 'Conceptos del sistema');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.3.2', 'Estructura y forma arquitectonica de alto nivel');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.3.2', 'Funciones necesarias del sistema (y especificaciones del comportamiento)');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.3.2', 'Incorporacion del nivel apropiado de tecnologia');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.3.2', 'La descomposicion de la forma en elementos, asignacion de funciones a los elementos, y definicion de interfaces');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.3.2', 'Recombinacion e intercambios entre conceptos');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.3.3', 'Consideracion de la implementacion y de las operaciones');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.3.3', 'Intercambios entre diferentes objetivos, funciones, estructuras y conceptos e iteraciones hasta la convergencia');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.3.3', 'Modelos apropiados de desempeño tecnico y otros atributos');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.3.3', 'Planes de gestion de la interfaz');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.3.3', 'Valor y costos del ciclo de vida (diseño, implementacion, operaciones, oportunidades, etc.)');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.3.4', 'Estimacion y asignacion de recursos');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.3.4', 'Gestion de la configuracion y la documentacion');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.3.4', 'Posibles mejoras al proceso de desarrollo');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.3.4', 'Proyecto de control de costos, desempeño y cronograma');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.3.4', 'Reconocimiento del valor ganado');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.3.4', 'Rendimiento en comparacion con la linea base');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.3.4', 'Revisiones y puntos de transicion adecuados');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.3.4', 'Riesgos y alternativas');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.4.1', 'Alojamiento de requerimientos en evolucion');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.4.1', 'Alternativas de diseño');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.4.1', 'Consideracion del ciclo de vida en el diseño');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.4.1', 'El diseño final');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.4.1', 'El diseño inicial');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.4.1', 'Iteracion hasta la convergencia');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.4.1', 'Optimizacion adecuada en presencia de restricciones');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.4.1', 'Prototipos experimentales y objetos de prueba en el desarrollo del diseño');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.4.1', 'Requerimientos para cada elemento o componente derivado de los objetivos y requerimientos del sistema');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.4.2', 'El proceso para productos derivables de unica plataforma');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.4.2', 'Las actividades en las fases del diseño del sistema (e.g. Diseño conceptual, preliminar y detallado)');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.4.2', 'Modelos de proceso apropiados para proyectos de desarrollo particulares (cascada, espiral, concurrente, etc.) ');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.4.3', 'Antes del trabajo de campo, la estandarizacion y la reutilizacion de diseño (incluyendo ingenieria inversa y refactorizacion, rediseño)');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.4.3', 'Captura del conocimiento de diseño');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.4.3', 'Conocimiento tecnico y cientifico');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.4.3', 'Modelos de pensamiento (solucion de problemas, indagacion, pensamiento sistemico, pensamiento creativo y critico)');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.4.4', 'Analisis cuantitativo de las alternativas');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.4.4', 'Calibracion y validacion de la herramienta de diseño');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.4.4', 'Modelado, simulaciones y pruebas');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.4.4', 'Refinamiento analitico del diseño');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.4.4', 'Tecnicas, herramientas y procesos apropiados');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.4.5', 'Ambientes de diseño multidisciplinares');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.4.5', 'Convenciones y supuestos disimiles');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.4.5', 'Diferencias en la madurez de modelos disciplinarios');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.4.5', 'Diseño multidisciplinar');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.4.5', 'Interacciones entre disciplinas');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.4.6', 'Desempeño, calidad, robustez, costo y valor del ciclo de vida');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.4.6', 'Diseño para:');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.4.6', 'Estetica');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.4.6', 'Evolucion, Mejora del producto');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.4.6', 'Implementacion, verificacion, pruebas y sostenibilidad ambiental');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.4.6', 'Interaccion y supervision de factores humanos');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.4.6', 'Mantenibilidad, confiabilidad y fiabilidad');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.4.6', 'Operaciones');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.4.6', 'Retiro, reutilizacion y reciclaje');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.4.6', 'Seguridad y proteccion');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.4.6', 'Sostenibilidad');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.5.1', 'Asignacion y disposicion de tareas');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.5.1', 'Consideraciones de sostenibilidad');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.5.1', 'Consideraciones para los usuarios/operadores humanos');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.5.1', 'El diseño del sistema de implementacion:');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.5.1', 'Flujo de trabajo');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.5.1', 'Los objetivos y metricas de rendimiento de la aplicacion, costo y calidad');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.5.2', 'Control de tolerancia, variabilidad, caracteristicas claves y procesos estadisticos');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.5.2', 'El montaje de piezas en construcciones mayores');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.5.2', 'La fabricacion de piezas');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.5.3', 'Algoritmos (estructuras de datos, control de flujo, flujo de datos)');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.5.3', 'El desgloce de componentes de alto nivel en modulos de diseño (incluyendo algoritmos y estructuras de datos) ');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.5.3', 'El diseño de bajo nivel (codigo)');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.5.3', 'El lenguaje y paradigmas de programacion');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.5.3', 'La construccion del sistema');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.5.4', 'Funcionamiento y seguridad del Hardware/Software');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.5.4', 'La integracion del software con hardware electronico (capacidad del procesador, comunicaciones, etc.) ');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.5.4', 'La integracion del software con sensores, controladores y hardware mecanico');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.5.5', 'La cerificacion de estandares');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.5.5', 'La validacion del rendimiento con las necesidades de los clientes');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.5.5', 'La verifiacion del rendimiento a los requerimientos del sistema');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.5.5', 'Procedimientos de pruebas y analisis (hardware vs. software, aceptacion vs. Calificacion)');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.5.6', 'Abastecimiento y Asociaciones');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.5.6', 'Aseguramiento de la calidad');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.5.6', 'Cadenas de suministro y logistica');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.5.6', 'Control de los costos, rendimiento y cronograma de la implementacion');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.5.6', 'La organizacion y estructura para la implementacion');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.5.6', 'Posibles mejoras al proceso de implementacion');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.5.6', 'Salud y seguridad humana');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.5.6', 'Seguridad ambiental');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.6.1', 'Analisis y modelado de las operaciones (y de la mision)');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.6.1', 'Arquitectura y desarrollo del proceso de operaciones');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.6.1', 'Los objetivos y metricas para el rendimiento operacional, valor y costos de operaciones sostenibles');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.6.1', 'Operaciones seguras y protegidas');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.6.2', 'Educacion para la operacion de los consumidores');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.6.2', 'Entrenar para operaciones profesionales:');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.6.2', 'Instrucción y programas');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.6.2', 'Interaccion entre procesos de operaciones');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.6.2', 'Procedimientos');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.6.2', 'Procesos de operaciones');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.6.2', 'SImulacion ');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.6.3', 'Mantenimiento y logistica');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.6.3', 'Rendimiento y confiabilidad del ciclo de vida');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.6.3', 'Retroalimentacion para facilitar la mejora del sistema');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.6.3', 'Valor y costos del ciclo de vida');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.6.4', 'Actualizaciones evolutivas del sistema');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.6.4', 'Mejoras con base en las necesidades detectadas en el funcionamiento');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.6.4', 'Mejoras pre-planeadas para el producto');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.6.4', 'Soluciones/Mejoras de contingencia resultantes de la necesidad operacional');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.6.5', 'Consideraciones ambientales para la eliminacion');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.6.5', 'La terminacion de la vida util');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.6.5', 'Opciones de eliminacion');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.6.5', 'Valor residual al fin del ciclo de vida');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.6.6', 'Aseguramiento de la calidad y la seguridad');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.6.6', 'Asociaciones y alianzas');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.6.6', 'Control de costos, desempeño y programacion de operaciones');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.6.6', 'Gestion del ciclo de vida');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.6.6', 'La organización y la estructura para las operaciones ');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.6.6', 'Posibles mejoras al proceso operacional');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.6.6', 'Salud y Seguridad Humana');
INSERT INTO `academ`.`Subtemas` (`id`, `tema`, `nombre`) VALUES (NULL, '4.6.6', 'Seguridad Ambiental');

COMMIT;
