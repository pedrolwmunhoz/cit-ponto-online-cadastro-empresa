# cit-virtual-ponto-cadastro-empresas

O cit-virtual-ponto-cadastro-empresas é um microsserviço para controle e cadastro de empresas no ponto virtual.

## Funcionamento

O serviço recebe uma solicitação 
    - 

Este projeto é codificado em Java 17/Maven utilizando o framework [SpringBoot](<https://spring.io/>) 3.3.0.

**Atenção**: Esse documento deve servir como guia para novos desenvolvedores por isso sempre manter ele atualizado com as dependencias necessarias para se executar esse projeto, 
como por exemplo as configurações das variaveis de ambiente (environments) e os sistemas que ele precisa para funcionar corretamente.

Aplicação: 

As configurações sensiveis desse projeto ficam em variaveis de ambiente que estão localizadas nos arquivos .env.
**Atenção**: Esses arquivos não devem ser comitados no repositorio, eles devem estar no .gitignore do seu projeto, pois podem possuir senhas 
e configurações privadas a equipe de desenvolvimento.

Conteudo do arquivo .env

| Nome da Env                 | Descrição do valor                               | Valor Padrão                                                        |
|:----------------------------|:-------------------------------------------------|:--------------------------------------------------------------------|
| SPRING_DATASOURCE_USERNAME | Nome do usuario de banco de dados                | "root"                                                        |
| SPRING_DATASOURCE_PASSWORD | Senha do usuario de banco de dados               | "taSumwUsxFsg71uPmlmsFnu1Od47hQS2"                                                      |
| SPRING_DATASOURCE_JDBC_URL | url de conexão do banco de dados                 | "jdbc:postgresql://dpg-cpp2n3g8fa8c73988ij0-a.oregon-postgres.render.com/virtualpontodb" |


Voce pode excutar o projeto com o comando **mvn spring-boot:run**, ao executar o Maven inicia a execução do projeto Spring Boot atual. Isso compila o projeto, resolve suas dependências, e inicia a aplicação Spring Boot incorporada, conforme configurado no arquivo pom.xml e na classe principal do aplicativo Spring Boot.

``` bash
mvn spring-boot:run
```


Os testes neste projeto devem ser feitos utilizando a versão 5 do Junit e com as anotações de teste do proprio quarkus.
A cobertura é feita utilizando o jacoco e ja esta configurado no pom.xml.

As classes de teste devem ficar na pasta: **/src/test/** e seguir a mesma organização das pastas do projeto.

Os testes vão executados sempre quando a aplicação foi construida, isso ocorre quando voce executar o script `./run/run.sh` com o comando
para executar com o docker compose.

Quando executar no modo local ele vai subir a aplicação sem executar os testes, contudo a versão 2 do quarkus permite a execução dos testes
durante o desenvolvimento, com as opções que são apresentadas no terminal

Caso precise executar os testes para verificar a cobertura execute o comando `test` ou `verify` do maven como nos exemplos abaixo:

``` bash
.\mvn clean test
```

``` bash
.\mvn clean verify
```

Se os testes executarem com sucesso o resultado da cobertura ficara localizado em `/target/site/jacoco/index.html`

