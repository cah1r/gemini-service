# Project Gemini 
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=cah1r_gemini-service&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=cah1r_gemini-service)

A backend service for booking and purchasing tickets for a transport company. The current project involves a reactive approach using Reactive MongoDB.

## Tech Stack

- **Java 21**
- **PostgreSQL**
- **Gradle**
- **Spring 3**
- **Docker**

## Features

- Secure authentication and authorization using JWT tokens.
- Easy setup and deployment with Docker and Docker Compose.

## Prerequisites

Before you begin, ensure you have met the following requirements:
- Java 21 installed on your machine.
- Docker and Docker Compose installed for running Postgres database.

## Installation

1. **Clone the repository:**

```shell
git clone https://github.com/cahir1/gemini-service.git
cd gemini-service
```
   
2. **Set up environment:**
   
   Create a `.env` file in the root directory and add the necessary environment variables as described below.
- `POSTGRES_USER`
- `POSTGRES_PASSWORD`
- `GEMINI_DB`
- `GEMINI_USER`
- `GEMINI_DB_PASSWORD`
- `JWT_SIGNING_KEY`
   

3. **Run Postgres with Docker Compose**
```shell
docker-compose up -d
```

4. **Run the application:**
```shell
./gradlew bootRun
```

## Usage
After starting the application, it should be accessible at <http://localhost:8080>