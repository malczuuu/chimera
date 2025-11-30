CREATE
    SEQUENCE seq_integration_logs START 1;

CREATE
    TABLE
        integration_logs(
            id BIGINT PRIMARY KEY DEFAULT nextval('seq_integration_logs'),
            label VARCHAR(64),
            direction VARCHAR(64),
            protocol VARCHAR(64),
            METHOD VARCHAR(64),
            address VARCHAR(2048),
            request_attributes VARCHAR(2048),
            request_body VARCHAR(2048),
            request_timestamp TIMESTAMP WITH TIME ZONE,
            response_body VARCHAR(2048),
            response_status VARCHAR(256),
            created_by VARCHAR(256),
            created_date TIMESTAMP WITH TIME ZONE,
            last_modified_by VARCHAR(256),
            last_modified_date TIMESTAMP WITH TIME ZONE,
            version BIGINT,
            dtype VARCHAR(1) NOT NULL
        );
