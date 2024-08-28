package com.cit.virtual_ponto.cadastro_empresa.dto;

import lombok.Data;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

import com.cit.virtual_ponto.cadastro_empresa.models.Endereco;
import com.cit.virtual_ponto.cadastro_empresa.models.Login;
import com.cit.virtual_ponto.cadastro_empresa.models.Telefone;

@Data
public class EmpresaDto extends PessoaDto{

    @NotBlank(message = "A inscrição estadual não pode ser vazia")
    @Size(max = 100, message = "A inscrição estadual deve ter no máximo 100 caracteres")
    private String inscricaoEstadual;

    @NotBlank(message = "O CNPJ não pode ser vazio")
    @Pattern(regexp = "\\d{14}", message = "O CNPJ deve conter 14 dígitos")
    private String cnpj;

    private List<@Valid FuncionarioDto> funcionarios;
}
