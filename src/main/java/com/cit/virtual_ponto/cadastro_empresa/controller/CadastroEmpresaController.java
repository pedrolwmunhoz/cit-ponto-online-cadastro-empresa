package com.cit.virtual_ponto.cadastro_empresa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cit.virtual_ponto.cadastro_empresa.dto.EmpresaDto;
import com.cit.virtual_ponto.cadastro_empresa.dto.LoginRequestDto;
import com.cit.virtual_ponto.cadastro_empresa.models.Endereco;
import com.cit.virtual_ponto.cadastro_empresa.models.PessoaJuridica;
import com.cit.virtual_ponto.cadastro_empresa.services.CadastroEmpresaService;
import com.cit.virtual_ponto.cadastro_empresa.services.ListarEmpresaService;
import com.cit.virtual_ponto.cadastro_empresa.services.ValidaLoginEmpresaService;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

@RestController
@RequestMapping("/")
@Validated
public class CadastroEmpresaController {

    private final CadastroEmpresaService empresaService;
    private final ListarEmpresaService listarEmpresaService;
    private final ValidaLoginEmpresaService validaLoginEmpresaService;

    @Autowired
    public CadastroEmpresaController(CadastroEmpresaService empresaService, ListarEmpresaService listarEmpresaService, ValidaLoginEmpresaService validaLoginEmpresaService) {
        this.empresaService = empresaService;
        this.listarEmpresaService = listarEmpresaService;
        this.validaLoginEmpresaService = validaLoginEmpresaService;
    }

    @GetMapping("/cadastrar")
    public ResponseEntity<PessoaJuridica> cadastrarEmpresa() {
        
        EmpresaDto novoEmpresa = new EmpresaDto();
        // Dados mockados para exemplo
        novoEmpresa.setPessoaId(10L);
        novoEmpresa.setCnpj("12345678000199");
        novoEmpresa.setRazaoSocial("Empresa Exemplo Ltda.");
        novoEmpresa.setInscricaoEstadual("123456789");
        novoEmpresa.setNome("Nome da Empresa Exemplo");
        novoEmpresa.setTelefone("11987654321");
        novoEmpresa.setEmail("empresa@exemplo.com");
        novoEmpresa.setSenha("senhaSegura123");
    
        // Dados do endereço mockado
        Endereco enderecoMock = new Endereco();
        enderecoMock.setLogradouro("Rua Exemplo");
        enderecoMock.setNumero("100");
        enderecoMock.setBairro("Centro");
        enderecoMock.setCidade("São Paulo");
        enderecoMock.setEstado("SP");
        enderecoMock.setCep("01001000");
        novoEmpresa.setEndereco(enderecoMock);
    
        // Dados relacionados ao Departamento ou outras relações podem ser definidos aqui
        // Departamento departamentoMock = new Departamento();
        // departamentoMock.setNome("Tecnologia");
        // novoEmpresa.setDepartamento(departamentoMock);
        PessoaJuridica empresa = empresaService.cadastrarEmpresa(novoEmpresa);
        return ResponseEntity.status(HttpStatus.CREATED).body(empresa);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<PessoaJuridica> atualizarEmpresa(@RequestBody @Valid EmpresaDto empresa) {
        PessoaJuridica empresaAtualizada = empresaService.atualizarEmpresa(empresa);
        return ResponseEntity.status(HttpStatus.OK).body(empresaAtualizada);
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<PessoaJuridica> excluirEmpresa(
            @PathVariable @Min(value = 1, message = "O ID da empresa deve ser um valor positivo") Long id) {
        PessoaJuridica empresaExcluida = empresaService.excluirEmpresa(id);
        return ResponseEntity.status(HttpStatus.OK).body(empresaExcluida);
    }

    @GetMapping("/listar-empresas")
    public ResponseEntity<List<PessoaJuridica>> listarEmpresas() {
        List<PessoaJuridica> empresas = listarEmpresaService.listarEmpresas();
        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/buscar-nome/{nomeEmpresa}")
    public ResponseEntity<List<PessoaJuridica>> buscarEmpresaPorNome(
            @PathVariable @NotBlank(message = "O nome da empresa não pode ser vazio") @Size(min = 2, max = 100, message = "O nome da empresa deve ter entre 2 e 100 caracteres") String nomeEmpresa) {
        List<PessoaJuridica> empresas = listarEmpresaService.buscarEmpresaPorNome(nomeEmpresa);
        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<PessoaJuridica> buscarEmpresaPorId(
            @PathVariable @Min(value = 1, message = "O ID da empresa deve ser um valor positivo") Long id) {
        return ResponseEntity.ok(listarEmpresaService.buscarEmpresaPorId(id));
    }


    @PostMapping("/validar-login")
    public ResponseEntity<PessoaJuridica> validarLogin(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(validaLoginEmpresaService.validarLogin(loginRequestDto));
    }

}
