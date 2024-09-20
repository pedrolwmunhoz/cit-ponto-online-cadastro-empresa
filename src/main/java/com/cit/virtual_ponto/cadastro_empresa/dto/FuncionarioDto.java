package com.cit.virtual_ponto.cadastro_empresa.dto;

import lombok.Data;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Data
public class FuncionarioDto extends PessoaDto {

    @NotBlank(message = "Nome não pode ser vazio")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @NotNull(message = "Funcionario ID não pode ser nulo")
    private Integer id_funcionario;

    @NotBlank(message = "CPF não pode ser vazio")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos numéricos")
    private String cpf;

    @NotBlank(message = "RG não pode ser vazio")
    @Pattern(regexp = "\\d{9}", message = "RG deve conter 9 dígitos numéricos")
    private String rg;

    @NotNull(message = "Data de nascimento não pode ser nula")
    private LocalDate dataNascimento;

    @NotNull(message = "ID folha pagamento não pode ser nula")
    private Integer id_folha_pagamento;

    @NotBlank(message = "ID empresa não pode ser vazio")
    private Integer id_empresa;

    @NotBlank(message = "ID departamento não pode ser vazio")
    private Integer id_departamento;

    @NotBlank(message = "ID cargo não pode ser vazio")
    private Integer id_cargo;

    @NotBlank(message = "ID jornada trabalho não pode ser vazio")
    private Integer id_jornada_trabalho;

    @NotBlank(message = "ID banco de horas não pode ser vazio")
    private Integer id_banco_horas;
}
