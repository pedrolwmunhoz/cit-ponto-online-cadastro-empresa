package com.cit.virtual_ponto.cadastro_empresa.services;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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


    private StringEncryptor encryptor;

    @Autowired
    public void setEncryptor(@Qualifier("jasyptStringEncryptor") StringEncryptor encryptor) {
        this.encryptor = encryptor;
    }

    @Autowired
    public ListarEmpresaService(CadastroEmpresaRepository cadastroEmpresaRepository) {
        this.cadastroEmpresaRepository = cadastroEmpresaRepository;
    }

    
    public List<EmpresaEntity> listarEmpresas() {
        try {
            List<EmpresaEntity> empresas = cadastroEmpresaRepository.findAll();
            empresas.forEach(this::decryptEmpresaFields);
            return empresas;
        } catch (DataAccessException e) {
            throw new ErrosSistema.DatabaseException(
                EnumErrosCadastroEmpresa.ERRO_LISTAR_EMPRESA.getMensagemErro(), e);
        }
    }

    public Optional<EmpresaEntity> buscarEmpresaPorId(Long id) {
        try {
            Optional<EmpresaEntity> empresa = cadastroEmpresaRepository.findById(id);
            if (empresa.isPresent()) {
                EmpresaEntity empresaExistente = empresa.get();
                this.decryptEmpresaFields(empresaExistente);
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
            List<EmpresaEntity> empresas = cadastroEmpresaRepository.findByNomeEmpresaContainingIgnoreCase(nomeEmpresa);
            empresas.forEach(this::decryptEmpresaFields);
            return empresas;
        } catch (Exception e) {
            throw new ErrosSistema.DatabaseException(
                EnumErrosCadastroEmpresa.ERRO_BUSCAR_EMPRESA.getMensagemErro(), e);
        }
    }

    private void decryptEmpresaFields(EmpresaEntity empresa) {
        empresa.setNomeEmpresa(decrypt(empresa.getNomeEmpresa()));
        empresa.setRazaoSocial(decrypt(empresa.getRazaoSocial()));
        empresa.setCnpj(decrypt(empresa.getCnpj()));
        empresa.setLogradouro(decrypt(empresa.getLogradouro()));
        empresa.setNumero(decrypt(empresa.getNumero()));
        empresa.setComplemento(decrypt(empresa.getComplemento()));
        empresa.setBairro(decrypt(empresa.getBairro()));
        empresa.setCidade(decrypt(empresa.getCidade()));
        empresa.setEstado(decrypt(empresa.getEstado()));
        empresa.setCep(decrypt(empresa.getCep()));
        empresa.setTelefone(decrypt(empresa.getTelefone()));
        empresa.setEmail(decrypt(empresa.getEmail()));
    }

    public String decrypt(String encryptedValue) {
        return encryptor.decrypt(encryptedValue);
    }
}
