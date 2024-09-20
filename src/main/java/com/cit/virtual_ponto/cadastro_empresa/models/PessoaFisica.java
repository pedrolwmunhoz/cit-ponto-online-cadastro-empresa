package com.cit.virtual_ponto.cadastro_empresa.models;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "pessoa_fisica")
public class PessoaFisica extends Pessoa {

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Column(name = "rg")
    private String rg;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "id_folha_pagamento", nullable = false, unique = true)
    private Integer idFolhaPagamento;

    @Column(name = "id_departamento", nullable = false)
    private Integer idDepartamento;

    @Column(name = "id_cargo", nullable = false)
    private Integer idCargo;

    @Column(name = "id_banco_horas", nullable = false, unique = true)
    private Integer idBancoHoras;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_empresa")
    private PessoaJuridica empresa;
}
