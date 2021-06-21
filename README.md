# hotel-spring

Projeto que visa fazer a gestão básica de um serviço de check in e check out de um hotel. Esse projeto possui as seguintes dependencias principais: Lombok, Spring, Hibernate,
Mockito, Docker, PostgreSQL.

<br>

<h2>🛠 Instalação</h2>

  1. Iniciando Banco de Dados
    O banco de dados(PostgreSQL) está sendo utilizado dentro de um docker, então é necessária a inicialização do docker.
    
    docker-composer up
  <br>
    
  2. Criando Tabela e Usuário
    Para criar a tabela e o usuário utilizado basta executar o script run.sh dentro da pasta database
    
    bash run.sh
  <br>
    
  Logo feito os passos acima serem feitos você já poderá iniciar o projeto que se encontra na pasta user utilizando Java 11. Logo na inicizalização o usuário padrão será criado com as credenciais <br><br>
  **username:** root<br>
  **password:** root@123456
  
<br>
<h2>Testando</h2>
O arquivo .json contém todas os endpoints para importaçao no Postman.

Em adiçao a isso, toda a documentação foi feita utilizando Swagger. Para Acessá-la basta entrar no link:<br><br>
http://your_host:8090/swagger-ui/
