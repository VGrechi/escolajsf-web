# Repositório: escolajsf-web
Descrição: Aplicação Web de uma escola hipotética com JSF, Primefaces, Maven, Spring Security e MySQL

## Lista de Funcionalidades
**Perfil Administrador:**
- Cadastro de Alunos
- Cadastro de Professores
- Cadastro de Turmas
- Associação de Alunos e Professores a Turmas

**Perfil Professor:**
- Cadastro de Atividades
- Associação de Atividades a Turmas
- Acompanhamento das Atividades (Download de Arquivos)

**Perfil Aluno:**
- Entrega de Tarefas (Upload de arquivos)
- Consulta das Tarefas Entregues (Download de Arquivos)

**Todos os perfis:**
- Alterar Senha

## Resumo da Especificação

**Perfil Administrador:**
> O Administrador é pré-configurado e é resposável por cadastrar Alunos, Professores e Turmas. As Turmas não podem ser excluídas, mas possuem os status "Ativo" e "Inativo". Um Professor não pode ser excluído se estiver vinculado a uma turma. No cadastro de Turmas, o Administrador seleciona o Professor responsável e os Alunos que farão parte da Turma.

**Perfil Professor:**
> O Professor é responsável por cadastrar atividades e vincula-las as Turmas sob sua responsabilidade. As Atividades possuem data de início e fim e podem ser usadas para diferentes Turmas do mesmo professor. Ele também poderá acompanhar todas as Atividades de determinada Turma, podendo obter os arquivos enviados pelos Alunos.

**Perfil Aluno:**
> O Aluno pode enviar arquivos para Atividades ativas (quando a data atual é maior que a data inicial e menor que a data final da Atividade). Ele pode sobrescrever o arquivo enviado enquanto a Atividade estiver ativa. Todos as Atividades com Entrega podem ser consultadas na Aba "Consultar".

**Todos os perfis:**
> Todos os perfis de usuário podem alterar sua própria senha.
