package com.javaee.escola.modelo.enums.tabelas;

public enum EnumTbEntrega {

	COD_ENTREGA("EntregaCodEntrega"),
	NOME_ARQUIVO("EntregaNomeArquivo"),
	CONTEUDO_ARQUIVO("EntregaCOnteudoArquivo"),
	DATA_SUBMETIDO("EntregaDataSubmetido"),
	COD_USUARIO("EntregaCodUsuario"),
	COD_TURMA("EntregaCodTurma"),
	COD_ATIVIDADE("EntregaCodAtividade"),
	
	;
	
	private String alias;

	private EnumTbEntrega(String alias) {
		this.alias = alias;
	}

	public String getAlias() {
		return alias;
	}
}
