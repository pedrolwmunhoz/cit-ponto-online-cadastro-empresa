package com.cit.virtual_ponto.cadastro_empresa.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "login")
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login_id")
    private Integer login_id;

    @Column(name = "email")
    private String email;

    @Column(name = "senha")
    private String senha;

    @Id
    @Column(name = "historico_login_id")
    private Integer historico_login_id;
}
