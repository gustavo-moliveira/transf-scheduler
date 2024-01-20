# transf-scheduler

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

