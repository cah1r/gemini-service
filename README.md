## Running App
In order to be able to run application locally you need to create `.env` file in root directory and fill following values:

### For Reactive MongoDB which currently needs to be created manually
- DB_PASSWORD
- DB_HOST
- DB_USER

### For Keycloak auth server and its postgres database which are created based on docker-compose

- KEYCLOAK_ADMIN_PASSWORD
- KEYCLOAK_DB_PASSWORD
