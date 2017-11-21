/*
 * 
 * TABELA TIPOUSUARIO
 * 
 * */
INSERT INTO `escolajsf`.`tipousuario`
(`TipoUsuario`,
`Descricao`)
VALUES
('AD',
'Administrador');

INSERT INTO `escolajsf`.`tipousuario`
(`TipoUsuario`,
`Descricao`)
VALUES
('PR',
'Professor');

INSERT INTO `escolajsf`.`tipousuario`
(`TipoUsuario`,
`Descricao`)
VALUES
('AL',
'Aluno');


/*
 * 
 * TABELA USUARIO - ADMINISTRADOR DO SISTEMA
 * Senha descriptografada: 123
 * 
 * */
INSERT INTO `escolajsf`.`usuario`
(`CodUsuario`,
`TipoUsuario`,
`Nome`,
`Email`,
`Senha`)
VALUES
(1,
'AD',
'Administrador Do Sistema',
'admin',
'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3');


