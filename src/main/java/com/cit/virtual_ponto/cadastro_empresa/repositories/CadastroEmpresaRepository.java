package com.cit.virtual_ponto.cadastro_empresa.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cit.virtual_ponto.cadastro_empresa.models.PessoaJuridica;

public interface CadastroEmpresaRepository extends JpaRepository<PessoaJuridica, Long> {
    
    Optional<PessoaJuridica> findByEmail(String email);

    Optional<PessoaJuridica> findByCnpj(String cnpj);

    Optional<PessoaJuridica> findByTelefone(String telefone);
}
