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

```
curl --request GET \
  --url http://localhost/payments/scheduled
```

#### Listando pagamentos confirmados

```
curl --request GET \
  --url http://localhost/payments/confirmed
```

#### Como criar um pagamento com recorrência.

```
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

* Está aplicação foi construida de ponta a ponta usando programação **Non Blocking** para requisições ele usa **WebFlux** e para mantar a conexão com banco de dados também non blocking usamos o **R2DBC**.
* Está apliação também utiliza boas práticas como **Clean Architect**, **Domain Driven Design** e **Unit Tests**.

#### Esse projeto é separado em dois microserviços:

* **Payment**: Reponsável por criar os pagamentos e recorrência.
* **Receipt**: Responsável por criar e gerenciar os recibos.
* A comunição entre esses dois microserviços é feita utilizando o padrão **Event Driven** para isso o projeto usa o **RabbitMQ** como broker, criando os **tópicos** e **filas**.
* Este projeto se comunica com esses serviços dentro na mesma **VPC** chamada **payment-network** configura no docker-compose.yml.
* O NGNIX é usado para fazer o proxy reverso e o load balancer, o microserviço **payment** sobe com 3 containers, e o NGNIX é responsável por fazer o balanceamento de carga.

## Como instalar esse projeto:

* Primeiro precisamos construir as imagens consumizadas dos **microserviços** e do **NGNIX**.

```

docker-compose build

```

* Quando a construção terminar, faça o deploy dos containers:

```
docker-compose up -d
```

* Após subir execute o camando:

```
docker-compose ps
```

```
➜  payment git:(main) docker-compose ps
  Name                 Command               State                                                 Ports                                               
-------------------------------------------------------------------------------------------------------------------------------------------------------
nginx       /docker-entrypoint.sh ngin ...   Up      443/tcp, 0.0.0.0:80->80/tcp                                                                       
payment-1   java -jar target/payment.jar     Up      0.0.0.0:58897->8080/tcp                                                                           
payment-2   java -jar target/payment.jar     Up      0.0.0.0:58898->8080/tcp                                                                           
payment-3   java -jar target/payment.jar     Up      0.0.0.0:58896->8080/tcp                                                                           
postgres    docker-entrypoint.sh postgres    Up      0.0.0.0:5432->5432/tcp                                                                            
rabbitmq    docker-entrypoint.sh rabbi ...   Up      15671/tcp, 0.0.0.0:15672->15672/tcp, 15691/tcp, 15692/tcp, 25672/tcp, 4369/tcp, 5671/tcp, 5672/tcp
receipt-1   java -jar target/receipt.jar     Up      0.0.0.0:58899->8080/tcp 
```

#### Depois de subir os containers, os serviços payments e receipts ficaram disponiveís na porta 80

* Acessar o console do RabbitMQ, pela porta 15672
* Acessar o Postgres, pela porta 5432

```
http://localhost:15672
```

![img_1.png](img_1.png)

```
$ psql -h localhost -d payment -U admin
```

![img_2.png](img_2.png)

#### Logs:

```
payment-1   | 2022-02-14 13:34:23.943  INFO 1 --- [tor-tcp-epoll-1] b.c.d.p.i.n.PaymentCreatedNotification   : created payment notified, payment=Payment(id=6e866731-9e66-470e-93f4-8dec3cf1fe9c, date=2022-02-14, amount=7000.0, description=hot dog, createdAt=2022-02-12, finalDate=2023-01-10, frequency=SEMANAL, status=CONFIRMED, destination=222.222.222-40)
payment-1   | 2022-02-14 13:34:23.984  INFO 1 --- [tor-tcp-epoll-1] b.c.d.p.i.n.GenerateReceiptNotification  : sent to generate receipt payment=Payment(id=6e866731-9e66-470e-93f4-8dec3cf1fe9c, date=2022-02-14, amount=7000.0, description=hot dog, createdAt=2022-02-12, finalDate=2023-01-10, frequency=SEMANAL, status=CONFIRMED, destination=222.222.222-40)
payment-1   | 2022-02-14 13:34:23.985  INFO 1 --- [tor-tcp-epoll-1] b.c.d.p.a.service.PaymentService         : payment created with amount=7000.0 date=2022-02-14 destination=222.222.222-40
receipt-1   | 2022-02-14 13:34:24.004  INFO 1 --- [ntContainer#0-1] b.c.d.r.a.listener.ReceiptListener       : receive payment to generate receipt, payment={"id":"6e866731-9e66-470e-93f4-8dec3cf1fe9c","date":"2022-02-14","amount":7000.0,"description":"hot dog","createdAt":"2022-02-12","finalDate":"2023-01-10","frequency":"SEMANAL","status":"CONFIRMED","destination":"222.222.222-40","nextDate":"2022-02-21"}
nginx       | 192.168.96.1 - - [14/Feb/2022:13:34:24 +0000] "POST /payments HTTP/1.1" 201 253 "-" "insomnia/2021.7.2"
receipt-1   | 2022-02-14 13:34:25.135  INFO 1 --- [tor-tcp-epoll-1] b.c.d.r.a.service.ReceiptService         : create receipt from payment, id=6e866731-9e66-470e-93f4-8dec3cf1fe9c
nginx       | 192.168.96.1 - - [14/Feb/2022:13:40:02 +0000] "GET /payments HTTP/1.1" 200 255 "-" "insomnia/2021.7.2"

```