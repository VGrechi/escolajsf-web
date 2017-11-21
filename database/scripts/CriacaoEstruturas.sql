/*
 * 
 * CRIAÇÃO DO BANCO DE DADOS
 * 
 * */
CREATE DATABASE `escolaJSF` /*!40100 DEFAULT CHARACTER SET latin1 */;

/*
 * 
 * CRIAÇÃO DAS ESTRUTURAS
 * 
 * 
 */
USE `escolaJSF`;

CREATE TABLE `tipousuario` (
  `TipoUsuario` char(2) NOT NULL,
  `Descricao` varchar(20) NOT NULL,
  PRIMARY KEY (`TipoUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `usuario` (
  `CodUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `TipoUsuario` char(2) NOT NULL,
  `Nome` varchar(100) DEFAULT NULL,
  `Email` varchar(100) NOT NULL,
  `Senha` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`CodUsuario`),
  KEY `TipoUsuario` (`TipoUsuario`),
  CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`TipoUsuario`) REFERENCES `tipousuario` (`TipoUsuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

CREATE TABLE `turma` (
  `CodTurma` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(100) NOT NULL,
  `Status` char(1) DEFAULT NULL,
  `CodUsuario` int(11) DEFAULT NULL,
  PRIMARY KEY (`CodTurma`),
  KEY `CodUsuario` (`CodUsuario`),
  CONSTRAINT `turma_ibfk_1` FOREIGN KEY (`CodUsuario`) REFERENCES `usuario` (`CodUsuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

CREATE TABLE `atividade` (
  `CodAtividade` int(11) NOT NULL AUTO_INCREMENT,
  `CodUsuario`int(11) NOT NULL,
  `DataInicial` datetime NOT NULL,
  `DataFinal` datetime NOT NULL,
  `Descricao` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`CodAtividade`),
  CONSTRAINT `atividade_ibfk_1` FOREIGN KEY (`CodUsuario`) REFERENCES `usuario` (`CodUsuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

CREATE TABLE `disciplina` (
  `CodTurma` int(11) NOT NULL,
  `CodUsuario` int(11) NOT NULL,
  PRIMARY KEY (`CodTurma`,`CodUsuario`),
  KEY `CodUsuario` (`CodUsuario`),
  CONSTRAINT `disciplina_ibfk_1` FOREIGN KEY (`CodUsuario`) REFERENCES `usuario` (`CodUsuario`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `disciplina_ibfk_2` FOREIGN KEY (`CodTurma`) REFERENCES `turma` (`CodTurma`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `entrega` (
  `CodEntrega` int(11) NOT NULL AUTO_INCREMENT,
  `CodTurma` int(11) NOT NULL,
  `CodAtividade` int(11) NOT NULL,
  `CodUsuario` int(11) NOT NULL,
  `DataSubmetido` datetime NOT NULL,
  `NomeArquivo` varchar(100) NOT NULL,
  `ConteudoArquivo` blob,
  PRIMARY KEY (`CodEntrega`),
  KEY `CodTurma` (`CodTurma`),
  KEY `CodAtividade` (`CodAtividade`),
  KEY `CodUsuario` (`CodUsuario`),
  CONSTRAINT `entrega_ibfk_1` FOREIGN KEY (`CodTurma`) REFERENCES `turma` (`CodTurma`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `entrega_ibfk_2` FOREIGN KEY (`CodAtividade`) REFERENCES `atividade` (`CodAtividade`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `entrega_ibfk_3` FOREIGN KEY (`CodUsuario`) REFERENCES `usuario` (`CodUsuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tarefa` (
  `CodTurma` int(11) NOT NULL,
  `CodAtividade` int(11) NOT NULL,
  PRIMARY KEY (`CodTurma`,`CodAtividade`),
  KEY `CodAtividade` (`CodAtividade`),
  CONSTRAINT `tarefa_ibfk_1` FOREIGN KEY (`CodTurma`) REFERENCES `turma` (`CodTurma`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tarefa_ibfk_2` FOREIGN KEY (`CodAtividade`) REFERENCES `atividade` (`CodAtividade`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;