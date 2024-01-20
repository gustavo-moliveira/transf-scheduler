# TRANSF SCHEDULER

O Transf Scheduler é um projeto de exemplo para agendamento de transferências, desenvolvido em Java com Spring Boot para o backend e Vue.js para o frontend.

## Tecnologias Utilizadas

### Backend
- Java
- Spring Boot
- Hibernate
- H2 Database (em memória)
- Maven
- JUnit
- Mockito

### Frontend
- Vue.js
- Axios

### Docker
- Docker Compose
  
## Executando o Projeto com Docker

Certifique-se de ter o Docker instalado na sua máquina.

Clone este repositório:
```
git clone https://github.com/gustavo-moliveira/transf-scheduler.git
```
```
cd transf-scheduler
```
```
docker-compose up
```

Aguarde até que os serviços estejam prontos.

O frontend estará disponível em http://localhost:8080 e o backend em http://localhost:8081.

## Backend

Navegue até o diretório do backend:
```
cd backend
```
Execute o seguinte comando Maven:
```
mvn spring-boot:run
```
## Frontend
```
cd frontend
```
Instale as dependências do Node.js:
```
npm install
```
Execute o servidor de desenvolvimento:
```
npm run serve
```
O frontend estará disponível em http://localhost:8080.

## Documentação

Optei por não utilizar o Swagger porque são poucos endpoints e o foco da aplicação não é isso. O foco é ver ela como um todo então decidi centralizar todo o conteúdo dentro do readme.

# Endpoints da API

A API fornece os seguintes endpoints para gerenciar transferências:

## Obter todas as transferências

### Endpoint
```
GET /v1/api/transfers
```
Este endpoint retorna uma lista de todas as transferências agendadas.

Como testar:
Faça uma solicitação GET para http://localhost:8081/v1/api/transfers usando seu cliente HTTP favorito (por exemplo, Postman).

## Agendar uma nova transferência

### Endpoint
```
POST /v1/api/transfers
```
Este endpoint permite agendar uma nova transferência.

### Parâmetros do corpo (JSON):

```json
{
  "sourceAccount": "ContaOrigem123",
  "destinationAccount": "ContaDestino456",
  "amount": 100.00,
  "transferDate": "25/01/2024"
}

```
Como testar:
Faça uma solicitação POST para http://localhost:8081/v1/api/transfers usando seu cliente HTTP favorito (por exemplo, Postman) e forneça os parâmetros do corpo conforme o exemplo acima.

A API retornará os detalhes da transferência agendada se for bem-sucedida.
