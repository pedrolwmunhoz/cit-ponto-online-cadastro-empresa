package com.cit.virtual_ponto.cadastro_empresa.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "pessoa_juridica")
public class PessoaJuridica extends Pessoa {


    @Column(name = "nomeFantasia", nullable = false)
    private String nomeFantasia;

    @Column(name = "nomeFantasia", nullable = false)
    private String razaoSocial;

    @Column(name = "cnpj", nullable = false, unique = true)
    private String cnpj;

    @Column(name = "inscricao_estadual", nullable = false, unique = true)
    private String inscricao_estadual;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PessoaFisica> funcionarios;
}
