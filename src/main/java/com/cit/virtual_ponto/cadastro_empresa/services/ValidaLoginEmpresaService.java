package com.cit.virtual_ponto.cadastro_empresa.services;

import java.util.Optional;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cit.virtual_ponto.cadastro_empresa.dto.EmpresaDto;
import com.cit.virtual_ponto.cadastro_empresa.exceptions.ErrosSistema;
import com.cit.virtual_ponto.cadastro_empresa.models.EmpresaEntity;
import com.cit.virtual_ponto.cadastro_empresa.repositories.CadastroEmpresaRepository;

@Service
public class ValidaLoginEmpresaService {

    private CadastroEmpresaRepository cadastroEmpresaRepository;

    private StringEncryptor encryptor;

    @Autowired
    public void setEncryptor(@Qualifier("jasyptStringEncryptor") StringEncryptor encryptor) {
        this.encryptor = encryptor;
    }

    @Autowired
    public ValidaLoginEmpresaService(CadastroEmpresaRepository cadastroEmpresaRepository) {
        this.cadastroEmpresaRepository = cadastroEmpresaRepository;
    }

    public EmpresaEntity validarLogin(EmpresaDto empresa) {
        String email = empresa.getEmail();
        Optional<EmpresaEntity> optionalEmpresaByEmail = cadastroEmpresaRepository.findByEmail(email);

        // Verifica se o email já está cadastrado
        if (optionalEmpresaByEmail.isPresent()) {
            EmpresaEntity empresaByEmail = optionalEmpresaByEmail.get();
            String decryptedSenha = decrypt(empresaByEmail.getSenha());

            if (decryptedSenha.equals(empresa.getSenha())) {
                return empresaByEmail;
            }
        }
        throw new ErrosSistema.EmpresaException(
                "Email ou senha incorreto.");
    }

    public String decrypt(String encryptedValue) {
        return encryptor.encrypt(encryptedValue);
    }
}
