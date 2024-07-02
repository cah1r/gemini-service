# Project Gemini

A backend service for booking and purchasing tickets for a transport company. The current project involves a reactive approach using Reactive MongoDB.

## Tech Stack

- **Java 21**
- **Reactive MongoDB**
- **Gradle**
- **Spring 3**
- **Docker**
- **OAuth2**

## Features

- Reactive programming model with Reactive MongoDB.
- Secure authentication and authorization using Keycloak.
- Easy setup and deployment with Docker and Docker Compose.

## Prerequisites

Before you begin, ensure you have met the following requirements:
- Java 21 installed on your machine.
- Docker and Docker Compose installed for running Keycloak and its Postgres database.
- MongoDB server running for the reactive database.

## Installation

1. **Clone the repository:**

```shell
git clone https://github.com/cahir1/gemini-service.git
cd gemini-service
```
   
2. **Set up environment:**
   
   Create a `.env` file in the root directory and add the necessary environment variables as described above.

   #### For Reactive MongoDB which currently needs to be created manually
   - `DB_PASSWORD`
   - `DB_HOST`
   - `DB_USER`

   #### For Keycloak auth server and its Postgres database which are created based on Docker Compose
   - `KEYCLOAK_ADMIN_PASSWORD`
   - `KEYCLOAK_DB_PASSWORD`
   

3. **Run Keycloak and Postgres with Docker Compose**
```shell
docker-compose up -d
```

4. **Run the application:**
```shell
./gradlew bootRun
```

## Usage
After starting the application, it should be accessible at <http://localhost:8080>