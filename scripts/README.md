## Scripts
This folder contains some operational scripts like DB migrations and automated instructions how to run everything locally.
[certstore](certstore) - `truststore` and `keystore` files generated from certificates. Used later for producer and consumer.
Would be nice to improve it later by generating in CI/CD. But this is only workaround due to lack of secured credentials store.  
[local-setup](local-setup) - Contains `docker-compose.yml` and few scripts for compose automation.
[migrations](migrations) - Flyway for DB migration.

### Run DB and Kafka locally
```
cd local-setup
./start_local.sh
```