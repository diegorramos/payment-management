# Payment Management

## Sobre o projeto:

* O Payment Management é tem como objetivo gerenciar pagamentos e suas recorrências.

### Com as seguintes regras:

* Os pagamentos tem como os seguintes dados obrigatórios.

* **Data do Pagamento**, 
* **Valor do pagamento**, 
* **Data da inclusão do pagamento** 
* **Status do pagamento**

### Como criar um pagamento:

```json
curl --request POST \
  --url http://localhost/payments \
  --header 'Content-Type: application/json' \
  --data '{
	"createdAt": "2022-02-12",
	"date": "2022-02-14",
	"amount": 100.0,
	"description": "hot dog",
	"destination": "222.222.222-40"
}'
```

#### Listando pagamentos

```json
curl --request GET \
  --url http://localhost/payments
```

### Os pagamentos são classificados pelo status:

* Agendado
* Confirmado

#### Listando pagamentos agendados

```json
curl --request GET \
  --url http://localhost/payments/scheduled
```

#### Listando pagamentos confirmados

```json
curl --request GET \
  --url http://localhost/payments/confirmed
```

#### Como criar um pagamento com recorrência.

```json
curl --request POST \
  --url http://localhost/payments \
  --header 'Content-Type: application/json' \
  --data '{
	"createdAt": "2022-02-12",
	"date": "2022-02-14",
	"amount": 100.0,
	"description": "hot dog",
	"recurrence": {
		"finalDate": "2023-01-10",
		"frequency" : "SEMANAL"
	},
	"destination": "222.222.222-40"
}'
```
* Após a criação do pagamento é gerado um recibo para o cliente.

#### Como funcionam as recorrências

* Quando a recorrência é **SEMANAL** o valor deve ser maior que **R$50** e da **data final** da recorrência não deve ser maior do que **12 meses**.
* Quando a recorrência é **MENSAL** o valor deve ser maior que **R$100** e da **data final** da recorrência não deve ser maior do que **24 meses**.
* Quando a recorrência é **TRIMESTRAL** o valor deve ser maior que **R$130** e da **data final** da recorrência não deve ser maior do que **36 meses**.
* Quando a recorrência é **SEMESTRAL** o valor deve ser maior que **R$150** e da **data final** da recorrência não deve ser maior do que **42 meses**.

## Stack:

* Docker | [Referência](https://docs.docker.com/get-started/)
* Docker Compose | [Referência](https://docs.docker.com/compose/)
* Spring Boot | [Referência](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
* WebFlux | [Referência](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html)
* Java 11
* NGINX | [Referência](http://nginx.org/en/docs/)
* Postgres | [Referência](https://www.postgresql.org/docs/)
* R2DBC | [Referência](https://docs.spring.io/spring-data/r2dbc/docs/current/reference/html/#reference)
* RabbitMQ | [Refetência](https://www.rabbitmq.com/tutorials/tutorial-five-python.html)

#### Sobre a arquitetura da aplicação:

* Está aplicação foi construida de ponta a ponta usando programação **Non Blocking** para requisições usamos **WebFlux** e para mantar a conexão com banco de dados também non blocking usamos o **R2DBC**.
* Está apliação também utiliza boas práticas como **Clean Architect**, **Domain Driven Design** e **Unit Tests**.

#### Esse projeto é separado em dois microserviços:

* **Payment**: Reponsável por criar os pagamentos e recorrência.
* **Receipt**: Responsável por criar e gerenciar os recibos.
* A comunição entre esses dois microserviços é feita utilizando o padrão **Event Driven** para isso usamos o **RabbitMQ** como broker, criando os **tópicos** e **filas**. 