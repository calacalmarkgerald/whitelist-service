
# Whitelist Service

Problem:

I have 3 environments where my apps are deployed: DEV, STAGE and PROD
I have 2 applications using this setup: app1, app2
In these environments, i keep a list of ips which identifies the list of clients that are allowed to access my apps
A client can have 1 or more ip addresses
An ip address can be associated to 1 or more clients

We want a webservice which can provide RESTful endpoints and serve the following functionality:
- add a client ip (IPv4 only) to the whitelist specific to an environment and app
- provide the list of ips without duplicates; can filtered by environment, app and/or client name
- remove a client ip from the list


## Run Locally

Clone the project

```bash
  git clone https://github.com/calacalmarkgerald/whitelist-service.git
```

Go to the project directory

```bash
  cd whitelist-service
```

Build the source code

```bash
  ./mvnw clean install
```

Run the application

```bash
   java -jar target/whitelist-service-0.0.1-SNAPSHOT.jar
```
Default port is 8080

## Running Tests

To run tests, run the following command

```bash
  ./mvnw test
```


## API Reference

#### All apis requires http basic authentication. Below is the default user and password:
**user:** admin \
**password:** password


#### Get all whitelisted clients

```http
  GET /api/v1/whitelist?clientName=${clientName}&appId=${appId}&environmentId=${environmentId}
```

| Header          | Type     | Description                         |
|:----------------|:---------|:------------------------------------|
| `Accept`        | `string` | application/json or application/xml |
| `Content-Type`  | `string` | application/json or application/xml |
| `Authorization` | `string` | Basic YWRtaW46cGFzc3dvcmQ=          |

| Query Parameter | Type     | Description                  |
|:----------------|:---------|:-----------------------------|
| `clientName`    | `string` | **Optional**. Client name    |
| `appId`         | `int`    | **Optional**. App Id         |
| `environmentId` | `int`    | **Optional**. Environment Id |

#### Get all whitelisted client IPs

```http
  GET api/v1/whitelist/ip?clientName=${clientName}&appId=${appId}&environmentId=${environmentId}
```

| Header          | Type     | Description                         |
|:----------------|:---------|:------------------------------------|
| `Accept`        | `string` | application/json or application/xml |
| `Content-Type`  | `string` | application/json or application/xml |
| `Authorization` | `string` | Basic YWRtaW46cGFzc3dvcmQ=          |

| Query Parameter | Type     | Description                  |
|:----------------|:---------|:-----------------------------|
| `clientName`    | `string` | **Optional**. Client name    |
| `appId`         | `int`    | **Optional**. App Id         |
| `environmentId` | `int`    | **Optional**. Environment Id |

#### Whitelist a client

```http
  POST api/v1/whitelist
```

| Header          | Type     | Description                         |
|:----------------|:---------|:------------------------------------|
| `Accept`        | `string` | application/json or application/xml |
| `Content-Type`  | `string` | application/json or application/xml |
| `Authorization` | `string` | Basic YWRtaW46cGFzc3dvcmQ=          |

| Body Parameter  | Type     | Description                                                    |
|:----------------|:---------|:---------------------------------------------------------------|
| `clientName`    | `string` | **Required**. Client name                                      |
| `clientIp`      | `string` | **Required**. Client name                                      |
| `appId`         | `int`    | **Required**. App Id *(app1 = 1, app2 = 2)*                    |
| `environmentId` | `int`    | **Required**. Environment Id *(DEV = 1, STAGE  = 2, PROD = 3)* |

#### Delete whitelisted client

```http
  DELETE api/v1/whitelist/${id}
```
| Header          | Type     | Description                         |
|:----------------|:---------|:------------------------------------|
| `Accept`        | `string` | application/json or application/xml |
| `Content-Type`  | `string` | application/json or application/xml |
| `Authorization` | `string` | Basic YWRtaW46cGFzc3dvcmQ=          |

| Path Parameter | Type     | Description                |
|:---------------|:---------|:---------------------------|
| `id`           | `string` | **Required**. Whitelist Id |