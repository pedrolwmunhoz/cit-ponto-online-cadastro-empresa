package com.cit.virtual_ponto.cadastro_empresa.services;

import java.util.List;
import java.util.Optional;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cit.virtual_ponto.cadastro_empresa.dto.LoginDto;
import com.cit.virtual_ponto.cadastro_empresa.exceptions.ErrosSistema;
import com.cit.virtual_ponto.cadastro_empresa.models.PessoaJuridica;
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

    public PessoaJuridica validarLogin(LoginDto loginRequestDto) {
        List<PessoaJuridica> empresas = cadastroEmpresaRepository.findAll();

        Optional<PessoaJuridica> empresaOptional = empresas.stream()
                .filter(empresa -> encryptor.decrypt(empresa.getEmail()).equals(loginRequestDto.getEmail()))
                .findFirst();

        if (empresaOptional.isPresent()) {
            PessoaJuridica empresa = empresaOptional.get();

            String senhaDescriptografada = encryptor.decrypt(empresa.getSenha());

            if (senhaDescriptografada.equals(loginRequestDto.getSenha())) {
                this.decryptEmpresaFields(empresa);
                return empresa; 
            }
        }
        throw new ErrosSistema.EmpresaException("Credenciais inválidas.");
    }


    private void decryptEmpresaFields(PessoaJuridica empresa) {
        empresa.setNome(encryptor.decrypt(empresa.getNome()));
        empresa.setRazaoSocial(encryptor.decrypt(empresa.getRazaoSocial()));
        empresa.setInscricaoEstadual(encryptor.decrypt(empresa.getInscricaoEstadual()));
        empresa.setCnpj(encryptor.decrypt(empresa.getCnpj()));
        empresa.setTelefone(encryptor.decrypt(empresa.getTelefone()));
        empresa.setEmail(encryptor.decrypt(empresa.getEmail()));
        empresa.setSenha(encryptor.decrypt(empresa.getSenha()));

        empresa.getEndereco().setLogradouro(encryptor.decrypt(empresa.getEndereco().getLogradouro()));
        empresa.getEndereco().setNumero(encryptor.decrypt(empresa.getEndereco().getNumero()));
        empresa.getEndereco().setComplemento(encryptor.decrypt(empresa.getEndereco().getComplemento()));
        empresa.getEndereco().setBairro(encryptor.decrypt(empresa.getEndereco().getBairro()));
        empresa.getEndereco().setCidade(encryptor.decrypt(empresa.getEndereco().getCidade()));
        empresa.getEndereco().setEstado(encryptor.decrypt(empresa.getEndereco().getEstado()));
        empresa.getEndereco().setCep(encryptor.decrypt(empresa.getEndereco().getCep()));

    }

}

