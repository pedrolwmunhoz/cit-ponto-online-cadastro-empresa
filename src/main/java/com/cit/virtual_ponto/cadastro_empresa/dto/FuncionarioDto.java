package com.cit.virtual_ponto.cadastro_empresa.dto;

import lombok.Data;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.cit.virtual_ponto.cadastro_empresa.models.Endereco;

@Data
public class FuncionarioDto extends PessoaDto {

    @NotNull(message = "Empresa ID não pode ser nulo")
    private Long empresaId;

    private Long departamentoId;

    @NotBlank(message = "CPF não pode ser vazio")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos numéricos")
    private String cpf;

    @NotBlank(message = "RG não pode ser vazio")
    @Pattern(regexp = "\\d{9}", message = "RG deve conter 9 dígitos numéricos")
    private String rg;

    @NotNull(message = "Data de nascimento não pode ser nula")
    private LocalDate dataNascimento;

    @NotNull(message = "Data de admissão não pode ser nula")
    private LocalDate dataAdmissao;

    @NotBlank(message = "Cargo não pode ser vazio")
    private String cargo;

    @NotNull(message = "Jornada de trabalho não pode ser nula")
    private Integer jornadaTrabalho; // Em horas

    private LocalTime horarioEntrada;

    private LocalTime horarioSaida;

    @NotBlank(message = "Tipo de contrato não pode ser vazio")
    private String tipoContrato;

    @NotNull(message = "Salário não pode ser nulo")
    private BigDecimal salario;

    private LocalTime intervaloDescanso;

    @NotBlank(message = "Matrícula não pode ser vazia")
    private String matricula;

    private String situacao;

    private @Valid Endereco endereco;

}
