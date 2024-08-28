package com.cit.virtual_ponto.cadastro_empresa.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class LoginDto {

    @NotBlank(message = "Email não pode ser vazio")
    @Email(message = "Email deve ser válido")
    private String email;

    @NotBlank(message = "Senha não pode ser vazio")
    @Size(min = 8, max = 100, message = "Senha deve conter 8 ou mais caracteres")
    private String senha;

    @NotBlank(message = "ID historico login não pode ser vazio")
    @Size(min = 1, max = 100, message = "ID historico login deve conter 1 ou mais caracteres")
    private String hitorico_login_id;
}
