package com.cit.virtual_ponto.cadastro_empresa.dto;

import lombok.Data;

@Data
public class FuncionarioDto {

    private Long funcionarioId;
    private Long empresaId;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
}
