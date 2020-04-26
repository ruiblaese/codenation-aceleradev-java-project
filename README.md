<p align="center">
    <img alt="Code Nation" src="https://github.com/ruiblaese/codenation-aceleradev-java-project/blob/log/.github/codenation.jpeg?raw=true" />
</p>

<h1 align="center">
    Projeto curso AceleraDev Java da CodeNation
</h1>
<p align="center">  
  <img alt="Made by Rui" src="https://img.shields.io/badge/Made%20by-ruiblaese-%2304D361">
  
  <img alt="Made with Java" src="https://img.shields.io/badge/Made%20with-Java-%1f425f">  
  
  <img alt="Made with SpringBoot" src="https://img.shields.io/badge/Made%20with-SpringBoot-%1f425f">  

  <img alt="CircleCI" src="https://circleci.com/gh/ruiblaese/codenation-aceleradev-java-project.svg?style=shield">    
</p>
 
<p align="center">
    <a href="#central-de-erros">Proposta: Central de Erros</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
    <a href="#desenvolvimento">Desenvolvimento</a>&nbsp;&nbsp;&nbsp;    
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


## Contato

<a href="https://www.linkedin.com/in/ruiblaese/" target="_blank" >
  <img alt="Linkedin - Rui Richard Blaese" src="https://img.shields.io/badge/Linkedin--%23F8952D?style=social&logo=linkedin">
</a>&nbsp;&nbsp;&nbsp;
<a href="mailto:ruiblaese@gmail.com" target="_blank" >
  <img alt="Email - Rui Richard Blaese" src="https://img.shields.io/badge/Email--%23F8952D?style=social&logo=gmail">
</a> 
 
