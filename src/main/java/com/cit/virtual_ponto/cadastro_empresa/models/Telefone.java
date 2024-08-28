package com.cit.virtual_ponto.cadastro_empresa.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "telefone")
public class Telefone {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "telefone_id")
    private Integer telefone_id;

    @Column(name = "ddd")
    private String ddd;

    @Column(name = "numero")
    private String numero;
}