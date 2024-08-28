package com.cit.virtual_ponto.cadastro_empresa.models;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

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

    @Column(name = "folha_pagamento_id", nullable = false)
    private Integer folha_pagamento_id;

    @Column(name = "empresa_id", nullable = false)
    private Integer empresa_id;

    @Column(name = "departamento_id", nullable = false)
    private Integer departamento_id;

    @Column(name = "cargo_id", nullable = false)
    private Integer cargo_id;

    @Column(name = "marcacao_ponto_id", nullable = false)
    private Integer marcacao_ponto_id;

    @Column(name = "banco_horas_id", nullable = false)
    private Integer banco_horas_id;
}
