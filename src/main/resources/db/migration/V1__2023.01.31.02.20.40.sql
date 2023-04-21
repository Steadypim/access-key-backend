CREATE SEQUENCE IF NOT EXISTS access_key_config_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS activated_access_key_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE access_key_config
(
    id                    BIGINT        NOT NULL,
    server_tag            VARCHAR(255)  NOT NULL,
    thread_limit          INTEGER       NOT NULL,
    max_linked_ips        INTEGER       NOT NULL,
    ttl                   numeric(21)   NOT NULL,
    auth_type             VARCHAR(255)  NOT NULL,
    rotate_period         numeric(21)   NOT NULL,
    allowed_destinations  VARCHAR(1024) NOT NULL,
    disabled_destinations VARCHAR(1024) NOT NULL,
    CONSTRAINT pk_access_key_config PRIMARY KEY (id)
);

CREATE TABLE access_key_template
(
    id                      UUID   NOT NULL,
    created_at              TIMESTAMP WITHOUT TIME ZONE,
    access_key_config_id    BIGINT NOT NULL,
    activated_access_key_id BIGINT,
    CONSTRAINT pk_access_key_template PRIMARY KEY (id)
);

CREATE TABLE activated_access_key
(
    id              BIGINT       NOT NULL,
    created_at      TIMESTAMP WITHOUT TIME ZONE,
    access_key_uuid VARCHAR(255) NOT NULL,
    CONSTRAINT pk_activated_access_key PRIMARY KEY (id)
);

ALTER TABLE activated_access_key
    ADD CONSTRAINT uc_activated_access_key_access_key_uuid UNIQUE (access_key_uuid);

ALTER TABLE access_key_template
    ADD CONSTRAINT FK_ACCESS_KEY_TEMPLATE_ON_ACCESS_KEY_CONFIG FOREIGN KEY (access_key_config_id) REFERENCES access_key_config (id);

ALTER TABLE access_key_template
    ADD CONSTRAINT FK_ACCESS_KEY_TEMPLATE_ON_ACTIVATED_ACCESS_KEY FOREIGN KEY (activated_access_key_id) REFERENCES activated_access_key (id);