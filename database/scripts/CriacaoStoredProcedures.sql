DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Proc_AtividadeBuscarAtividadePorId`(IN ParamCodAtividade INT)
BEGIN

SELECT 
	CodAtividade AS AtividadeCodAtividade,
    DataInicial AS AtividadeDataInicial,
    DataFinal AS AtividadeDataFinal,
    Descricao AS AtividadeDescricao
FROM Atividade
WHERE CodAtividade = ParamCodAtividade;

END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Proc_AtividadeBuscarTodosPorUsuario`(IN ParamCodUsuario INT)
BEGIN

SELECT 
	CodAtividade AS AtividadeCodAtividade,
    CodUsuario AS AtividadeCodUsuario,
    DataInicial AS AtividadeDataInicial,
    DataFinal AS AtividadeDataFinal,
    Descricao AS AtividadeDescricao
FROM Atividade
WHERE CodUsuario = ParamCodUsuario;

END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Proc_AtividadeInserirOuAtualizar`(IN ParamCodAtividade INT, IN ParamCodUsuario INT,
				IN ParamDataInicial DATETIME, IN ParamDataFinal DATETIME, 
                IN ParamDescricao VARCHAR(300), OUT OutParamCodAtividade INT)
BEGIN

IF(ParamCodAtividade = 0) THEN

	INSERT INTO Atividade
		(CodAtividade, CodUsuario, DataInicial, DataFinal, Descricao)
	VALUES
		(ParamCodAtividade, ParamCodUsuario, ParamDataInicial, ParamDataFinal, ParamDescricao);
        
	SET OutParamCodAtividade = (SELECT MAX(CodAtividade) FROM Atividade);

ELSE

	UPDATE Atividade
	SET
		CodUsuario = ParamCodUsuario,
		DataInicial = ParamDataInicial,
		DataFinal = ParamDataFinal,
		Descricao = ParamDescricao
	WHERE CodAtividade = ParamCodAtividade;
    
    SET OutParamCodAtividade = ParamCodAtividade;

END IF;

END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Proc_AtividadeRemoverAtividade`(IN ParamCodAtividade INT)
BEGIN

DELETE FROM Atividade WHERE CodAtividade = ParamCodAtividade;

END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Proc_DisciplinaBuscarAlunosPorTurma`(IN ParamCodTurma INT)
BEGIN

	SELECT 
		U.CodUsuario AS UsuarioCodUsuario,
        U.Nome AS UsuarioNome
	FROM Usuario U
    JOIN Disciplina D ON (D.CodUsuario = U.CodUsuario)
    WHERE D.CodTurma = ParamCodTurma;
    
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Proc_DisciplinaInserirOuAtualizar`(IN ParamCodTurma INT, IN ParamCodUsuario INT)
BEGIN

IF NOT EXISTS (SELECT * FROM Disciplina 
				WHERE CodTurma = ParamCodTurma AND CodUsuario = ParamCodUsuario) THEN

	INSERT INTO Disciplina 
		(CodTurma, CodUsuario)
	VALUES
		(ParamCodTurma, ParamCodUsuario);

END IF;

END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Proc_DisciplinaRemoverDisciplina`(IN ParamCodTurma INT, IN ParamCodUsuario INT)
BEGIN

	DELETE FROM Disciplina 
    WHERE CodTurma = ParamCodTurma AND CodUsuario = ParamCodUsuario;

END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Proc_EntregaBuscarTodosPorTurmaAtividade`(IN ParamCodTurma INT, IN ParamCodAtividade INT)
BEGIN

SELECT 
	E.CodEntrega AS EntregaCodEntrega,
    E.DataSubmetido AS EntregaDataSubmetido,
	E.NomeArquivo AS EntregaNomeArquivo,
    E.ConteudoArquivo AS EntregaConteudoArquivo,
    E.CodTurma AS EntregaCodTurma,
    E.CodAtividade AS EntregaCodAtividade,
    U.CodUsuario AS UsuarioCodUsuario,
    U.Nome AS UsuarioNome
FROM Entrega E
JOIN Usuario U ON (U.CodUsuario = E.CodUsuario)
WHERE E.CodAtividade = ParamCodAtividade
AND E.CodTurma = ParamCodTurma
ORDER BY U.Nome;

END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Proc_EntregaBuscarTodosPorUsuario`(IN ParamCodUsuario INT)
BEGIN

	SELECT
		E.CodEntrega AS EntregaCodEntrega,
		E.DataSubmetido As EntregaDataSubmetido,
        E.NomeArquivo AS EntregaNomeArquivo,
        E.ConteudoArquivo As EntregaConteudoArquivo,
        T.CodTurma AS TurmaCodTurma,
		T.Nome AS TurmaNome,
        A.CodAtividade As AtividadeCodAtividade,
        A.Descricao As AtividadeDescricao
	FROM Entrega E
    JOIN Turma T ON (T.CodTurma = E.CodTurma)
    JOIN Atividade A ON (A.CodAtividade = E.CodAtividade)
    WHERE E.CodUsuario = ParamCodUsuario
    ORDER BY E.DataSubmetido DESC, T.Nome ASC;

END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Proc_EntregaInserirOuAtualizar`(IN ParamCodEntrega INT, 
					IN ParamCodTurma INT,	IN ParamCodAtividade INT,	
                    IN ParamCodUsuario INT, IN ParamNomeArquivo VARCHAR(100), 
                    IN ParamConteudoArquivo BLOB, OUT OutParamCodEntrega INT)
BEGIN

IF(ParamCodEntrega = 0) THEN

	INSERT INTO Entrega
		(CodEntrega, CodTurma, CodAtividade, CodUsuario, DataSubmetido, NomeArquivo, ConteudoArquivo)
	VALUES
		(ParamCodEntrega, ParamCodTurma, ParamCodAtividade, ParamCodUsuario, NOW(), ParamNomeArquivo, ParamConteudoArquivo);
        
	SET OutParamCodEntrega = (SELECT MAX(CodEntrega) FROM Entrega);

ELSE

	UPDATE Entrega
	SET
		DataSubmetido = NOW(),
        NomeArquivo = ParamNomeArquivo,
        ConteudoArquio = ParamConteudoArquivo
	WHERE CodEntrega = ParamCodEntrega;
    
    SET OutParamCodEntrega = ParamCodEntrega;

END IF;
	
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Proc_TarefaAdicionarTarefa`(IN ParamCodTurma INT, IN ParamCodAtividade INT)
BEGIN

INSERT INTO Tarefa
	(CodTurma, CodAtividade)
VALUES
	(ParamCodTurma, ParamCodAtividade);

END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Proc_TarefaBuscarAtividadesPorTurma`(IN ParamCodTurma INT)
BEGIN


SELECT 
	A.CodAtividade AS AtividadeCodAtividade,
    A.DataInicial AS AtividadeDataInicial,
    A.DataFinal AS AtividadeDataFinal,
    A.Descricao AS AtividadeDescricao
FROM Atividade A
JOIN Tarefa T ON (T.CodAtividade = A.CodAtividade)
WHERE T.CodTurma = ParamCodTurma
AND NOW() BETWEEN A.DataInicial AND A.DataFinal;

END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Proc_TarefaInserirOuAtualizar`(IN ParamCodTurma INT, IN ParamCodAtividade INT)
BEGIN

IF NOT EXISTS (SELECT * FROM Tarefa 
				WHERE CodTurma = ParamCodTurma AND CodAtividade = ParamCodAtividade) THEN

	INSERT INTO Tarefa 
		(CodTurma, CodAtividade)
	VALUES
		(ParamCodTurma, ParamCodAtividade);

END IF;

END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Proc_TurmaBuscarTodos`()
BEGIN

SELECT
	T.CodTurma AS TurmaCodTurma,
    T.Nome AS TurmaNome,
    U.CodUsuario AS UsuarioCodUsuario,
    U.Nome AS UsuarioNome,
    COALESCE(COUNT(D.CodUsuario), 0) AS TurmaCampoVirtualTotalDeAlunos,
    T.Status AS TurmaStatus
FROM Turma T
JOIN Usuario U ON (T.CodUsuario = U.CodUsuario)
LEFT JOIN Disciplina D ON (T.CodTurma = D.CodTurma)
GROUP BY D.CodTurma;

END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Proc_TurmaBuscarTodosPorUsuario`(IN ParamCodUsuario INT, IN ParamTipoUsuario CHAR(2))
BEGIN

IF (ParamTipoUsuario = 'PR') THEN

	SELECT
		T.CodTurma AS TurmaCodTurma,
		T.Nome AS TurmaNome,
		U.CodUsuario AS UsuarioCodUsuario,
		U.Nome AS UsuarioNome,
		COALESCE(COUNT(D.CodUsuario), 0) AS TurmaCampoVirtualTotalDeAlunos,
		T.Status AS TurmaStatus
	FROM Turma T
	JOIN Usuario U ON (T.CodUsuario = U.CodUsuario)
	LEFT JOIN Disciplina D ON (T.CodTurma = D.CodTurma)
    WHERE T.CodUsuario = ParamCodUsuario
    AND T.Status = 'A'
	GROUP BY D.CodTurma;


ELSE

	SELECT
		T.CodTurma AS TurmaCodTurma,
		T.Nome AS TurmaNome,
		U.CodUsuario AS UsuarioCodUsuario,
		U.Nome AS UsuarioNome,
		COALESCE(COUNT(D.CodUsuario), 0) AS TurmaCampoVirtualTotalDeAlunos,
		T.Status AS TurmaStatus
	FROM Turma T
	JOIN Usuario U ON (T.CodUsuario = U.CodUsuario)
	LEFT JOIN Disciplina D ON (T.CodTurma = D.CodTurma)
    WHERE D.CodUsuario = ParamCodUsuario
	AND T.Status = 'A'
	GROUP BY D.CodTurma;


END IF;








END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Proc_TurmaBuscarTurmaPorId`(IN ParamCodTurma INT)
BEGIN

SELECT
	T.CodTurma AS TurmaCodTurma,
    T.Nome AS TurmaNome,
    U.CodUsuario AS UsuarioCodUsuario,
    U.Nome AS UsuarioNome,
    COALESCE(COUNT(D.CodUsuario), 0) AS TurmaCampoVirtualTotalDeAlunos,
    T.Status AS TurmaStatus
FROM Turma T
JOIN Usuario U ON (T.CodUsuario = U.CodUsuario)
LEFT JOIN Disciplina D ON (T.CodTurma = D.CodTurma)
WHERE T.CodTurma = ParamCodTurma
GROUP BY D.CodTurma;

END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Proc_TurmaInserirOuAtualizar`(IN ParamCodTurma INT,
			IN ParamNome VARCHAR(100), IN ParamStatus CHAR(1), 
            IN ParamCodUsuario INT, OUT OutParamCodTurma INT)
BEGIN

IF(ParamCodTurma = 0) THEN
	INSERT INTO Turma
		(CodTurma,
		Nome,
		Status,
		CodUsuario)
	VALUES
		(ParamCodTurma,
		ParamNome,
		ParamStatus,
		ParamCodUsuario);
        
	SET OutParamCodTurma = (SELECT MAX(CodTurma) FROM Turma);

ELSE

	UPDATE Turma
	SET
		Nome = ParamNome,
		Status = ParamStatus,
		CodUsuario = CodUsuario
	WHERE CodTurma = ParamCodTurma;
    
    SET OutParamCodTurma = ParamCodTurma;
    
END IF;




END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Proc_UsuarioAlterarSenha`(IN ParamCodUsuario INT, IN ParamSenha VARCHAR(100))
BEGIN

UPDATE Usuario
SET
	Senha = ParamSenha
WHERE CodUsuario = ParamCodUsuario;

END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Proc_UsuarioBuscarParaLogin`(IN ParamEmail VARCHAR(100), IN ParamSenha VARCHAR(100),
													OUT OutParamResult INT)
BEGIN

SELECT SQL_CALC_FOUND_ROWS 
	U.CodUsuario AS UsuarioCodUsuario,
    U.Nome AS UsuarioNome,
    U.Email AS UsuarioEmail,
    U.Senha AS UsuarioSenha,
    TU.TipoUsuario AS TipoUsuarioTipo,
    TU.Descricao AS TipoUsuarioDescricao
FROM Usuario U
JOIN TipoUsuario TU ON (TU.TipoUsuario = U.TipoUsuario)
WHERE U.Email = ParamEmail AND U.Senha = ParamSenha
LIMIT 1;

IF(FOUND_ROWS() > 0) THEN
	SET OutParamResult = 0;
ELSE
	SET OutParamResult = 100;
END IF;




END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Proc_UsuarioBuscarTodos`(IN ParamTipoUsuario CHAR(2))
BEGIN

SELECT
	CodUsuario AS UsuarioCodUsuario,
    Nome AS UsuarioNome,
    Email AS UsuarioEmail,
    TipoUsuario AS UsuarioTipoUsuario
FROM Usuario
WHERE TipoUsuario = ParamTipoUsuario;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Proc_UsuarioInserirOuAtualizar`(IN ParamCodUsuario INT,
					IN ParamTipoUsuario CHAR(2),
                    IN ParamNome VARCHAR(100),
                    IN ParamEmail VARCHAR(100),
                    IN ParamSenha VARCHAR(100))
BEGIN


IF(ParamCodUsuario = 0) THEN

INSERT INTO Usuario
	(CodUsuario, TipoUsuario, Nome, Email, Senha)
VALUES
	(ParamCodUsuario, ParamTipoUsuario, ParamNome, ParamEmail, ParamSenha);

ELSE

	IF(ParamSenha IS NULL) THEN
    
		UPDATE Usuario
			SET
				Nome = ParamNome,
				Email = ParamEmail
			WHERE CodUsuario = ParamCodUsuario;
    
    ELSE
    
		UPDATE Usuario
			SET
				Nome = ParamNome,
				Email = ParamEmail,
				Senha = ParamSenha
			WHERE CodUsuario = ParamCodUsuario;
    
    END IF;

END IF;

END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Proc_UsuarioRemoverUsuario`(IN ParamCodUsuario INT, OUT ParamResult INT)
BEGIN

DECLARE VarCountTurmas BOOLEAN;

SET VarCountTurmas = EXISTS (SELECT * FROM Turma WHERE CodUsuario = ParamCodUsuario);

IF (VarCountTurmas) THEN

	SET ParamResult = 900;
    
ELSE
	
    DELETE FROM Usuario WHERE CodUsuario = ParamCodUsuario;
    SET ParamResult = 0;
	
END IF;

END$$
DELIMITER ;
