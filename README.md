# Onboarding Taller Webflux

[Link Taller.](https://bancolombia.sharepoint.com/:w:/r/teams/Nequi-Backend/_layouts/15/Doc.aspx?sourcedoc=%7B0905690A-8754-43F5-8A01-622B7A3DFC6D%7D&file=Onboarding%20-%20Nuevo%20Stack%20Tecnologico.docx&action=default&mobileredirect=true)

## Primeros pasos

Desde la consola nos situamos en la carpeta del proyecto donde esta el archivo docker-compose.yml, y corremos el siguiente comando:
```bash
docker compose up -d
```

Al hacer esto verificamos con el siguiente comando si ya se agregaron los contenedores de localstack, postgres y redis.

```bash
docker ps
```
Al tener esto se debe agregar la cola de SQS y la tabla en Dynamo con los siguientes comandos.
```bash
aws --endpoint-url=http://localhost:4566 dynamodb create-table \
  --table-name Users \
  --attribute-definitions AttributeName=id,AttributeType=N \
  --key-schema AttributeName=id,KeyType=HASH \
  --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5


aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name sqs_q_onboarding
```

## INICIO

Se inicia el proyecto en el IDE de su preferencia, al ser la primera vez el automaticamente crea la tabla en postgres gracias a la libreria de FlyWay Migration.

## SERVICIOS

### SAVE USER
curl --location --request POST 'http://localhost:8080/api/users/1'

### GET USER BY ID
curl --location 'http://localhost:8080/api/users?id=1'

### GET USER BY VALUE
curl --location 'http://localhost:8080/api/users?value=ge'

### GET USERS
curl --location 'http://localhost:8080/api/users'

