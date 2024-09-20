package com.cit.virtual_ponto.cadastro_empresa.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class EnderecoDto {
    @NotBlank(message = "O logradouro não pode ser vazio")
    @Size(max = 255, message = "O logradouro deve ter no máximo 255 caracteres")
    private String logradouro;

    @NotBlank(message = "O número não pode ser vazio")
    @Size(max = 10, message = "O número deve ter no máximo 10 caracteres")
    private String numero;

    @Size(max = 255, message = "O complemento deve ter no máximo 255 caracteres")
    private String complemento;

    @NotBlank(message = "O bairro não pode ser vazio")
    @Size(max = 100, message = "O bairro deve ter no máximo 100 caracteres")
    private String bairro;

    @NotBlank(message = "A cidade não pode ser vazia")
    @Size(max = 100, message = "A cidade deve ter no máximo 100 caracteres")
    private String cidade;

    @NotBlank(message = "O estado não pode ser vazio")
    @Size(min = 2, max = 2, message = "O estado deve ter 2 caracteres")
    private String estado;

    @NotBlank(message = "O CEP não pode ser vazio")
    @Pattern(regexp = "\\d{8}", message = "O CEP deve conter 8 dígitos")
    private String cep;
}
