package com.cit.virtual_ponto.cadastro_empresa.dto;

import lombok.Data;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

@Data
public class EmpresaDto {

    private Long empresaId;

    @NotBlank(message = "O nome da empresa não pode ser vazio")
    @Size(max = 100, message = "O nome da empresa deve ter no máximo 100 caracteres")
    private String nomeEmpresa;

    @NotBlank(message = "A razão social não pode ser vazia")
    @Size(max = 100, message = "A razão social deve ter no máximo 100 caracteres")
    private String razaoSocial;

    @NotBlank(message = "O CNPJ não pode ser vazio")
    @Pattern(regexp = "\\d{14}", message = "O CNPJ deve conter 14 dígitos")
    private String cnpj;

    @NotBlank(message = "O logradouro não pode ser vazio")
    @Size(max = 255, message = "O logradouro deve ter no máximo 255 caracteres")
    private String logradouro;

    @NotBlank(message = "O número não pode ser vazio")
    @Size(max = 10, message = "O número deve ter no máximo 10 caracteres")
    private String numero;

    @Size(max = 255, message = "O complemento deve ter no máximo 255 caracteres")
    private String complemento;

    @NotBlank(message = "O bairro não pode ser vazio")
    @Size(max = 100, message = "O bairro deve ter no máximo 100 caracteres")
    private String bairro;

    @NotBlank(message = "A cidade não pode ser vazia")
    @Size(max = 100, message = "A cidade deve ter no máximo 100 caracteres")
    private String cidade;

    @NotBlank(message = "O estado não pode ser vazio")
    @Size(min = 2, max = 2, message = "O estado deve ter 2 caracteres")
    private String estado;

    @NotBlank(message = "O CEP não pode ser vazio")
    @Pattern(regexp = "\\d{8}", message = "O CEP deve conter 8 dígitos")
    private String cep;

    @NotBlank(message = "O telefone não pode ser vazio")
    @Pattern(regexp = "\\d{10,11}", message = "O telefone deve conter 10 ou 11 dígitos")
    private String telefone;

    @NotBlank(message = "O email não pode ser vazio")
    @Email(message = "O email deve ser válido")
    @Size(max = 100, message = "O email deve ter no máximo 100 caracteres")
    private String email;

    @NotBlank(message = "Senha não pode ser vazio")
    @Size(min = 8, max = 100, message = "Senha deve conter 8 ou mais caracteres")
    private String senha;

    @NotNull(message = "A lista de funcionários não pode ser nula")
    private List<@Valid FuncionarioDto> funcionarios;
}
