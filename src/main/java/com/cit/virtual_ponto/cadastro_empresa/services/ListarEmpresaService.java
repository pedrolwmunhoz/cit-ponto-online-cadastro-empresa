package com.cit.virtual_ponto.cadastro_empresa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.cit.virtual_ponto.cadastro_empresa.exceptions.EnumErrosCadastroEmpresa;
import com.cit.virtual_ponto.cadastro_empresa.exceptions.ErrosSistema;
import com.cit.virtual_ponto.cadastro_empresa.models.EmpresaEntity;
import com.cit.virtual_ponto.cadastro_empresa.repositories.CadastroEmpresaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ListarEmpresaService {

    private CadastroEmpresaRepository cadastroEmpresaRepository;

    @Autowired
    public ListarEmpresaService(CadastroEmpresaRepository cadastroEmpresaRepository) {
        this.cadastroEmpresaRepository = cadastroEmpresaRepository;
    }

    
    public List<EmpresaEntity> listarEmpresas() {
        try {
            return cadastroEmpresaRepository.findAll();
        } catch (DataAccessException e) {
            throw new ErrosSistema.DatabaseException(
                EnumErrosCadastroEmpresa.ERRO_LISTAR_EMPRESA.getMensagemErro(), e);
        }
    }

    public Optional<EmpresaEntity> buscarEmpresaPorId(Long id) {
        try {
            Optional<EmpresaEntity> empresa = cadastroEmpresaRepository.findById(id);
            if (empresa.isPresent()) {
                return empresa;
            } else {
                throw new ErrosSistema.EmpresaException(
                    EnumErrosCadastroEmpresa.EMPRESA_NAO_ENCONTRADO_ID.getMensagemErro() + id);
            }
        } catch (Exception e) {
            throw new ErrosSistema.DatabaseException(
                EnumErrosCadastroEmpresa.ERRO_BUSCAR_EMPRESA.getMensagemErro(), e);
        }
    }

    public List<EmpresaEntity> buscarEmpresaPorNome(String nomeEmpresa) {
        try {
            return cadastroEmpresaRepository.findByNomeEmpresaContainingIgnoreCase(nomeEmpresa);
        } catch (Exception e) {
            throw new ErrosSistema.DatabaseException(
                EnumErrosCadastroEmpresa.ERRO_BUSCAR_EMPRESA.getMensagemErro(), e);
        }
    }
}
