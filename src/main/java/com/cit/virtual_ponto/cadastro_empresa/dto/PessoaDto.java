package com.cit.virtual_ponto.cadastro_empresa.dto;

import lombok.Data;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Data
public abstract class PessoaDto {

    private Integer pessoaId;

    @NotBlank(message = "Nome não pode ser vazio")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @NotBlank(message = "O email não pode ser vazio")
    @Email(message = "O email deve ser válido")
    @Size(max = 100, message = "O email deve ter no máximo 100 caracteres")
    private String email;

    @NotBlank(message = "Senha não pode ser vazio")
    @Size(max = 100, message = "O email deve ter no máximo 100 caracteres")
    private String senha;

    private @Valid EnderecoDto endereco;

    private @Valid TelefoneDto telefone;

    private @Valid LoginDto login;

    
}
