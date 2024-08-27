package com.cit.virtual_ponto.cadastro_empresa.dto;

import lombok.Data;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

@Data
public abstract class PessoaDto {

    private Long pessoaId;

    @NotBlank(message = "Nome não pode ser vazio")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @NotBlank(message = "O telefone não pode ser vazio")
    @Pattern(regexp = "\\d{10,11}", message = "O telefone deve conter 10 ou 11 dígitos")
    private String telefone;

    @NotBlank(message = "O email não pode ser vazio")
    @Email(message = "O email deve ser válido")
    @Size(max = 100, message = "O email deve ter no máximo 100 caracteres")
    private String email;

    @NotBlank(message = "Senha não pode ser vazio")
    @Size(min = 8, max = 100, message = "Senha deve conter 8 ou mais caracteres")
    private String senha;

    
}
