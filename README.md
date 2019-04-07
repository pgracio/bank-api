# Bank API

### How to

Start kafka

```
docker-compose up -d
```

Start account-service

```
./gradlew :account-service:bootRun
```


Start transaction-service

```
./gradlew :transaction-service:bootRun
```

Transaction API

http://localhost:8081/swagger-ui.html#/

Account API

http://localhost:8082/swagger-ui.html#/
