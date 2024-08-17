CREATE DATABASE gemini_db;
CREATE DATABASE keycloak_db;

CREATE USER gemini_admin WITH PASSWORD '';
CREATE USER keycloak_admin WITH PASSWORD '';

GRANT ALL PRIVILEGES ON DATABASE gemini_db TO gemini_admin;
GRANT ALL PRIVILEGES ON DATABASE keycloak_db TO keycloak_admin;

\c keycloak_db;
GRANT ALL PRIVILEGES ON SCHEMA public TO keycloak_admin;

\c gemini_db
CREATE SCHEMA gemini;
ALTER SCHEMA gemini OWNER TO gemini_admin;
GRANT ALL PRIVILEGES ON SCHEMA public TO gemini_admin;