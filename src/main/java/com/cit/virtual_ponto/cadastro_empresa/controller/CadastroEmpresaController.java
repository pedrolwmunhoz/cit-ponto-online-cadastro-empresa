package com.cit.virtual_ponto.cadastro_empresa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cit.virtual_ponto.cadastro_empresa.dto.EmpresaDto;
import com.cit.virtual_ponto.cadastro_empresa.models.EmpresaEntity;
import com.cit.virtual_ponto.cadastro_empresa.services.CadastroEmpresaService;
import com.cit.virtual_ponto.cadastro_empresa.services.ListarEmpresaService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class CadastroEmpresaController {

    private final CadastroEmpresaService empresaService;
    private final ListarEmpresaService listarEmpresaService;

    @Autowired
    public CadastroEmpresaController(CadastroEmpresaService empresaService, ListarEmpresaService listarEmpresaService) {
        this.empresaService = empresaService;
        this.listarEmpresaService = listarEmpresaService;
    }

    @GetMapping("/cadastrar")
    public ResponseEntity<EmpresaEntity> cadastrarEmpresa(@RequestBody EmpresaDto empresa) {
        EmpresaEntity novoEmpresa = empresaService.cadastrarEmpresa(empresa);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoEmpresa);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<EmpresaEntity> atualizarEmpresa(@RequestBody EmpresaDto empresa) {
        EmpresaEntity empresaAtualizada = empresaService.atualizarEmpresa(empresa);
        if (empresaAtualizada != null) {
            return ResponseEntity.ok(empresaAtualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> excluirEmpresa(@PathVariable Long id) {
        boolean removido = empresaService.excluirEmpresa(id);
        if (removido) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/listar-empresas")
    public ResponseEntity<List<EmpresaEntity>> listarEmpresas() {
        List<EmpresaEntity> empresas = listarEmpresaService.listarEmpresas();
        return ResponseEntity.ok(empresas);
}


    @GetMapping("/buscar-nome")
    public ResponseEntity<List<EmpresaEntity>> buscarEmpresaPorNome(@RequestParam String nomeEmpresa) {
        List<EmpresaEntity> empresas = listarEmpresaService.buscarEmpresaPorNome(nomeEmpresa);
        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<EmpresaEntity> buscarEmpresaPorId(@PathVariable Long id) {
        Optional<EmpresaEntity> empresa = listarEmpresaService.buscarEmpresaPorId(id);
        if (empresa.isPresent()) {
            return ResponseEntity.ok(empresa.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
