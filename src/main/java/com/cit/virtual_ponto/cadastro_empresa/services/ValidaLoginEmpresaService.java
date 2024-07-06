package com.cit.virtual_ponto.cadastro_empresa.services;

import java.util.List;
import java.util.Optional;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cit.virtual_ponto.cadastro_empresa.dto.LoginRequestDto;
import com.cit.virtual_ponto.cadastro_empresa.exceptions.ErrosSistema;
import com.cit.virtual_ponto.cadastro_empresa.models.EmpresaEntity;
import com.cit.virtual_ponto.cadastro_empresa.repositories.CadastroEmpresaRepository;

@Service
public class ValidaLoginEmpresaService {
    
    private StringEncryptor encryptor;

    @Autowired
    public void setEncryptor(@Qualifier("jasyptStringEncryptor") StringEncryptor encryptor) {
        this.encryptor = encryptor;
    }
    @Autowired
    private CadastroEmpresaRepository cadastroEmpresaRepository;

    public EmpresaEntity validarLogin(LoginRequestDto loginRequestDto) {
        List<EmpresaEntity> empresas = cadastroEmpresaRepository.findAll();

        Optional<EmpresaEntity> empresaOptional = empresas.stream()
                .filter(empresa -> encryptor.decrypt(empresa.getEmail()).equals(loginRequestDto.getEmail()))
                .findFirst();

        if (empresaOptional.isPresent()) {
            EmpresaEntity empresa = empresaOptional.get();

            String senhaDescriptografada = encryptor.decrypt(empresa.getSenha());

            if (senhaDescriptografada.equals(loginRequestDto.getSenha())) {
                this.decryptEmpresaFields(empresa);
                return empresa; 
            }
        }
        throw new ErrosSistema.EmpresaException("Credenciais inválidas.");
    }


    private void decryptEmpresaFields(EmpresaEntity empresa) {
        empresa.setNomeEmpresa(encryptor.decrypt(empresa.getNomeEmpresa()));
        empresa.setRazaoSocial(encryptor.decrypt(empresa.getRazaoSocial()));
        empresa.setCnpj(encryptor.decrypt(empresa.getCnpj()));
        empresa.setLogradouro(encryptor.decrypt(empresa.getLogradouro()));
        empresa.setNumero(encryptor.decrypt(empresa.getNumero()));
        empresa.setComplemento(encryptor.decrypt(empresa.getComplemento()));
        empresa.setBairro(encryptor.decrypt(empresa.getBairro()));
        empresa.setCidade(encryptor.decrypt(empresa.getCidade()));
        empresa.setEstado(encryptor.decrypt(empresa.getEstado()));
        empresa.setCep(encryptor.decrypt(empresa.getCep()));
        empresa.setTelefone(encryptor.decrypt(empresa.getTelefone()));
        empresa.setEmail(encryptor.decrypt(empresa.getEmail()));
        empresa.setSenha(encryptor.decrypt(empresa.getSenha()));
    }

}

