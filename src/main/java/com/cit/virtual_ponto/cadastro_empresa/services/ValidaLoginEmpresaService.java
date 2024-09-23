package com.cit.virtual_ponto.cadastro_empresa.services;

import java.util.Optional;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cit.virtual_ponto.cadastro_empresa.dto.LoginDto;
import com.cit.virtual_ponto.cadastro_empresa.exceptions.ErrosSistema;
import com.cit.virtual_ponto.cadastro_empresa.models.Login;
import com.cit.virtual_ponto.cadastro_empresa.models.PessoaJuridica;
import com.cit.virtual_ponto.cadastro_empresa.repositories.CadastroEmpresaRepository;
import com.cit.virtual_ponto.cadastro_empresa.repositories.LoginRepository;

@Service
public class ValidaLoginEmpresaService {
    
    private StringEncryptor encryptor;

    @Autowired
    public void setEncryptor(@Qualifier("jasyptStringEncryptor") StringEncryptor encryptor) {
        this.encryptor = encryptor;
    }
    @Autowired
    private CadastroEmpresaRepository cadastroEmpresaRepository;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private HashService hashService;

    public PessoaJuridica validarLogin(LoginDto loginRequestDto) {
        Optional<Login> optionalLogin = loginRepository.findByHashEmail(hashService.generateHash(loginRequestDto.getEmail()));

        if (optionalLogin.isPresent()) {
            Login login = optionalLogin.get();

            PessoaJuridica empresa = cadastroEmpresaRepository.findById(login.getIdLogin()).get();

            String senhaDescriptografada = encryptor.decrypt(login.getSenhaUsuario());

            if (senhaDescriptografada.equals(loginRequestDto.getSenha())) {
                this.decryptEmpresaFields(empresa);
                return empresa; 
            }
        }
        throw new ErrosSistema.EmpresaException("Credenciais inválidas.");
    }


    private void decryptEmpresaFields(PessoaJuridica empresa) {
        empresa.setRazaoSocial(encryptor.decrypt(empresa.getRazaoSocial()));
        empresa.setNomeFantasia(encryptor.decrypt(empresa.getNomeFantasia()));
        empresa.setInscricao_estadual(encryptor.decrypt(empresa.getInscricao_estadual()));
        empresa.setCnpj(encryptor.decrypt(empresa.getCnpj()));

        empresa.getTelefone().setDdd(encryptor.decrypt(empresa.getTelefone().getDdd()));
        empresa.getTelefone().setNumero(encryptor.decrypt(empresa.getTelefone().getNumero()));

        empresa.getLogin().setEmail(encryptor.decrypt(empresa.getLogin().getEmail()));
        empresa.getLogin().setSenhaUsuario(encryptor.decrypt(empresa.getLogin().getSenhaUsuario()));
        
        empresa.getEndereco().setLogradouro(encryptor.decrypt(empresa.getEndereco().getLogradouro()));
        empresa.getEndereco().setNumero(encryptor.decrypt(empresa.getEndereco().getNumero()));
        empresa.getEndereco().setComplemento(encryptor.decrypt(empresa.getEndereco().getComplemento()));
        empresa.getEndereco().setBairro(encryptor.decrypt(empresa.getEndereco().getBairro()));
        empresa.getEndereco().setCidade(encryptor.decrypt(empresa.getEndereco().getCidade()));
        empresa.getEndereco().setEstado(encryptor.decrypt(empresa.getEndereco().getEstado()));
        empresa.getEndereco().setCep(encryptor.decrypt(empresa.getEndereco().getCep()));

    }

}

