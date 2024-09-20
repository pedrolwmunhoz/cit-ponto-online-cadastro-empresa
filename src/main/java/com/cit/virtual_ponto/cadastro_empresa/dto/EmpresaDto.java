package com.cit.virtual_ponto.cadastro_empresa.dto;

import lombok.Data;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;
@Data
public class EmpresaDto extends PessoaDto{


    @NotBlank(message = "O nome fantasia não pode ser vazio")
    @Size(max = 100, message = "O nome fantasia deve ter no máximo 100 caracteres")
    private String nomeFantasia;

    @NotBlank(message = "A razão social não pode ser vazio")
    @Size(max = 100, message = "O razão social deve ter no máximo 100 caracteres")
    private String razaoSocial;

    @NotBlank(message = "A inscrição estadual não pode ser vazia")
    @Size(max = 100, message = "A inscrição estadual deve ter no máximo 100 caracteres")
    private String inscricaoEstadual;

    @NotBlank(message = "O CNPJ não pode ser vazio")
    @Pattern(regexp = "\\d{14}", message = "O CNPJ deve conter 14 dígitos")
    private String cnpj;

    private List<@Valid FuncionarioDto> funcionarios;
}
