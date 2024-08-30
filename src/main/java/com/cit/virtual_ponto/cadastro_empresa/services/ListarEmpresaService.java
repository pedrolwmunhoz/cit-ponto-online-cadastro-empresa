package com.cit.virtual_ponto.cadastro_empresa.services;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cit.virtual_ponto.cadastro_empresa.exceptions.EnumErrosCadastroEmpresa;
import com.cit.virtual_ponto.cadastro_empresa.exceptions.ErrosSistema;
import com.cit.virtual_ponto.cadastro_empresa.models.PessoaJuridica;
import com.cit.virtual_ponto.cadastro_empresa.repositories.CadastroEmpresaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<PessoaJuridica> listarEmpresas() {
        List<PessoaJuridica> empresas = cadastroEmpresaRepository.findAll();
        empresas.forEach(this::decryptEmpresaFields);
        return empresas;
    }

    public PessoaJuridica buscarEmpresaPorId(Integer id) {
        Optional<PessoaJuridica> empresa = cadastroEmpresaRepository.findById(id);
        if (empresa.isPresent()) {
            PessoaJuridica empresaExistente = empresa.get();
            this.decryptEmpresaFields(empresaExistente);
            return empresaExistente;
        } else {
            throw new ErrosSistema.EmpresaException(
                    EnumErrosCadastroEmpresa.EMPRESA_NAO_ENCONTRADO_ID.getMensagemErro() + id);
        }
    }

    public List<PessoaJuridica> buscarEmpresaPorNome(String nomeEmpresa) {

        // recupera lista de todas as empresas
        List<PessoaJuridica> empresas = cadastroEmpresaRepository.findAll();
        empresas.forEach(this::decryptEmpresaFields);

        // filtra por nome
        List<PessoaJuridica> empresasFiltrada = empresas.stream()
                .filter(empresa -> nomeEmpresa.equalsIgnoreCase(empresa.getNome()))
                .collect(Collectors.toList());

        return empresasFiltrada;
    }

    private void decryptEmpresaFields(PessoaJuridica empresa) {

        empresa.setNome(decrypt(empresa.getNome()));
        empresa.setInscricao_estadual(decrypt(empresa.getInscricao_estadual()));
        empresa.setCnpj(decrypt(empresa.getCnpj()));
        empresa.setEmail(decrypt(empresa.getEmail()));

        empresa.getTelefone().setDdd(decrypt(empresa.getTelefone().getDdd()));
        empresa.getTelefone().setNumero(decrypt(empresa.getTelefone().getNumero()));

        empresa.getLogin().setEmail(decrypt(empresa.getLogin().getEmail()));
        empresa.getLogin().setSenhaUsuario(decrypt(empresa.getLogin().getSenhaUsuario()));
        
        empresa.getEndereco().setLogradouro(decrypt(empresa.getEndereco().getLogradouro()));
        empresa.getEndereco().setNumero(decrypt(empresa.getEndereco().getNumero()));
        empresa.getEndereco().setComplemento(decrypt(empresa.getEndereco().getComplemento()));
        empresa.getEndereco().setBairro(decrypt(empresa.getEndereco().getBairro()));
        empresa.getEndereco().setCidade(decrypt(empresa.getEndereco().getCidade()));
        empresa.getEndereco().setEstado(decrypt(empresa.getEndereco().getEstado()));
        empresa.getEndereco().setCep(decrypt(empresa.getEndereco().getCep()));

    }

    public String decrypt(String encryptedValue) {
        return encryptor.decrypt(encryptedValue);
    }
}
