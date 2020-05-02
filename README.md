<p align="center">
    <img alt="Code Nation" src="https://raw.githubusercontent.com/ruiblaese/codenation-aceleradev-java-project/master/.github/codenation.jpeg" />
</p>

<h1 align="center">
    Projeto curso AceleraDev Java da CodeNation
</h1>
<p align="center">  
  <img alt="Made by Rui" src="https://img.shields.io/badge/Made%20by-ruiblaese-%2304D361">
  
  <img alt="Made with Java" src="https://img.shields.io/badge/Made%20with-Java-%1f425f">  
  
  <img alt="Made with SpringBoot" src="https://img.shields.io/badge/Made%20with-SpringBoot-%1f425f">  

  <img alt="CircleCI" src="https://circleci.com/gh/ruiblaese/codenation-aceleradev-java-project.svg?style=shield">  

<img alt="Project top programing language" src="https://img.shields.io/github/languages/top/ruiblaese/codenation-aceleradev-java-project">  

  <img alt="Repository size" src="https://img.shields.io/github/repo-size/ruiblaese/codenation-aceleradev-java-project">
</p>
 
<p align="center">
    <a href="#central-de-erros">Proposta: Central de Erros</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
    <a href="#desenvolvimento">Desenvolvimento</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
    <a href="#deploy-heroku">Deploy Heroku</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
    <a href="#contato">Contato</a>
</p>
 

## Central de Erros
### Objetivo
Em projetos modernos é cada vez mais comum o uso de arquiteturas baseadas em serviços ou microsserviços. Nestes ambientes complexos, erros podem surgir em diferentes camadas da aplicação (backend, frontend, mobile, desktop) e mesmo em serviços distintos. Desta forma, é muito importante que os desenvolvedores possam centralizar todos os registros de erros em um local, de onde podem monitorar e tomar decisões mais acertadas. Neste projeto vamos implementar uma API Rest para centralizar registros de erros de aplicações.

Abaixo estão os requisitos desta API, o time terá total liberdade para tomar as decisões técnicas e de arquitetura da API, desde que atendam os requisitos abaixo.

### API
#### Tecnologia
Utilizar a tecnologia base da aceleração para o desenvolvimento (Exemplo: Java, Node.js)
Premissas
A API deve ser pensada para atender diretamente um front-end
Deve ser capaz de gravar os logs de erro em um banco de dados relacional
O acesso a ela deve ser permitido apenas por requisições que utilizem um token de acesso válido
#### Funcionalidades
Deve permitir a autenticação do sistema que deseja utilizar a API gerando o Token de Acesso
Pode ser acessado por multiplos sistemas
Deve permitir gravar registros de eventos de log salvando informações de Level(error, warning, info), Descrição do Evento, LOG do Evento, ORIGEM(Sistema ou Serviço que originou o evento), DATA(Data do evento), QUANTIDADE(Quantidade de Eventos de mesmo tipo)
Deve permitir a listagem dos eventos juntamente com a filtragem de eventos por qualquer parâmetro especificado acima
Deve suportar Paginação
Deve suportar Ordenação por diferentes tipos de atributos
A consulta de listagem não deve retornar os LOGs dos Eventos
Deve permitir a busca de um evento por um ID, dessa maneira exibindo o LOG desse evento em específico

## Desenvolvimento

### Checklist funcionalidades
* Autenticação da aplicação gerando Token de Acesso :heavy_check_mark:
* Acesso por multiplos sistemas :heavy_check_mark:
* Permitir gravar registros de eventos :heavy_check_mark:
* Permitir listagem dos eventos :heavy_check_mark:
  * Filtrar eventos por qualquer parâmetro especificado :x:
  * Suportar paginação :x:
  * Suportar ordenação por diferentes tipos de atributos :x:
* Permitir busca de evento por um Id :x:

### Detalhes do projeto
* Projeto criado com Spring Boot e Java 8
* Banco de dados Postgres com JPA e Spring Data JPA
* Implementado utilizado TDD
* Versionamento de banco de dados com Flyway
* Testes com JUnit e Mockito com banco H2 em memória
* Deploy no servidor Heroku (instância gratuita)
* Integração contínua com CircleCI
* Project Lombok
* Documentação dos endpoints com Swagger
* Segurança da API com autenticação via tokens JWT 

### Requerimentos
- Java 8
- Docker

### Como executar a aplicação
```bash
git clone https://github.com/ruiblaese/codenation-aceleradev-java-project
cd codenation-aceleradev-java-project
docker-compose up -d db
./mvnw spring-boot:run
# Acesse os endpoints através da url http://# localhost:8080
```

Também é possível compilar o projeto para executar em um ambiente de produção, para isso execute o seguinte comando na raiz do projeto

```bash
./mvnw clean install
```

O pacote será gerado dentro da pasta target, basta executá-lo com o comando abaixo, não esquecendo de configurar qual o profile e a porta que a aplicação deverá utilizar.
Também é necessário criar uma variável de ambiente com as credenciais de acesso ao banco de dados com o nome $DATABASE_URL ou alterar o arquivo application-prod.properties.

```bash
java -jar -Dspring.profiles.active=prod -Dserver.port=443 error.manager-0.0.1-SNAPSHOT.jar
```
### Como autenticar na API
Antes de fazer requisições nos endpoint você deve estar autenticado, antes crie um usuário através de uma requisição POST na rota */user* como no exemplo abaixo:

```json
{
	"name": "user",
	"email": "user@user.com",
	"password": "123456"
}
```
Exemplo requisição acima com cURL: 
```bash
curl -X POST "http://localhost:8080/user" -H "accept: */*" -H "Content-Type: application/json" -d "{\t\"name\": \"user\",\t\"email\": \"user@user.com\",\t\"password\": \"123456\"}"
```

Agora você pode autenticar na API e obter um token de acesso através do endpoint */auth*, utilizando o email e a senha cadastrados:

```json
{
	"email": "user@user.com",
	"password":"123456"
}
```

Exemplo requisição acima com cURL: 
```bash
curl -X POST "http://localhost:8080/auth" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"email\": \"user@user.com\", \"password\": \"123456\"}"
```


Você receberá um token do tipo Bearer que deve ser enviado no cabeçalho das requisições.

Exemplo requisição com Bearer: 
```bash
curl -X GET "http://localhost:8080/log" -H "accept: */*" -H "Authorization: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyQHVzZXIuY29tIiwicm9sZSI6bnVsbCwiY3JlYXRlZCI6MTU4ODQ1NTc4MDgyNCwiZXhwIjoxNTg4NDYxNzgwfQ.tg--NuT6f51oNOqubVDFaZi70UPDkaqZJSXfCiYqe_6EybHdXu2zKvVjhDn3NAHLqLN_if4ATsUvfw27d7Yp9A"
```

Todos esses exemplos e outros podem ser encontrados e testados na documentação swagger.

#### Como executar os testes
Os testes podem ser executados com o seguinte comando:

```bash
./mvnw test
```

#### Documentação
Utilize a interface do Swagger para ter acesso a documentação dos endpoints, ela está disponível na url http://localhost:8080/swagger-ui.html


## Deploy Heroku
- API: https://blaese-error-manager.herokuapp.com/   
- Swagger: https://blaese-error-manager.herokuapp.com/swagger-ui.html  


## Contato

<a href="https://www.linkedin.com/in/ruiblaese/" target="_blank" >
  <img alt="Linkedin - Rui Richard Blaese" src="https://img.shields.io/badge/Linkedin--%23F8952D?style=social&logo=linkedin">
</a>&nbsp;&nbsp;&nbsp;
<a href="mailto:ruiblaese@gmail.com" target="_blank" >
  <img alt="Email - Rui Richard Blaese" src="https://img.shields.io/badge/Email--%23F8952D?style=social&logo=gmail">
</a> 
 
