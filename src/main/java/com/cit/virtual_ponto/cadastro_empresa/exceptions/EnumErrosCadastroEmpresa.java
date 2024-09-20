package com.cit.virtual_ponto.cadastro_empresa.exceptions;

public enum EnumErrosCadastroEmpresa {
    ID_EMPRESA_NAO_ENCONTRADA("Empresa com o ID não encontrado"),

    ERRO_CADASTRAR_EMPRESA("Erro ao cadastrar empresa"),
    ERRO_ATUALIZAR_EMPRESA("Erro ao atualizar empresa"),
    ERRO_EXCLUIR_EMPRESA("Erro ao excluir empresa"),
    ERRO_BUSCAR_EMPRESA("Erro ao buscar empresa"),
    ERRO_LISTAR_EMPRESA("Erro ao listar empresa");

    private final String mensagemErro;

    EnumErrosCadastroEmpresa(String mensagemErro) {
        this.mensagemErro = mensagemErro;
    }

    public String getMensagemErro() {
        return mensagemErro;
    }
}
