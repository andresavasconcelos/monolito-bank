
# Bank - sistema de gerenciamento de conta de transações de valores de um banco

## Descrição do Projeto

Este projeto é um monolito que simula um sistema de banco onde posso gerenciar transferencia de valores e conta de pessoas cadastradas no banco.

## Funcionalidades

- **Cadastrar uma nova conta**;
- **Credita e Debita valores de uma conta bancária**;
- **Lista contas a partir do usuario admin:**;

## Estrutura do Projeto

```bash
bank/
├─ src/
│  ├─ main/
│  │  ├─ java/
│  │  │  └─ br/
│  │  │     └─ com/
│  │  │        └─ castGroup/
│  │  │           ├─ Main.java
│  │  │           ├─ controller/
│  │  │           │   ├─ AdminController.java
│  │  │           │   └─ UserController.java
│  │  │           ├─ config/
│  │  │           │   └─ GlobalModelAttributes.java
│  │  │           ├─ dto/
│  │  │           │   ├─ AccountDto.java
│  │  │           │   └─ TransactionDto.java
│  │  │           ├─ exception/
│  │  │           │   └─ ResourceNotFoundException.java
│  │  │           ├─ model/
│  │  │           │   ├─ Account.java
│  │  │           │   └─ Transaction.java
│  │  │           ├─ repository/
│  │  │           │   └─ AccountRepository.java
│  │  │           └─ service/
│  │  │               ├─ AccountService.java
│  │  │               └─ AccountServiceImpl.java
│  │  └─ resources/
│  │     ├─ static/
│  │     │   └─ css/
│  │     │       └─ styles.css
│  │     └─ templates/
│  │         ├─ fragments/
│  │         │   └─ header.html
│  │         ├─ admin/
│  │         │   ├─ list-accounts.html
│  │         │   └─ create-account.html
│  │         └─ user/
│  │             ├─ account-transactions.html
│  │             ├─ transfer.html
│  │             ├─ statement.html
│  │             └─ balance.html
├─ pom.xml

 
```

## Passos para Executar o Projeto

### Pré-requisitos

- **Java 8**

### Executar a Aplicação

1. Clone o repositório:

    ```bash
       git clone https://github.com/andresavasconcelos/monolito-bank.git
       cd monolito-bank
    ```

2. Compile:
    ```bash
      mvn clean install
    ```
3. Execute o projeto com Docker. Certifique que o Docker esteja instalado e executando
    ```bash
      cd docker
      docker-compose up --build
   ``` 
   *** Sem o docker ***
    ```bash
      mvn spring-boot:run
    ```

4. Acesse o serviço:

- Serviço: `http://localhost:8080`

### Executar Testes

Para executar os testes unitários (ainda não está disponível):

   ```bash
     mvn clean test
   ```