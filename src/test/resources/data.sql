CREATE TABLE addresses
(
    id           UUID         NOT NULL,
    apartment_no VARCHAR(6)   NOT NULL,
    building_no  VARCHAR(6)   NOT NULL,
    city         VARCHAR(70)  NOT NULL,
    company_name VARCHAR(255) NOT NULL,
    nip          VARCHAR(16)  NOT NULL,
    street       VARCHAR(150) NOT NULL,
    zip_code     VARCHAR(6)   NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE drivers
(
    id           UUID         NOT NULL,
    first_name   VARCHAR(255) NOT NULL,
    last_name    VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE lines
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    description VARCHAR(50)           NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (description)
);

CREATE TABLE payments
(
    id             UUID           NOT NULL,
    created_tsp    TIMESTAMP,
    payment_status VARCHAR(255)   NOT NULL,
    updated_tsp    TIMESTAMP,
    "value"        DECIMAL(38, 2) NOT NULL,
    ticket_id      UUID,
    PRIMARY KEY (id)
);

CREATE TABLE routes
(
    id                  UUID    NOT NULL,
    is_active           BOOLEAN NOT NULL,
    is_ticket_available BOOLEAN NOT NULL,
    price               DECIMAL(38, 2),
    end_stop_id         BIGINT  NOT NULL,
    start_stop_id       BIGINT  NOT NULL,
    line_id             BIGINT  NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE schedules
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    course_no      INTEGER,
    departure_time TIME,
    stop_id        BIGINT                NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE stops
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    details    VARCHAR(70),
    line_order INTEGER               NOT NULL,
    town       VARCHAR(70)           NOT NULL,
    line_id    BIGINT                NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE tickets
(
    id             UUID           NOT NULL,
    auth_code      VARCHAR(6),
    payment_status VARCHAR(255),
    price          DECIMAL(38, 2) NOT NULL,
    purchase_tsp   TIMESTAMP,
    route_id       UUID           NOT NULL,
    user_id        UUID,
    PRIMARY KEY (id),
    UNIQUE (auth_code)
);

CREATE TABLE users
(
    id           UUID         NOT NULL,
    created_tsp  TIMESTAMP,
    email        VARCHAR(100) NOT NULL,
    first_name   VARCHAR(100) NOT NULL,
    last_name    VARCHAR(100) NOT NULL,
    phone_number INTEGER,
    updated_tsp  TIMESTAMP,
    address_id   UUID,
    role         VARCHAR(255) NOT NULL,
    password     VARCHAR(255),
    PRIMARY KEY (id),
    UNIQUE (email),
    UNIQUE (address_id)
);

CREATE TABLE cars
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    registration VARCHAR(10) NOT NULL UNIQUE,
    capacity     INTEGER     NOT NULL,
    id_number    INTEGER,
    name         VARCHAR(50),
    created_tsp  TIMESTAMP
);

CREATE TABLE tickets_bundles
(
    id               UUID           NOT NULL,
    tickets_quantity INTEGER        NOT NULL,
    price            DECIMAL(38, 2) NOT NULL,
    is_active        BOOLEAN,
    PRIMARY KEY (id)
);

CREATE TABLE tickets_bundle_route
(
    tickets_bundle_id UUID,
    route_id          UUID
);

ALTER TABLE payments
    ADD CONSTRAINT fk_payments_ticket_id FOREIGN KEY (ticket_id) REFERENCES tickets (id);
ALTER TABLE routes
    ADD CONSTRAINT fk_routes_end_stop_id FOREIGN KEY (end_stop_id) REFERENCES stops (id);
ALTER TABLE stops
    ADD CONSTRAINT fk_stops_line_id FOREIGN KEY (line_id) REFERENCES lines (id);
ALTER TABLE users
    ADD CONSTRAINT fk_users_address_id FOREIGN KEY (address_id) REFERENCES addresses (id);
ALTER TABLE payments
    ADD CONSTRAINT fk_payments_user_id FOREIGN KEY (ticket_id) REFERENCES tickets (id);
ALTER TABLE tickets
    ADD CONSTRAINT fk_tickets_route_id FOREIGN KEY (route_id) REFERENCES routes (id);
ALTER TABLE schedules
    ADD CONSTRAINT fk_schedules_stop_id FOREIGN KEY (stop_id) REFERENCES stops (id);
ALTER TABLE routes
    ADD CONSTRAINT fk_routes_start_stop_id FOREIGN KEY (start_stop_id) REFERENCES stops (id);


INSERT INTO users (id, email, first_name, last_name, phone_number, password, role)
VALUES ('cf376c0b-5278-4890-87c9-89168c452ec6', 's.murdoch@example.com', 'Scott', 'Murdoch', 1234567890,
        '$2y$10$TeHPu9toSBH2Hqh01aj0D.lxdzh30kZAYxJTFxh9a0.PsOzs1ezx.', 'USER');

INSERT INTO lines (id, description)
VALUES (100001, 'Adventure Bay - Foggy Bottom');

INSERT INTO stops (id, town, details, line_order, line_id)
VALUES (100002, 'Adventure Bay', 'Paw Patrol Headquarter', 1, 100001);
INSERT INTO stops (id, town, details, line_order, line_id)
VALUES (100003, 'Foggy Bottom', 'Humdingers Cave', 2, 100001);


INSERT INTO lines (id, description)
VALUES (200001, 'Autodromo Nazionale di Monza');

INSERT INTO stops (id, town, details, line_order, line_id)
VALUES (200002, 'Monza', 'Lesmo', 6, 200001);
INSERT INTO stops (id, town, details, line_order, line_id)
VALUES (200003, 'Monza', 'Ascari', 8, 200001);

INSERT INTO routes (id, is_active, is_ticket_available, price, end_stop_id, start_stop_id, line_id)
VALUES ('80814069-0f45-4043-958b-f064bd780656', true, true, 11.69, 200003, 200002, 200001);

INSERT INTO tickets_bundles (id, tickets_quantity, price, is_active)
VALUES ('a3867abb-51b0-476f-ba9f-eaf87c4710d0',11, 111, true);

INSERT INTO tickets_bundle_route (tickets_bundle_id, route_id)
VALUES ('a3867abb-51b0-476f-ba9f-eaf87c4710d0', '80814069-0f45-4043-958b-f064bd780656');