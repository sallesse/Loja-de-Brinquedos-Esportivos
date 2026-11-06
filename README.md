# Loja-de-Brinquedos-Esportivos

# Loja de Brinquedos Esportivos – AC4 (POO – Ibmec)

Aplicação **Spring Boot** para uma empresa de brinquedos esportivos para crianças de até 15 anos.
O projeto expõe uma **API REST** (testada no Postman) e uma **interface Web com Thymeleaf**, com persistência em banco **H2** via **Spring Data JPA (Repository)**, validações com **Bean Validation** e controle de acesso com **Spring Security** (perfis `USER` e `ADMIN`).

## Integrantes

| Nome completo | RA |
|---|---|
| Gabriel Sallesse de Oliveira | 202601007513 |
| VICTOR RYU NOGUCHI | 202601028243 |

**Turma / Curso:** 8001 eng. de computacao
**IDE utilizada:** IntelliJ

## Tecnologias

- Java 17 · Maven · Spring Boot 4.1.2
- Spring Web, Spring Data JPA, Validation, Spring Security, Thymeleaf
- Banco de dados H2
- Spring Boot DevTools
- Postman (testes dos endpoints)

## Configuração do projeto (Spring Initializr)

O projeto foi criado em [start.spring.io](https://start.spring.io) com a configuração abaixo:

| Item | Valor |
|---|---|
| Project | Maven |
| Language | Java |
| Spring Boot | 4.1.2 |
| Group | `br.edu.ibmec` |
| Artifact | `loja-de-brinquedos-esportivos` |
| Packaging | Jar |
| Configuration | Properties |
| Java | 17 |
| Dependências | Spring Security, Spring Boot DevTools, Spring Web, Spring Data JPA, H2 Database, Validation, Thymeleaf |

![Configuração final do Spring Initializr](imagens/01-spring-initializr.jpeg)

<img width="1600" height="1040" alt="WhatsApp Image 2026-11-06 at 11 03 20" src="https://github.com/user-attachments/assets/f2157bf6-6726-457d-88fa-e47c699a88bc" />


## Como executar

1. Clone o repositório e abra o projeto na IDE (ou use o terminal na pasta do projeto).
2. Execute a classe principal da aplicação, ou rode `mvn spring-boot:run`.
3. Acesse `http://localhost:8080` (servidor Tomcat embutido, porta 8080).

## Arquitetura

```
Postman (JSON) ──HTTP──▶ Controller (endpoints) ──▶ Repository (JpaRepository) ──▶ Banco H2 (tabela de brinquedos)
Navegador (Thymeleaf) ──▶ Controller (páginas)  ─────────────┘
                               ▲
                  Spring Security (usuários e perfis)
```

## Tabela do banco de dados

Tabela de brinquedos com as colunas obrigatórias do enunciado:

| Coluna | Descrição | Validação |
|---|---|---|
| Id | Identificador gerado automaticamente | – |
| Nome | Nome do brinquedo | @NotBlank(message ="nome obrigatorio") |
| Tipo | Modalidade (ex.: Futebol) | @NotBlank |
| Classificacao | Idade indicada, em anos (ex.: 4, 6) | @Max(value="15") |
| Tamanho | Ex.: Médio | @Size(max=50) |
| Preco | Valor em reais (ex.: 89.90) | @Positive

## Repository (`JpaRepository`)

O acesso ao banco é feito por uma interface que estende `JpaRepository<T, K>`, onde:

- **T** é a entidade gerenciada (`Brinquedo`);
- **K** é o tipo da chave primária (`Long`).

```java
// [CONFERIR/COLAR AQUI o código real da sua interface]
public interface BrinquedoRepository extends JpaRepository<Brinquedo, Long> {
}
```

O Spring Data JPA gera a implementação dessa interface em tempo de execução. Com isso, o projeto ganha sem escrever SQL os métodos:

| Método | Uso no projeto |
|---|---|
| `save(entidade)` | POST (insere) e PUT (atualiza, quando o id já existe) |
| `findAll()` | GET que lista todos os brinquedos |
| `findById(id)` | GET de um brinquedo pelo id |
| `deleteById(id)` | DELETE pelo id |

## Validação (Bean Validation)

As entradas são validadas com anotações do Bean Validation nos atributos da entidade, acionadas por `@Valid` nos endpoints.
Quando um dado é inválido, a API recusa a requisição em vez de gravar no banco.

<img width="1600" height="1040" alt="WhatsApp Image 2026-11-06 at 11 36 20" src="https://github.com/user-attachments/assets/5ed60af3-8581-404b-a6f5-e7cbfbc71f40" />


## Segurança (Spring Security)

Há uma tela de login e dois perfis de usuário:

| Usuário (e-mail) | Perfil | Permissões observadas nos testes |
|---|---|---|
| `admin@loja.com` | ADMIN | Consulta, altera e cadastra brinquedos (painel com formulário de cadastro) |
| `user@loja.com` | USER | Apenas consulta: o painel não mostra o cadastro e o `PUT` na API é bloqueado com **403 Forbidden** |

Na API, a autenticação é feita no Postman pela aba **Authorization → Basic Auth**.

## Testes dos endpoints no Postman

Todos os testes usam `http://localhost:8080`.

| Operação | Requisição | Resultado |
|---|---|---|
| Create | `POST /brinquedo` | 201 Created |
| Read | `GET /brinquedo/1` | 200 OK |
| Update | `PUT /brinquedo/1` | 200 OK com o perfil ADMIN; 403 com o perfil USER |
| Delete | `DELETE /brinquedo/1` 

### Create (POST) – 201 Created

Envio do JSON com os dados de um novo brinquedo. A API gravou no banco e devolveu o registro com o `id` gerado (`1`).

![POST criando brinquedo](imagens/02-post-criar-201.jpeg)

### Update (PUT) com USER – 403 Forbidden

Tentativa de alterar o brinquedo 1 autenticado como `user@loja.com`. O Spring Security bloqueia a operação, pois o perfil USER não tem permissão de escrita.

![PUT com perfil USER bloqueado](imagens/04-put-user-403.jpeg)

### Update (PUT) com ADMIN – 200 OK

A mesma requisição autenticada como `admin@loja.com` é aceita. O campo `classificacao` mudou de `4` para `6`, confirmando a atualização no banco.

![PUT com perfil ADMIN](imagens/05-put-admin-200.jpeg)

### Read (GET) com USER – 200 OK

O perfil USER consegue consultar o brinquedo pelo id e vê o dado já atualizado (`classificacao` = 6), provando que a alteração foi persistida.

![GET com perfil USER](imagens/06-get-user-200.jpeg)

### Delete – 201 created


<img width="1600" height="1040" alt="WhatsApp Image 2026-11-06 at 11 41 24" src="https://github.com/user-attachments/assets/e2b4bc2b-c5ce-404d-8ed4-5ce1854ce99c" />


## Interface Web (Thymeleaf)

### Landing page (acesso público)

<img width="1600" height="974" alt="WhatsApp Image 2026-11-06 at 11 38 38" src="https://github.com/user-attachments/assets/606f949b-2a25-4d4f-a3ae-0e8337b01b82" />


### Tela de login

![Tela de login](imagens/07-tela-login.jpeg)

### Painel como ADMIN

O ADMIN vê os dados do usuário logado, o formulário **Cadastrar brinquedo** e a tabela com os brinquedos. A tabela mostra o brinquedo criado e atualizado pelo Postman.

![Painel do ADMIN](imagens/03-painel-admin.jpeg)

### Painel como USER

O USER vê apenas a tabela de brinquedos. O formulário de cadastro não aparece para este perfil.

![Painel do USER](imagens/08-painel-user.jpeg)

## Estrutura do projeto

<img width="1600" height="1394" alt="WhatsApp Image 2026-11-06 at 11 37 28" src="https://github.com/user-attachments/assets/43c9ea0b-6f84-438b-96b2-41106c013efd" />

