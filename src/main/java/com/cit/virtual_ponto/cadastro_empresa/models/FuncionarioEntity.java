package com.cit.virtual_ponto.cadastro_empresa.models;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Data
@Table(name = "funcionario")
@Entity(name = "FuncionarioEntity")
public class FuncionarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long funcionarioId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private EmpresaEntity empresa;

    @Column(unique = true, nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(unique = true, nullable = false)
    private String telefone;

}
