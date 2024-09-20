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
import com.cit.virtual_ponto.cadastro_empresa.repositories.EnderecoRepository;
import com.cit.virtual_ponto.cadastro_empresa.repositories.LoginRepository;
import com.cit.virtual_ponto.cadastro_empresa.repositories.TelefoneRepository;

import java.util.Optional;

@Service
public class CadastroEmpresaService {

    private CadastroEmpresaRepository cadastroEmpresaRepository;

    private TelefoneRepository telefoneRepository;
    private EnderecoRepository enderecoRepository;
    private LoginRepository loginRepository;

    private StringEncryptor encryptor;

    @Autowired
    public void setEncryptor(@Qualifier("jasyptStringEncryptor") StringEncryptor encryptor) {
        this.encryptor = encryptor;
    }

    @Autowired
    public CadastroEmpresaService(
            CadastroEmpresaRepository cadastroEmpresaRepository, 
            TelefoneRepository telefoneRepository,
            EnderecoRepository enderecoRepository,
            LoginRepository loginRepository
            ) {
        this.cadastroEmpresaRepository = cadastroEmpresaRepository;
        this.telefoneRepository = telefoneRepository;
        this.enderecoRepository = enderecoRepository;
        this.loginRepository = loginRepository;
    }

    //metodo responsavel por cadastrar empresa
    @Transactional
    public PessoaJuridica cadastrarEmpresa(EmpresaDto empresa) {

        // valida se já está cadastradada
        this.validarCadastroEmpresa(empresa);

        PessoaJuridica novaEmpresa = new PessoaJuridica();
        //criptografa as informações da nova empresa
        this.setEmpresaFields(novaEmpresa, empresa);

        // salva a nova empresa
        loginRepository.save(novaEmpresa.getLogin());
        telefoneRepository.save(novaEmpresa.getTelefone());
        enderecoRepository.save(novaEmpresa.getEndereco());
        
        return cadastroEmpresaRepository.save(novaEmpresa);
    }

    //metodo responsavel por atualizar empresa
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
            loginRepository.save(empresaAtualizada.getLogin());
            telefoneRepository.save(empresaAtualizada.getTelefone());
            enderecoRepository.save(empresaAtualizada.getEndereco());
            
            return cadastroEmpresaRepository.save(empresaAtualizada);

        }  else {
            throw new ErrosSistema.EmpresaException(
                EnumErrosCadastroEmpresa.ID_EMPRESA_NAO_ENCONTRADA.getMensagemErro());
        }
    }

    //metodo responsavel por excluir empresa
   @Transactional
    public PessoaJuridica excluirEmpresa(Integer id) {
        Optional<PessoaJuridica> optionalEmpresa = cadastroEmpresaRepository.findById(id);

        if (optionalEmpresa.isPresent()) {
            PessoaJuridica empresaExcluida = optionalEmpresa.get();
            cadastroEmpresaRepository.deleteById(id);
            return empresaExcluida;
        } else {
            throw new ErrosSistema.EmpresaException(
                EnumErrosCadastroEmpresa.ID_EMPRESA_NAO_ENCONTRADA.getMensagemErro());
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
        novaEmpresa.setNomeFantasia(encrypt(empresa.getNomeFantasia()));
        novaEmpresa.setRazaoSocial(encrypt(empresa.getRazaoSocial()));
        novaEmpresa.setInscricao_estadual(encrypt(empresa.getInscricaoEstadual()));
        novaEmpresa.setCnpj(encrypt(empresa.getCnpj()));
        novaEmpresa.setEmail(encrypt(empresa.getEmail()));

        novaEmpresa.setTelefone(new Telefone());
        novaEmpresa.getTelefone().setDdd(encrypt(empresa.getTelefone().getDdd()));
        novaEmpresa.getTelefone().setNumero(encrypt(empresa.getTelefone().getNumero()));
        novaEmpresa.setLogin(new Login());
        novaEmpresa.getLogin().setEmail(encrypt(empresa.getLogin().getEmail()));
        novaEmpresa.getLogin().setSenhaUsuario(encrypt(empresa.getLogin().getSenha()));
        
        novaEmpresa.setEndereco(new Endereco());
        novaEmpresa.getEndereco().setLogradouro(encrypt(empresa.getEndereco().getLogradouro()));
        novaEmpresa.getEndereco().setNumero(encrypt(empresa.getEndereco().getNumero()));
        novaEmpresa.getEndereco().setComplemento(encrypt(empresa.getEndereco().getComplemento()));
        novaEmpresa.getEndereco().setBairro(encrypt(empresa.getEndereco().getBairro()));
        novaEmpresa.getEndereco().setCidade(encrypt(empresa.getEndereco().getCidade()));
        novaEmpresa.getEndereco().setEstado(encrypt(empresa.getEndereco().getEstado()));
        novaEmpresa.getEndereco().setCep(encrypt(empresa.getEndereco().getCep()));
        
    }

    public String encrypt(String encryptedValue) {
        return encryptor.encrypt(encryptedValue);
    }

}
