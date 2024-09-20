package com.cit.virtual_ponto.cadastro_empresa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cit.virtual_ponto.cadastro_empresa.dto.EmpresaDto;
import com.cit.virtual_ponto.cadastro_empresa.dto.EnderecoDto;
import com.cit.virtual_ponto.cadastro_empresa.dto.LoginDto;
import com.cit.virtual_ponto.cadastro_empresa.dto.TelefoneDto;
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
        // Criação de uma nova empresa com dados fictícios
            EmpresaDto novaEmpresa = new EmpresaDto();
            novaEmpresa.setPessoaId(10);
            novaEmpresa.setNomeFantasia("Munhoz Enterprise");
            novaEmpresa.setRazaoSocial("LTDA Munhoz Enterprise");
            novaEmpresa.setInscricaoEstadual("123456789");
            novaEmpresa.setCnpj("12.345.678/0001-99");
            novaEmpresa.setEmail("empresa@example.com");

            // Configuração do telefone
            TelefoneDto telefone = new TelefoneDto();
            telefone.setDdd("11");
            telefone.setNumero("987654321");
            novaEmpresa.setTelefone(telefone);

            // Configuração do login
            LoginDto login = new LoginDto();
            login.setEmail("empresa@example.com");
            login.setSenha("senha123");
            novaEmpresa.setLogin(login);

            // Configuração do endereço
            EnderecoDto endereco = new EnderecoDto();
            endereco.setLogradouro("Rua Exemplo");
            endereco.setNumero("100");
            endereco.setComplemento("Apto 202");
            endereco.setBairro("Centro");
            endereco.setCidade("São Paulo");
            endereco.setEstado("SP");
            endereco.setCep("12345-678");
            novaEmpresa.setEndereco(endereco);

        PessoaJuridica novoEmpresa = empresaService.cadastrarEmpresa(novaEmpresa);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoEmpresa);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<PessoaJuridica> atualizarEmpresa(@RequestBody @Valid EmpresaDto empresa) {
        PessoaJuridica empresaAtualizada = empresaService.atualizarEmpresa(empresa);
        return ResponseEntity.status(HttpStatus.OK).body(empresaAtualizada);
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<PessoaJuridica> excluirEmpresa(
            @PathVariable @Min(value = 1, message = "O ID da empresa deve ser um valor positivo") Integer id) {
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
            @PathVariable @Min(value = 1, message = "O ID da empresa deve ser um valor positivo") Integer id) {
        return ResponseEntity.ok(listarEmpresaService.buscarEmpresaPorId(id));
    }


    @PostMapping("/validar-login")
    public ResponseEntity<PessoaJuridica> validarLogin(@RequestBody @Valid LoginDto loginRequestDto) {
        return ResponseEntity.ok(validaLoginEmpresaService.validarLogin(loginRequestDto));
    }

}
