package com.cit.virtual_ponto.cadastro_empresa.models;

import lombok.Data;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Table(name = "pessoa_fisica")
public class PessoaFisica extends Pessoa {

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Column(name = "rg")
    private String rg;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "data_admissao", nullable = false)
    private LocalDate dataAdmissao;

    @Column(name = "cargo", nullable = false)
    private String cargo;

    @Column(name = "jornada_trabalho")
    private Integer jornadaTrabalho; // Em horas

    @Column(name = "horario_entrada")
    private LocalTime horarioEntrada;

    @Column(name = "horario_saida")
    private LocalTime horarioSaida;

    @Column(name = "tipo_contrato", nullable = false)
    private String tipoContrato;

    @Column(name = "salario", nullable = false)
    private BigDecimal salario;

    @Column(name = "intervalo_descanso")
    private LocalTime intervaloDescanso;

    @Column(name = "matricula", nullable = false, unique = true)
    private String matricula;

    @Column(name = "situacao")
    private String situacao;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "departamento_id", nullable = false)
    private Departamento departamento;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "empresa_id", nullable = false)
    private PessoaJuridica empresa;
}
