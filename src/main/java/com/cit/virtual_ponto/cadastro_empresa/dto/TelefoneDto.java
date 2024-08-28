package com.cit.virtual_ponto.cadastro_empresa.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class TelefoneDto {

    @NotBlank(message = "O DDD não pode ser vazio")
    @Size(min = 2, max = 2, message = "O DDD deve ter 2 caracteres")
    private String ddd;

    @NotBlank(message = "O numero não pode ser vazio")
    @Pattern(regexp = "\\d{9,11}", message = "O telefone deve conter 9 ou 11 dígitos")
    private String numero;
}
