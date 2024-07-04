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

        // valida se já está cadastradada
        this.validarCadastroEmpresa(empresa);

        EmpresaEntity novaEmpresa = new EmpresaEntity();
        //criptografa as informações da nova empresa
        this.encryptEmpresaFields(novaEmpresa, empresa);

        // salva a nova empresa
        return cadastroEmpresaRepository.save(novaEmpresa);
    }

    @Transactional
    public EmpresaEntity atualizarEmpresa(EmpresaDto empresa) {

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
                EnumErrosCadastroEmpresa.EMPRESA_NAO_ENCONTRADO_ID.getMensagemErro());
        }
    }
    
   @Transactional
    public EmpresaEntity excluirEmpresa(Long id) {
        Optional<EmpresaEntity> optionalEmpresa = cadastroEmpresaRepository.findById(id);

        if (optionalEmpresa.isPresent()) {
            EmpresaEntity empresaExcluida = optionalEmpresa.get();
            cadastroEmpresaRepository.deleteById(id);
            return empresaExcluida;
        } else {
            throw new ErrosSistema.EmpresaException(
                EnumErrosCadastroEmpresa.EMPRESA_NAO_ENCONTRADO_ID.getMensagemErro());
        }
    }

    public void validarCadastroEmpresa(EmpresaDto empresa) {

        //verifica o id da empresa
        Long empresaId = empresa.getEmpresaId();
        Optional<EmpresaEntity> optionalEmpresa = cadastroEmpresaRepository.findById(empresaId);
        if (optionalEmpresa.isPresent()) {
            throw new ErrosSistema.EmpresaException(
                    "Empresa já cadastrada com id.");
        }

        // Verifica se o email já está cadastrado
        String email = this.encrypt(empresa.getEmail());
        Optional<EmpresaEntity> optionalEmpresaByEmail = cadastroEmpresaRepository.findByEmail(email);
        if (optionalEmpresaByEmail.isPresent()) {
            throw new ErrosSistema.EmpresaException(
                    "Email já cadastrado.");
        }

        // Verifica se o cnpj já está cadastrado
        String cnpj = this.encrypt(empresa.getCnpj());
        Optional<EmpresaEntity> optionalEmpresaByNome = cadastroEmpresaRepository.findByCnpj(cnpj);
        if (optionalEmpresaByNome.isPresent()) {
            throw new ErrosSistema.EmpresaException(
                    "Cnpj já cadastrado.");
        }

        // Verifica se o telefone já está cadastrado
        String telefone = this.encrypt(empresa.getTelefone());
        Optional<EmpresaEntity> optionalEmpresaByTelefone = cadastroEmpresaRepository
                .findByTelefone(telefone);
        if (optionalEmpresaByTelefone.isPresent()) {
            throw new ErrosSistema.EmpresaException(
                    "Telefone já cadastrado.");
        }

    }

    private void encryptEmpresaFields(EmpresaEntity novaEmpresa, EmpresaDto empresa) {
        novaEmpresa.setEmpresaId(empresa.getEmpresaId());
        novaEmpresa.setNomeEmpresa(encrypt(empresa.getNomeEmpresa()));
        novaEmpresa.setRazaoSocial(encrypt(empresa.getRazaoSocial()));
        novaEmpresa.setCnpj(encrypt(empresa.getCnpj()));
        novaEmpresa.setLogradouro(encrypt(empresa.getLogradouro()));
        novaEmpresa.setNumero(encrypt(empresa.getNumero()));
        novaEmpresa.setComplemento(encrypt(empresa.getComplemento()));
        novaEmpresa.setBairro(encrypt(empresa.getBairro()));
        novaEmpresa.setCidade(encrypt(empresa.getCidade()));
        novaEmpresa.setEstado(encrypt(empresa.getEstado()));
        novaEmpresa.setCep(encrypt(empresa.getCep()));
        novaEmpresa.setTelefone(encrypt(empresa.getTelefone()));
        novaEmpresa.setEmail(encrypt(empresa.getEmail()));
        novaEmpresa.setSenha(encrypt(empresa.getSenha()));

    }

    public String encrypt(String encryptedValue) {
        return encryptor.encrypt(encryptedValue);
    }

}
