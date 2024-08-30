package com.cit.virtual_ponto.cadastro_empresa.dto;

import lombok.Data;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Data
public class FuncionarioDto extends PessoaDto {

    @NotNull(message = "Funcionario ID não pode ser nulo")
    private Integer funcionario_id;

    @NotBlank(message = "CPF não pode ser vazio")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos numéricos")
    private String cpf;

    @NotBlank(message = "RG não pode ser vazio")
    @Pattern(regexp = "\\d{9}", message = "RG deve conter 9 dígitos numéricos")
    private String rg;

    @NotNull(message = "Data de nascimento não pode ser nula")
    private LocalDate dataNascimento;

    @NotNull(message = "ID folha pagamento não pode ser nula")
    private Integer folha_pagamento_id;

    @NotBlank(message = "ID empresa não pode ser vazio")
    private Integer empresa_Id;

    @NotBlank(message = "ID departamento não pode ser vazio")
    private Integer departamento_id;

    @NotBlank(message = "ID cargo não pode ser vazio")
    private Integer cargo_id;

    @NotBlank(message = "ID marcação de ponto não pode ser vazio")
    private Integer marcacao_ponto_id;

    @NotBlank(message = "ID banco de horas não pode ser vazio")
    private Integer banco_horas_id;
}
