package com.cit.virtual_ponto.cadastro_empresa.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "pessoa_juridica")
public class PessoaJuridica extends Pessoa {

    @Column(name = "cnpj", nullable = false, unique = true)
    private String cnpj;

    @Column(name = "razao_social", nullable = false, unique = true)
    private String razaoSocial;

    @Column(name = "inscricao_estadual", nullable = false, unique = true)
    private String inscricaoEstadual;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PessoaFisica> funcionarios;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Departamento> departamentos;
}
