CREATE DATABASE gemini_db;
CREATE USER gemini_admin WITH PASSWORD '';
GRANT ALL PRIVILEGES ON DATABASE gemini_db TO gemini_admin;
CREATE SCHEMA gemini;
ALTER SCHEMA gemini OWNER TO gemini_admin;
GRANT ALL PRIVILEGES ON SCHEMA public TO gemini_admin;