package com.cit.virtual_ponto.cadastro_empresa.services;

import jakarta.transaction.Transactional;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cit.virtual_ponto.cadastro_empresa.dto.EmpresaDto;
import com.cit.virtual_ponto.cadastro_empresa.exceptions.EnumErrosCadastroEmpresa;
import com.cit.virtual_ponto.cadastro_empresa.exceptions.ErrosSistema;
import com.cit.virtual_ponto.cadastro_empresa.models.Endereco;
import com.cit.virtual_ponto.cadastro_empresa.models.Login;
import com.cit.virtual_ponto.cadastro_empresa.models.PessoaJuridica;
import com.cit.virtual_ponto.cadastro_empresa.models.Telefone;
import com.cit.virtual_ponto.cadastro_empresa.repositories.CadastroEmpresaRepository;
import com.cit.virtual_ponto.cadastro_empresa.repositories.TelefoneRepository;

import java.util.Optional;

@Service
public class CadastroEmpresaService {

    private CadastroEmpresaRepository cadastroEmpresaRepository;

    private TelefoneRepository telefoneRepository;

    private StringEncryptor encryptor;

    @Autowired
    public void setEncryptor(@Qualifier("jasyptStringEncryptor") StringEncryptor encryptor) {
        this.encryptor = encryptor;
    }

    @Autowired
    public CadastroEmpresaService(
            CadastroEmpresaRepository cadastroEmpresaRepository, TelefoneRepository telefoneRepository) {
        this.cadastroEmpresaRepository = cadastroEmpresaRepository;
        this.telefoneRepository = telefoneRepository;
    }

    @Transactional
    public PessoaJuridica cadastrarEmpresa(EmpresaDto empresa) {

        // valida se já está cadastradada
        this.validarCadastroEmpresa(empresa);

        PessoaJuridica novaEmpresa = new PessoaJuridica();
        //criptografa as informações da nova empresa
        this.setEmpresaFields(novaEmpresa, empresa);

        // salva a nova empresa
        return cadastroEmpresaRepository.save(novaEmpresa);
    }

    @Transactional
    public PessoaJuridica atualizarEmpresa(EmpresaDto empresa) {

        Integer empresaId = empresa.getPessoaId();
        Optional<PessoaJuridica> optionalEmpresa = cadastroEmpresaRepository.findById(empresaId);

        //valida se empresa existe
        if (optionalEmpresa.isPresent()) {

            PessoaJuridica empresaAtualizada = optionalEmpresa.get();
            //criptografa as informações da empresa
            this.setEmpresaFields(empresaAtualizada, empresa);

            //salva empresaAtualizada
            return cadastroEmpresaRepository.save(empresaAtualizada);

        }  else {
            throw new ErrosSistema.EmpresaException(
                EnumErrosCadastroEmpresa.EMPRESA_NAO_ENCONTRADO_ID.getMensagemErro());
        }
    }
    
   @Transactional
    public PessoaJuridica excluirEmpresa(Integer id) {
        Optional<PessoaJuridica> optionalEmpresa = cadastroEmpresaRepository.findById(id);

        if (optionalEmpresa.isPresent()) {
            PessoaJuridica empresaExcluida = optionalEmpresa.get();
            cadastroEmpresaRepository.deleteById(id);
            return empresaExcluida;
        } else {
            throw new ErrosSistema.EmpresaException(
                EnumErrosCadastroEmpresa.EMPRESA_NAO_ENCONTRADO_ID.getMensagemErro());
        }
    }

    public void validarCadastroEmpresa(EmpresaDto empresa) {

        //verifica o id da empresa
        Integer empresaId = empresa.getPessoaId();
        Optional<PessoaJuridica> optionalEmpresa = cadastroEmpresaRepository.findById(empresaId);
        if (optionalEmpresa.isPresent()) {
            throw new ErrosSistema.EmpresaException(
                    "Empresa já cadastrada com id.");
        }

        // Verifica se o email já está cadastrado
        String email = this.encrypt(empresa.getEmail());
        Optional<PessoaJuridica> optionalEmpresaByEmail = cadastroEmpresaRepository.findByEmail(email);
        if (optionalEmpresaByEmail.isPresent()) {
            throw new ErrosSistema.EmpresaException(
                    "Email já cadastrado.");
        }

        // Verifica se o cnpj já está cadastrado
        String cnpj = this.encrypt(empresa.getCnpj());
        Optional<PessoaJuridica> optionalEmpresaByNome = cadastroEmpresaRepository.findByCnpj(cnpj);
        if (optionalEmpresaByNome.isPresent()) {
            throw new ErrosSistema.EmpresaException(
                    "Cnpj já cadastrado.");
        }

        // Verifica se o telefone já está cadastrado
        String ddd = this.encrypt(empresa.getTelefone().getDdd());
        String telefone = this.encrypt(empresa.getTelefone().getNumero());
        Optional<Telefone> optionalTelefone = telefoneRepository
                .findByDddAndNumero(ddd, telefone);
        if (optionalTelefone.isPresent()) {
            throw new ErrosSistema.EmpresaException(
                    "Telefone já cadastrado.");
        }

    }

    private void setEmpresaFields(PessoaJuridica novaEmpresa, EmpresaDto empresa) {
        novaEmpresa.setNome(encrypt(empresa.getNome()));
        novaEmpresa.setInscricao_estadual(encrypt(empresa.getInscricaoEstadual()));
        novaEmpresa.setCnpj(encrypt(empresa.getCnpj()));
        novaEmpresa.setEmail(encrypt(empresa.getEmail()));

        Telefone telefone = new Telefone();
        telefone.setDdd(encrypt(empresa.getTelefone().getDdd()));
        telefone.setNumero(encrypt(empresa.getTelefone().getNumero()));

        Login login = new Login();
        login.setEmail(encrypt(empresa.getEmail()));
        login.setSenha(encrypt(empresa.getSenha()));
        
        Endereco endereco = new Endereco();
        endereco.setLogradouro(encrypt(empresa.getEndereco().getLogradouro()));
        endereco.setNumero(encrypt(empresa.getEndereco().getNumero()));
        endereco.setComplemento(encrypt(empresa.getEndereco().getComplemento()));
        endereco.setBairro(encrypt(empresa.getEndereco().getBairro()));
        endereco.setCidade(encrypt(empresa.getEndereco().getCidade()));
        endereco.setEstado(encrypt(empresa.getEndereco().getEstado()));
        endereco.setCep(encrypt(empresa.getEndereco().getCep()));
        novaEmpresa.setEndereco(endereco);
        
    }

    public String encrypt(String encryptedValue) {
        return encryptor.encrypt(encryptedValue);
    }

}
