package com.cit.virtual_ponto.cadastro_empresa.models;
import java.sql.Date;
import java.sql.Time;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "historico_login")
public class HistoricoLogin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historico")
    private Long id_historico;

    @Column(name = "data")
    private Date data;

    @Column(name = "hora")
    private Time hora;

    @Column(name = "historico_login_id")
    private Integer historico_login_id;


}
