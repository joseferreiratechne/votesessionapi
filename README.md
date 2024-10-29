# Vote Session API

Esta API de Votação permite que os usuários criem e gerenciem sessões de votação para tópicos específicos. 
Com funcionalidades como abertura de sessões, registro de votos e consulta de resultados, a API é ideal para desenvolvedores que desejam integrar recursos de votação em suas aplicações. 
A API é projetada para ser fácil de usar e se integra perfeitamente a sistemas de gerenciamento de eventos e plataformas de feedback.

## Tecnologias Utilizadas

- Java 22.0.1
- Spring Boot 3.x
- Springdoc OpenAPI 2.6.0
- PostgreSQL
- RabbitMQ
- Docker

## Instalação

Siga os passos abaixo para instalar e configurar o projeto:

1. **Clone o repositório**:
   ```
   git clone https://github.com/joseferreiratechne/votesessionapi.git
   cd votesessionapi
   ```

## Verificações e execuções

1. **Verifique se o Docker está instalado**:
Execute o comando abaixo para verificar se o Docker está instalado e funcionando corretamente:
```
docker --version
```

2. Se o Docker não estiver instalado, siga as instruções de instalação para o seu sistema operacional.

3. **Inicie os containers com Docker Compose**:

Execute o comando abaixo para iniciar os containers do PostgreSQL e RabbitMQ:

```
docker-compose up -d
```

4. **Instale as dependências**:

```
mvn install
```

5. **Executo a aplicação**

```
mvn spring-boot:run
```

## Uso
Após iniciar a aplicação, você pode acessar os seguintes endpoints:

Documentação da API: http://localhost:8080/swagger-ui.html
API Docs: http://localhost:8080/v3/api-docs
RabbitMQ: http://localhost:15672
user/password : guest

**Exemplo de Requisição**
```
curl -X GET http://localhost:8080/v1/exemplo
```

## Observações
A sessão ficará disponivel pelo tempo que foi informado ou por default(1 minuto), assim que for encerrado o resultado será mostrado no console da aplicação, mas tambem pode verificar o resultado de qualquer sessão via url.

## Versionamento da API
A API utiliza versionamento na URL. As versões da API são especificadas na URL, permitindo que diferentes versões coexistam. Por exemplo, para acessar a versão 1 da API, utilize o seguinte formato:
http://localhost:8080/v1/recursos
