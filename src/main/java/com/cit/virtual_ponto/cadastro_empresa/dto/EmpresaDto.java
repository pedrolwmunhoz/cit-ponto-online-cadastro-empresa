package com.cit.virtual_ponto.cadastro_empresa.dto;

import lombok.Data;

import java.util.List;

@Data
public class EmpresaDto {

    private Long empresaId;
    private String nomeEmpresa;
    private String razaoSocial;
    private String cnpj;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String telefone;
    private String email;
    private List<FuncionarioDto> funcionarios;
}
