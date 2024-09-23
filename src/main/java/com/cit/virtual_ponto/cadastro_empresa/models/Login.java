package com.cit.virtual_ponto.cadastro_empresa.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "login")
public class Login {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_login")
    private Integer idLogin;

    @Column(name = "hash_email", nullable = false, unique = true)
    private String hashEmail;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "senha_usuario", nullable = false)
    private String senhaUsuario;

    @Column(name = "id_historico_login", nullable = false, unique = true)
    private Integer idHistoricoLogin;
}
