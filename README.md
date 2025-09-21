# Delivery Microservices

![Java](https://img.shields.io/badge/Java-21+-red?logo=java\&style=flat-square)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?logo=spring\&style=flat-square)
![Apache Kafka](https://img.shields.io/badge/Kafka-Streaming-black?logo=apachekafka\&style=flat-square)
![Spring HTTP Interfaces](https://img.shields.io/badge/Spring%20HTTP%20Interfaces-Declarative%20REST-orange?style=flat-square)
![Resilience4j](https://img.shields.io/badge/Resilience4j-Retry%20%26%20Circuit%20Breaker-yellow?style=flat-square)
![Eureka](https://img.shields.io/badge/Eureka-Service%20Discovery-blue?style=flat-square)

---

## 📦 Descrição

**delivery-microservices** é um sistema de **microserviços para gestão de entregas**, construído com Spring Boot e Spring Cloud.
Ele integra comunicação síncrona (**via REST/HTTP Interfaces**) e assíncrona (**via Kafka**), além de implementar resiliência com **Resilience4j**.

A arquitetura inclui serviços de **entregas**, **entregadores**, **notificações**, além de **gateway** e **service discovery**.

---

## 🧠 Tecnologias e Frameworks

| Ferramenta / Biblioteca           | Propósito                                                       |
| --------------------------------- | --------------------------------------------------------------- |
| ☕ **Java 21+**                    | Linguagem base                                                  |
| 🌱 **Spring Boot / Spring Cloud** | Construção de microsserviços e integração                       |
| 📬 **Apache Kafka**               | Comunicação assíncrona entre serviços                           |
| 🔗 **Spring HTTP Interfaces**     | Cliente REST declarativo para chamadas síncronas entre serviços |
| ⚡ **Resilience4j**                | Retry e Circuit Breaker para resiliência                        |
| 🌐 **Eureka Server & Client**     | Service Discovery                                               |
| 🧪 **JUnit / Rest-Assured**       | Testes unitários e de integração                                |
| 🗄️ **PostgreSQL**                | Banco de dados relacional de cada microsserviço                 |
| 🛠️ **Docker Compose**            | Inicializar Kafka, PostgreSQL, UIs auxiliares, etc.             |

---

## 📁 Estrutura do Projeto

```
delivery-microservices/
├── courier-management/
├── delivery-tracking/
├── gateway/
├── service-registry/
├── docker-compose.yml
└── README.md
```

---

## 🛠️ Como Executar Localmente

### Pré-requisitos

* Docker & Docker Compose
* Java 21 ou superior
* Maven instalado

### Passos:

```bash
git clone <URL_DO_REPO>
cd delivery-microservices
mvn clean install
docker-compose up -d
```

---

## 🔌 Comunicação Entre Microserviços

| Origem             | Destino                 | Canal                    |
| ------------------ | ----------------------- | ------------------------ |
| api-gateway        | Todos os microsserviços | REST                     |
| delivery → courier | `courier-management`    | REST via HTTP Interfaces |
| courier/delivery   | notification-service    | Kafka (eventos JSON)     |

---

## ⚙️ Configurações e Resiliência (Resilience4j)

* As configurações de **Circuit Breaker** e **Retry** estão definidas diretamente no `application.yml` de cada microserviço.
* Exemplo (trecho do **gateway**):

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: delivery-tracking-route
          uri: lb://delivery-tracking
          predicates:
            - Path=/api/v1/deliveries/**
          filters:
            - name: Retry
              args:
                retries: 3
                statuses: INTERNAL_SERVER_ERROR, BAD_GATEWAY
                method: GET,PUT
            - name: CircuitBreaker
              args:
                name: delivery-tracking-route-circuit-breaker
                statusCodes:
                  - 500
                  - 502
                  - 504

resilience4j:
  circuitbreaker:
    configs:
      default:
        slidingWindowType: COUNT_BASED
        slidingWindowSize: 10
        minimumNumberOfCalls: 5
        failureRateThreshold: 50
    instances:
      delivery-tracking-route-circuit-breaker:
        baseConfig: default
```

---

## 📐 Modelo de Domínio - Delivery

### 🧱 Aggregate Root: `Delivery`

| Campo                                       | Tipo             | Descrição                 |
| ------------------------------------------- | ---------------- | ------------------------- |
| `id`                                        | UUID             | Identificador da entrega  |
| `status`                                    | `DeliveryStatus` | Status atual da entrega   |
| `courierId`                                 | UUID             | ID do entregador          |
| `placedAt`, `assignedAt`, `fulfilledAt`     | OffsetDateTime   | Datas chave da entrega    |
| `distanceFee`, `totalCost`, `courierPayout` | BigDecimal       | Cálculos financeiros      |
| `totalItems`                                | Integer          | Total de itens da entrega |

#### Métodos principais

* `addItem(name, quantity)`
* `removeItem(id)`
* `removeItems()`
* `editPreparationDetails(preparationDetails)`
* `place()`
* `pickUp(courierId)`
* `markAsDelivered()`
* `draft()`

---

### 📦 Entidades relacionadas

**Item**

```java
- id: UUID
- name: String
- quantity: Integer
```

**ContactPoint** (Value Object)

```java
- zipCode: String
- street: String
- number: String
- complement: String
- name: String
- phone: String
```

---

### 🧭 Status da Entrega (`DeliveryStatus`)

* `DRAFT`
* `WAITING_FOR_COURIER`
* `IN_TRANSIT`
* `DELIVERED`

---

### 📡 Eventos de Domínio

| Evento                   | Quando ocorre                       |
| ------------------------ | ----------------------------------- |
| `DeliveryPlacedEvent`    | Quando a entrega é registrada       |
| `DeliveryPickedUpEvent`  | Quando o entregador coleta os itens |
| `DeliveryFulfilledEvent` | Quando a entrega é concluída        |

---

## 📞 Contato

* GitHub: [@rodrigo-folha](https://github.com/rodrigo-folha)

---