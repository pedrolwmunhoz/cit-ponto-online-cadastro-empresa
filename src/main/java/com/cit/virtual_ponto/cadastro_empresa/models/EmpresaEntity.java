package com.cit.virtual_ponto.cadastro_empresa.models;

import lombok.Data;

import java.util.List;
import jakarta.persistence.*;

@Data
@Entity(name = "EmpresaEntity")
@Table(name = "empresa")
public class EmpresaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "empresa_id")
    private Long empresaId;

    @Column(name = "nome_empresa", nullable = false)
    private String nomeEmpresa;

    @Column(name = "razao_social", nullable = false)
    private String razaoSocial;

    @Column(name = "cnpj", nullable = false, unique = true)
    private String cnpj;

    @Column(name = "logradouro", nullable = false)
    private String logradouro;

    @Column(name = "numero", nullable = false)
    private String numero;

    @Column(name = "complemento")
    private String complemento;

    @Column(name = "bairro", nullable = false)
    private String bairro;

    @Column(name = "cidade", nullable = false)
    private String cidade;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "cep", nullable = false)
    private String cep;

    @Column(name = "telefone", nullable = false)
    private String telefone;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FuncionarioEntity> funcionarios;

}
