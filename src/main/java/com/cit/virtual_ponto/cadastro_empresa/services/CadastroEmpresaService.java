package com.cit.virtual_ponto.cadastro_empresa.services;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cit.virtual_ponto.cadastro_empresa.exceptions.EnumErrosCadastroEmpresa;
import com.cit.virtual_ponto.cadastro_empresa.exceptions.ErrosSistema;
import com.cit.virtual_ponto.cadastro_empresa.models.EmpresaEntity;
import com.cit.virtual_ponto.cadastro_empresa.repositories.CadastroEmpresaRepository;

import java.util.Optional;

@Service
public class CadastroEmpresaService {

    private CadastroEmpresaRepository cadastroEmpresaRepository;

    @Autowired
    public CadastroEmpresaService(
            CadastroEmpresaRepository cadastroEmpresaRepository) {
        this.cadastroEmpresaRepository = cadastroEmpresaRepository;
    }

    @Transactional
    public EmpresaEntity cadastrarEmpresa(EmpresaEntity empresa) {
        try {
            // valida se já está cadastradada
            this.validarCadastroEmpresa(empresa);

            // salva o novo empresa
            return cadastroEmpresaRepository.save(empresa);
        } catch (Exception e) {
            throw new ErrosSistema.DatabaseException(
                EnumErrosCadastroEmpresa.ERRO_CADASTRAR_EMPRESA.getMensagemErro(), e);
        }
    }

    @Transactional
    public EmpresaEntity atualizarEmpresa(EmpresaEntity empresa) {
        try {
            
            Long empresaId = empresa.getEmpresaId();
            Optional<EmpresaEntity> optionalEmpresa = cadastroEmpresaRepository.findById(empresaId);

            //valida se empresa existe
            if (optionalEmpresa.isPresent()) {

                return cadastroEmpresaRepository.save(empresa);

            }  else {
                throw new ErrosSistema.EmpresaException(
                    EnumErrosCadastroEmpresa.EMPRESA_NAO_ENCONTRADO_ID.getMensagemErro() + empresaId + " empresa inexistente");
            }
        } catch (Exception e) {
            throw new ErrosSistema.DatabaseException(
                EnumErrosCadastroEmpresa.ERRO_ATUALIZAR_EMPRESA.getMensagemErro(), e);
        }
    }
    
   @Transactional
    public boolean excluirEmpresa(Long id) {
        try {
            if (cadastroEmpresaRepository.existsById(id)) {
                cadastroEmpresaRepository.deleteById(id);
                return true;
            } else {
                throw new ErrosSistema.EmpresaException(
                    EnumErrosCadastroEmpresa.EMPRESA_NAO_ENCONTRADO_ID.getMensagemErro() + id + " empresa inexistente");
            }
        } catch (Exception e) {
            throw new ErrosSistema.DatabaseException(
                EnumErrosCadastroEmpresa.ERRO_EXCLUIR_EMPRESA.getMensagemErro(), e);
        }
    }

    public void validarCadastroEmpresa(EmpresaEntity empresa) {

        //verifica o id da empresa
        Long empresaId = empresa.getEmpresaId();
        Optional<EmpresaEntity> optionalEmpresa = cadastroEmpresaRepository.findById(empresaId);
        if (optionalEmpresa.isPresent()) {
            throw new ErrosSistema.EmpresaException(
                    "Empresa já cadastrada com id: " + empresaId);
        }

        // Verifica se o email já está cadastrado
        String email = empresa.getEmail();
        Optional<EmpresaEntity> optionalEmpresaByEmail = cadastroEmpresaRepository.findByEmail(email);
        if (optionalEmpresaByEmail.isPresent()) {
            throw new ErrosSistema.EmpresaException(
                    "Email já cadastrado: " + email);
        }

        // Verifica se o cnpj já está cadastrado
        String cnpj = empresa.getCnpj();
        Optional<EmpresaEntity> optionalEmpresaByNome = cadastroEmpresaRepository.findByCnpj(cnpj);
        if (optionalEmpresaByNome.isPresent()) {
            throw new ErrosSistema.EmpresaException(
                    "Cnpj já cadastrado: " + cnpj);
        }

        // Verifica se o telefone já está cadastrado
        String telefone = empresa.getTelefone();
        Optional<EmpresaEntity> optionalEmpresaByTelefone = cadastroEmpresaRepository
                .findByTelefone(telefone);
        if (optionalEmpresaByTelefone.isPresent()) {
            throw new ErrosSistema.EmpresaException(
                    "Telefone já cadastrado: " + telefone);
        }

    }
}
