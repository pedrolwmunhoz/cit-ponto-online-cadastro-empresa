package com.cit.virtual_ponto.cadastro_empresa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cit.virtual_ponto.cadastro_empresa.dto.EmpresaDto;
import com.cit.virtual_ponto.cadastro_empresa.models.EmpresaEntity;
import com.cit.virtual_ponto.cadastro_empresa.services.CadastroEmpresaService;
import com.cit.virtual_ponto.cadastro_empresa.services.ListarEmpresaService;
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

    @Autowired
    public CadastroEmpresaController(CadastroEmpresaService empresaService, ListarEmpresaService listarEmpresaService) {
        this.empresaService = empresaService;
        this.listarEmpresaService = listarEmpresaService;
    }

    @GetMapping("/cadastrar")
    public ResponseEntity<EmpresaEntity> cadastrarEmpresa() {
        EmpresaDto empresa = new EmpresaDto();
        empresa.setEmpresaId(1L);
        empresa.setNomeEmpresa("Empresa Teste");
        empresa.setRazaoSocial("Razão Social Teste");
        empresa.setCnpj("12345678901234"); // Preencha com um CNPJ válido
        empresa.setLogradouro("Rua Teste");
        empresa.setNumero("123");
        empresa.setComplemento("Complemento Teste");
        empresa.setBairro("Bairro Teste");
        empresa.setCidade("Cidade Teste");
        empresa.setEstado("SP"); // Exemplo de estado
        empresa.setCep("12345-678"); // Exemplo de CEP
        empresa.setTelefone("(12) 3456-7890"); // Exemplo de telefone
        empresa.setEmail("empresa@teste.com");
        empresa.setSenha("12345678");
        EmpresaEntity novoEmpresa = empresaService.cadastrarEmpresa(empresa);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoEmpresa);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<EmpresaEntity> atualizarEmpresa(@RequestBody @Valid EmpresaDto empresa) {
        EmpresaEntity empresaAtualizada = empresaService.atualizarEmpresa(empresa);
        return ResponseEntity.status(HttpStatus.OK).body(empresaAtualizada);
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<EmpresaEntity> excluirEmpresa(
            @PathVariable @Min(value = 1, message = "O ID da empresa deve ser um valor positivo") Long id) {
        EmpresaEntity empresaExcluida = empresaService.excluirEmpresa(id);
        return ResponseEntity.status(HttpStatus.OK).body(empresaExcluida);
    }

    @GetMapping("/listar-empresas")
    public ResponseEntity<List<EmpresaEntity>> listarEmpresas() {
        List<EmpresaEntity> empresas = listarEmpresaService.listarEmpresas();
        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/buscar-nome/{nomeEmpresa}")
    public ResponseEntity<List<EmpresaEntity>> buscarEmpresaPorNome(
            @PathVariable @NotBlank(message = "O nome da empresa não pode ser vazio") @Size(min = 2, max = 100, message = "O nome da empresa deve ter entre 2 e 100 caracteres") String nomeEmpresa) {
        List<EmpresaEntity> empresas = listarEmpresaService.buscarEmpresaPorNome(nomeEmpresa);
        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<EmpresaEntity> buscarEmpresaPorId(
            @PathVariable @Min(value = 1, message = "O ID da empresa deve ser um valor positivo") Long id) {
        return ResponseEntity.ok(listarEmpresaService.buscarEmpresaPorId(id));
    }

}
