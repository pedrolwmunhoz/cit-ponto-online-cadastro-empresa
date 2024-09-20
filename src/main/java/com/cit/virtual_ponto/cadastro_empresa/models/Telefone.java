package com.cit.virtual_ponto.cadastro_empresa.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "telefone")
public class Telefone {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_telefone")
    private Integer idTelefone;

    @Column(name = "ddd", nullable = false)
    private String ddd;

    @Column(name = "numero", nullable = false, unique = true)
    private String numero;
}