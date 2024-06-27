package com.cit.virtual_ponto.cadastro_empresa.services;

import jakarta.transaction.Transactional;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cit.virtual_ponto.cadastro_empresa.dto.EmpresaDto;
import com.cit.virtual_ponto.cadastro_empresa.exceptions.EnumErrosCadastroEmpresa;
import com.cit.virtual_ponto.cadastro_empresa.exceptions.ErrosSistema;
import com.cit.virtual_ponto.cadastro_empresa.models.EmpresaEntity;
import com.cit.virtual_ponto.cadastro_empresa.repositories.CadastroEmpresaRepository;

import java.util.Optional;

@Service
public class CadastroEmpresaService {

    private CadastroEmpresaRepository cadastroEmpresaRepository;

    private StringEncryptor encryptor;

    @Autowired
    public void setEncryptor(@Qualifier("jasyptStringEncryptor") StringEncryptor encryptor) {
        this.encryptor = encryptor;
    }

    @Autowired
    public CadastroEmpresaService(
            CadastroEmpresaRepository cadastroEmpresaRepository) {
        this.cadastroEmpresaRepository = cadastroEmpresaRepository;
    }

    @Transactional
    public EmpresaEntity cadastrarEmpresa(EmpresaDto empresa) {
        try {
            // valida se já está cadastradada
            this.validarCadastroEmpresa(empresa);

            EmpresaEntity novaEmpresa = new EmpresaEntity();
            //criptografa as informações da nova empresa
            this.encryptEmpresaFields(novaEmpresa, empresa);

            // salva a nova empresa
            return cadastroEmpresaRepository.save(novaEmpresa);
        } catch (Exception e) {
            throw new ErrosSistema.DatabaseException(
                EnumErrosCadastroEmpresa.ERRO_CADASTRAR_EMPRESA.getMensagemErro(), e);
        }
    }

    @Transactional
    public EmpresaEntity atualizarEmpresa(EmpresaDto empresa) {
        try {
            
            Long empresaId = empresa.getEmpresaId();
            Optional<EmpresaEntity> optionalEmpresa = cadastroEmpresaRepository.findById(empresaId);

            //valida se empresa existe
            if (optionalEmpresa.isPresent()) {

                EmpresaEntity empresaAtualizada = optionalEmpresa.get();
                //criptografa as informações da empresa
                this.encryptEmpresaFields(empresaAtualizada, empresa);

                //salva empresaAtualizada
                return cadastroEmpresaRepository.save(empresaAtualizada);

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

    public void validarCadastroEmpresa(EmpresaDto empresa) {

        //verifica o id da empresa
        Long empresaId = empresa.getEmpresaId();
        Optional<EmpresaEntity> optionalEmpresa = cadastroEmpresaRepository.findById(empresaId);
        if (optionalEmpresa.isPresent()) {
            throw new ErrosSistema.EmpresaException(
                    "Empresa já cadastrada com id: " + empresaId);
        }

        // Verifica se o email já está cadastrado
        String email = this.encrypt(empresa.getEmail());
        Optional<EmpresaEntity> optionalEmpresaByEmail = cadastroEmpresaRepository.findByEmail(email);
        if (optionalEmpresaByEmail.isPresent()) {
            throw new ErrosSistema.EmpresaException(
                    "Email já cadastrado: " + email);
        }

        // Verifica se o cnpj já está cadastrado
        String cnpj = this.encrypt(empresa.getCnpj());
        Optional<EmpresaEntity> optionalEmpresaByNome = cadastroEmpresaRepository.findByCnpj(cnpj);
        if (optionalEmpresaByNome.isPresent()) {
            throw new ErrosSistema.EmpresaException(
                    "Cnpj já cadastrado: " + cnpj);
        }

        // Verifica se o telefone já está cadastrado
        String telefone = this.encrypt(empresa.getTelefone());
        Optional<EmpresaEntity> optionalEmpresaByTelefone = cadastroEmpresaRepository
                .findByTelefone(telefone);
        if (optionalEmpresaByTelefone.isPresent()) {
            throw new ErrosSistema.EmpresaException(
                    "Telefone já cadastrado: " + telefone);
        }

    }

    private void encryptEmpresaFields(EmpresaEntity novaEmpresa, EmpresaDto empresa) {
        novaEmpresa.setEmpresaId(empresa.getEmpresaId());
        novaEmpresa.setNomeEmpresa(encryptor.encrypt(empresa.getNomeEmpresa()));
        novaEmpresa.setRazaoSocial(encryptor.encrypt(empresa.getRazaoSocial()));
        novaEmpresa.setCnpj(encryptor.encrypt(empresa.getCnpj()));
        novaEmpresa.setLogradouro(encryptor.encrypt(empresa.getLogradouro()));
        novaEmpresa.setNumero(encryptor.encrypt(empresa.getNumero()));
        novaEmpresa.setComplemento(encryptor.encrypt(empresa.getComplemento()));
        novaEmpresa.setBairro(encryptor.encrypt(empresa.getBairro()));
        novaEmpresa.setCidade(encryptor.encrypt(empresa.getCidade()));
        novaEmpresa.setEstado(encryptor.encrypt(empresa.getEstado()));
        novaEmpresa.setCep(encryptor.encrypt(empresa.getCep()));
        novaEmpresa.setTelefone(encryptor.encrypt(empresa.getTelefone()));
        novaEmpresa.setEmail(encryptor.encrypt(empresa.getEmail()));

    }

    public String encrypt(String encryptedValue) {
        return encryptor.encrypt(encryptedValue);
    }

}
